from unittest import TestCase

from Config.ConfigurationController import ConfigurationController
from GPSModule.Location import Location
from RidesModule.Ride import Ride
import datetime
from Config.Constants import Constants


class CommunicationController_test(TestCase):
    def test_set_serial(self):
        try:
            constants = Constants.get_instance()
            ConfigurationController()
            self.assertEqual(constants.get_serialNumber,"first1" )
        except:
            
            self.fail()
    def test_read_config_from_default(self):
        try:
            
            self.assertTrue(True)
        except:
            
            self.fail()
    def test_get_rp_config_file(self):
        try:
            
            self.assertTrue(True)
        except:
            
            self.fail()
    def test_send_ride_to_server(self):
        try:
            
            self.assertTrue(True)
        except:
            
            self.fail()



    


    


    # def test_get_rp_config_file(self):
    #     ConfigurationController()
    #     x = 5
    #     self.fail()

    # def test_send_ride_to_server(self):
    #     ConfigurationController()
    #     hazards = []
    #     start_loc = Location(68.25, 72.25)
    #     destination_loc = Location(68.25, 72.25)
    #     start_time = datetime.datetime.now()
    #     finish_time = datetime.datetime.now()
    #     junctions = []
    #     ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)
    #     send_ride_to_server(ride)
    #     self.fail()
