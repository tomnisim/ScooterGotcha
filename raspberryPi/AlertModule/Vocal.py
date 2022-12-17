from AlertModule.Alert import Alert


class Vocal(Alert):
    def __init__(self):
        super().__init__()
        self.sound = "tik-tik"

    def alert(self, hazard):
        super().alert(hazard)
        print("Vocal alert "+self.duration+" "+self.power)