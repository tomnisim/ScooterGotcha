import configparser

from PersistenceModule.CommunicationController import CommunicationController

# all variables
# method that change the values according to server


serialNumber = ""
number_of_coordinates = ""
SERVER_ADDRESS = ""
minimum_distance_to_alert = ""
alert_duration = ""
alert_type = ""
number_of_routes = ""


class Config_data():
    def __init__(self):
        self.set_serial()
        self.get_config_data()

    def get_config_data(self):
        config_data = CommunicationController.get_rp_config_file()
        if config_data is False:
            self.read_from_config()
        else:
            self.set_data(config_data)

    def set_serial(self):
        filename = "raspberryPi/Config/serial.txt"
        global serialNumber
        with open(filename, "r") as f:
            serialNumber = f.read().strip()

    def read_from_config(self):
        filename = "raspberryPi/Config/default_config.txt"
        # read
        config = configparser.ConfigParser()
        config.read(filename)

        # server address
        global SERVER_ADDRESS
        SERVER_ADDRESS = config['ServerAddress']['host'] + ":" + config['ServerAddress']['port']

        # camera
        camera_type = config['Camera']['Type']

        # GPS
        global number_of_coordinates
        number_of_coordinates = config['GPS']['num_of_coordinates']

        # Appendix A
        global minimum_distance_to_alert
        minimum_distance_to_alert = config['ScooterAppendixA']['minimum_distance_to_alert']
        global alert_duration
        alert_duration = config['ScooterAppendixA']['alert_duration']
        global alert_type
        alert_type = config['ScooterAppendixA']['alert_type']

    def set_data(self, config_data):
        # server address
        global SERVER_ADDRESS
        SERVER_ADDRESS = config_data['host'] + ":" + config_data['port']

        # camera
        camera_type = config_data['CameraType']

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


