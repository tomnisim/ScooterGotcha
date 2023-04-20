from PersistenceModule import FilesController
from PersistenceModule.CommunicationController import CommunicationController


class PersistenceController():
    def __init__(self):
        self.files_controller = FilesController.get_instance()
        self.communication_controller = CommunicationController.get_instance()

    def save_ride(self, ride):
        waiting_rides = self.files_controller.get_waiting_rides()
        waiting_rides.append(ride)
        self.files_controller.clear_waiting_rides()
        for waiting_ride in waiting_rides:
            success = self.communication_controller.send_ride_to_server(waiting_ride)
            if not success:
                self.files_controller.save_ride(ride)
