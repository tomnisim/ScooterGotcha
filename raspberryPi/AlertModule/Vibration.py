from AlertModule.Alert import Alert


class Vibration(Alert):
    def __init__(self):
        super().__init__()

    def alert(self):
        super().alert()
        print("Vibration alert "+self.duration+" "+self.power)

