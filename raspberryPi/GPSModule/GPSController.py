# import serial
# import pynmea2
import pynmea2
import requests

from Utils.Logger import system_logger


class GPSController:
    __instance = None

    def __init__(self):
        self.gps_serial = self.init_gps()
        system_logger.info(f'GPS Controller initialization')
        if GPSController.__instance != None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            GPSController.__instance = self

    @classmethod
    def get_instance(cls):
        if cls.__instance == None:
            cls()
        return cls.__instance

    # Initialize the GPS module
    def init_gps(self):
        # ser = serial.Serial('/dev/ttyUSB0', 9600, timeout=1)

        ser = 8
        return ser

    # Get the current location from the GPS module
    def get_location(self):
        return ("34.856", "32.989")
        # location = None
        # while location is None:
        #     try:
        #         data = self.gps_serial.readline().decode('ascii', errors='replace')
        #         if data.startswith('$GPGGA'):
        #             msg = pynmea2.parse(data)
        #             lat = msg.latitude
        #             lng = msg.longitude
        #             location = (lat, lng)
        #     except Exception as e:
        #         print(f"Error: {e}")
        # return location




    #
    # def get_GPS_location(self):
    #     print("got GPS location")
    #     nx = gpsd.next()
    #     if nx['class'] == 'TPV':
    #         latitude = getattr(nx, 'lat', "Unknown")
    #         longitude = getattr(nx, 'lon', "Unknown")
    #         print("Your position: lon = " + str(longitude) + ", lat = " + str(latitude))
    #     return [1000000, 1000000]






