from unittest import TestCase
import time
from picamera2 import Picamera2, Preview
import numpy as np
import io
import cv2

# picam2 = None
class CameraController_test(TestCase):
    # def setUp(self):
    #     picam2 = Picamera2()
    def test_camera_connection(self):
        try:
            with Picamera2() as camera:
                camera = Picamera2()
                camera_config = camera.create_still_configuration(lores={"size": (640, 640)}, display="lores")
                camera.configure(camera_config)
                camera.start()
                self.assertTrue(True)
            
        except:
            
            self.fail()

    def test_capture_frame(self):
        try:
            with Picamera2() as picam2:
                picam2 = Picamera2()
                picam2.capture_file("test.jpg")
                image_path = 'test.jpg'
                image = cv2.imread(image_path)
                cv2.imshow('Image', image)
                cv2.waitKey(0)
                cv2.destroyAllWindows()
                picam2.close()
        except:
            self.fail()


    