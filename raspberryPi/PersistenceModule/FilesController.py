import json
import os
import pickle
import uuid
from shutil import disk_usage
from pathlib import Path


class FilesController:
    __instance = None
    folder_name = "../ridesStorage"

    def __init__(self):
        if FilesController.__instance is not None:
            raise Exception("Singleton class can only be instantiated once")
        else:
            FilesController.__instance = self
            self.create_folder()

    @classmethod
    def get_instance(cls):
        if cls.__instance is None:
            cls()
        return cls.__instance

    # private
    def load_waiting_rides(self):
        # print("self.folder_name", self.folder_name)
        rides = []
        for file_name in os.listdir(self.folder_name):
            print(os.getcwd())
            print(f'{self.folder_name}/{file_name}')
            if file_name.endswith(".json") and os.stat(f'{self.folder_name}/{file_name}').st_size > 0:
                with open(os.path.join(self.folder_name, file_name), "r") as json_file:
                    ride = json.load(json_file)
                    rides.append(ride)
        return rides

    """
    :return: A list of rides who didn't send to server, in JSON format for each ride.
    """

    def get_waiting_rides(self):
        # TODO: Sort from latest to first.
        waiting_rides = self.load_waiting_rides()
        return waiting_rides

    """
    this method create a new folder in memory if dont exists.
    """

    def create_folder(self):
        if not os.path.exists(self.folder_name):
            os.makedirs(self.folder_name)

    def save_ride_to_file(self, ride):
        if self._is_space_available():
            file_name = self._generate_file_name()
            with open(os.path.join(self.folder_name, file_name), "w") as json_file:
                # Serialize the object and write it to the file
                # pickle.dump(ride, json_file)
                json.dump(ride, json_file, ensure_ascii=False, indent=4)
        else:
            self._delete_oldest_file()
            self.save_ride_to_file(ride)

    def _is_space_available(self, min_space_mb=1):
        _, _, free = disk_usage(self.folder_name)
        free_mb = free // (1024 * 1024)
        return free_mb >= min_space_mb

    def _delete_oldest_file(self):
        files = Path(self.folder_name).glob('*')
        oldest_file = None
        min_mtime = float('inf')

        for file in files:
            if file.is_file():
                mtime = file.stat().st_mtime
                if mtime < min_mtime:
                    min_mtime = mtime
                    oldest_file = file

        if oldest_file is not None:
            oldest_file.unlink()

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
