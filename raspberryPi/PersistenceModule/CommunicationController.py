import json

import requests
from Config import InitData
from PersistenceModule.urls import finish_ride_url, get_rp_config_file_url
from RidesModule.Ride import FinishRideRequest
from Utils.Logger import ride_logger, system_logger
from Utils.Response import Response


def get_rp_config_file():
    response = None
    url = InitData.SERVER_ADDRESS + get_rp_config_file_url
    try:
        res = requests.get(url)
        response = Response(res.json())
    except:
        system_logger.info('communication Problem')

    if not response or response.was_exception:
        return False
    return response.value

def send_ride_to_server(ride):
    def get_junctions_string(junctions_list):
        if len(junctions_list) == 0:
            return 'No junctions in this route\n'
        res = ""
        junction_no=1
        for j in junctions_list:
            res+= f'-------Junction {junction_no}----------->  Location :  lan = {j.longitude} , lng = {j.latitude} \n '
            junction_no +=1
        return res
    def get_hazards_string(hazards_list):
        if len(hazards_list) == 0:
            return 'No hazards in this route\n'
        res = ""
        hazard_no=1
        for hazard in hazards_list:
            res+= f'-------Hazard {hazard_no}-----------> Size : {hazard.size} \n Type : {hazard.type} \n  Location : lan = {hazard.location.longitude} , lng = {hazard.location.latitude} \n'
            hazard_no +=1
        return res

    response = None

    ride_logger.info(f'RIDE SUMMERY\n Start time : {ride.start_time} \n End time : {ride.end_time} \n Start location:  lan = {ride.start_location.longitude}, lng = {ride.start_location.latitude} \n Destination location:  lan = {ride.destination_location.longitude}, lng = {ride.destination_location.latitude} \n '
                     f'Junctions : \n{get_junctions_string(ride.junctions)} Hazards detected in this ride : \n{get_hazards_string(ride.hazards)} ')

    url = InitData.SERVER_ADDRESS + finish_ride_url


    rideDTO = FinishRideRequest(ride)
    data = {"rpSerialNumber": rideDTO.rpSerialNumber,
    "origin": {"longitude":rideDTO.origin.longitude, "latitude":rideDTO.origin.latitude},
    "destination": {"longitude":rideDTO.destination.longitude, "latitude":rideDTO.destination.latitude},
    "startTime" :  rideDTO.startTime,
    "endTime" :  rideDTO.endTime,
    "hazards" :  rideDTO.hazards,
    "ridingActions" :  rideDTO.ridingActions,
    "junctions" :  rideDTO.junctions}

    json_data = json.dumps(data)
    headers = {'Content-Type': 'application/json'}
    try:
        res = requests.post(url, data=json_data, headers=headers)
        response = Response(res.json())

    except:
        system_logger.info('communication problem')

    if not response or response.was_exception:
        return False
    return True


class CommunicationController:
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
