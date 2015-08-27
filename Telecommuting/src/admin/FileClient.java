package admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import main.MainFrame;
import user.UserMenuBar;

public class FileClient {
	private String hostname;
	private int port;
	
	BufferedReader br;

	private Socket s;
	

	public FileClient(){
		connect();
	}
	public FileClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		connect();
	}
	
	class Listen extends Thread {
		
		public Listen() {
			try {
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				int num = br.read();
				
				if (num == 1) {
					MainFrame.frame.setJMenuBar(new UserMenuBar());

//					FaceTrackingView.drawingTimer.stop();

					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.repaint();
					MainFrame.frame.setTitle("재택근무관리 프로그램 (사용자)");
					MainFrame.frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainFrame.frame,
							"일치하는 얼굴이 없습니다.", "경고",
							JOptionPane.WARNING_MESSAGE);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void connect() {
		s = new Socket();
		
		try {
			s.connect( new InetSocketAddress("127.0.0.1", 9999) );
			
			new Listen().start();
			
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