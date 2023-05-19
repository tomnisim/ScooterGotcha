import time
from moviepy.editor import VideoFileClip
import numpy as np

from Config.Constants import Constants
from Utils.Logger import system_logger
from picamera2 import Picamera2, Preview
import cv2

i = 0


class CameraController:
    __instance = None

    def __init__(self):
        self.clip = None
        self.frames_generator = None
        self.index = -1
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
        print('init real camera')
        picam2 = Picamera2()
        try:
            
            camera_config = picam2.create_still_configuration(lores={"size": (640, 640)}, display="lores")
            picam2.configure(camera_config)
            return  picam2
        finally:
            picam2.close()
        
        
    def start_camera(self):
        self._camera.start()
    def close_camera(self):
        self._camera.close()
    # Initialize the camera
    def init_camera_mock(self):
        self.clip = VideoFileClip('potholes_video_bs.mp4')
        self.frames_generator = self.clip.iter_frames()
        num_frames = len(list(self.clip.iter_frames()))
        print("num_frames:"+ str(num_frames))

    # Get the next frame from the camera
    def get_next_frame_realtime(self):
        print('get next real frame')
        camera_config = self._camera.create_still_configuration(lores={"size": (640, 640)}, display="lores")
        self._camera.configure(camera_config)
        self._camera.capture_file("test.jpg")
        image_path = 'test.jpg'
        image = cv2.imread(image_path)
        cv2.imshow('Image', image)
        cv2.waitKey(0)
        cv2.destroyAllWindows()
        return image

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
