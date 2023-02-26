import configparser


class Service:
    def __init__(self):

        server_address, camera_type, num_of_coordinates, appendix_A = self.read_from_config('Config/config.txt')

    def read_from_config(self, file_name, ):
        server_address = {}
        appendix_A={}
        # read
        config = configparser.ConfigParser()
        config.read(file_name)


        i = config['ServerAddress']
        #server address
        host = config['ServerAddress']['host']
        port = config['ServerAddress']['port']
        protocol = config['ServerAddress']['protocol']

        server_address['host']=host
        server_address['port']=port
        server_address['protocol']=protocol

        #camera
        camera_type = config['Camera']['Type']

        #GPS
        num_of_coordinates = config['GPS']['num_of_coordinates']

        #Appendix A
        minimum_distance_to_alert = config['ScooterAppendixA']['minimum_distance_to_alert']
        alert_duration = config['ScooterAppendixA']['alert_duration']
        alert_types = config['ScooterAppendixA']['alert_types'].split('/')
        number_of_routes = config['ScooterAppendixA']['number_of_routes']
        admin_email = config['ScooterAppendixA']['admin_email']
        admin_password = config['ScooterAppendixA']['admin_password']
        minimum_password_length = config['ScooterAppendixA']['minimum_password_length']

        appendix_A['minimum_distance_to_alert'] = minimum_distance_to_alert
        appendix_A['alert_duration'] = alert_duration
        appendix_A['alert_types'] = alert_types
        appendix_A['number_of_routes'] = number_of_routes
        appendix_A['admin_email'] = admin_email
        appendix_A['admin_password'] = admin_password
        appendix_A['minimum_password_length'] = minimum_password_length
        return server_address, camera_type, num_of_coordinates, appendix_A



