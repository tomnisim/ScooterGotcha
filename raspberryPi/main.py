#
import base64
import datetime
import threading

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
from moviepy.video.io.VideoFileClip import VideoFileClip
from ultralytics.yolo.v8.segment import SegmentationValidator
from ultralyticsplus import YOLO, render_result
# from CameraModule.CameraController import CameraController
import signal
import sys

from AlertModule.Vocal import Vocal
from CameraModule.CameraController import CameraController
from Config.ConfigurationController import ConfigurationController
from Config.Constants import Constants
from GPSModule.GPSController import GPSController
from GPSModule.Location import Location
from RidesModule.Ride import Ride, to_dto
# from Service.Service import Service
from RidesModule.RideController import RideController
from Tests.Integration_test import update_end_button_task
from Utils.Response import Response
from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType
from gpiozero import LED, Button
from signal import pause 
import RPi.GPIO as GPIO



# GPIO.setmode(GPIO.BOARD)
# buzzer =18
# ledPin = 12
# buttonPin=16
# # 
# GPIO.setup(ledPin, GPIO.OUT)
# GPIO.setup(buzzer, GPIO.OUT)
# GPIO.setup(buttonPin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

# print("here")
# while True:
#     bs = GPIO.input(buttonPin)
    
#     if bs==False:
#         GPIO.output(ledPin, GPIO.HIGH)
#         GPIO.output(buzzer, GPIO.HIGH)
#     else:
#         GPIO.output(ledPin, GPIO.LOW)
#         GPIO.output(buzzer, GPIO.LOW)




def close_camera(signal, frame):
    print("Signal received, closing camera, and clean GPIO...")
    CameraController.get_instance().close_camera()
    GPIO.cleanup() 
    sys.exit(0)
    

# Register the signal handlers
signal.signal(signal.SIGINT, close_camera)
signal.signal(signal.SIGTSTP, close_camera)



# POTHOLES_DETECTION_MODEL_ID = 'keremberke/yolov8n-pothole-segmentation'

# model = YOLO(POTHOLES_DETECTION_MODEL_ID)
def run_for_tests_camera():
    picam2 = Picamera2()
    camera_config = picam2.create_still_configuration(lores={"size": (640, 640)}, display="lores")
    picam2.configure(camera_config)
    # picam2.video_configuration.controls.FrameRate = 25.0
    picam2.start()
    return  picam2
def get_next_frame_realtime(picam2):
    # create numpy array to hold frame data
    frame = np.empty((640 * 640 * 3,), dtype=np.uint8)
    # capture frame
    
    picam2.capture_file("test.jpg")
    image_path = 'test.jpg'
    image = cv2.imread(image_path)
    print(image)
    print(image.data)
    cv2.imshow('Image', image)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    picam2.close()
    # image = Image.open("test.jpg") # Replace 'image.jpg' with the path to your image file
    # plt.imshow(image)
    # plt.axis('off')  # Optional: Turn off the axis labels
    # plt.show()

    # reshape frame data into 3D array (height x width x channels)
    return image


def predict(frame):
    img = Image.fromarray(frame)
    # Resize the image to (640, 640)
    img = img.resize((640, 640))
    # Convert the image to mode RGB
    img = img.convert('RGB')
    image = img
    results = model(img)

    # parse results
    result = results[0]

    boxes = result.boxes.xyxy  # x1, y1, x2, y2
    # Get the size of the tensor
    size = boxes.size()
    num_potholes = size[0]
    print("num of potholes:",num_potholes)

    scores = result.boxes.conf
    categories = result.boxes.cls
    scores = result.probs  # for classification models
    masks = result.masks  # for segmentation models

    # show results on image - for testing
    render = render_result(model=model, image=image, result=result)
    
    render.show()

def test_send_image():

    image_path = 'test1.jpg'
    frame = cv2.imread(image_path)

    image_bytes = cv2.imencode('.jpg', frame)[1].tobytes()
    # base64_string = base64.b64encode(image_bytes).decode('utf-8')
    # If using binary data
    print(len(image_bytes))
    payload = {
        'data': list(image_bytes)
    }
    json_data = json.dumps(payload)
    url = 'http://192.168.1.13:5050/send_ride_test'
    headers = {'Content-Type': 'application/json'}
    response = requests.post(url, data=json_data,headers=headers)

    # Step 5: Check the response from the server
    if response.status_code == 200:
        print('Frame sent successfully!')
    else:
        print('Error sending frame. Status code:', response.status_code)

def test_send_ride():
    image_path = 'test1.jpg'
    start_loc = Location(34.801402, 31.265106)
    destination_loc = Location(34.797558, 31.267604)
    frame = cv2.imread(image_path)
    hazard = Hazard(0.5, Location(21.32, 32.32), HazardType.Pothole, frame)
    print('skghevbbkwbvfkgb')
    hazards=[hazard]
    start_time= finish_time = datetime.datetime.now()
    junctions=[Location(21.32, 32.32)]*5
    ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)
    rideDTO = to_dto(ride, 'first')

    url = 'http://192.168.1.13:5050/finish_ride'

    json_data = json.dumps(rideDTO)
    headers = {'Content-Type': 'application/json'}
    try:
        res = requests.post(url, data=json_data, headers=headers)
        response = Response(res.json())
        print(response.value)
        print(response.was_exception)
        print(response.message)

    except Exception as e:
        print('e->>>', e)





def test_execute_ride():
    _GPS_controller = GPSController.get_instance()
    _camera_controller = CameraController.get_instance()
    alerter = Vocal(Constants.get_instance().get_alert_duration())
    ride_controller = RideController(alerter, _GPS_controller, _camera_controller)
    ConfigurationController()
    ride_duration = 5
    ride_controller.end_button_thread.set_end_button_mock(False)
    time_between_junctions = Constants.get_instance().get_time_between_junctions()
    expected_junctions_number = ride_duration / int(time_between_junctions)

    time_between_frames = Constants.get_instance().get_time_between_frames()
    try:
        end_ride_thread = threading.Thread(target=update_end_button_task, args=(ride_duration, ride_controller))
        end_ride_thread.start()
        ride = ride_controller.execute_ride()
        actual_junctions_number = len(ride.get_junctions())
        actual_ride_duration = (ride.get_end_time() - ride.get_start_time()).total_seconds()
    except Exception as e:
        print("Error -> "+e)
if __name__ == '__main__':
    try:
        print("hello")
        # run_for_tests()
        # test_send_image()
        test_send_ride()
        # service = Service()
        # service.run()
        # test_button()
        # print("BI")
        # test_send()
        # run_for_tests()
        service = Service()
        service.run()
    except Exception as e:
        print('e->', e)
        CameraController.get_instance().close_camera()
    finally:
        CameraController.get_instance().close_camera()








