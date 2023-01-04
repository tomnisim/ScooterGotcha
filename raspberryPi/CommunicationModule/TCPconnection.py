


import requests

from Utils.Response import Response

url = "http://localhost:8080/login"
class TCPconnection():
    # Set the API endpoint URL

    def __init__(self):
        pass


    def get_rp_config_file(self):
        pass
        # Make the POST request
res = requests.post(url, data="ffffffff")
response = Response(res)
config_file_txt = response.get_value()
f = open("config.txt", "a")
f.write("Now the file has more content!")
f.close()
# set config by response data

