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
        self.index = self.index + 1
        loc1 = Location("34.801738", "31.265175")
        loc2 = Location("34.800288", "31.265134")
        loc3 = Location("34.799254", "31.265184")
        loc4 = Location("34.799241", "31.266148")
        loc5 = Location("34.798181", "31.266117")
        loc6 = Location("34.798159", "31.267327")
        my_list = [loc1, loc2, loc3, loc4, loc5, loc6]
        print("Junction #" + str(self.index) + "Was Collected.")
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


