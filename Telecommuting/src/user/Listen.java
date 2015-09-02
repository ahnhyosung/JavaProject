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
	
	public Listen(UserChatPanel user,BufferedReader br) {
		this.user = user;
		this.br = br;
		this.start();
	}
	
	@Override
	public void run() {
		if(user.socket.isConnected()){
			try {
				while(true){
					String msg;
					msg = br.readLine();
					StringTokenizer sToken = new StringTokenizer(msg, ":");
					String str = sToken.nextToken();
					if(str.equals("NewUser") || str.equals("OriUser")) {
						
						name.add(sToken.nextToken());
						user.setListParticipant(name);
					} else {
						user.setTextArea_content(msg);
					}
					
				}
			} catch (IOException e) {
				try {
					br.close();
				} catch (IOException e1) {}
				System.out.println("client socket close");
			}
		}else{
			try {
				user.socket.close();
			} catch (IOException e) {
			}
		}
		
	}
	

}
