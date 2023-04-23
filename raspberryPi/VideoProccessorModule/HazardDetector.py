import numpy as np
import matplotlib.pyplot as plt

from VideoProccessorModule.EventDetector import EventDetector
from keras.models import load_model

from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType
from datasets import load_dataset

from ultralyticsplus import YOLO, render_result
from PIL import Image


POTHOLES_DETECTION_MODEL_ID = 'keremberke/yolov8n-pothole-segmentation'

class HazardDetector():

    def __init__(self):
        print("HazardDetector build.")
        self.potholes_model = self.load_potholes_model('my_model.h5')
        self.pole_tree_model = self.load_pole_tree_model('my_model.h5')
        self.road_sign_model = self.load_road_sign_model('traffic_classifier.h5')







    # def detect_hazards_in_frame(self, frame, location):
    #     print(frame)
    #     # Display the frame using Matplotlib
    #     plt.imshow(frame)
    #     plt.show()
    #
    #
    #     # frame = np.resize(frame, (None, 32, 30, 3))
    #     is_pothole = True
    #     # is_pothole = self.potholes_model.predict(frame)
    #     # is_pole_tree = self.pole_tree_model.predict(frame)
    #     # is_road_sign = self.road_sign_model.predict(frame)
    #     size = 1  #TODO - get size
    #     hazard = None
    #
    #     detected_hazards = []
    #
    #     if is_pothole:
    #
    #         pothole_hazard = Hazard(size, location ,HazardType.Pothole)
    #         detected_hazards.append(pothole_hazard)
    #
    #     # if is_pole_tree:
    #     #     pole_tree_hazard = Hazard(size, location ,HazardType.PoleTree)
    #     #     detected_hazards.append(pole_tree_hazard)
    #     #
    #     # if is_road_sign:
    #     #     road_sign_hazard = Hazard(size, location ,HazardType.RoadSign)
    #     #     detected_hazards.append(road_sign_hazard)
    #
    #     return detected_hazards



    def load_potholes_model(self, pothole_path):
        # load potholes model
        model = YOLO(POTHOLES_DETECTION_MODEL_ID)
        return model
        # model = load_model(pothole_path)
        # return model
    def load_pole_tree_model(self, poleTree_path):
        model = load_model(poleTree_path)
        return model
    def load_road_sign_model(self, roadSign_path):
        model = load_model(roadSign_path)
        return model



    def detect_potholes(self, frame , loc):
        detected_hazards = []
        is_pothole = self.potholes_model.predict(frame)
        if is_pothole:
            size = 1  # TODO - get size
            pothole_hazard = Hazard(size, loc ,HazardType.Pothole)
            detected_hazards.append(pothole_hazard)
        return detected_hazards

    def detect_road_signs(self, frame , loc):
        detected_hazards = []
        # plt.imshow(frame)
        # plt.show()

        frame = np.random.rand(352, 640, 3)

        # Reshape the ndarray to the desired shape
        new_shape = (-1, 30, 30, 3)
        frame = np.reshape(frame, new_shape)

        is_road_sign = self.road_sign_model.predict(frame)
        if is_road_sign:
            size = 1  # TODO - get size
            road_sign_hazard = Hazard(size, loc, HazardType.RoadSign)
            detected_hazards.append(road_sign_hazard)

        return detected_hazards
    def detect_hazards_in_frame(self, frame, loc):
        # self.detect_potholes(frame, loc)
        self.detect_road_signs(frame, loc)



        # is_pole_tree = self.pole_tree_model.predict(frame)








        # if is_pole_tree:
        #     pole_tree_hazard = Hazard(size, location ,HazardType.PoleTree)
        #     detected_hazards.append(pole_tree_hazard)
        #


        return detected_hazards

    def predict(self, frame):
        # image = Image.open(image)
        image = Image.fromarray(frame)

        # Show image - for testing
        image.show()

        # perform inference
        results = self.model(image)

        # parse results
        result = results[0]

        boxes = result.boxes.xyxy  # x1, y1, x2, y2
        # Get the size of the tensor
        size = boxes.size()
        print("num of potholes:", size[0])

        scores = result.boxes.conf
        categories = result.boxes.cls
        scores = result.probs  # for classification models
        masks = result.masks  # for segmentation models

        # show results on image - for testing
        render = render_result(model=self.model, image=image, result=result)
        render.show()






