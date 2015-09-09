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
import main.MainMenuBar;
import camera.FaceTrackingView;
import db.DBProcess;

public class FileClient {
	private String hostname;
	private int port;

	public Socket socket;
	private BufferedOutputStream out;

	public FileClient(int i) {
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
			new Listen(socket, i).start();
			new Speak(socket).start();
		} catch (ConnectException e) {
			System.out.println("관리자 서버에 접속할 수 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class Listen extends Thread {
		Socket listen_socket;
		BufferedReader br;
		int attORlev;

		public Listen(Socket listen_socket, int i) {
			this.listen_socket = listen_socket;
			attORlev = i;

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

					System.out.println("읽는 중");
					String match_file_name = br.readLine();
					System.out.println("읽었당!");

					if (!match_file_name.equals("error")) {

						FaceTrackingView.drawingTimer.stop();
						FaceTrackingView.closeCamera();

						System.out.println("클라이언트에서 받음 : " + match_file_name);
						MainFrame.frame.setJMenuBar(new UserMenuBar(
								match_file_name));

						if (attORlev == 0) {
							MainFrame.contentPane.removeAll();
							MainFrame.contentPane.repaint();
							MainFrame.frame.setTitle("재택근무관리 프로그램 (사용자)");
							MainFrame.frame.setVisible(true);

							new DBProcess().userAttendance(match_file_name);
							
						} else if (attORlev == 1) {
							MainFrame.frame.setJMenuBar(new MainMenuBar());
							MainFrame.contentPane.removeAll();
							MainFrame.contentPane.repaint();
							MainFrame.frame.setTitle("재택근무관리 프로그램");
							MainFrame.frame.setVisible(true);
							
							new DBProcess().userLeave(match_file_name);
						}

						break;
					} else {
						JOptionPane.showMessageDialog(MainFrame.frame,
								"일치하는 얼굴이 없습니다.", "경고",
								JOptionPane.WARNING_MESSAGE);
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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