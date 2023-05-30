import threading
from datetime import time

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


class Service:
    def __init__(self):
        system_logger.info("Init System")
        self.persistence_controller = PersistenceController()
        self.persistence_controller.config_system()

        # Create Thread For Update Configuration.
        update_configuration_thread = UpdateConfigThread(self.persistence_controller)
        # TODO: uncomment update_config_thread = threading.Thread(target=update_configuration_thread.task())
        # TODO: uncomment update_config_thread.start()

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
            # Connect to the local gpsd
            gpsd.connect()

            # Get gps position
            packet = gpsd.get_current()

            # See the inline docs for GpsResponse for the available data
            print(packet.position())
            system_logger.info('yess')
        except:
            system_logger.error('fuck')
    def run(self):
        self.test_gps()
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


        

