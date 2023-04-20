from AlertModule.Alert import Alert
# from gpiozero import Buzzer
# import RPi.GPIO as GPIO
import time
import winsound
# import pygame.mixer

class Vocal(Alert):
    def __init__(self):
        duration = 10
        power = 10

        super().__init__(duration, power)

    def alert(self):
        # Play a simple beep sound
        winsound.Beep(1, 500)
        time.sleep(2)  # wait for 2 seconds to avoid repeated alerts

        print("Vocal alert "+str(self.duration)+" "+str(self.power))

