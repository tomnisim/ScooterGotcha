# import picamera
import time
from moviepy.editor import VideoFileClip
import numpy as np

from Config.Constants import Constants
from Utils.Logger import system_logger

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
        camera = picamera.PiCamera()
        camera.resolution = (640, 480)  # TODO: maybe 640, 640 TODO
        camera.framerate = 30
        time.sleep(2)  # Give the camera some time to warm up
        return camera

    # Initialize the camera
    def init_camera_mock(self):
        self.clip = VideoFileClip('potholes_video_bs.mp4')
        self.frames_generator = self.clip.iter_frames()
        num_frames = len(list(self.clip.iter_frames()))
        print("num_frames:"+ str(num_frames))

    # Get the next frame from the camera
    def get_next_frame_realtime(self):
        # create numpy array to hold frame data
        frame = np.empty((640 * 640 * 3,), dtype=np.uint8)
        # capture frame
        self._camera.capture(frame, format='rgb')

        # reshape frame data into 3D array (height x width x channels)
        frame = frame.reshape((640, 640, 3))
        return frame

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
