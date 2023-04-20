
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



# ------------------ EVENT LOGGER -----------------------------------

# Create a logger for events
event_logger = logging.getLogger('event_logger')
event_logger.setLevel(logging.INFO)

# Create a file handler for the event logger
event_handler = logging.FileHandler('events.log')
event_handler.setLevel(logging.INFO)

# Create a formatter for the event handler
event_formatter = logging.Formatter('%(asctime)s %(message)s')
event_handler.setFormatter(event_formatter)

# Add the event handler to the event logger
event_logger.addHandler(event_handler)
# this import only works on a raspberry pi




#_-------------------------------------- HOW TO USE ----------------

# # Log an error  -> error_logger.error(str(e))
# # Log an event -> event_logger.info('The program started.')
