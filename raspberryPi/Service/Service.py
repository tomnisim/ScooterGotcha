import configparser
import wave
import math
import struct
import threading

from AlertModule.Vocal import Vocal
from AlertModule.VocalCreator import VocalCreator
from CameraModule.CameraController import CameraController
from Config.Config_data import Config_data
from GPSModule.GPSController import GPSController
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.RideController import RideController

live_button = False
def manage_live_button():
    global live_button


class Service:
    def __init__(self):
        config_data = Config_data()
        self._GPS_controller = GPSController.get_instance()
        self._camera_controller = CameraController.get_instance()
        self.alerter = VocalCreator() # #TODO: have to make switch case according the configuration file
        self.ride_controller = self.create_ride_controller(self._GPS_controller, self._camera_controller )

        live_button_thread = threading.Thread(target=manage_live_button)

    def create_ride_controller(self, gps_controller, camera_controller):
        curr_alerter = self.alerter.create_alerter()
        ride_controller = RideController(curr_alerter, gps_controller, camera_controller)
        return ride_controller

    def run(self):
        while True:
            if live_button:
                ride = self.ride_controller.execute_ride()
                PersistenceController.save_ride(ride)

