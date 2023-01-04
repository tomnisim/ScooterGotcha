import socket

class TCPconnection:
    def __init__(self):
        self.s = 0

    def open_connection_with_server(self):
        host = socket.gethostname()
        port = 6666  # The same port as used by the server
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.s.connect((host, port))

    def send_data_to_server_by_socket(self, data):
        # data to bytes
        data_bytes = (b'data')
        self.s.sendall(data_bytes)
        answer = self.s.recv(1024)
        self.s.close()
        print('Received', repr(answer))
