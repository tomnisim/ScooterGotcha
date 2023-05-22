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
        print(len(frame))

        self.frame= self.convert_frame_to_byte_string(frame)
        print(len(self.frame))

    def convert_frame_to_byte_string(self, frame):
        image_bytes = cv2.imencode('.jpg', frame)[1].tobytes()
        # base64_string = base64.b64encode(image_bytes).decode('utf-8')
        return list(image_bytes)


