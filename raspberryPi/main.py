
# from Service.Service import Service
import os
# import RPi.GPIO as GPIO
import time
import subprocess

from Service.Service import Service

if __name__ == '__main__':
    # Move the mouse
    # pyautogui.moveRel(100, 100, duration=1.0)
    service = Service()
    service.run()
    # Open the file system
    # subprocess.run(['pcmanfm', '-w'])
    # subprocess.run(['pcmanfm', '-n'])

    #
    #
    # # Set up GPIO
    # GPIO.setmode(GPIO.BCM)
    # GPIO.setup(18, GPIO.OUT)
    #
    # # Activate alert
    # GPIO.output(18, GPIO.HIGH)
    # time.sleep(1)  # Change the time to adjust the length of the alert
    # GPIO.output(18, GPIO.LOW)
    #
    # # Clean up GPIO
    # GPIO.cleanup()




