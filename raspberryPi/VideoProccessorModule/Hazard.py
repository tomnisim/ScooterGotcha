import base64
import cv2
import json
import requests

class Hazard():
    def __init__(self, size, location, type, frame):
        self.size = size
        self.location = location
        self.type = type
        self.frame = self.encode_image(frame)

    def encode_image(self, frame):
        # Convert the frame to JPEG and encode as base64
        ret, buffer = cv2.imencode('.jpg', frame)
        frame_base64 = base64.b64encode(buffer).decode('utf-8')
        return frame_base64

