import datetime

from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from GPSModule.Location import Location
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.Ride import Ride
from Utils.Logger import ride_logger
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector


from moviepy.editor import VideoFileClip

import threading
import time


clip = VideoFileClip('potholes_video_bs.mp4')
frames_generator = clip.iter_frames()
frames = list(clip.iter_frames())[0:3]

junctions = []
hazards = []
# frames = []
id = 0
stop = False # TODO: change to True




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
            # loc, frame = frames.pop(0)
            frame = frames.pop(0)
            # loc = Location(loc)
            loc = Location("23.34.23", "43.23.12")
            current_hazards = hazard_detector.detect_hazards_in_frame(frame, loc)
            hazards_detect = False
            if len(current_hazards) > 0:
                alerter.alert()
                hazards += current_hazards
                hazards_detect = True
                # ADD to ride logger
            hazards_detect_msg = 'YES' if hazards_detect else 'NO'
            ride_logger.info(f'frame in location : lan = {loc.lng} , lng  = {loc.lat} \n Time : {datetime.datetime.now()} \n hazard detected : {hazards_detect_msg}')




class RideController():
    def __init__(self, alerter, gps_controller, camera_controller):

        self._GPS_controller = gps_controller
        self._camera_controller = camera_controller

        # create road, event, hazard detectors
        self._event_detector = EventDetector()
        self._road_detector = RoadDetector()
        self._hazard_detector = HazardDetector()
        self.alerter = alerter









    def execute_ride(self):
        global stop
        stop = False

        junctions_thread = threading.Thread(target=collect_junctions_task, args=(self._GPS_controller, ))
        frames_thread = threading.Thread(target=get_frames_task, args=(self._camera_controller, self._GPS_controller))
        hazards_thread = threading.Thread(target=detect_hazrds_task, args=(self._hazard_detector, self.alerter))

        start_time = datetime.datetime.now()
        start_loc = self._GPS_controller.get_location()

        junctions_thread.start()
        frames_thread.start()
        hazards_thread.start()

        # hazards_thread.join()
        # while live_button:
        #     pass

        stop=True

        finish_time=datetime.datetime.now()
        destination_loc =self._GPS_controller.get_location()

        ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)
        return ride





    # def detect_events(self, events, frame):
    #
    #     # todo : what data should event hold? detector should return event.
    #     speed_change_event = self.events_detector.detect_speed_change(frame)
    #     if speed_change_event:
    #         events.append(speed_change_event)
    #     sharp_turn_event = self.events_detector.detect_sharp_turn(frame)
    #     if sharp_turn_event:
    #         events.append(sharp_turn_event)
    #
    #     return events