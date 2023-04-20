from AlertModule.AlertCreator import AlertCreator
from AlertModule.Vocal import Vocal


class VocalCreator(AlertCreator):
    def __init__(self):
        super().__init__(12, 12)

    def create_alerter(self):
        return Vocal()