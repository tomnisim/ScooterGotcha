import datetime

from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from GPSModule.Location import Location
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.Ride import Ride
from Utils.Logger import ride_logger, system_logger
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector

# from datasets import load_dataset

from moviepy.editor import VideoFileClip

import threading
import time

#get_frames_for_test
clip = VideoFileClip('potholes_video_bs.mp4')
# clip = VideoFileClip('potholes_bs.mp4')
frames = list(clip.iter_frames(fps=1))
print("number of frames : ",len(frames))
DATASET_PATH = "keremberke/pothole-segmentation"
# ds = load_dataset(DATASET_PATH, name="full")
# example = ds['train'][0]
# frames = [example['image'], example['image'], example['image']]



junctions = []
hazards = []
# frames = []
id = 0
stop = False # TODO: change to True

end_button = False # TODO: change to False
def manage_end_button():
    global end_button
    while True:
        end_button_mock = input("Push to end\n")
        print("end_button_mock", end_button_mock)
        while end_button_mock != "f":
            print("not pushed")
            a=6
        end_button = True
        print("pushed -----------------------------------------------------")
        # end_button = not end_button



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
        image = camera_controller.get_next_frame()
        loc = Location("23.34.23", "43.23.12")

        # loc = gps_controller.get_location()
        frames.append((loc, image))


def detect_hazrds_task(hazard_detector, alerter):
    global stop
    global hazards
    global frames

    while not stop:
        if len(frames) > 0:
            # TODO: uncomment
            # loc, frame = frames.pop(0)
            frame = frames.pop(0)
            # loc = Location(loc)
            loc = Location("23.34.23", "43.23.12")
            current_hazards = hazard_detector.detect_hazards_in_frame(frame, loc)
            # current_hazards =[]
            hazards_detect = False
            if len(current_hazards) > 0:
                alerter.alert()
                hazards += current_hazards
                hazards_detect = True
                # ADD to ride logger
            hazards_detect_msg = 'YES' if hazards_detect else 'NO'
            ride_logger.info(f'frame in location : lan = {loc.longitude} , lng  = {loc.latitude} \n Time : {datetime.datetime.now()} \n hazard detected : {hazards_detect_msg}')




class RideController:
    def __init__(self, alerter, gps_controller, camera_controller):
        system_logger.info(f'Ride Controller initialization')


        self._GPS_controller = gps_controller
        self._camera_controller = camera_controller
        self.alerter = alerter

        # create road, event, hazard detectors
        self._event_detector = EventDetector()
        self._road_detector = RoadDetector()
        self._hazard_detector = HazardDetector()



    def execute_ride(self):

        global stop
        global end_button
        stop = False

        junctions_thread = threading.Thread(target=collect_junctions_task, args=(self._GPS_controller, ))
        # frames_thread = threading.Thread(target=get_frames_task, args=(self._camera_controller, self._GPS_controller))
        hazards_thread = threading.Thread(target=detect_hazrds_task, args=(self._hazard_detector, self.alerter))

        start_time = datetime.datetime.now()
        start_loc = self._GPS_controller.get_location()



        # TODO: uncomment
        # junctions_thread.start()
        # frames_thread.start()
        hazards_thread.start()
        # #TODO : remove join
        # hazards_thread.join()
        # create thread that manage the live buttun
        end_button_thread = threading.Thread(target=manage_end_button)
        end_button_thread.start()

        # TODO: uncomment
        while not end_button:
            # print("7")
            a=6



        stop=True

        finish_time=datetime.datetime.now()
        destination_loc =self._GPS_controller.get_location()
        print("Ride finish - Create ride object")
        ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)

        return ride

    # TODO: uncomment
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