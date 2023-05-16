#
import numpy as np
# import matplotlib.pyplot as plt
# import math
from matplotlib import pyplot as plt
# from Utils.Logger import system_logger
# # from keras.models import load_model
# from VideoProccessorModule.Hazard import Hazard
# from VideoProccessorModule.HazardType import HazardType
from PIL import Image
from picamera2 import Picamera2, Preview
from moviepy.video.io.VideoFileClip import VideoFileClip
from ultralytics.yolo.v8.segment import SegmentationValidator
from ultralyticsplus import YOLO, render_result
def run_for_tests_camera():
    picam2 = Picamera2()
    camera_config = picam2.create_still_configuration(lores={"size": (640, 640)}, display="lores")
    picam2.configure(camera_config)
    # picam2.video_configuration.controls.FrameRate = 25.0
    picam2.start()
    return  picam2
def get_next_frame_realtime(picam2):
    # create numpy array to hold frame data
    frame = np.empty((640 * 640 * 3,), dtype=np.uint8)
    # capture frame
    picam2.capture_file("test.jpg")
    image = Image.open("test.jpg") # Replace 'image.jpg' with the path to your image file

    # plt.imshow(image)
    # plt.axis('off')  # Optional: Turn off the axis labels
    # plt.show()

    # reshape frame data into 3D array (height x width x channels)
    frame = frame.reshape((640, 640, 3))
    return frame




def run_for_tests():
    rf = Roboflow(api_key="1EQ1Efjqv4OtzzFRD7SB")
    project = rf.workspace().project("gotcha")
    model = project.version(2).model
    v = VideoFileClip('potholes_video_bs.mp4')

    frames_generator =v.iter_frames()
    lst =list(frames_generator)
    print(len(lst))
    # Navigate to the folder
    os.chdir('C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\Image_tests')
    for i in range(1, 187 ,10):
        img = Image.fromarray(lst[i])
        # img = img.resize((640, 640))
        # img = img.convert('RGB')
        # # Open the image file
        # img = Image.open('example.jpg')
        # Save the image to a folder
        img.save(f'test{i}.jpg')

    for id_ in range(1, 187, 10):
        pred= model.predict(f"C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\image_tests\\\\test{id_}.jpg",  overlap=30)
        if len(pred.predictions)>0:
            print(f'found pothole in image {id_}')
        model.predict(f"C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\image_tests\\\\test{id_}.jpg",  overlap=30).save(
            f"pred{id_}.jpg")

if __name__ == '__main__':
    # run_for_tests()
    c = run_for_tests_camera()
    f = get_next_frame_realtime(c)
    img = Image.fromarray(f)
    img.open()
    # run_for_tests_detection()
    # service = Service()
    # service.run()








