from PersistenceModule import FilesController
from PersistenceModule.CommunicationController import CommunicationController
from Utils.Logger import system_logger

class PersistenceController():
    def __init__(self):
        system_logger.info(f'Persistence Controller initialization')
        self.files_controller = FilesController.get_instance()
        self.communication_controller = CommunicationController

    def save_ride(self, ride):
        waiting_rides = self.files_controller.get_waiting_rides()
        waiting_rides.append(ride)
        self.files_controller.clear_waiting_rides()
        for waiting_ride in waiting_rides:
            success = self.communication_controller.send_ride_to_server(waiting_ride)
            if not success:
                system_logger.info(f'failed to send ride that start at {ride.start_time} to server')
                self.files_controller.save_ride(ride0)
            else:
                system_logger.info(f'ride that start at {ride.start_time} sends to server successfully')
