import configparser


class Service:
    def __init__(self):
        host , port, protocol = self.read_from_config('Config/config.txt')

    def read_from_config(self, file_name):
        # read 
        config = configparser.ConfigParser()
        config.read(file_name)
        host = config['ServerAddress']['host']
        port = config['ServerAddress']['port']
        protocol = config['ServerAddress']['protocol']
        return host, port, protocol




