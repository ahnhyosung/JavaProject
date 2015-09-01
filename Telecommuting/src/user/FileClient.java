package user;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import main.MainFrame;
import camera.FaceTrackingView;

public class FileClient {
	private String hostname;
	private int port;

	public Socket socket;
	private BufferedOutputStream out;

	public FileClient() {
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
			new Listen(socket).start();
			new Speak(socket).start();
		} catch (ConnectException e) {
			System.out.println("������ ������ ������ �� �����ϴ�.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class Listen extends Thread {
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

		@Override
		public void run() {
			try {
				while (true) {

					System.out.println("�д� ��");
					String match_file_name = br.readLine();
					System.out.println("�о���!");

					if (!match_file_name.equals(null)) {

						FaceTrackingView.drawingTimer.stop();
						FaceTrackingView.closeCamera();
						
						System.out.println("Ŭ���̾�Ʈ���� ���� : " + match_file_name);
						MainFrame.frame.setJMenuBar(new UserMenuBar(match_file_name));

						MainFrame.contentPane.removeAll();
						MainFrame.contentPane.repaint();
						MainFrame.frame.setTitle("���ñٹ����� ���α׷� (�����)");
						MainFrame.frame.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(MainFrame.frame,
								"��ġ�ϴ� ���� �����ϴ�.", "���",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public class Speak extends Thread {

		Socket speak_socket;
		BufferedOutputStream out;

		public Speak(Socket speak_socket) {
			this.speak_socket = speak_socket;

			try {
				out = new BufferedOutputStream(
						this.speak_socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			sendImage();
		}

		public void sendImage() {
			try {

				byte[] buffer = new byte[256];
				int bytesRead = 0;

				FileInputStream fileIn = new FileInputStream(
						"C:\\Temp\\test_1.jpg");

				DataInputStream dis = new DataInputStream(fileIn);

				while (true) {
					bytesRead = dis.read(buffer);
					System.out.println(bytesRead);

					if (bytesRead == -1) {
						break;
					}

					out.write(buffer, 0, bytesRead);

				}
				out.flush();
				System.out.println("client exit");

				dis.close();
				// out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}