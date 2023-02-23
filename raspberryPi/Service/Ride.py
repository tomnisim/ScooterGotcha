import datetime

from AlertModule.Alert import Alert
from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector
from datetime import date


class Ride:
    def __init__(self, ride_id, rider_email, city, start_time, end_time, origin, destination):
        print("Ride is build.")
        self.ride_id = ride_id
        self.rider_email = rider_email
        self.date = date.today()
        self.city = city
        self.start_time = start_time
        self.end_time = end_time
        self.origin = origin
        self.destanation = destanation


