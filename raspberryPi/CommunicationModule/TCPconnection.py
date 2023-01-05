


import requests

from CommunicationModule.urls import finish_ride_url, get_rp_config_file_url
from Utils.Response import Response


class TCPconnection():
    # Set the API endpoint URL

    def __init__(self):
        pass


    def get_rp_config_file(self):
        res = requests.post(get_rp_config_file_url, data="ffffffff")
        response = Response(res)


    def finish_ride(self):
        res = requests.post(finish_ride_url, data="ffffffff")
        response = Response(res)
        config_file_txt = response.get_value()
        f = open("config.txt", "a")
        f.write("Now the file has more content!")
        f.close()
        # set config by response data






