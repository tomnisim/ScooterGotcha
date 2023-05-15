import time
from Utils.Logger import system_logger


class UpdateConfigThread:
    def __init__(self, persistence_controller):
        self.persistence_controller = persistence_controller

    def task(self):
        system_logger.info(f'Start thread update Configuration')
        while True:
            time.sleep(3600)
            self.persistence_controller.config_system()
