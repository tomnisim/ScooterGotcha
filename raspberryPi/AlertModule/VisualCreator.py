from AlertModule.AlertCreator import AlertCreator
from AlertModule.Visual import Visual


class VisualCreator(AlertCreator):
    def __init__(self):
        super().__init__()

    def create_alerter(self):
        return Visual()