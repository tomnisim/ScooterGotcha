from AlertModule.Alert import Alert
from gpiozero import Buzzer
import RPi.GPIO as GPIO
import time
import pygame.mixer

class Vocal(Alert):
    def __init__(self, alert_file_path):

        super().__init__()
        self.alert_file_path = alert_file_path

    def alert(self):


        GPIO.setmode(GPIO.BCM)
        pygame.mixer.init()  # initialize the mixer
        alert_sound = pygame.mixer.Sound(self.alert_file_path)  # load the sound file


        alert_sound.play()  # play the sound
        time.sleep(2)  # wait for 2 seconds to avoid repeated alerts

        print("Vocal alert "+self.duration+" "+self.power)

