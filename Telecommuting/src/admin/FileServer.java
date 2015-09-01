package admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
	private final int PORT = 9999;

	public ServerSocket server_socket = null;
	public Socket socket;

	public FileServer() {

		try {
			server_socket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		new FileServerThread(server_socket, socket).start();

	}

}