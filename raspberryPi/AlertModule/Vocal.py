from AlertModule.Alert import Alert
# from gpiozero import Buzzer
# import RPi.GPIO as GPIO
import time
# import winsound
# import pygame.mixer
from Utils.Logger import system_logger
import RPi.GPIO as GPIO


class Vocal(Alert):
    def __init__(self, duration):
        self.ledPin = 18
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(self.ledPin, GPIO.OUT)
        power = 10

        super().__init__(duration, power)
        system_logger.info(f'Vocal alerter creation ->  duration: {str(self.duration)}, power: {str(self.power)}')

    def alert(self):
        frequency = 40
        system_logger.info(f'Vocal alert -> frequancy: {frequency}, duration: {int(self.duration)*100}')
        GPIO.output(self.ledPin, GPIO.HIGH)
        time.sleep(1)
        GPIO.output(self.ledPin, GPIO.LOW)

        # Play a simple beep sound
        # winsound.Beep(frequency, int(self.duration))
        # time.sleep(2)  # wait for 2 seconds to avoid repeated alerts

