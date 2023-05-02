from AlertModule.Alert import Alert
# from gpiozero import Buzzer
# import RPi.GPIO as GPIO
import time
import winsound
# import pygame.mixer
from Utils.Logger import system_logger


class Vocal(Alert):
    def __init__(self, duration):

        power = 10

        super().__init__(duration, power)
        system_logger.info(f'Vocal alerter creation ->  duration: {str(self.duration)}, power: {str(self.power)}')

    def alert(self):
        frequency = 40
        system_logger.info(f'Vocal alert -> frequancy: {frequency}, duration: {self.duration*100}')
        # Play a simple beep sound
        winsound.Beep(frequency, self.duration)
        time.sleep(2)  # wait for 2 seconds to avoid repeated alerts

