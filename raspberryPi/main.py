#
import base64
import datetime
import threading
from transformers import AutoModel
import numpy as np
# import matplotlib.pyplot as plt
# import math
import requests
import json
import cv2
from matplotlib import pyplot as plt
# from Utils.Logger import system_logger
# # from keras.models import load_model
# from VideoProccessorModule.Hazard import Hazard
# from VideoProccessorModule.HazardType import HazardType
from PIL import Image
# from picamera2 import Picamera2, Preview
from ultralytics.yolo.v8.segment import SegmentationValidator
from ultralyticsplus import YOLO, render_result
# from CameraModule.CameraController import CameraController
import signal
import sys
from Utils.Logger import system_logger

from AlertModule.Vocal import Vocal
from CameraModule.CameraController import CameraController
from Config.ConfigurationController import ConfigurationController
from Config.Constants import Constants
from GPSModule.GPSController import GPSController
from GPSModule.Location import Location
from RidesModule.Ride import Ride, to_dto
from Service.Service import Service
from RidesModule.RideController import RideController
from Tests.Integration_test import update_end_button_task
from Utils.Response import Response
from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType
# from gpiozero import LED, Buzzer, Button
from signal import pause 
import RPi.GPIO as GPIO
from roboflow import Roboflow
import gpsd
import time
GPIO.setmode(GPIO.BOARD)


def close_camera(signal, frame):
    system_logger.info("Signal received, closing camera, and clean GPIO...")
    CameraController.get_instance().close_camera()
    GPIO.cleanup() 
    sys.exit(0)
    

# Register the signal handlers
signal.signal(signal.SIGINT, close_camera)
signal.signal(signal.SIGTSTP, close_camera)


if __name__ == '__main__':
    try:
        print("hello")
        service = Service()
        service.run()
    except Exception as e:
        print('e->', e)
        CameraController.get_instance().close_camera()
    finally:
        GPIO.cleanup() 
        CameraController.get_instance().close_camera()



