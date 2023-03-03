
from AlertModule.Alert import Alert
from gpiozero import LED

class Visual(Alert):
    def __init__(self):
        super().__init__()
        self.number_of_blinks = 5

    def alert(self):

        print("Visual alert "+self.duration+" "+self.power)

        led = LED(4)
        for i in range(self.number_of_blinks):
            led.blink()
        pause()
    def create_alerter(self):
        pass