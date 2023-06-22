import numpy as np
import matplotlib.pyplot as plt
import math
from Config.Constants import Constants

from Utils.Logger import system_logger
# from keras.models import load_model
from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType

from ultralyticsplus import YOLO, render_result
from PIL import Image
from roboflow import Roboflow
import cv2

POTHOLES_DETECTION_MODEL_ID = 'keremberke/yolov8n-pothole-segmentation'
API_KEY = "1EQ1Efjqv4OtzzFRD7SB"
class HazardDetector:

    def __init__(self):
        system_logger.info("HazardDetector build.")
        self.model_type = int(Constants.get_instance().get_model())

        load_yolo=load_roboflow = False
        try :
            self.potholes_model = self.load_potholes_model(POTHOLES_DETECTION_MODEL_ID)
            load_yolo = True
            self.model_type = 0
        except:
            pass
        try :
            self.roboflow_model = self.load_roboflow_model(API_KEY)
            load_roboflow = True
            self.model_type = 1
        except:
            pass
        if not load_roboflow and not load_yolo:
            system_logger.info('Failed to load potholes detection model')
            system_logger.error('Failed to load potholes detection model')

        self.log_model_type("model type",self.model_type)
        
    def log_model_type(self, msg, model_type):
        model_type_str = "YOLO"
        if model_type == 1:
            model_type_str = "ROBOFLOW"
        system_logger.info(f'{msg} - {model_type_str}')   
        

    def load_roboflow_model(self, api_key):
        rf = Roboflow(api_key=api_key)
        project = rf.workspace().project("gotcha")
        model = project.version(3).model
        return model

   
    def load_potholes_model(self, pothole_path):
        model = YOLO(pothole_path)
        image_path = 'test.jpg'
        frame = cv2.imread(image_path)
        image = self.convert_frame_to_YOLO_input(frame)
        results = model(image)
        return model

    def load_pole_tree_model(self, poleTree_path):
        model = load_model(poleTree_path)
        return model
    def load_road_sign_model(self, roadSign_path):
        model = load_model(roadSign_path)
        return model



    def detect_potholes(self, frame, image_path , loc):
        detected_hazards = []
        is_pothole , size= None, None
        if self.model_type == 0:
            is_pothole, size = self.predict_yolo(frame)
        elif self.model_type==1:
            try:
                is_pothole, size = self.predict_roboflow(image_path)
            except:
                is_pothole, size = self.predict_yolo(frame)
                self.model_type=0
        
        self.log_model_type("Detect With",self.model_type)

        if is_pothole:
            pothole_hazard = Hazard(size, loc, HazardType.Pothole, image_path)
            detected_hazards.append(pothole_hazard)
        return detected_hazards

    def detect_road_signs(self, frame , loc):
        detected_hazards = []

        new_shape = (-1, 30, 30, 3)
        frame = np.reshape(frame, new_shape)

        is_road_sign = self.road_sign_model.predict(frame)
        if is_road_sign:
            size = 1  # TODO - get size
            road_sign_hazard = Hazard(size, loc, HazardType.RoadSign, frame)
            detected_hazards.append(road_sign_hazard)

        return detected_hazards
    def detect_hazards_in_frame(self, frame, image_path, loc):
        detected_hazards = []
        detected_hazards += self.detect_potholes(frame, image_path, loc)

        return detected_hazards



    def convert_frame_to_YOLO_input(self, frame):
        img = Image.fromarray(frame)
        img = img.convert('RGB')
        return img

    def predict_yolo(self, frame):
        image = self.convert_frame_to_YOLO_input(frame)
        results = self.potholes_model(image)
        result = results[0]
        boxes = result.boxes.xyxy  # x1, y1, x2, y2
        size = boxes.size()
        num_potholes = size[0]
        system_logger.info(f"num of potholes:{num_potholes}")
        
        
        if num_potholes>0:
            render = render_result(model=self.potholes_model, image=image, result=result)

            render.show()

        return num_potholes > 0 , 0

    def predict_roboflow(self, image_path):
        answer = self.roboflow_model.predict(image_path, confidence=40, overlap=30)
        result = answer.json()
        
        predictions = result['predictions']
        if len(predictions)>0:
            answer.save(image_path)
            size=predictions[0]['width']
            return True, size
        
        return False, 0







