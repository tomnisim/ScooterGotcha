import keyboard

from Config.Constants import Constants
from Utils.Logger import system_logger
import time
import RPi.GPIO as GPIO


class EndButtonThread:
    def __init__(self):
        self.end_button = False
        self.button_pin = 16
        self.ledPin = 12
        # TODO: uncomment
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(self.ledPin, GPIO.OUT)
        # GPIO.setup(buzzer, GPIO.OUT)
        GPIO.setup(self.button_pin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

    def set_end_button_mock(self, end_button):
        self.end_button = end_button
        print("Update End Button to ", end_button)

    def get_end_button(self):
        return self.end_button

    def task(self):

        if Constants.get_instance().get_MOCK_RUNNING():
            self.manage_end_button_task_mock()
        else:
            # self.manage_end_button_task_mock()
            self.manage_end_button_task()

    def manage_end_button_task_mock(self):
        system_logger.info(f'Start Thread manage_end_button_task_mock')

        while True:
            end_button_mock = input("PRESS (e) TO END\n")
            while end_button_mock != "e":
                pass
            print("END.")
            self.end_button = not self.end_button
            break
            
            
       
    def manage_end_button_task(self):
        system_logger.info(f'Start thread manage_end_button_task - REAL')
        print("PRESS (e) TO END - REAL\n")
        while True:
            button_state = GPIO.input(self.button_pin)
            if button_state == False:
                system_logger.info("End Button Press")
                self.end_button = not self.end_button
                time.sleep(1)  # Debounce
                break


   