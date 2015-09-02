package admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ServerThread extends Thread {

	BufferedReader br;
	BufferedWriter bw;
	private AdminChatConnect server;
	Socket s;

	public ServerThread(AdminChatConnect server, Socket socket) {
		this.server = server;
		s = socket;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

		} catch (IOException e) {
			System.out.println("������ �����ϴ�." + s);
		}

	}

	@Override
	public void run() {
		try {
			while (true) {
				String msg = br.readLine();
				server.broadcast(msg);
			}

		} catch (IOException e) {
			this.interrupted();
		}

	}

	public void speakToMsg(String msg) {
		try {
			bw.write(msg + "\n");
			bw.flush();

		} catch (IOException e) {
			try {
				bw.close();
			} catch (IOException e1) {
				System.out.println("��Ƽ ������ ���� ��¿� ���� ����");
			}
		}

	}

}
