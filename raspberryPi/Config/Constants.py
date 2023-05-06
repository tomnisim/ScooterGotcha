
class Constants:
    __instance = None

    def __init__(self):
        self.MOCK_RUNNING = True
        self.serialNumber = ""
        self.number_of_coordinates = ""
        self.SERVER_ADDRESS = ""
        self.minimum_distance_to_alert = ""
        self.alert_duration = 0
        self.alert_type = ""
        self.number_of_routes = ""
        self.time_between_junctions = ""
        self.camera_type = ""

        if Constants.__instance is not None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            Constants.__instance = self

    @classmethod
    def get_instance(cls):
        if cls.__instance is None:
            cls()
        return cls.__instance

    def get_serialNumber(self):
        return self.serialNumber

    def set_serial_number(self, serial):
        self.serialNumber = serial

    def get_SERVER_ADDRESS(self):
        return self.SERVER_ADDRESS

    def set_SERVER_ADDESS(self, SERVER_ADDRESS):
        self.SERVER_ADDRESS = SERVER_ADDRESS

    def set_number_of_coordinates(self, number_of_coordinates):
        self.number_of_coordinates = number_of_coordinates

    def set_minimum_distance_to_alert(self, minimum_distance_to_alert):
        self.minimum_distance_to_alert = minimum_distance_to_alert

    def get_minimum_distance_to_alert(self):
        return self.minimum_distance_to_alert

    def get_alert_duration(self):
        return self.alert_duration

    def set_alert_duration(self, alert_duration):
        self.alert_duration = alert_duration

    def set_camera_type(self, camera_type):
        self.camera_type = camera_type

    def set_alert_type(self, alert_type):
        self.alert_type = alert_type

    def get_MOCK_RUNNING(self):
        return self.MOCK_RUNNING
