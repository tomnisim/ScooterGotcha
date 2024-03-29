import gps

from Config.Constants import Constants
from GPSModule.Location import Location
from Utils.Logger import system_logger

session = None


class GPSController:
    __instance = None

    def __init__(self):
        self.gps_serial = self.init_gps()
        self.index = -1
        self.x = 0.001
        system_logger.info(f'GPS Controller initialization')
        if GPSController.__instance is not None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            GPSController.__instance = self

    @classmethod
    def get_instance(cls):
        if cls.__instance is None:
            cls()
        return cls.__instance

    def get_location(self):
        if Constants.get_instance().get_MOCK_RUNNING():
            return self.get_location_mock()
        else:
            return self.get_location_mock()
            # return self.get_location_realtime()

    def init_gps(self):
        if Constants.get_instance().get_MOCK_RUNNING():
            return self.init_gps_mock()
        else:
            return self.init_gps_mock()
            # return self.init_gps_realtime()

    def init_gps_realtime(self):
        global session
        session = gps.gps("localhost", "2947")
        session.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)

    # Initialize the GPS module
    def init_gps_mock(self):
        return None

    def get_location_mock(self):
        
        latitude = 34.801402 
        longitude = 31.265106 + self.x
        self.x+=0.001
    
        return Location(latitude, longitude)

        return my_list[self.index % 6]

    # Get the current location from the GPS module
    def get_location_realtime(self):
        global session
        while True:
            try:
                report = session.next()
                if report['class'] == 'TPV':
                    if hasattr(report, 'lat') and hasattr(report, 'lon'):
                        latitude = report.lat
                        longitude = report.lon
                        return Location(latitude, longitude)
            except KeyError:
                pass
            except StopIteration:
                session = None
                return None


