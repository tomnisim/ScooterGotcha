from AlertModule.Alert import Alert
# from gpiozero import Buzzer
# import RPi.GPIO as GPIO
import time
import winsound
# import pygame.mixer

class Vocal(Alert):
    def __init__(self, alert_file_path):
        duration = 10
        power = 10

        super().__init__(duration, power)
        self.alert_file_path = alert_file_path

    def alert(self):
        # Play a simple beep sound
        winsound.Beep(440, 500)

        # Play a vocal alert
        # winsound.PlaySound('path/to/your/alert.wav', winsound.SND_FILENAME)

        # GPIO.setmode(GPIO.BCM)
        # pygame.mixer.init()  # initialize the mixer
        # alert_sound = pygame.mixer.Sound(self.alert_file_path)  # load the sound file


        # alert_sound.play()  # play the sound
        time.sleep(2)  # wait for 2 seconds to avoid repeated alerts

        print("Vocal alert "+str(self.duration)+" "+str(self.power))

