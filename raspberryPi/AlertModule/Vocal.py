import threading
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
        alert_thread = threading.Thread(target=self.alert_task, args=(self.ledPin,self.duration))
        alert_thread.start()

    def alert_task(self, led_pin, duration):
        system_logger.info(f'Start thread alert task')
        flag = True
        while flag:
            GPIO.output(led_pin, GPIO.HIGH)
            time.sleep(1)
            flag = False
        GPIO.output(led_pin, GPIO.LOW)
        system_logger.info(f'Vocal alert -> duration: {duration}')
   
