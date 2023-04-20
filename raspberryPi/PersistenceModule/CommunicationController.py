import requests
from Config.Config_data import serialNumber, SERVER_ADDRESS
from PersistenceModule.urls import finish_ride_url, get_rp_config_file_url
from Utils.Response import Response


class CommunicationController():
    __instance = None

    def __init__(self):
        if CommunicationController.__instance != None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            CommunicationController.__instance = self

    @classmethod
    def get_instance(cls):
        if cls.__instance == None:
            cls()
        return cls.__instance


    def get_rp_config_file(self):
        res = requests.get(SERVER_ADDRESS + get_rp_config_file_url, params={serialNumber: serialNumber})
        response = Response(res)
        if not response or not response.was_exception:
            return False
        return response.value


    def send_rides_to_server(self, ride):
        res = requests.post(SERVER_ADDRESS + finish_ride_url, data=ride)
        response = Response(res)
        if not response or not response.was_exception:
            return False
        return True







