import threading
from datetime import time

import keyboard
from AlertModule.Vocal import Vocal
from CameraModule.CameraController import CameraController
from Config import InitData
from GPSModule.GPSController import GPSController
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.RideController import RideController
import time
from Utils.Logger import system_logger

# TODO: uncomment
# button_pin = 17
# GPIO.setmode(GPIO.BCM)
# GPIO.setup(button_pin, GPIO.IN, pull_up_down=GPIO.PUD_UP)
start_button = False



# def manage_start_button_task_mock2():
#
#     def on_key_press(event):
#         global start_button
#         print(f'Key {event.name} was pressed')
#         if event.name == "s":
#             start_button = not start_button
#             print("Enter key was pressed")
#     keyboard.on_press(on_key_press)



def manage_start_button_task_mock():
    system_logger.info(f'Start thread manage_start_button_task_mock')
    global start_button
    while True:
        start_button_mock = input("PUSH TO START\n")
        while start_button_mock != "s":
            a=5
        start_button = not start_button

def manage_start_button_task():
    system_logger.info(f'Start thread manage_start_button_task')
    try:
        while True:
            button_state = GPIO.input(button_pin)
            if button_state == False:
                system_logger.info("Live Button Press")
                start_button = not start_button
                time.sleep(1)  # Debounce
    finally:
        GPIO.cleanup()

def update_config():
    system_logger.info(f'Start thread update_config')
    while True:

        InitData.InitData()
        time.sleep(3600)

class Service:
    def __init__(self):
        system_logger.info("Init System")
        InitData.InitData()
        update_config_thread = threading.Thread(target=update_config)
        update_config_thread.start()

        # create vocal alerter + GPS, camera, ride controllers
        self._GPS_controller = GPSController.get_instance()
        self._camera_controller = CameraController.get_instance()
        self.alerter = Vocal(InitData.alert_duration)  #TODO: have to make switch case according the configuration file
        self.ride_controller = self.create_ride_controller(self._GPS_controller, self._camera_controller )

        # create thread that manage the live buttun
        start_button_thread = threading.Thread(target=manage_start_button_task_mock) # MOCK : replace to 'manage_start_button_task'
        start_button_thread.start()

    def create_ride_controller(self, gps_controller, camera_controller):
        ride_controller = RideController(self.alerter, gps_controller, camera_controller)
        return ride_controller

    def run(self):
        per = PersistenceController()
        global start_button
        while True:
            if start_button:
                ride = self.ride_controller.execute_ride()
                per.save_ride(ride)

                # MOCK - for the debug video
                while True:
                    pass


