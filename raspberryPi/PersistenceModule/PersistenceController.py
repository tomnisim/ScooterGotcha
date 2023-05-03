from PersistenceModule.CommunicationController import send_ride_to_server
from PersistenceModule.FilesController import FilesController
from RidesModule.Ride import Ride, to_dto, from_dto
from Utils.Logger import system_logger

class PersistenceController():
    def __init__(self):
        system_logger.info(f'Persistence Controller initialization')
        self.files_controller = FilesController.get_instance()


    def save_ride(self, ride):
        waiting_rides = self.files_controller.get_waiting_rides()
        waiting_rides.append(to_dto(ride))
        self.files_controller.clear_waiting_rides()
        for waiting_ride in waiting_rides:
            success = send_ride_to_server(waiting_ride)
            if not success:
                system_logger.info(f'failed to send ride that start at {ride.start_time} to server')
                self.files_controller.save_ride_to_file(waiting_ride)
            else:
                system_logger.info(f'ride that start at {ride.start_time} sends to server successfully')
