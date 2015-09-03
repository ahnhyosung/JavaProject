package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Listen extends Thread {
	BufferedReader br;
	private UserChatPanel user;
	Vector<String> name = new Vector<String>();

	public Listen(UserChatPanel user, BufferedReader br) {
		this.user = user;
		this.br = br;
		this.start();
	}

	@Override
	public void run() {
		if (user.socket != null && user.socket.isConnected()) {
			try {
				while (true) {
					String msg;
					msg = br.readLine();
					StringTokenizer sToken = new StringTokenizer(msg, ":");
					String str = sToken.nextToken();
					if (str.equals("NewUser") || str.equals("OriUser")) {

						name.add(sToken.nextToken());
						user.setListParticipant(name);
					} else if (str.equals("OutUser")) {
						name.remove(Integer.parseInt(sToken.nextToken()));
						user.setListParticipant(name);
					} else if (msg.equals("관리자가 서버를 종료합니다.")){
						name.removeAllElements();
						user.setListParticipant(name);
						user.textArea_content.append(msg);
						user.enableTextField();
						user.button_participate.setEnabled(true);
						user.button_exit.setEnabled(false);
					} else {
						user.setTextArea_content(msg);
					}

				}
			} catch (NullPointerException e1) {
				try {
					this.interrupt();
					br.close();
					user.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				try {
					br.close();
				} catch (IOException e1) {
				}
				System.out.println("client socket close");
			}
		}
	}

}
