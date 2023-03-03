import tensorflow as tf

MODEL_PATH = 'Utils/my_model.h5'

class HazardDetector:
    def __init__(self):
        self._model = self.load_model(MODEL_PATH)
        print("HazardDetector build.")

    def load_model(self, file_name):
        # Load your saved model from a file
        loaded_model = tf.keras.models.load_model(file_name)
        return loaded_model
    def detect_hazards_in_frame(self, frame):
        print("found hazards..")
        # Use the loaded model to make predictions
        predictions = self._model.predict(frame)
        # todo : this method should return list of hazards - have to build every hazard.
        return ["1", "2", "3"]


