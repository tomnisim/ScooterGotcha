import configparser
import wave
import math
import struct
import threading
from datetime import time

# this import only works on a raspberry pi
# import RPi.GPIO as GPIO

from AlertModule.Vocal import Vocal
from AlertModule.VocalCreator import VocalCreator
from CameraModule.CameraController import CameraController
from Config.Config_data import Config_data
from GPSModule.GPSController import GPSController
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.RideController import RideController
import time

# Configure GPIO
# this pin depends on the physical pin we will use
button_pin = 17
# GPIO.setmode(GPIO.BCM)
# GPIO.setup(button_pin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

live_button = True # TODO: change to False
def manage_live_button():
    global live_button
    # try:
    #     while True:
    #         button_state = GPIO.input(button_pin)
    #         if button_state == False:
    #             live_button = not live_button
    #             time.sleep(1)  # Debounce
    # finally:
    #     GPIO.cleanup()

def update_config():
    while True:
        Config_data()
        time.sleep(3600)

class Service:
    def __init__(self):
        Config_data()
        update_config_thread = threading.Thread(target=update_config)
        update_config_thread.start()

        # create vocal alerter + GPS, camera, ride controllers
        self._GPS_controller = GPSController.get_instance()
        self._camera_controller = CameraController.get_instance()
        self.alerter = VocalCreator() # #TODO: have to make switch case according the configuration file
        self.ride_controller = self.create_ride_controller(self._GPS_controller, self._camera_controller )

        # create thread that manage the live buttun
        live_button_thread = threading.Thread(target=manage_live_button)
        live_button_thread.start()

    def create_ride_controller(self, gps_controller, camera_controller):
        curr_alerter = self.alerter.create_alerter()
        ride_controller = RideController(curr_alerter, gps_controller, camera_controller)
        return ride_controller

    def run(self):
        per = PersistenceController()
        global live_button
        while True:
            if live_button:
                ride = self.ride_controller.execute_ride()
                per.save_ride(ride)

