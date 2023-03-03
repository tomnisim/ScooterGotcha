from VideoProccessorModule.EventDetector import EventDetector


class RoadDetector(EventDetector):
    def __init__(self):
        print("road detector build.")


    def detect(self, frame, sidewalk, roadway):
        #TODO : detect the type of road and increment the field according to the result
        print("figure out the road type")
        detect_sidewalk = True
        if detect_sidewalk:
            return sidewalk+1, roadway
        return sidewalk, roadway+1


    def calculate_percentages(self, sidewalk, roadway):
        sideway_precent = sidewalk / (sidewalk + roadway)
        roadway_precent = roadway / (sidewalk + roadway)
        return sideway_precent, roadway_precent
