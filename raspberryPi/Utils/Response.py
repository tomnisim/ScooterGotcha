class Response():
    def __init__(self, res):
        self.value = res["value"]
        self.was_exception = res["was_exception"]
        self.message = res["message"]

    def get_value(self):
        return self.value

    def get_was_exception(self):
        return self.was_exception

    def get_message(self):
        return self.message
