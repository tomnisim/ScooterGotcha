from AlertModule.Alert import Alert
from gpiozero import Buzzer
from signal import pause

class Vocal(Alert):
    def __init__(self):
        super().__init__()
        self.sound = "tik-tik"

    def alert(self, hazard):
        super().alert(hazard)
        print("Vocal alert "+self.duration+" "+self.power)

        buzzer = Buzzer(4)
        # beep every half second
        buzzer.beep(0.5, 0.5)

        pause()