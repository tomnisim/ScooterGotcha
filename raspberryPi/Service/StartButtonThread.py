from Config.Constants import Constants
from Utils.Logger import system_logger
import time
import RPi.GPIO as GPIO
# buzzer =18
# ledPin = 12
# buttonPin=16 
class StartButtonThread:
    def __init__(self):
        self.button_pin = 16
        self.ledPin = 12
        self.start_button = False
        # TODO: uncomment
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(self.ledPin, GPIO.OUT)
        # GPIO.setup(buzzer, GPIO.OUT)
        GPIO.setup(self.button_pin, GPIO.IN, pull_up_down=GPIO.PUD_UP)

    def get_start_button(self):
        return self.start_button

    def run(self):
        if Constants.get_instance().get_MOCK_RUNNING():
            self.manage_start_button_task_mock()
        else:
            # self.manage_start_button_task_mock()
            self.manage_start_button_task()

    def manage_start_button_task_mock(self):
        system_logger.info(f'Start thread manage_start_button_task_mock')
        while True:
            start_button_mock = input("PRESS (s) TO START\n")
            while start_button_mock != "s":
                pass
            print("START...")
            self.start_button = not self.start_button
            break

    def manage_start_button_task(self):
        system_logger.info(f'Start thread manage_start_button_task')
        print("PRESS (s) TO START - real\n")
        try:
            while True:
                button_state = GPIO.input(self.button_pin)
                if button_state == False:
                    system_logger.info("Live Button Press - start")
                    self.start_button = not self.start_button
                    time.sleep(1)  # Debounce
                    break
        finally:
            GPIO.cleanup()

    # def manage_start_button_task_mock2():
    #
    #     def on_key_press(event):
    #         global start_button
    #         print(f'Key {event.name} was pressed')
    #         if event.name == "s":
    #             start_button = not start_button
    #             print("Enter key was pressed")
    #     keyboard.on_press(on_key_press)
