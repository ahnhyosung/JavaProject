package admin;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Luxand.FSDK;
import db.DBProcess;

public class FileServer implements Runnable {
	private final int PORT = 9999;
	public static DBProcess dbproc;

	public static BufferedWriter bw;

	public FileServer() {
		dbproc = new DBProcess();
	}

	public void run() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (s != null) {
			try {
				System.out.println("Ŭ���̾�Ʈ �����");
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
						"C:\\Temp\\in\\in.jpg");

				byte[] buffer = new byte[10000];
				int bytesRead = 0;
				while ((bytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, bytesRead);
				}
				out.flush();
				out.close();

				dbproc.selectUser(1);
				dbproc.closeCon();

				TFaceRecord tRecode = new TFaceRecord();
				tRecode.menuEnrollFace();
				float fnum = tRecode.menuMatchFace();

				bw = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));

				if (fnum != 0.0f) {
					bw.write(1);
				} else {
					bw.write(0);
				}
				bw.flush();

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