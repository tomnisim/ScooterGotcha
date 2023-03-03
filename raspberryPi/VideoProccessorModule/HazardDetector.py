from VideoProccessorModule.EventDetector import EventDetector


class HazardDetector(EventDetector):

    def __init__(self):
        print("HazardDetector build.")

    def detect_hazards_in_frame(self, frame):
        print("found hazards..")
        # todo : this method should return list of hazards - have to build every hazard.
        return ["1", "2", "3"]