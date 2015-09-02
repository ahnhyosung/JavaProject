package admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class FileServer extends Thread{
	private final int PORT = 9999;

	public ServerSocket server_socket = null;
	public Socket socket;
	private Vector<FileServerThread> vector = new Vector<>();
	private int index = 0;

	public FileServer() {

		try {
			server_socket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.start();

	}
	@Override
	public void run() {
		
		
			while(true){
				try {
					socket = server_socket.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("접속 하였습니다.");
				
				FileServerThread thread = new FileServerThread(server_socket, socket);
				vector.add(index, thread);
				vector.get(index).start();
				index++;
				
			}
			
		
		
		
	}

}