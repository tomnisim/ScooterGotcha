import base64
from io import BytesIO
from PIL import Image
import cv2
import json
import requests

class Hazard():
    def __init__(self, size, location, type, frame):
        self.size=size
        self.location = location
        self.type = type
        self.frame= self.convert_frame_to_byte_array(frame)

    def convert_frame_to_byte_array(self, frame_path):
        # img =Image.fromarray(frame, 'RGB')
        # return list(img.read())
        path =f'{frame_path}'
        with open(path, 'rb') as img:
            return list(img.read())
        # image_bytes = cv2.imencode('.jpg', frame)[1].tobytes()
        # image_bytes = bytearray(frame.tobytes())
        # return image_bytes


