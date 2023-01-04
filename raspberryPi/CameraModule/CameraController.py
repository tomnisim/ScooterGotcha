from picamera import PiCamera
from time import sleep


class CameraController:
    def __init__(self):
        self.camera = PiCamera()
        print("camera controller build.")

    def init_camera(self):
        self.camera.rotation = 180
        self.camera.resolution = (2592, 1944)
        self.camera.framerate = 15

    def get_next_frame(self):
        print("take a picture")

    def take_still_picture(self):
        self.camera.start_preview()
        sleep(5)
        self.camera.capture('/home/pi/Desktop/image.jpg')
        self.camera.stop_preview()

    def take_5_still_pictures(self):
        self.camera.start_preview()
        for i in range(5):
            sleep(5)
            self.camera.capture('/home/pi/Desktop/image%s.jpg' % i)
        self.camera.stop_preview()

    def record_video(self):
        self.camera.start_preview()
        self.camera.start_recording('/home/pi/Desktop/video.h264')
        sleep(5)
        self.camera.stop_recording()
        self.camera.stop_preview()
