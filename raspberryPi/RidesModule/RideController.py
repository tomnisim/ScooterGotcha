
class RideController():
    def __init__(self):
        self.rides={}
        self.end_curr_ride = False

    def end_ride(self):
        self.end_curr_ride = True

    def start_ride(self, destination):
        start_time = datetime.datetime.now()
        hazards = []
        events = []  # speed changes, sharp turns..
        x = 3
        ride_active = True  # the ride is not over yet.
        start_location = self.GPS_controller.get_GPS_location()
        first_frame = self.camera_controller.get_next_frame()
        road_type = self.road_detector.detect(first_frame)

        # todo : implement event who finish the loop - the ride is over.
        while ride_active:
            frame = self.camera_controller.get_next_frame()
            current_hazards = self.hazards_detector.detect_hazards_in_frame(frame)
            if len(current_hazards) > 0:
                for hazard in current_hazards:
                    self.alerter.alert(hazard)
                    hazards.append(hazard)

            # todo : what data should event hold? detector should return event.
            speed_change_event = self.events_detector.detect_speed_change(frame)
            if speed_change_event:
                events.append(speed_change_event)
            sharp_turn_event = self.events_detector.detect_sharp_turn(frame)
            if sharp_turn_event:
                events.append(sharp_turn_event)

            x -= 1
            if x < 0:
                ride_flag = False

        end_time = datetime.datetime.now()
        destination_location = self.GPS_controller.get_GPS_location()
        self.finish_ride(road_type, hazards, events, start_location, destination_location, start_time, end_time)

    def finish_ride(self, road_type, hazards, events, start_location, destination_location, start_time, end_time):
        # ride = Ride()
        # todo :  send the server user's & ride's data.
        print(
            "summary: " + road_type + hazards + events + start_location + destination_location + start_time + end_time)