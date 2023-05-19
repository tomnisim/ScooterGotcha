#
import numpy as np
# import matplotlib.pyplot as plt
# import math

import cv2
from matplotlib import pyplot as plt
# from Utils.Logger import system_logger
# # from keras.models import load_model
# from VideoProccessorModule.Hazard import Hazard
# from VideoProccessorModule.HazardType import HazardType
from PIL import Image
from picamera2 import Picamera2, Preview
from moviepy.video.io.VideoFileClip import VideoFileClip
from ultralytics.yolo.v8.segment import SegmentationValidator
from ultralyticsplus import YOLO, render_result

from Service.Service import Service
POTHOLES_DETECTION_MODEL_ID = 'keremberke/yolov8n-pothole-segmentation'

model = YOLO(POTHOLES_DETECTION_MODEL_ID)
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
    # run_for_tests()
    # c = run_for_tests_camera()
    # image = get_next_frame_realtime(c)
    # predict(image)

    # img = Image.fromarray(f)
    # img.open()
    # run_for_tests_detection()
    service = Service()
    service.run()








