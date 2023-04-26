import base64
from io import BytesIO
from PIL import Image
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

    def encode_from_PIL(self, image):
        # Create an in-memory binary stream
        stream = BytesIO()

        # Save the image to the stream in PNG format
        image.save(stream, format='PNG')

        # Get the encoded bytes from the stream
        encoded_image = stream.getvalue()

