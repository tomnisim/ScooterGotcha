
import logging


# ------------------ ERROR LOGGER -----------------------------------
# Create a logger for errors
error_logger = logging.getLogger('error_logger')
error_logger.setLevel(logging.ERROR)

# Create a file handler for the error logger
error_handler = logging.FileHandler('error.log')
error_handler.setLevel(logging.ERROR)

# Create a formatter for the error handler
error_formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
error_handler.setFormatter(error_formatter)

# Add the error handler to the error logger
error_logger.addHandler(error_handler)



# ------------------ SYSTEM LOGGER -----------------------------------

# Create a logger for events
system_logger = logging.getLogger('system_logger')
system_logger.setLevel(logging.INFO)

# Create a file handler for the system logger
system_handler = logging.FileHandler('system.log')
system_handler.setLevel(logging.INFO)

# Create a formatter for the event handler
system_formatter = logging.Formatter('%(asctime)s %(message)s')
system_handler.setFormatter(system_formatter)

# Add the event handler to the event logger
system_logger.addHandler(system_handler)




# ------------------ RIDE LOGGER -----------------------------------

# Create a logger for events
ride_logger = logging.getLogger('ride_logger')
ride_logger.setLevel(logging.INFO)

# Create a file handler for the system logger
ride_handler = logging.FileHandler('ride.log')
ride_handler.setLevel(logging.INFO)

# Create a formatter for the event handler
ride_formatter = logging.Formatter('%(asctime)s %(message)s')
ride_handler.setFormatter(ride_formatter)

# Add the event handler to the event logger
ride_logger.addHandler(ride_handler)

#_-------------------------------------- HOW TO USE ----------------

# # Log an error  -> error_logger.error(str(e))
# # Log an event -> event_logger.info('The program started.')
