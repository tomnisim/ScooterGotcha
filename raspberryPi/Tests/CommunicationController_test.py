from unittest import TestCase

from CameraModule.CameraController import CameraController
from Config.ConfigurationController import ConfigurationController
from GPSModule.Location import Location
from PersistenceModule.CommunicationController import CommunicationController
from RidesModule.Ride import Ride, to_dto
import datetime
from Config.Constants import Constants
from VideoProccessorModule.Hazard import Hazard
from VideoProccessorModule.HazardType import HazardType


class CommunicationController_test(TestCase):
    def setUp(self):
        ConfigurationController()

    def test_set_serial(self):
        try:
            constants = Constants.get_instance()
            # Test set_serial & read_config_from_default
            ConfigurationController()
            serial_number = "first1"
            real_serial_number = constants.get_serialNumber()

            # Test get rp config file after initialize properly default config
            self._get_rp_config_file_test()
            self.assertEqual(real_serial_number,serial_number, f'Serial Number should be {serial_number}' )
        except:
            
            self.fail()

    def _get_rp_config_file_test(self):
        try:
            communication_controller = CommunicationController()
            res = communication_controller.get_rp_config_file()
            self.assertNotEqual(res, False, "Got RP configuration file successfully")
        except:
            self.fail("Communication Problem : get_rp_config_file fail")
    def _create_ride_for_test(self):
        camera_controller = CameraController.get_instance()
        loc = Location(23.43, 45.45)
        frame = None #camera_controller.get_next_frame()
        junctions = [loc]*5
        # hazards = [Hazard(0.5, loc, HazardType.Pothole, frame)]*5
        hazards = []

        t = datetime.datetime.now()
        ride = Ride(hazards, loc, loc, t, t, junctions)
        return ride

    def test_send_ride_to_server(self):
        try:
            configurationController = ConfigurationController()
            ride = self._create_ride_for_test()
            rideDTO = to_dto(ride, configurationController.get_serial())
            communication_controller = CommunicationController()
            res = communication_controller.send_ride_to_server(rideDTO)
            self.assertNotEqual(res, False, "Send ride to server successfully")
            self.assertTrue(True)
        except:
            
            self.fail()

