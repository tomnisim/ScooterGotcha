from unittest import TestCase
import time
import libcamera
from picamera2 import Picamera2, Preview
import numpy as np
import io
camera = None
class CameraController_test(TestCase):
    def test_camera_connection(self):
        try:
            self.assertTrue(True)
            picam2 = Picamera2()
            camera_config = picam2.create_still_configuration(main={"size": (1920, 1080)}, lores={"size": (640, 480)}, display="lores")
            picam2.configure(camera_config)
            picam2.start_preview(Preview.QTGL)
            picam2.start()
            time.sleep(2)
            # picam2.capture_file("test.jpg")
            camera = picam2
            # picam2.close()
            self.assertTrue(True)
        except:
            
            self.fail()

    def test_capture_frame(self):
        try:
            self.assertTrue(True)
            # Set the resolution and framerate of the camera
            camera.resolution = (640, 640)
            camera.framerate = 30

            # Wait for the camera to warm up
            time.sleep(2)

            # Create a file-like object to capture the image data
            stream = io.BytesIO()

            # Capture a frame from the camera and store it in the stream
            camera.capture(stream, format='jpeg', use_video_port=True)

            # Rewind the stream to the beginning
            stream.seek(0)

            # Convert the image data to a NumPy array
            image = np.frombuffer(stream.getvalue(), dtype=np.uint8)

            # Reshape the array to the correct image dimensions
            image = image.reshape((640, 640, 3))

            # Release the camera resources
            # camera.close()
        except:
            self.fail()


    