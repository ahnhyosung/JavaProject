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
				System.out.println("클라이언트 대기중");
				Socket client = s.accept();
				System.out.println("client = " + client.getInetAddress());
				new FileServerClient(client).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class FileServerClient extends Thread {
		private Socket socket;
		InputStream in;
		FileOutputStream out;

		FileServerClient(Socket s) throws IOException {
			socket = s;

			in = socket.getInputStream();
			out = new FileOutputStream("C:\\Temp\\in\\in.jpg");
		}

		public void run() {
			try {

				System.out.println("테스트 시작");

				byte[] buffer = new byte[100000];
				int bytesRead = 0;
				while ((bytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, bytesRead);
				}

				System.out.println("테스트ㅋㅋㅋ");
				out.flush();
				out.close();

				dbproc.selectUser(1);
				dbproc.closeCon();

				System.out.println("테스트ㅎㅎ");

				TFaceRecord tRecode = new TFaceRecord();
				tRecode.menuEnrollFace();
				float fnum = tRecode.menuMatchFace();

				System.out.println("테스트0");

				bw = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));

				if (fnum != 0.0f) {
					bw.write("1");
				} else {
					bw.write("0");
				}

				System.out.println("테스트1");
				bw.flush();
				System.out.println("테스트2");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}