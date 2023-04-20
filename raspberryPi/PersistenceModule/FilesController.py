




class FilesController():
    __instance = None

    def __init__(self):

        if FilesController.__instance != None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            FilesController.__instance = self

    @classmethod
    def get_instance(cls):
        if cls.__instance == None:
            cls()
        return cls.__instance

    #private
    def load_waiting_rides(self):
        pass

    def get_waiting_rides(self):
        waiting_rides = self.load_waiting_rides()
        return waiting_rides


    def save_ride(self, ride):
        pass

    def clear_waiting_rides(self):
        pass