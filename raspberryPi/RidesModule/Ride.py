import datetime

from AlertModule.Alert import Alert
from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector
from datetime import date


class Ride:

    def __init__(self, city, start_time, end_time, origin, destination, hazards):
        print("Ride is build.")
        self._date = date.today()
        self._city = city
        self._start_time = start_time
        self._end_time = end_time
        self._origin = origin
        self._destanation = destination
        self._hazards = hazards

    @property
    def _city(self):

        return self._city

    @_city.setter
    def city(self, value):
        self._city = value
