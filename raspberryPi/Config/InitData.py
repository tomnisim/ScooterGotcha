import configparser

# method that change the values according to server
from PersistenceModule.CommunicationController import get_rp_config_file

serialNumber = ""
number_of_coordinates = ""
SERVER_ADDRESS = ""
minimum_distance_to_alert = ""
alert_duration = 0
alert_type = ""
number_of_routes = ""
time_between_junctions = ""


class InitData:
    def __init__(self):
        self.set_serial()
        self.read_config_from_default()
        self.read_config_from_server()

    def read_config_from_server(self):
        config_data = get_rp_config_file()
        if config_data is False:
            pass
        else:
            self.set_data(config_data)

    def set_serial(self):
        filename = "Config\serial.txt"
        global serialNumber
        with open(filename, "r") as f:
            serialNumber = f.read().strip()

    def read_config_from_default(self):
        global alert_duration
        filename = "Config/default_config.txt"
        # read
        config = configparser.ConfigParser()
        config.read(filename)

        # server address
        global SERVER_ADDRESS
        SERVER_ADDRESS = config['ServerAddress']['protocol'] + config['ServerAddress']['host'] + ":" + config['ServerAddress']['port']

        # camera
        camera_type = config['Camera']['Type']

        # GPS
        global number_of_coordinates
        number_of_coordinates = config['GPS']['num_of_coordinates']

        # Appendix A
        global minimum_distance_to_alert
        minimum_distance_to_alert = config['ScooterAppendixA']['minimum_distance_to_alert']
        alert_duration = int(config['ScooterAppendixA']['alert_duration'])
        global alert_type
        alert_type = config['ScooterAppendixA']['alert_type']

    def set_data(self, config_data):
        # server address
        global SERVER_ADDRESS
        SERVER_ADDRESS = config_data['protocol'] + config_data['host'] + ":" + config_data['port']

        # camera
        # camera_type = config_data['CameraType']

        # GPS
        global number_of_coordinates
        number_of_coordinates = config_data['number_of_coordinates']

        # Appendix A
        global minimum_distance_to_alert
        minimum_distance_to_alert = config_data['minimum_distance_to_alert']
        global alert_duration
        alert_duration = config_data['alert_duration']
        global alert_type
        alert_type = config_data['alert_type']


