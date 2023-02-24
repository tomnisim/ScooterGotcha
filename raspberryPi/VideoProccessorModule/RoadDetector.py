from VideoProccessorModule.EventDetector import EventDetector


class RoadDetector(EventDetector):
    def __init__(self):
        print("road detector build.")
        self._sidewalk = 0
        self._roadway = 0

    def detect(self, frame):
        #TODO : detect the type of road and increnebt the field according to the result
        print("figure out the road type")

    def calculate_percentages(self):
        sideway_precent = self._sidewalk / (self._sidewalk + self._roadway)
        roadway_precent = self._roadway / (self._sidewalk + self._roadway)
        return sideway_precent, roadway_precent
