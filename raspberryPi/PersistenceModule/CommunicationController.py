import json
import datetime
import random

import requests
from Config import ConfigurationController
from Config.Constants import Constants
from GPSModule.Location import Location
from PersistenceModule.urls import finish_ride_url, get_rp_config_file_url
from Utils.Logger import ride_logger, system_logger
from Utils.Response import Response


class CommunicationController:
    def __init__(self):
        self.finish_ride_suffix = "/finish_ride"
        self.get_rp_config_file_suffix = "/get_rp_config_file"

    def get_url(self):
        constants = Constants.get_instance()
        return constants.get_SERVER_ADDRESS()

    def get_rp_config_file(self):
        response = None
        url = self.get_url() + self.get_rp_config_file_suffix
        print('url is:', url)
        try:
            res = requests.get(url)
            response = Response(res.json())
        except Exception as e:
            system_logger.info('e->', e)
        # Failed to get Configuration File:
        if not response or response.was_exception:
            system_logger.info('Communication Problem: Cant get Raspberry Pi Configuration File From Server.')
            return False
        # Success to get Configuration File:
        system_logger.info('Got Raspberry Pi Configuration File From Server Successfully.')
        return response.value

    def send_ride_to_server(self, rideDTO):
        response = None
        url = self.get_url() + self.finish_ride_suffix

        json_data = json.dumps(rideDTO)
        headers = {'Content-Type': 'application/json'}
        try:
            res = requests.post(url, data=json_data, headers=headers)
            response = Response(res.json())

        except Exception as e:
            print('e->', e)
            system_logger.info('communication problem')

        # Failed to Send Ride:
        if not response or response.was_exception:
            system_logger.info('Communication Problem: Cant Send Ride to Server.')
            return False
        # Success to Send Ride:
        else:
            system_logger.info('Send Ride to Server Successfully.')
            return True


def get_junctions_string(junctions_list):
    if len(junctions_list) == 0:
        return 'No junctions in this route\n'
    res = ""
    junction_no = 1
    for loc in junctions_list:
        res += f'Junction {junction_no}----------->  Location :  lan = {loc.longitude} , lng = {loc.latitude} \n '
        junction_no += 1
    return res


def get_hazards_string(hazards_list):
    if len(hazards_list) == 0:
        return 'No hazards in this route\n'
    res = ""
    hazard_no = 1
    for hazard in hazards_list:
        res += f'Hazard {hazard_no}-----------> Size : {hazard.size} \n Type : {hazard.type} \n  Location : lan = {hazard.location.latitude} , lng = {hazard.location.longitude} \n'
        hazard_no += 1
    return res


def write_to_logger():
    ride_logger.info(f'RIDE SUMMERY\n {rideDTO}')


def get_hazards_string_mock(hazards_list, loc_mocks):
    if len(hazards_list) == 0:
        return 'No hazards in this route\n'
    res = ""
    hazard_no = 1
    for hazard in hazards_list:
        size_mock = random.uniform(15, 35)
        loc_mock = loc_mocks.pop(0)
        res += f'Hazard {hazard_no}-----------> Size : {size_mock} cm \n Type : {hazard.type} \n  Location : lan = {loc_mock.latitude} , lng = {loc_mock.longitude} \n'
        hazard_no += 1
    return res


def write_to_logger_mock(ride):
    loc_start = Location(31.263341, 34.799084)
    loc_j1 = Location(31.263323, 34.799234)
    loc_j2 = Location(31.263296, 34.799481)
    loc_finish = Location(31.263360, 34.799642)
    loc_h1 = Location(31.263415, 34.799245)
    loc_h2 = Location(31.263360, 34.799427)
    loc_h3 = Location(31.263323, 34.799256)
    loc_h4 = Location(31.263378, 34.799438)

    junctions_mock = [loc_j1, loc_j2]
    hazards_loc_mock = [loc_h1, loc_h2, loc_h3, loc_h4]

    # TODO: MOCK
    ride_logger.info(
        f'RIDE SUMMERY\n Start time : {ride.start_time} \n End time : {ride.start_time + datetime.timedelta(seconds=8)} \n Start location:  lan = {loc_start.latitude}, lng = {loc_start.longitude} \n Destination location:  lan = {loc_finish.latitude}, lng = {loc_finish.longitude} \n '
        f'Junctions : \n{get_junctions_string_mock(junctions_mock)} Hazards detected in this ride : \n{get_hazards_string_mock(ride.hazards, hazards_loc_mock)} ')


def get_junctions_string_mock(junctions_list):
    if len(junctions_list) == 0:
        return 'No junctions in this route\n'
    res = ""
    junction_no = 1
    for loc in junctions_list:
        res += f'Junction {junction_no}----------->  Location :  lan = {loc.longitude} , lng = {loc.latitude} \n '
        junction_no += 1
    return res
