package admin;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
		try {

			file_server_thread_socket = file_server_socket.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
			System.out.println("플러쉬를 하나?");

			while (true) {

				bytesRead = in.read(buffer, 0, 256);
				System.out.println(bytesRead);

				if (bytesRead < 256) {
					dos.write(buffer, 0, bytesRead);
					break;
				}

				dos.write(buffer, 0, bytesRead);
//				out.flush();

			}
			System.out.println("yang");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				out.close();
			} catch (IOException ie) {
			}
		}

		System.out.println("테스트ㅋㅋㅋ");

		dbproc.selectUser(1);
		dbproc.closeCon();

		System.out.println("테스트ㅎㅎ");

		TFaceRecord tRecode = new TFaceRecord();
		tRecode.menuEnrollFace();
		String match_file_name = tRecode.menuMatchFace();

		System.out.println("테스트0");

		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					file_server_thread_socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (match_file_name != null) {
			try {
				bw.write(match_file_name + "\n");
				System.out.println("1보냄");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				bw.write(match_file_name + "\n");
				System.out.println("0보냄");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("테스트1");
		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("테스트2");

	}
}
