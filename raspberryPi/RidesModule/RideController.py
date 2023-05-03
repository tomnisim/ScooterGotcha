import datetime

import keyboard
from Config import InitData
from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from GPSModule.Location import Location
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.Ride import Ride
from Service import Service
from Utils.Logger import ride_logger, system_logger
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector
from moviepy.editor import VideoFileClip

import threading
import time

# MOCK - get_frames_for_test
clip = VideoFileClip('potholes_video_bs.mp4')
# clip = VideoFileClip('potholes_bs.mp4')
frames = list(clip.iter_frames(fps=1))[7:]
# print("number of frames : ",len(frames))
DATASET_PATH = "keremberke/pothole-segmentation"
# ds = load_dataset(DATASET_PATH, name="full")
# example = ds['train'][0]
# frames = [example['image'], example['image'], example['image']]



junctions = []
hazards = []
id = 0
stop = False # TODO: change to True
end_button = False # TODO: change to False
stop_flag = False
def manage_end_button2():
    def on_key_press(event):
        print(f'Key {event.name} was pressed')
        if event.name == "f":
            Service.start_button = not Service.start_button
            print("Enter key was pressed")

    keyboard.on_press(on_key_press)
def manage_start_button_task_mock():
    system_logger.info(f'Start thread manage_start_button_task_mock')
    # global start_button
    while True:
        start_button_mock = input()
        while start_button_mock!="f":
            a=5
        Service.start_button = not Service.start_button
def manage_end_button():
    system_logger.info(f'Start thread manage_end_button')

    global end_button
    while True:
        end_button_mock = input(("PUSH TO END\n"))
        while end_button_mock != "f":
            a=6
        end_button = not end_button



def collect_junctions_task(gps_controller):
    system_logger.info(f'Start thread collect_junctions_task')
    global junctions
    while not stop:
        loc = gps_controller.get_location_mock()
        junctions.append(loc)
        time.sleep(int(InitData.minimum_distance_to_alert))

def get_frames_task(camera_controller, gps_controller):
    system_logger.info(f'Start thread get_frames_task')

    global frames
    while not stop:
        image = camera_controller.get_next_frame()
        loc = gps_controller.get_location_mock()
        frames.append((loc, image))


def detect_hazards_task(hazard_detector, alerter):
    global stop
    global hazards
    global frames
    global stop_flag

    while not stop:
        if len(frames) > 0:
            # TODO: uncomment
            # loc, frame = frames.pop(0)
            frame = frames.pop(0)
            loc = Location("23.34", "43.23")
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
            ride_logger.info(f'Frame -> Location : lan = {loc.longitude} , lng  = {loc.latitude} \n Time : {datetime.datetime.now()} \n hazard detected : {hazards_detect_msg}\n')




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





    def execute_ride(self):
        print('execute ride')
        global stop
        global end_button
        global frames
        global hazards
        global stop_flag
        stop = False

        junctions_thread = threading.Thread(target=collect_junctions_task, args=(self._GPS_controller, ))
        # frames_thread = threading.Thread(target=get_frames_task, args=(self._camera_controller, self._GPS_controller))
        hazards_thread = threading.Thread(target=detect_hazards_task, args=(self._hazard_detector, self.alerter))

        start_time = datetime.datetime.now()
        start_loc = self._GPS_controller.get_location_mock()



        # TODO: uncomment
        # frames_thread.start()
        junctions_thread.start()
        hazards_thread.start()


        # # create thread that manage the live buttun
        # end_button_thread = threading.Thread(target=manage_start_button_task_mock)
        # end_button_thread.start()

        # while Service.start_button:
        while not stop_flag:
            a=6
        print("Finish ride")



        stop=True

        finish_time=datetime.datetime.now()
        destination_loc =self._GPS_controller.get_location_mock()
        ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)
        return ride

