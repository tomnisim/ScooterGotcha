from AlertModule.AlertCreator import AlertCreator
from AlertModule.Vibration import Vibration


class VibrationCreator(AlertCreator):
    def __init__(self):
        super().__init__()

    def create_alerter(self, alert_file_path):
        return Vibration()