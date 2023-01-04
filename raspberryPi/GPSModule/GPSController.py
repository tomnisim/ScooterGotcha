class GPSController:
    def __init__(self):
        print("GPSController build.")

    def get_GPS_location(self):
        print("got GPS location")
        nx = gpsd.next()
        if nx['class'] == 'TPV':
            latitude = getattr(nx, 'lat', "Unknown")
            longitude = getattr(nx, 'lon', "Unknown")
            print("Your position: lon = " + str(longitude) + ", lat = " + str(latitude))
        return [1000000, 1000000]

