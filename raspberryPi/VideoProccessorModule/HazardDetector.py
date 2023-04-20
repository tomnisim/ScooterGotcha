from VideoProccessorModule.EventDetector import EventDetector
from keras.models import load_model

from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType


class HazardDetector():

    def __init__(self):
        print("HazardDetector build.")
        self.potholes_model = self.load_potholes_model('Utils/my_model.h5')
        self.pole_tree_model = self.load_pole_tree_model('Utils/my_model.h5')
        self.road_sign_model = self.load_road_sign_model('Utils/my_model.h5')


    def detect_hazards_in_frame(self, frame, location):
        print(frame)
        is_pothole = self.potholes_model.predict(frame)
        is_pole_tree = self.pole_tree_model.predict(frame)
        is_road_sign = self.road_sign_model.predict(frame)
        size = 10

        detected_hazards = []

        if is_pothole:

            pothole_hazard = Hazard(size, location, HazardType.Pothole, frame)
            detected_hazards.append(pothole_hazard)

        if is_pole_tree:
            pole_tree_hazard = Hazard(size, location, HazardType.PoleTree, frame)
            detected_hazards.append(pole_tree_hazard)

        if is_road_sign:
            road_sign_hazard = Hazard(size, location, HazardType.RoadSign, frame)
            detected_hazards.append(road_sign_hazard)

        return detected_hazards



    def load_potholes_model(self, pothole_path):
        model = load_model(pothole_path)
        return model
    def load_pole_tree_model(self, poleTree_path):
        model = load_model(poleTree_path)
        return model
    def load_road_sign_model(self, roadSign_path):
        model = load_model(roadSign_path)
        return model