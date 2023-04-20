


import requests

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
        res = requests.post(get_rp_config_file_url, data="ffffffff")
        response = Response(res)
        if not response or not response.was_exception :
            return False
        return True


    def send_rides_to_server(self, ride):

        res = requests.post(finish_ride_url, data=ride)
        response = Response(res)

        if not response or not response.was_exception :
            return False
        return True







