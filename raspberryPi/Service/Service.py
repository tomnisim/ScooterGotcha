import configparser
import wave
import math
import struct

from AlertModule.Vocal import Vocal
from AlertModule.VocalCreator import VocalCreator
from CameraModule.CameraController import CameraController
from Config.InitData import InitData
from GPSModule.GPSController import GPSController
from RidesModule.RideController import RideController


class Service:
    def __init__(self):

        config_data = InitData()
        self._GPS_controller = GPSController.get_instance()
        self._camera_controller = CameraController.get_instance()


        self.alerter = VocalCreator() # #TODO: have to make switch case according the configuration file
        self.create_ride_controller()



    def create_ride_controller(self, gps_controller, camera_controller):
        curr_alerter = self.alerter.create_alerter()
        ride_controller = RideController(curr_alerter, gps_controller, camera_controller)

