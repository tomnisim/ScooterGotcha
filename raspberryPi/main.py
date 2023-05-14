
import numpy as np
import matplotlib.pyplot as plt
import math

from Utils.Logger import system_logger
# from keras.models import load_model
from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType

from ultralyticsplus import YOLO, render_result
from PIL import Image
import time
from datasets import load_dataset
from picamera2 import Picamera2, Preview
ds = load_dataset("keremberke/pothole-segmentation", name="full")
tests_images = ds['test']
POTHOLES_DETECTION_MODEL_ID = 'keremberke/yolov8n-pothole-segmentation'
from Service.Service import Service
image = tests_images[0]['image']
def run_for_tests_detection():
    model = YOLO(POTHOLES_DETECTION_MODEL_ID)
    results = model(image)

    # parse results
    result = results[0]

    boxes = result.boxes.xyxy  # x1, y1, x2, y2
    # Get the size of the tensor
    size = boxes.size()
    num_potholes = size[0]
    print("num of potholes:",num_potholes)

    if num_potholes>0:
        xyxy = boxes.numpy()
        x1 = xyxy[0][0]
        y1 = xyxy[0][1]
        x2 = xyxy[0][2]
        y2 = xyxy[0][3]
        # print(x1, y1, x2, y2)

        hazard_size = math.sqrt((x2 - x1)**2 + (y2 - y1)**2)
    else:
        hazard_size = 0


    scores = result.boxes.conf
    categories = result.boxes.cls
    scores = result.probs  # for classification models
    masks = result.masks  # for segmentation models

    # show results on image - for testing
    render = render_result(model=model, image=image, result=result)
    # if num_potholes>0:
    render.show()

    # return num_potholes > 0 , hazard_size


    
    print("HI")
def run_for_tests_camera():
    picam2 = Picamera2()
    camera_config = picam2.create_still_configuration(main={"size": (1920, 1080)}, lores={"size": (640, 480)}, display="lores")
    picam2.configure(camera_config)
    picam2.start_preview(Preview.QTGL)
    picam2.start()
    time.sleep(2)
    # picam2.capture_file("test.jpg")
    camera = picam2





def run_for_tests():
    pass

if __name__ == '__main__':
    run_for_tests()
    run_for_tests_camera()
    # run_for_tests_detection()
    # service = Service()
    # service.run()








