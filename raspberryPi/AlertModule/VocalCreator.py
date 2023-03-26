from AlertModule.AlertCreator import AlertCreator
from AlertModule.Vocal import Vocal


class VocalCreator(AlertCreator):
    def __init__(self):
        super().__init__()

    def create_alerter(self, alert_file_path):
        return Vocal(alert_file_path)