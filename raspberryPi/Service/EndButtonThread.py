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
        self.start_button = False
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
        try:
            while True:
                button_state = GPIO.input(self.button_pin)
                if button_state is False:
                    system_logger.info("End Button Press")
                    self.end_button = not self.end_button
                    time.sleep(1)  # Debounce
                    break
        finally:
            GPIO.cleanup(self)

    # def manage_end_button_task_mock2():
    #
    #     def on_key_press(event):
    #         global end_button
    #         print(f'Key {event.name} was pressed')
    #         if event.name == "s":
    #             end_button = not end_button
    #             print("Enter key was pressed")
    #     keyboard.on_press(on_key_press)

    # def manage_end_button2():
    #     def on_key_press(event):
    #         print(f'Key {event.name} was pressed')
    #         if event.name == "f":
    #             Service.start_button = not Service.start_button
    #             print("Enter key was pressed")
    #
    #     keyboard.on_press(on_key_press)
    #
    # def manage_start_button_task_mock():
    #     system_logger.info(f'Start thread manage_start_button_task_mock')
    #     # global start_button
    #     while True:
    #         start_button_mock = input()
    #         while start_button_mock != "f":
    #             a = 5
    #         Service.start_button = not Service.start_button
    #
    # def manage_end_button():
    #     system_logger.info(f'Start thread manage_end_button')
    #
    #     global end_button
    #     while True:
    #         end_button_mock = input(("PUSH TO END\n"))
    #         while end_button_mock != "f":
    #             a = 6
    #         end_button = not end_button
    #
    #
