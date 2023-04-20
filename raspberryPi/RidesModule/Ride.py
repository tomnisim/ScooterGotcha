import datetime

from AlertModule.Alert import Alert
from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector
from datetime import date


class Ride:

    def __init__(self,  hazards, start_loc, destination_loc, start_time, finish_time, junctions):
        print("Ride is build.")

        self._hazards = hazards
        # self._events = events
        self._start_location = start_loc
        self._destination_location = destination_loc
        self._start_time = start_time
        self._end_time = finish_time
        self.junctions = junctions

