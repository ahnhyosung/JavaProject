package admin;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer implements Runnable {
	private final int PORT = 9999;

//	public FileServer() {
//		new Thread(new FileServer()).start();
//	}

	public void run() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (s != null) {
			try {
				Socket client = s.accept();
				System.out.println("client = " + client.getInetAddress());
				new Thread(new FileServerClient(client)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class FileServerClient implements Runnable {
		private Socket socket;

		FileServerClient(Socket s) {
			socket = s;
		}

		public void run() {
			try {
			
				InputStream in = socket.getInputStream();
				FileOutputStream out = new FileOutputStream(
						"C:\\temp\\in.jpg");

				byte[] buffer = new byte[10000];
				int bytesRead = 0;
				while ((bytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, bytesRead);
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}