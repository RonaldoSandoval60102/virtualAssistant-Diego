import whisper
import socket

def transcribe_audio(audio_file):
    model = whisper.load_model("tiny")
    result = model.transcribe(audio_file)
    return result["text"]

def start_server(port):
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(("127.0.0.0", port))
    server_socket.listen(1)
    print("Waiting for connections at the port", port)

    while True:
        client_socket, client_address = server_socket.accept()
        print("Connection accepted from:", client_address)
        audio_file_path = client_socket.recv(1024).decode().rstrip()
        transcribed_text = transcribe_audio(audio_file_path)
        client_socket.sendall(transcribed_text.encode())
        client_socket.close()

if __name__ == "__main__":
    PORT = 12345 
    start_server(PORT)
