
import gps
from GPSModule.Location import Location
from Utils.Logger import system_logger

session = None
class GPSController:
    __instance = None

    def __init__(self):
        self.gps_serial = self.init_gps_mock()
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

    def init_gps(self):
        global session
        session = gps.gps("localhost", "2947")
        session.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)

    # Initialize the GPS module
    def init_gps_mock(self):
        return None

    def get_location_mock(self):
        return Location("34.856", "32.989")

    # Get the current location from the GPS module
    def get_location(self):
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










