import configparser
from Config.Constants import Constants


class ConfigurationController:
    def __init__(self):
        self.constants = Constants.get_instance()
        self.set_serial()
        self.read_config_from_default()

    """
    This Method set Serial Number from Serial # Configuration File.
    """

    def set_serial(self):
        filename = 'Config/serial.txt'
        with open(filename, "r") as f:
            serialNumber = f.read().strip()
        self.constants.set_serial_number(serialNumber)

    def read_config_from_default(self):
        filename = 'Config/default_config.txt'
        # read
        config = configparser.ConfigParser()
        config.read(filename)

        # server address
        SERVER_ADDRESS = config['ServerAddress']['protocol'] + config['ServerAddress']['host'] + ":" + \
                         config['ServerAddress']['port']
        self.constants.set_SERVER_ADDESS(SERVER_ADDRESS)

        # camera
        camera_type = config['Camera']['Type']
        self.constants.set_camera_type(camera_type)

        # GPS
        number_of_coordinates = config['GPS']['num_of_coordinates']
        self.constants.set_number_of_coordinates(number_of_coordinates)

        # Appendix A
        minimum_distance_to_alert = config['ScooterAppendixA']['minimum_distance_to_alert']
        self.constants.set_minimum_distance_to_alert(minimum_distance_to_alert)

        alert_duration = config['ScooterAppendixA']['alert_duration']
        self.constants.set_alert_duration(alert_duration)

        alert_type = config['ScooterAppendixA']['alert_type']
        self.constants.set_alert_type(alert_type)

    def set_data(self, config_data):
        # server address
        SERVER_ADDRESS = config_data['protocol'] + config_data['host'] + ":" + config_data['port']
        self.constants.set_SERVER_ADDESS(SERVER_ADDRESS)

        # camera
        # camera_type = config_data['CameraType']

        # GPS
        global number_of_coordinates
        number_of_coordinates = config_data['number_of_coordinates']
        self.constants.set_number_of_coordinates(number_of_coordinates)

        # Appendix A
        minimum_distance_to_alert = config_data['minimum_distance_to_alert']
        self.constants.set_minimum_distance_to_alert(minimum_distance_to_alert)

        alert_duration = config_data['alert_duration']
        self.constants.set_alert_duration(alert_duration)
        global alert_type
        alert_type = config_data['alert_type']

    def get_serial(self):
        return self.constants.get_serialNumber()
