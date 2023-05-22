import threading
import time
from unittest import TestCase
from AlertModule import Vocal

# from AlertModule.Vocal import Vocal
from CameraModule.CameraController import CameraController
from Config.ConfigurationController import ConfigurationController
from Config.Constants import Constants
from GPSModule.GPSController import GPSController
from RidesModule.RideController import RideController

def update_end_button_task(ride_duration, ride_controller):
    time.sleep(ride_duration)
    print("Update End Button")
    ride_controller.end_button_thread.set_end_button_mock(True)

class RideController_test(TestCase):
    def setUp(self):
        self._GPS_controller = GPSController.get_instance()
        self._camera_controller = CameraController.get_instance()
        self.alerter = Vocal(Constants.get_instance().get_alert_duration())
        self.ride_controller = RideController(self.alerter, self._GPS_controller, self._camera_controller)
        ConfigurationController()

    def test_execute_ride(self):
        for ride_duration in range(6, 7):
            self.ride_controller.end_button_thread.set_end_button_mock(False)
            time_between_junctions = Constants.get_instance().get_time_between_junctions()
            expected_junctions_number = ride_duration / int(time_between_junctions)

            time_between_frames = Constants.get_instance().get_time_between_frames()
            expected_frames_number = ride_duration / int(time_between_frames)
            try:
                end_ride_thread = threading.Thread(target=update_end_button_task, args=(ride_duration, self.ride_controller))
                end_ride_thread.start()
                ride = self.ride_controller.execute_ride()
                actual_junctions_number = len(ride.get_junctions())
                actual_ride_duration = (ride.get_end_time() - ride.get_start_time()).total_seconds()
                self.assertGreaterEqual(1, abs(actual_ride_duration-ride_duration))
                self.assertGreaterEqual(actual_junctions_number, expected_junctions_number)
            except:

                self.fail()

    