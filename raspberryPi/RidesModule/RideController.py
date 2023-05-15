import datetime

import keyboard
from Config import ConfigurationController
from CameraModule.CameraController import CameraController
from Config.Constants import Constants
from GPSModule.GPSController import GPSController
from GPSModule.Location import Location
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.Ride import Ride
from Service.EndButtonThread import EndButtonThread
from Utils.Logger import ride_logger, system_logger
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector
from moviepy.editor import VideoFileClip

import threading
import time




junctions = []
hazards = []
frames = []
id = 0
stop = False  # TODO: change to True
end_button = False  # TODO: change to False
stop_flag = False



def collect_junctions_task(gps_controller):
    system_logger.info(f'Start thread collect_junctions_task')
    global junctions
    while not stop:
        loc = gps_controller.get_location()
        junctions.append(loc)
        time_between_junctions = Constants.get_instance().get_time_between_junctions()
        time.sleep(float(time_between_junctions))


def get_frames_task(camera_controller, gps_controller):
    system_logger.info(f'Start thread get_frames_task')
    global frames
    while not stop:
        image = camera_controller.get_next_frame()
        loc = gps_controller.get_location()
        frames.append((loc, image))


def detect_hazards_task(hazard_detector, alerter):
    global stop
    global hazards
    global frames
    global stop_flag

    while not stop:
        if len(frames) > 0:
            loc, frame = frames.pop(0)
            current_hazards = hazard_detector.detect_hazards_in_frame(frame, loc)
            # current_hazards = []
            hazards_detect = False
            if len(current_hazards) > 0:
                alerter.alert()
                hazards += current_hazards
                hazards_detect = True
            # ADD to ride logger
            stop_flag = True
            hazards_detect_msg = 'YES' if hazards_detect else 'NO'
            ride_logger.info(
                f'Frame -> Location : lan = {loc.longitude} , lng  = {loc.latitude} \n Time : {datetime.datetime.now()} \n hazard detected : {hazards_detect_msg}\n')


class RideController:
    def __init__(self, alerter, gps_controller, camera_controller):
        system_logger.info(f'Ride Controller initialization')

        self.alerter = alerter
        self._GPS_controller = gps_controller
        self._camera_controller = camera_controller

        # create road, event, hazard detectors
        self._event_detector = EventDetector()
        self._road_detector = RoadDetector()
        self._hazard_detector = HazardDetector()
        self.end_button_thread = EndButtonThread()

    def execute_ride(self):
        print('execute ride')
        global stop
        global end_button
        global frames
        global hazards
        global stop_flag
        stop = False

        junctions_thread = threading.Thread(target=collect_junctions_task, args=(self._GPS_controller,))
        frames_thread = threading.Thread(target=get_frames_task, args=(self._camera_controller, self._GPS_controller))
        hazards_thread = threading.Thread(target=detect_hazards_task, args=(self._hazard_detector, self.alerter))

        start_time = datetime.datetime.now()
        start_loc = self._GPS_controller.get_location_mock()

        # TODO: uncomment
        frames_thread.start()
        junctions_thread.start()
        hazards_thread.start()

        # create thread that manage end button


        endbutton_thread = threading.Thread(target=self.end_button_thread.task)
        endbutton_thread.start()

        while not self.end_button_thread.get_end_button():
            pass
        print("Finish ride")

        stop = True

        finish_time = datetime.datetime.now()
        destination_loc = self._GPS_controller.get_location_mock()

        ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)
        return ride
