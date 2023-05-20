import numpy as np
import matplotlib.pyplot as plt
import math

from Utils.Logger import system_logger
# from keras.models import load_model
from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType

from ultralyticsplus import YOLO, render_result
from PIL import Image


POTHOLES_DETECTION_MODEL_ID = 'keremberke/yolov8n-pothole-segmentation'

class HazardDetector:

    def __init__(self):
        system_logger.info("HazardDetector build.")
        self.potholes_model = self.load_potholes_model(POTHOLES_DETECTION_MODEL_ID)
        # self.pole_tree_model = self.load_pole_tree_model('my_model.h5')
        # self.road_sign_model = self.load_road_sign_model('traffic_classifier.h5')


    def load_potholes_model(self, pothole_path):
        model = YOLO(pothole_path)
        return model

    def load_pole_tree_model(self, poleTree_path):
        model = load_model(poleTree_path)
        return model
    def load_road_sign_model(self, roadSign_path):
        model = load_model(roadSign_path)
        return model



    def detect_potholes(self, frame , loc):
        detected_hazards = []
        is_pothole, size = self.predict(frame)
        if is_pothole:
            pothole_hazard = Hazard(size, loc, HazardType.Pothole, frame)
            detected_hazards.append(pothole_hazard)
        return detected_hazards

    def detect_road_signs(self, frame , loc):
        detected_hazards = []
        # plt.imshow(frame)
        # plt.show()
        # frame = np.random.rand(352, 640, 3)
        # Reshape the ndarray to the desired shape
        new_shape = (-1, 30, 30, 3)
        frame = np.reshape(frame, new_shape)

        is_road_sign = self.road_sign_model.predict(frame)
        if is_road_sign:
            size = 1  # TODO - get size
            road_sign_hazard = Hazard(size, loc, HazardType.RoadSign, frame)
            detected_hazards.append(road_sign_hazard)

        return detected_hazards
    def detect_hazards_in_frame(self, frame, loc):
        detected_hazards = []
        detected_hazards += self.detect_potholes(frame, loc)
        # detected_hazards += self.detect_road_signs(frame, loc)
        # is_pole_tree = self.pole_tree_model.predict(frame)
        return detected_hazards



    def convert_frame_to_YOLO_input(self, frame):
        # Assume that the frame variable contains the ndarray frame
        img = Image.fromarray(frame)
        # Resize the image to (640, 640)
        img = img.resize((640, 640))
        # Convert the image to mode RGB
        img = img.convert('RGB')
        return img

    def predict(self, frame):
        image = frame
        # image = self.convert_frame_to_YOLO_input(frame)
        # if the frame is ndarray and we want to show it
        # image = Image.open(frame)
        # image = Image.fromarray(frame)

        # Show image - for testing
        # image.show()

        # perform inference
        results = self.potholes_model(image)

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
        render = render_result(model=self.potholes_model, image=image, result=result)
        if num_potholes>0:
            render.show()

        return num_potholes > 0 , hazard_size






