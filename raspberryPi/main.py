from datasets import load_dataset
from moviepy.video.io.VideoFileClip import VideoFileClip
from Service.Service import Service

DATASET_PATH = "keremberke/pothole-segmentation"

def test_datset_and_frames():
    clip = VideoFileClip('potholes_video_bs.mp4')
    frames_generator = clip.iter_frames()
    frames = list(clip.iter_frames())[0:3]

    ds = load_dataset(DATASET_PATH, name="full")
    example = ds['train'][0]


if __name__ == '__main__':


    service = Service()
    service.run()









