from AlertModule.Alert import Alert


class Verbal(Alert):
    def __init__(self):
        super().__init__()
        self.voice = "men"
        self.words = "be careful"

    def alert(self):
        super().alert()
        print("Verbal alert "+self.duration+" "+self.power)

    def create_alerter(self):
        pass