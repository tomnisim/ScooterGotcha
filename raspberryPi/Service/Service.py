import threading
import time

import keyboard
from AlertModule.Vocal import Vocal
from CameraModule.CameraController import CameraController
from Config import ConfigurationController
from Config.Constants import Constants
from GPSModule.GPSController import GPSController
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.RideController import RideController
from Service.StartButtonThread import StartButtonThread
from Service.UpdateConfigThread import UpdateConfigThread
from Utils.Logger import system_logger
import os
import glob
import gpsd
import RPi.GPIO as GPIO


class Service:
    def __init__(self):
        system_logger.info("Init System")
        self.persistence_controller = PersistenceController()
        self.persistence_controller.config_system()

        # Create Thread For Update Configuration.
        update_configuration_thread = UpdateConfigThread(self.persistence_controller)
        update_config_thread = threading.Thread(target=update_configuration_thread.task())
        update_config_thread.start()

        # create Alerter + GPS, Camera, ride, Persistence controllers:

        self._GPS_controller = GPSController.get_instance()
        self._camera_controller = CameraController.get_instance()
        self.alerter = Vocal(Constants.get_instance().get_alert_duration())  #TODO: have to make switch case according the configuration file
        self.ride_controller = RideController(self.alerter, self._GPS_controller, self._camera_controller)

    def clear_ride_images(self):
        path = '/home/tomnisim/ScooterGotcha/raspberryPi/RideImages/*'
        files = glob.glob(path)
        for file in files:
            try:
                os.remove(file)
            except:
                pass

    def test_gps(self):
        try:
            gpsd.connect()
            packet = gpsd.get_current()
            print(packet.position())
            system_logger.info('GPS WORKS!!!!')
        except:
            system_logger.error('ERROR - GPS dont work')


    def turn_on_led(self, duration):
        ledPin = 18
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(ledPin, GPIO.OUT)
        led_thread = threading.Thread(target=self.led_task, args=(ledPin,duration))
        led_thread.start()

    def led_task(self, led_pin, duration):
        system_logger.info(f'Start thread led task')
        GPIO.output(led_pin, GPIO.HIGH)
        time.sleep(duration)
        GPIO.output(led_pin, GPIO.LOW)
        system_logger.info(f'Led on -> duration: {duration}')
    def run(self):
        self.test_gps()
        self.turn_on_led(2)
        self._camera_controller.start_camera()
        while True:
            
            # Create Thread for Managing 'live button'
            start_button_thread = StartButtonThread()
            start_button_task = threading.Thread(target=start_button_thread.run())
            start_button_task.start()
            if start_button_thread.get_start_button():
                
                ride = self.ride_controller.execute_ride()
                self.persistence_controller.save_ride(ride)
                self.clear_ride_images()


        

