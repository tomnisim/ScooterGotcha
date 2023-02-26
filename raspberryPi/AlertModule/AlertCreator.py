from abc import ABC, abstractmethod


class AlertCreator(ABC):
    def __init__(self, duration, power):
        self.duration = duration # time in seconds
        self.power = power # range(0,100) - percent


    @abstractmethod
    def create_alerter(self):
        pass

