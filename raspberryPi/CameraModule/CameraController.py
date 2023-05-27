import time
from moviepy.editor import VideoFileClip
import numpy as np

from Config.Constants import Constants
from Utils.Logger import system_logger
from picamera2 import Picamera2, Preview
import cv2
import io
from PIL import Image
CAPTURED_IMG_FILE = 'RideImages/frame'

class CameraController:
    __instance = None

    def __init__(self):
        self.clip = None
        self.frames_generator = None
        self.index = -1
        self.i=389
        self._camera = self.init_camera()
        system_logger.info(f'Camera Controller initialization')
        if CameraController.__instance is not None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            CameraController.__instance = self

    @classmethod
    def get_instance(cls):
        if cls.__instance is None:
            cls()
        return cls.__instance

    # Initialize the camera
    def init_camera(self):
        if Constants.get_instance().get_MOCK_RUNNING():
            return self.init_camera_mock()
        else:
            return self.init_camera_realtime()
        
    def init_camera_realtime(self):
        system_logger.info('init real camera')
        picam2 = Picamera2()
        camera_config = picam2.create_still_configuration(main={"size": (640, 480)}, lores={"size": (640, 480)}, display="lores")
        picam2.configure(camera_config)
        return  picam2

        
        
    def start_camera(self):
        system_logger.info("start camera")
        self._camera.start()
    def close_camera(self):
        system_logger.info("close camera")
        self._camera.close()
    def init_camera_mock(self):
        self.clip = VideoFileClip('potholes_video_bs.mp4')
        self.frames_generator = self.clip.iter_frames()
        num_frames = len(list(self.clip.iter_frames()))
        print("num_frames:"+ str(num_frames))

    # Get the next frame from the camera
    def get_next_frame_realtime(self):
        image_path = f'{CAPTURED_IMG_FILE}{self.i}.jpg' 
        print(image_path)
        self._camera.capture_file(image_path)
        self.i+=1
        image = cv2.imread(image_path)
        return image, image_path

    def get_next_frame(self):
        if Constants.get_instance().get_MOCK_RUNNING():
            self.frames = list(self.clip.iter_frames(fps=1))[7:]
            return self.get_next_frame_mock()
        else:
            return self.get_next_frame_realtime()

    def get_next_frame_mock(self):
        self.index = self.index + 1
        print("frame #"+str(self.index))
        return self.frames[self.index]
