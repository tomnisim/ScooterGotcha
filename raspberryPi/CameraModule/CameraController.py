# import picamera
import time
from moviepy.editor import VideoFileClip
import numpy as np

from Utils.Logger import system_logger

i=0

class CameraController:
    __instance = None
    def __init__(self):
        self._camera = self.init_camera_mock() # TODO: change to init_camera
        system_logger.info(f'Camera Controller initialization')
        self.clip = VideoFileClip('potholes_video_bs.mp4')
        self.frames_generator = self.clip.iter_frames()
        num_frames = len(list(self.clip.iter_frames()))
        if CameraController.__instance != None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            CameraController.__instance = self


    @classmethod
    def get_instance(cls):
        if cls.__instance == None:
            cls()
        return cls.__instance

    # Initialize the camera
    def init_camera(self):
        camera = picamera.PiCamera()
        camera.resolution = (640, 480) # TODO: maybe 640, 640 TODO
        camera.framerate = 30
        time.sleep(2)  # Give the camera some time to warm up
        return camera
    # Initialize the camera
    def init_camera_mock(self):
        return None


    # Get the next frame from the camera
    def get_next_frame(self):
        # create numpy array to hold frame data
        frame = np.empty((640 * 640 * 3,), dtype=np.uint8)
        # capture frame
        self._camera.capture(frame, format='rgb')

        # reshape frame data into 3D array (height x width x channels)
        frame = frame.reshape((640, 640, 3))
        return frame

