



# all variables
# method that change the values according to server
from Utils.Logger import system_logger


class Config_data():
    def __init__(self):
        system_logger.info("Update Configuration Data")
        self.get_config_data()
        self.set_serial()

    def get_config_data(self):
        pass

    def set_serial(self):
        pass