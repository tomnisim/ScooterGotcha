import gpsd
from unittest import TestCase



class CameraController_test(TestCase):
    def test_gps_connection(self):
        try:
            gpsd.connect()

            # Get the latest GPS data
            packet = gpsd.get_current()

            # Print the latitude and longitude
            print('Latitude:', packet.lat)
            print('Longitude:', packet.lon)
            self.assertTrue(True)
        except:
            
            self.fail()

    