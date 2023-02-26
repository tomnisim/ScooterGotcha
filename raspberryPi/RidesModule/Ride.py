import datetime

from AlertModule.Alert import Alert
from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector
from datetime import date


class Ride:

    def __init__(self, city, sideway_precent, roadway_precent, hazards, events, start_location, destination_location, start_time, end_time):
        print("Ride is build.")
        self._city=city
        self._sideway_precent = sideway_precent
        self._roadway_precent = roadway_precent
        self._hazards = hazards
        self._events = events
        self._start_location = start_location
        self._destination_location = destination_location
        self._start_time = start_time
        self._end_time = end_time

