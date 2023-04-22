import json

import requests
from Config import InitData
from PersistenceModule.urls import finish_ride_url, get_rp_config_file_url
from RidesModule.Ride import FinishRideRequest
from Utils.Response import Response


def get_rp_config_file():
    url = InitData.SERVER_ADDRESS + get_rp_config_file_url
    res = requests.get(url)
    response = Response(res.json())
    if not response or response.was_exception:
        return False
    return response.value


def send_ride_to_server(ride):
    url = InitData.SERVER_ADDRESS + finish_ride_url


    rideDTO = FinishRideRequest(ride)
    data = {"rpSerialNumber": rideDTO.rpSerialNumber,
    "origin": {"longitude":rideDTO.origin.longitude, "latitude":rideDTO.origin.latitude},
    "destination": {"longitude":rideDTO.destination.longitude, "latitude":rideDTO.destination.latitude},
    "city" :  rideDTO.city,
    "startTime" :  rideDTO.startTime,
    "endTime" :  rideDTO.endTime,
    "hazards" :  rideDTO.hazards,
    "ridingActions" :  rideDTO.ridingActions,
    "junctions" :  rideDTO.junctions}

    json_data = json.dumps(data)
    headers = {'Content-Type': 'application/json'}
    res = requests.post(url, data=json_data, headers=headers)





    response = Response(res.json())
    if not response or response.was_exception:
        return False
    return True


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
