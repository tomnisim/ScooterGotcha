from AlertModule.Alert import Alert
from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector

def finish_ride(road_type, hazards, start_location, destination_location):
    print("summary")

def start_ride():
    camera_controller = CameraController()
    road_detector = RoadDetector()
    hazards_detector = HazardDetector()
    GPS_controller = GPSController()
    alerter = Alert()
    hazards = []


    # ride = Ride()

    x = 3


    ride_flag = True  # the ride is not over yet.
    start_location = GPS_controller.get_GPS_location()
    first_frame = camera_controller.get_next_frame()
    road_type = road_detector.detecte(first_frame)

    while (ride_flag):
        frame = camera_controller.get_next_frame()
        current_hazards = hazards_detector.detect_hazards_in_frame(frame)
        if len(current_hazards) > 0:
            for hazard in current_hazards:
                alerter.alert(hazard)
                hazards.append(hazard)

        x-=1

        if x < 0:
            ride_flag = False


    destination_location = GPS_controller.get_GPS_location()
    finish_ride(road_type, hazards, start_location, destination_location)



if __name__ == '__main__':
    start_ride()


