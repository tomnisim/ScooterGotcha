from PersistenceModule.CommunicationController import CommunicationController
from PersistenceModule.FilesController import FilesController
from Utils.Logger import system_logger


class PersistenceController():
    def __init__(self):
        system_logger.info(f'Persistence Controller initialization')
        self.files_controller = FilesController.get_instance()
        self.communication_controller = CommunicationController.get_instance()

    def save_ride(self, ride):
        waiting_rides = self.files_controller.get_waiting_rides()
        waiting_rides.append(ride)
        ride = waiting_rides.pop(-1)
        success = self.communication_controller.send_rides_to_server(ride)
        # success = self.communication_controller.send_rides_to_server(waiting_rides)
        if not success:
            system_logger.info(f'failed to send rides ro server')
            self.files_controller.save_ride(ride)
        else:
            self.files_controller.clear_waiting_rides()
