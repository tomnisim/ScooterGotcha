# import picamera
import time
from moviepy.editor import VideoFileClip

from Utils.Logger import system_logger

i=0

class CameraController:
    __instance = None
    def __init__(self):
        # self._camera = self.init_camera() # TODO: has to connect the RP camera
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

    # Get the next frame from the camera
    def get_next_frame(self):
        global i
        frame = None
        try:
            frame = next(self.frames_generator)
        except StopIteration:
            print('No more frames')

        print(f' frame number {i}')
        i+=1
        return frame


        # # Create a bytes buffer for the image data
        # frame_data = bytearray()
        # camera.capture(frame_data, 'jpeg')
        # return frame_data

    # def take_still_picture(self):
    #     self.camera.start_preview()
    #     time.sleep(5)
    #     self.camera.capture('/home/pi/Desktop/image.jpg')
    #     self.camera.stop_preview()
    #
    # def take_5_still_pictures(self):
    #     self.camera.start_preview()
    #     for i in range(5):
    #         time.sleep(5)
    #         self.camera.capture('/home/pi/Desktop/image%s.jpg' % i)
    #     self.camera.stop_preview()
    #
    # def record_video(self):
    #     self.camera.start_preview()
    #     self.camera.start_recording('/home/pi/Desktop/video.h264')
    #     time.sleep(5)
    #     self.camera.stop_recording()
    #     self.camera.stop_preview()
