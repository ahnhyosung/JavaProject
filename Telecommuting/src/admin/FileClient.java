package admin;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FileClient {
	private String hostname;
	private int port;

	

	public FileClient(){
		connect();
	}
	public FileClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		connect();
	}

	private void connect() {
		Socket s = new Socket();
		
		try {
			s.connect( new InetSocketAddress("127.0.0.1", 9999) );
			BufferedOutputStream out = new BufferedOutputStream(
					s.getOutputStream());
			FileInputStream fileIn = new FileInputStream(
					"C:\\temp\\test.jpg");
			byte[] buffer = new byte[10000];
			int bytesRead = 0;
			while ((bytesRead = fileIn.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
			out.close();
			fileIn.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}