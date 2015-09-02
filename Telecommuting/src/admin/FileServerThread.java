package admin;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import camera.TFaceRecord;
import db.DBProcess;

public class FileServerThread extends Thread {
	private ServerSocket file_server_socket;
	private Socket file_server_thread_socket;
	BufferedInputStream in;
	FileOutputStream out;
	public BufferedWriter bw;

	public DBProcess dbproc;

	public FileServerThread(ServerSocket ss, Socket s) {

		dbproc = new DBProcess();

		file_server_socket = ss;
		file_server_thread_socket = s;
		

	}

	@Override
	public void destroy() {
		try {
			file_server_thread_socket.close();
			file_server_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		System.out.println("client = "
				+ file_server_thread_socket.getInetAddress());

		try {

			in = new BufferedInputStream(
					file_server_thread_socket.getInputStream());

			out = new FileOutputStream("C:\\Temp\\in\\in.jpg");

		} catch (IOException e) {
			e.printStackTrace();
		}

		DataOutputStream dos = new DataOutputStream(out);

		byte[] buffer = new byte[256];
		int bytesRead = 0;

		try {

			while (true) {

				bytesRead = in.read(buffer, 0, 256);
				System.out.println(bytesRead);

				if (bytesRead < 256) {
					dos.write(buffer, 0, bytesRead);
					break;
				}

				dos.write(buffer, 0, bytesRead);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				out.close();
			} catch (IOException ie) {
			}
		}


		dbproc.selectUser(1);
		dbproc.closeCon();


		TFaceRecord tRecode = new TFaceRecord();
		tRecode.menuEnrollFace();
		String match_file_name = tRecode.menuMatchFace();


		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					file_server_thread_socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (match_file_name != null) {
			try {
				bw.write(match_file_name + "\n");
				System.out.println("1º¸³¿");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				bw.write("error" + "\n");
				System.out.println("0º¸³¿");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
