from AlertModule.AlertCreator import AlertCreator
from AlertModule.Verbal import Verbal


class VerbalCreator(AlertCreator):
    def __init__(self):
        super().__init__()

    def create_alerter(self):
        return Verbal()