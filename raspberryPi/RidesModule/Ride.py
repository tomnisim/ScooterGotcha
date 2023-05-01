import datetime

# from AlertModule.Alert import Alert
# from CameraModule.CameraController import CameraController
# from GPSModule.GPSController import GPSController
# from VideoProccessorModule.EventDetector import EventDetector
# from VideoProccessorModule.HazardDetector import HazardDetector
# from VideoProccessorModule.RoadDetector import RoadDetector
from Utils.Logger import ride_logger


class Ride:

    def __init__(self,  hazards, start_loc, destination_loc, start_time, finish_time, junctions):
        ride_logger.info('Ride is build')

        self.hazards = hazards
        # self._events = events
        self.start_location = start_loc
        self.destination_location = destination_loc
        self.start_time = start_time
        self.end_time = finish_time
        self.junctions = junctions

class FinishRideRequest:
    def __init__(self, ride):
        self.hazards = ride.hazards
        self.origin = ride.start_location
        self.destination = ride.destination_location
        self.startTime = ride.start_time.strftime("%Y-%m-%d;%H:%M")

        self.endTime = ride.end_time.strftime("%Y-%m-%d;%H:%M")

        self.junctions = ride.junctions
        self.ridingActions = []
        self.rpSerialNumber = "first"

