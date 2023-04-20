import datetime

from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.Ride import Ride
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector

from moviepy.editor import VideoFileClip
import matplotlib.pyplot as plt

import threading
import time



junctions = []
hazards = []
frames = []
id = 0
stop = True
live_button = False



def collect_junctions_task(gps_controller):
    global id
    global junctions
    while not stop:
        loc = gps_controller.get_location()
        junctions.append((id, loc))
        id+=1
        time.sleep(60)

def get_frames_task(camera_controller, gps_controller):
    global frames
    while not stop:
        frame = camera_controller.get_next_frame()
        loc = gps_controller.get_location()
        frames.append((loc, frame))

def detect_hazrds_task(hazard_detector, alerter):
    global hazards
    global frames

    while not stop:
        if len(frames) > 0:
            loc, frame = frames.pop(0)
            current_hazards = hazard_detector.detect_hazards_in_frame(frame, loc)
            if len(current_hazards) > 0:
                alerter.alert()
                hazards += current_hazards




class RideController():
    def __init__(self, alerter, gps_controller, camera_controller):

        self._GPS_controller = gps_controller
        self._camera_controller = camera_controller
        self._event_detector = EventDetector()
        self._road_detector = RoadDetector()
        self._hazard_detector = HazardDetector()
        self.alerter = alerter

        while True:
            if live_button:
                ride = self.execute_ride()
                PersistenceController.save_ride(ride)






    def execute_ride(self):
        global stop
        global live_button
        stop = False

        junctions_thread = threading.Thread(target=collect_junctions_task, args=(self._GPS_controller))
        frames_thread = threading.Thread(target=get_frames_task, args=(self._camera_controller, self._GPS_controller))
        hazards_thread = threading.Thread(target=detect_hazrds_task, args=(self._hazard_detector, self.alerter))

        start_time = datetime.datetime.now()
        start_loc = self._GPS_controller.get_location()

        junctions_thread.start()
        frames_thread.start()
        hazards_thread.start()

        while live_button:
            pass

        stop=True

        finish_time=datetime.datetime.now()
        destination_loc =self._GPS_controller.get_location()

        ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)
        return ride







    def start_ride(self):
        sideway_counter = roadway_counter = 0
        start_time = datetime.datetime.now()
        hazards = []
        events = []  # speed changes, sharp turns..

        # start_location = self._GPS_controller.get_location()
        start_location = ""

        clip = VideoFileClip('potholes_video_bs.mp4')

        # TODO : implement event who finish the loop - the ride is over.
        for frame in clip.iter_frames():
            # Display the frame using Matplotlib
            plt.imshow(frame)
            plt.show()

            location =""
            # location = self._GPS_controller.get_location()

            self.detect_road_type_in_frame(frame, sideway_counter , roadway_counter)
            sideway_counter, roadway_counter = self.detect_road_type_in_frame(frame, sideway_counter, roadway_counter)
            current_hazards = self._hazard_detector.detect_hazards_in_frame(frame, location)


            for hazard in current_hazards:

                hazards.append(hazard)

            self.detect_events(events, frame)


        #Get data for finish ride
        end_time = datetime.datetime.now()
        # destination_location = self._GPS_controller.get_location()
        destination_location = ""
        city = self._GPS_controller.get_city(destination_location)
        sideway_precentage, roadway_precentage = self._road_detector.calculate_percentages(sideway_counter , roadway_counter)
        self.finish_ride(city, sideway_precentage, roadway_precentage, hazards, events, start_location, destination_location, start_time, end_time)



    def detect_road_type_in_frame(self, frame, sideway_counter, roadway_counter):
        sideway_counter, roadway_counter = self._road_detector.detect(frame, sideway_counter, roadway_counter)
        return sideway_counter, roadway_counter

    def detect_events(self, events, frame):

        # todo : what data should event hold? detector should return event.
        speed_change_event = self.events_detector.detect_speed_change(frame)
        if speed_change_event:
            events.append(speed_change_event)
        sharp_turn_event = self.events_detector.detect_sharp_turn(frame)
        if sharp_turn_event:
            events.append(sharp_turn_event)

        return events