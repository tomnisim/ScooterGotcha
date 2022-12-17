from AlertModule.Alert import Alert


class Verbal(Alert):
    def __init__(self):
        super().__init__()
        self.voice = "men"
        self.words = "be careful"

    def alert(self, hazard):
        super().alert(hazard)
        print("Verbal alert "+self.duration+" "+self.power)