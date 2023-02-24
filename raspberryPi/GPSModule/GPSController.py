class GPSController:
    __instance = None

    def __init__(self):
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



    def get_GPS_location(self):
        print("got GPS location")
        nx = gpsd.next()
        if nx['class'] == 'TPV':
            latitude = getattr(nx, 'lat', "Unknown")
            longitude = getattr(nx, 'lon', "Unknown")
            print("Your position: lon = " + str(longitude) + ", lat = " + str(latitude))
        return [1000000, 1000000]

