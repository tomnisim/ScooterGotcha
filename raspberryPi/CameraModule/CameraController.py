from time import sleep


class CameraController:
    __instance = None
    def __init__(self):
        self._camera = RPCAMERA() # TODO: has to connect the RP camera
        print("camera controller build.")
        if CameraController.__instance != None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            CameraController.__instance = self


    @classmethod
    def get_instance(cls):
        if cls.__instance == None:
            cls()
        return cls.__instance





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
