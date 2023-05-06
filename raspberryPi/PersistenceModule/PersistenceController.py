import Config
from Config.ConfigurationController import ConfigurationController
from PersistenceModule.CommunicationController import CommunicationController
from PersistenceModule.FilesController import FilesController
from RidesModule.Ride import Ride, to_dto, from_dto, RideDTO
from Utils.Logger import system_logger

class PersistenceController:
    def __init__(self):
        system_logger.info(f'Persistence Controller initialization')
        self.files_controller = FilesController.get_instance()
        self.communicationController = CommunicationController()
        self.configurationController = ConfigurationController()

    """
    This Method Try to Send All Waiting Rides to Server,
        If Failed to Send, Save All Waiting Rides into Files.
     """
    def save_ride(self, ride):
        waiting_rides = self.files_controller.get_waiting_rides()
        if ride is not None:
            rideDTO = to_dto(ride, self.configurationController.get_serial())
            waiting_rides.append(rideDTO)
        self.files_controller.clear_waiting_rides()
        for waiting_ride in waiting_rides:
            success = self.communicationController.send_ride_to_server(waiting_ride)
            if not success:
                self.files_controller.save_ride_to_file(waiting_ride)

    """
    This Method Try to Read Configuration Data from Server.
     if failed, Read from Raspberry Pi Default Configuration File.
     """
    def config_system(self):
        config_data = self.communicationController.get_rp_config_file()
        if config_data is False:
            # read from default
            self.configurationController.read_config_from_default()
        else:
            # read from server
            self.configurationController.set_data(config_data)


