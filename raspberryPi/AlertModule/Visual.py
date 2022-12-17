from AlertModule.Alert import Alert


class Visual(Alert):
    def __init__(self):
        super().__init__()
        self.number_of_blinks = 5

    def alert(self, hazard):
        super().alert(hazard)
        print("Visual alert "+self.duration+" "+self.power)