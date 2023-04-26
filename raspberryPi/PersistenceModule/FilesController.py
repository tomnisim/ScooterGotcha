import json
import os
import uuid


class FilesController:
    __instance = None
    folder_name = "../ridesStorage/"

    def __init__(self):
        if FilesController.__instance != None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            FilesController.__instance = self
            self.create_folder()

    @classmethod
    def get_instance(cls):
        if cls.__instance == None:
            cls()
        return cls.__instance

    #private
    def load_waiting_rides(self):
        rides = []
        for file_name in os.listdir(self.folder_name):
            if file_name.endswith(".json"):
                with open(os.path.join(self.folder_name, file_name), "r") as json_file:
                    ride = json.load(json_file)
                    rides.append(ride)
        return rides

    def get_waiting_rides(self):
        waiting_rides = self.load_waiting_rides()
        return waiting_rides

    def create_folder(self):
        if not os.path.exists(self.folder_name):
            os.makedirs(self.folder_name)

    def save_ride_to_file(self, ride):
        file_name = self._generate_file_name()
        # Convert the object to JSON format and store it in the file
        with open(os.path.join(self.folder_name, file_name), "w") as json_file:
            json.dump(ride, json_file, ensure_ascii=False, indent=4)

    def _generate_file_name(self):
        while True:
            file_name = f"obj{uuid.uuid4()}.json"
            if not os.path.exists(os.path.join(self.folder_name, file_name)):
                break
        return file_name

    def clear_waiting_rides(self, file_name=None):
        if file_name:
            file_path = os.path.join(self.folder_name, file_name)
            if os.path.exists(file_path):
                os.remove(file_path)
            else:
                print(f"File '{file_name}' not found in folder '{self.folder_name}'.")
        else:
            for file_name in os.listdir(self.folder_name):
                file_path = os.path.join(self.folder_name, file_name)
                if os.path.isfile(file_path):
                    os.remove(file_path)