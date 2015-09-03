package admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class AdminChatConnect extends Thread {

	private ServerSocket serverSocket;
	private Socket socket;
	public Vector<ServerThread> vector = new Vector<>();
	public Vector<String> vector_user_list = new Vector<>();
	private AdminChatPanel admin;

	private boolean flag = true;

	public AdminChatConnect(AdminChatPanel admin, int port) {
		this.admin = admin;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("���� ����");
			admin.admin_chatFrame(port);

		} catch (IOException e) {
			System.out.println("���������ϴµ� ������ ������ �ʴ´�.");
		}

		this.start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				socket = serverSocket.accept();

				System.out.println("���� �Ͽ����ϴ�.");

				ServerThread thread = new ServerThread(this, socket);
				vector.add(thread);
				vector.get(vector.size() - 1).start();

				if (flag) {
					vector.get(vector.size() - 1).userName = "������";
					broadcast("NewUser:������");
					flag = false;

				} else {
					for (String name : vector_user_list) {
						vector.get(vector.size() - 1).speakToMsg(
								"OriUser:" + name);
					}

				}

			}

		} catch (IOException e) {
			System.out.println(serverSocket);
		}

	}

	public void ExitServer() {
		try {
			for (int i = 0; i < vector.size(); i++) {
				System.out.println("����1 " + i);
				vector.get(i).interrupt();
				System.out.println("����2 " + i);
				// vector.get(i).br.close();
				// System.out.println("����3 " + i);
				// vector.get(i).bw.close();
				System.out.println("����4 " + i);
				vector.get(i).s.close();
				System.out.println("����5 " + i);

			}
			vector.clear();
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("�������� ������.");
		}
	}

	public void broadcast(String msg) {

		StringTokenizer sToken = new StringTokenizer(msg, ":");
		if (sToken.nextToken().equals("NewUser")) {
			vector_user_list.add(sToken.nextToken());
		}

		for (ServerThread st : vector) {
			st.speakToMsg(msg);
		}
	}

}