
class Facade():
    def __init__(self, camera_controller, alert_controller, gpsController, eventDetector):
        self.camera_controller = camera_controller
        self.alert_controller = alert_controller
        self.gpsController = gpsController
        self.eventDetector = eventDetector
        self.alerter = Alert()  # todo :  have to be specific - chose one of the classes by config.


    def start_ride(self):
        self.rides_controller.start_ride

