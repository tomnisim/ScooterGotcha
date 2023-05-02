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

    def to_dto(self):
        rideDTO = vars(FinishRideRequest(self))
        return rideDTO

class FinishRideRequest:
    def __init__(self, ride):
        self.hazards = self.serialize_hazards(ride.hazards)
        self.origin = self.serialize_location(ride.start_location)
        self.destination = self.serialize_location(ride.destination_location)
        self.start_time = self.serialize_time(ride.start_time)
        self.end_time = self.serialize_time(ride.end_time)
        self.junctions = self.serialize_junctions(ride.junctions)
        self.ridingActions = []
        self.rpSerialNumber = "first"

    def serialize_hazards(self,hazards):
        hazard_lst=[]
        for hazard in hazards:
            hazard_data = {'type': 'hazard.type',
                           'location': self.serialize_location(hazard.location),
                           'size':hazard.size,
                           'frame':hazard.frame}
            hazard_lst.append(hazard_data)
        return hazard_lst

    def serialize_junctions(self, junctions):
        junction_lst = []
        for loc in junctions:
            junction_data = self.serialize_location(loc)
            junction_lst.append(junction_data)
        return junction_lst

    def serialize_location(self, location):
        return {
            'location': {'latitude': location.latitude, 'longitude': location.longitude}
                             }
    def serialize_time(self, time):
        return time.strftime("%Y-%m-%d;%H:%M")
