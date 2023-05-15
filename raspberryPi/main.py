#
# import numpy as np
# import matplotlib.pyplot as plt
# import math
#
# from Utils.Logger import system_logger
# # from keras.models import load_model
# from VideoProccessorModule.Hazard import Hazard
# from VideoProccessorModule.HazardType import HazardType
#
from moviepy.video.io.VideoFileClip import VideoFileClip
from ultralytics.yolo.v8.segment import SegmentationValidator
from ultralyticsplus import YOLO, render_result
# from PIL import Image
# import time
from datasets import load_dataset
# from picamera2 import Picamera2, Preview
ds = load_dataset("keremberke/pothole-segmentation", name="full")
tests_images = ds['test']
POTHOLES_DETECTION_MODEL_ID = 'keremberke/yolov8n-pothole-segmentation'
# from Service.Service import Service
# image = tests_images[0]['image']
# def run_for_tests_detection():
#     model = YOLO(POTHOLES_DETECTION_MODEL_ID)
#     results = model(image)
#
#     # parse results
#     result = results[0]
#
#     boxes = result.boxes.xyxy  # x1, y1, x2, y2
#     # Get the size of the tensor
#     size = boxes.size()
#     num_potholes = size[0]
#     print("num of potholes:",num_potholes)
#
#     if num_potholes>0:
#         xyxy = boxes.numpy()
#         x1 = xyxy[0][0]
#         y1 = xyxy[0][1]
#         x2 = xyxy[0][2]
#         y2 = xyxy[0][3]
#         # print(x1, y1, x2, y2)
#
#         hazard_size = math.sqrt((x2 - x1)**2 + (y2 - y1)**2)
#     else:
#         hazard_size = 0
#
#
#     scores = result.boxes.conf
#     categories = result.boxes.cls
#     scores = result.probs  # for classification models
#     masks = result.masks  # for segmentation models
#
#     # show results on image - for testing
#     render = render_result(model=model, image=image, result=result)
#     # if num_potholes>0:
#     render.show()
#
#     # return num_potholes > 0 , hazard_size
#
#
#
#     print("HI")
# def run_for_tests_camera():
#     picam2 = Picamera2()
#     camera_config = picam2.create_still_configuration(main={"size": (1920, 1080)}, lores={"size": (640, 480)}, display="lores")
#     picam2.configure(camera_config)
#     picam2.start_preview(Preview.QTGL)
#     picam2.start()
#     time.sleep(2)
#     # picam2.capture_file("test.jpg")
#     camera = picam2
#

from PIL import Image
import os


from roboflow import Roboflow




def run_for_tests():
    rf = Roboflow(api_key="1EQ1Efjqv4OtzzFRD7SB")
    project = rf.workspace().project("gotcha")
    model = project.version(2).model
    v = VideoFileClip('potholes_video_bs.mp4')

    frames_generator =v.iter_frames()
    lst =list(frames_generator)
    print(len(lst))
    # Navigate to the folder
    os.chdir('C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\Image_tests')
    for i in range(1, 187 ,10):
        img = Image.fromarray(lst[i])
        # img = img.resize((640, 640))
        # img = img.convert('RGB')
        # # Open the image file
        # img = Image.open('example.jpg')
        # Save the image to a folder
        img.save(f'test{i}.jpg')

    for id_ in range(1, 187, 10):
        pred= model.predict(f"C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\image_tests\\\\test{id_}.jpg",  overlap=30)
        if len(pred.predictions)>0:
            print(f'found pothole in image {id_}')
        model.predict(f"C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\image_tests\\\\test{id_}.jpg",  overlap=30).save(
            f"pred{id_}.jpg")

if __name__ == '__main__':
    run_for_tests()
    # run_for_tests_camera()
    # run_for_tests_detection()
    # service = Service()
    # service.run()








