from AlertModule.Alert import Alert


class Vibration(Alert):
    def __init__(self):
        super().__init__()

    def alert(self, hazard):
        super().alert(hazard)
        print("Vibration alert "+self.duration+" "+self.power)