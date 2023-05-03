import datetime

# from AlertModule.Alert import Alert
# from CameraModule.CameraController import CameraController
# from GPSModule.GPSController import GPSController
# from VideoProccessorModule.EventDetector import EventDetector
# from VideoProccessorModule.HazardDetector import HazardDetector
# from VideoProccessorModule.RoadDetector import RoadDetector
from GPSModule.Location import Location
from Utils.Logger import ride_logger
from VideoProccessorModule.Hazard import Hazard


def to_dto(ride):
    rideDTO = vars(FinishRideRequest(ride))
    return rideDTO


def hazards_from_dto(hazardsDTO):
    hazards = []
    for h in hazards:
        size = h['size']
        location = Location(h['location']['latitude'], h['location']['longitude'])
        type = h['type']
        frame=h['frame']
        hazard = Hazard(size, location, type, frame)
        hazards.append(hazard)
    return hazards


def junctions_from_dto(junctionsDTO):
    junctions = []
    for j in junctionsDTO:
        junction = Location(j['location']['latitude'], j['location']['longitude'])
        junctions.append(junction)
    return junctions



def from_dto(rideDTO):
    hazards = hazards_from_dto(rideDTO['hazards'])
    junctions = junctions_from_dto(rideDTO['junctions'])
    start_loc = Location(rideDTO['origin']['location']['latitude'], rideDTO['origin']['location']['longitude'])
    destination_loc = Location(rideDTO['destination']['location']['latitude'], rideDTO['destination']['location']['longitude'])
    start_time = datetime.datetime.strptime(rideDTO['startTime'], "%Y-%m-%d;%H:%M")
    finish_time = datetime.datetime.strptime(rideDTO['endTime'], "%Y-%m-%d;%H:%M")
    return Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions )
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
        self.hazards = self.serialize_hazards(ride.hazards)
        self.origin = self.serialize_location_with_key(ride.start_location)
        self.destination = self.serialize_location_with_key(ride.destination_location)
        self.startTime = self.serialize_time(ride.start_time)
        self.endTime = self.serialize_time(ride.end_time)
        self.junctions = self.serialize_junctions(ride.junctions)
        self.ridingActions = []
        self.rpSerialNumber = "first12345"

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
            junction_data = self.serialize_location_with_key(loc)
            junction_lst.append(junction_data)
        return junction_lst
    def serialize_location_with_key(self, location):
        return {'location': {'latitude': location.latitude, 'longitude': location.longitude}}
    def serialize_location(self, location):
        return {'latitude': location.latitude, 'longitude': location.longitude}

    def serialize_time(self, time):
        return time.strftime("%Y-%m-%d;%H:%M")
