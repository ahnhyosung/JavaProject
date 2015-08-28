package admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import main.MainFrame;
import user.UserMenuBar;

public class FileClient {
	private String hostname;
	private int port;

	private Socket socket;
	private BufferedOutputStream out;

	// public FileClient(){
	// connect();
	// new Listen(s).start();
	// }

	public FileClient() {
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
			new Listen(socket).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Listen extends Thread {
		Socket listen_socket;
		BufferedReader br;

		public Listen(Socket listen_socket) {
			this.listen_socket = listen_socket;

			try {
				br = new BufferedReader(new InputStreamReader(
						listen_socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// ee
		@Override
		public void run() {
			try {
				while (true) {
					String num = br.readLine();

					if (num == "1") {
						MainFrame.frame.setJMenuBar(new UserMenuBar());

						// FaceTrackingView.drawingTimer.stop();

						MainFrame.contentPane.removeAll();
						MainFrame.contentPane.repaint();
						MainFrame.frame.setTitle("재택근무관리 프로그램 (사용자)");
						MainFrame.frame.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(MainFrame.frame,
								"일치하는 얼굴이 없습니다.", "경고",
								JOptionPane.WARNING_MESSAGE);
					}
				}

				// s.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendImage() {
		try {

			InputStream in;
			try {
				in = new FileInputStream("C:\\Temp\\test.jpg");
				OutputStream out = new FileOutputStream("C:\\Temp\\test" + "_"
						+ 1 + ".jpg");

				int bData;

				while (true) {
					bData = in.read();
					if (bData == -1)
						break;

					out.write(bData);
				}
				in.close();
				out.close();

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			byte[] buffer = new byte[10000];
			int bytesRead = 0;
			out = new BufferedOutputStream(socket.getOutputStream());
			
			FileInputStream fileIn = new FileInputStream("C:\\Temp\\test" + "_"
					+ 1 + ".jpg");
			
			while ((bytesRead = fileIn.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			fileIn.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}