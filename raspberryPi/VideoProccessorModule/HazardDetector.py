import tensorflow as tf
from keras import datasets, layers, models
from keras.layers import Dense, Conv2D, Flatten, Dropout, MaxPooling2D
from keras.backend import clear_session
from keras.wrappers.scikit_learn import KerasClassifier
import matplotlib.pyplot as plt
import gdown
import zipfile
from PIL import Image
import numpy as np
import os
from sklearn.model_selection import train_test_split, GridSearchCV
import requests


class HazardDetector:
    def __init__(self):

        print("HazardDetector build.")
        files = self.get_images() # files[0] -> potholes array , files[1] -> no_potholes array
        x, y , train_images, test_images, train_labels, test_labels= self.convert_data(files)
        params, keras_class = self.define_parameters()
        grid_result = self.make_grid(params, x, y, keras_class)
        model, best_params =  self.build_model(grid_result, train_images)
        history = self.train_model(model, train_images, train_labels, best_params, test_images, test_labels)
        self.compare_batch_sizes(history, test_images, test_labels)



    def get_images(self):
        # TODO : load arrays of images
        # # load sunglasses images
        # for filename in os.listdir("/content/Sunglassess"):
        #     # load the image and convert to array
        #     img = Image.open("/content/Sunglassess/" + filename)
        #     img = np.array(img)
        #     sun_glasses.append(img)
        #
        # # load no sunglasses images
        # for filename in os.listdir("/content/NoSunglasses"):
        #     # load the image and convert to array
        #     img = Image.open("/content/NoSunglasses/" + filename)
        #     img = np.array(img)
        #     no_sun_glasses.append(img)
        pass
    def convert_data(self, files):
        potholes = files[0]
        no_potholes = files[1]

        # convert data and labels to numpy arrays
        potholes = np.array(potholes)
        potholes_labels = np.ones(potholes.shape[0])
        no_potholes = np.array(no_potholes)
        no_potholes_labels = np.zeros(no_potholes.shape[0])

        # # shuffeling the data
        # np.random.shuffle(potholes)
        # np.random.shuffle(no_potholes)

        x = np.concatenate((potholes, no_potholes))
        y = np.concatenate((potholes_labels, no_potholes_labels))

        # Download and prepare data
        train_images, test_images, train_labels, test_labels = train_test_split(x, y, test_size=1 / 3)

        # Normalize pixel values to be between 0 and 1
        train_images, test_images = train_images / 255.0, test_images / 255.0

        # print the length of the training and the testing data.
        print(f'Number of images in training (test) set: {len(train_images)} ({len(test_images)})')

        return x, y , train_images, test_images, train_labels, test_labels

    # Visualize model history
    def plot_model_history(self, history):
        plt.plot(history.history['accuracy'], label='Training accuracy')
        plt.plot(history.history['val_accuracy'], label='Validation accuracy')
        plt.title('Training / validation accuracies')
        plt.ylabel('Accuracy')
        plt.xlabel('Epoch')
        plt.legend(loc="upper left")
        plt.show()

        plt.plot(history.history['loss'], label='Training loss')
        plt.plot(history.history['val_loss'], label='Validation loss')
        plt.title('Training / validation loss values')
        plt.ylabel('Loss value')
        plt.xlabel('Epoch')
        plt.legend(loc="upper left")
        plt.show()

    # Create convolutional base
    def create_model(self, optimizer, input_shape):

        clear_session()
        model = models.Sequential()
        model.add(Conv2D(128, kernel_size=(3, 3), activation='relu', input_shape=(input_shape[0], input_shape[1], 1)))
        model.add(MaxPooling2D((2, 2)))
        model.add(Conv2D(64, kernel_size=(3, 3), activation='relu'))
        model.add(MaxPooling2D((2, 2)))
        model.add(Conv2D(64, kernel_size=(3, 3), activation='relu'))
        model.add(Flatten())
        model.add(Dense(128, activation='relu'))
        model.add(Dense(1, activation='softmax'))
        model.compile(optimizer=optimizer, loss='binary_crossentropy', metrics=['accuracy'])
        return model

    def define_parameters(self):

        keras_class = KerasClassifier(build_fn=self.create_model) # check if its OK to add parameter to 'create model'

        # define the grid search parameters
        opts = ['SGD', 'RMSprop', 'Adagrad', 'Adadelta', 'Adam', 'Adamax', 'Nadam']

        params = {
            'optimizer': opts,
            'epochs': [10, 50, 100],
        }
        return params, keras_class

    def make_grid(self, params, x, y, keras_class):
        grid = GridSearchCV(estimator=keras_class, param_grid=params, cv=3)
        grid_result = grid.fit(x, y, verbose=0)
        print(grid_result.best_params_)
        print("Best Score is: %f using %s" % (grid_result.best_score_, grid_result.best_params_))
        return grid_result

    def build_model(self, grid_result, train_images):
        # Inpute shape (image_height, image_width, color_channels)
        input_shape = train_images[0].shape
        # training new model using hyper params
        best_params = grid_result.best_params_
        model = self.create_model(best_params['optimizer'], )
        model.compile(loss='binary_crossentropy', metrics=['accuracy'], optimizer=best_params['optimizer'])
        return model, best_params

    def train_model(self, model, train_images, train_labels, best_params, test_images, test_labels):
        # train the model
        history = model.fit(train_images, train_labels, epochs=best_params['epochs'],
                            validation_data=(test_images, test_labels))
        return history

    def summary(self, model, history, test_images, test_labels):
        model.summary()
        self.plot_model_history(history)

        # print the test accuaracy performence
        test_loss, test_acc = model.evaluate(test_images, test_labels, verbose=2)

        print(f'our accuracy of the model is {test_acc}')
        print(f'our loss of the model is {test_loss}')

    # Function for printing plots to compare between the batch sizes
    def comp_batch(self, loss, acc, size):
        plt.plot(size, acc)
        plt.title('validation accuracies')
        plt.ylabel('Accuracy')
        plt.xlabel('Batch')
        plt.show()

        plt.plot(size, loss)
        plt.title('validation loss values')
        plt.ylabel('Loss value')
        plt.xlabel('Batch')
        plt.show()

    def compare_batch_sizes(self, history, test_images, test_labels):
        losses = []
        acces = []

        batches = [10, 20, 40, 60, 80, 100]
        for batch_size in batches:
            # training new model using hyper params
            # model = create_model(best_params['optimizer'])
            model = self.create_model('Adam')


            test_loss, test_acc = model.evaluate(test_images, test_labels, verbose=0)
            losses.append(test_loss)
            acces.append(test_acc)

        self.comp_batch(losses, acces, batches)

        print(f'best accuracy is {max(acces)}')


    def detect_hazards_in_frame(self, frame):
        print("found hazards..")
        # todo : this method should return list of hazards - have to build every hazard.
        return ["1", "2", "3"]