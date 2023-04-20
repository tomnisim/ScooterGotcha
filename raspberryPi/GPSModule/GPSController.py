# import serial
# import pynmea2
import requests


class GPSController:
    __instance = None

    def __init__(self):
        self.gps_serial = self.init_gps()
        print("GPSController build.")
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
        location = None
        while location is None:
            try:
                data = self.gps_serial.readline().decode('ascii', errors='replace')
                if data.startswith('$GPGGA'):
                    msg = pynmea2.parse(data)
                    lat = msg.latitude
                    lng = msg.longitude
                    location = (lat, lng)
            except Exception as e:
                print(f"Error: {e}")
        return location


    def get_city(self, location):
        # Replace YOUR_API_KEY with your actual API key for the Google Maps Geocoding API
        api_key = "YOUR_API_KEY"
        # Construct the URL for the API request
        url = "https://maps.googleapis.com/maps/api/geocode/json?latlng={},{}&key={}".format(location[0], location[1],
                                                                                             api_key)
        # Send the API request and get the response as JSON
        response = requests.get(url).json()
        # Parse the response and get the name of the city
        for result in response["results"]:
            for component in result["address_components"]:
                if "locality" in component["types"]:
                    return component["long_name"]
        # If the city name couldn't be found, return None
        return None

    #
    # def get_GPS_location(self):
    #     print("got GPS location")
    #     nx = gpsd.next()
    #     if nx['class'] == 'TPV':
    #         latitude = getattr(nx, 'lat', "Unknown")
    #         longitude = getattr(nx, 'lon', "Unknown")
    #         print("Your position: lon = " + str(longitude) + ", lat = " + str(latitude))
    #     return [1000000, 1000000]






