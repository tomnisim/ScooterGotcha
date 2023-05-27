from abc import ABC, abstractmethod


class Alert(ABC):
    def __init__(self, duration, power):
        self.duration = duration # time in seconds
        self.power = power # range(0,100) - percent

    @abstractmethod
    def alert(self, duration):
        system_logger.info("start alert, analyze hazard and save details about it ")











