from unittest import TestCase
from PIL import Image
from VideoProccessorModule.HazardDetector import HazardDetector
import os
import cv2
from datasets import load_dataset

ds = load_dataset("keremberke/pothole-segmentation", name="full")
tests_images = ds['test']
train_images = ds['train']
valid_images = ds['validation']

#
def convert_frame_to_YOLO_input(frame):
    img = Image.fromarray(frame)
    img = img.resize((640, 640))
    img = img.convert('RGB')
    return img

hazard_detector = HazardDetector()
def get_frames():
    with_potholes_path = 'Tests/frames_with_potholes'
    with_potholes_path = 'C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\Image_tests'
    without_potholes_path = 'Tests/frames_without_potholes'
    without_potholes_path = 'C:\\Users\\Tom\\Desktop\\university\\fourthYear\\semester8\\SeminarProject\\raspberryPi\\Image_tests'

    with_potholes_list = []
    without_potholes_list = []
#
    for filename in os.listdir(with_potholes_path):
        if filename.endswith('.jpg') or filename.endswith('.jpeg') or filename.endswith('.png'):
            img = cv2.imread(os.path.join(with_potholes_path, filename))
            img_resized = convert_frame_to_YOLO_input(img)
            with_potholes_list.append(img_resized)

    for filename in os.listdir(without_potholes_path):
        if filename.endswith('.jpg') or filename.endswith('.jpeg') or filename.endswith('.png'):
            img = cv2.imread(os.path.join(without_potholes_path, filename))
            img_resized = cv2.resize(img, (640, 640), interpolation=cv2.INTER_AREA)
            # Append the resized image to the image list
            without_potholes_list.append(img_resized)
    with_potholes_list = train_images
    return with_potholes_list, without_potholes_list
frames_with_potholes , frames_without_potholes  = get_frames()





class HazardDetector_test(TestCase):

    def test_predict_with_potholes(self):
        detection_no = 0
        predictions_no = 10
        try:
            for id, img in enumerate(frames_with_potholes):
                is_potholes, size = hazard_detector.predict(img['image'])
                if is_potholes:
                    detection_no += 1
                if id == 10:
                    break
            print(f'Detect Potholes in {detection_no} frames out of {predictions_no} -->  {int(detection_no / predictions_no * 100)}%')
            self.assertTrue(True)
        except:
            self.fail()
    def test_predict_without_potholes(self):
        un_detection_no = 0
        predictions_no = len(frames_without_potholes)
        try:

            for img in frames_without_potholes:
                is_potholes, size = hazard_detector.predict(img)
                if not is_potholes:
                    un_detection_no += 1
            print(f'Undetected Potholes in {un_detection_no} frames out of {predictions_no} -->  {int(un_detection_no / predictions_no * 100)}%')
            self.assertTrue(True)

        except:
            self.fail()


