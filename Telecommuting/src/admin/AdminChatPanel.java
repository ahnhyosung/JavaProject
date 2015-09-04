package admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ButtonUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import org.omg.CORBA.IdentifierHelper;
import org.omg.Messaging.SyncScopeHelper;
import org.omg.PortableInterceptor.ServerRequestInfoOperations;

// 바뀐거 확인용2
public class AdminChatPanel extends JPanel {
	private JTextField textField_sentence; // 대화 입력 텍스트 필드
	private JTextField textField_portnum; // 포트 번호 입력 테스트 필드
	private JButton button_create; // 회의방 개설 버튼
	private JButton button_exit; // 회의방 종료 버튼
	private JTextArea textArea_content;
	private JList list_participant;

	// private ServerSocket serverSocket;
	private Socket mySocket;
	private BufferedReader serverBr;
	private BufferedWriter serverBw;

	private AdminChatConnect serverStart;
	private AdminChatPanel admin = this;

	Vector<String> name = new Vector<String>();

	/**
	 * Create the panel.
	 */
	public AdminChatPanel() {
		setLayout(null);

		JButton button_user3 = new JButton("사용자3");
		button_user3.setBounds(667, 287, 294, 257);
		add(button_user3);

		JButton button_user1 = new JButton("사용자1");
		button_user1.setBounds(667, 26, 294, 257);
		add(button_user1);

		JButton button_admin = new JButton("관리자");
		button_admin.setBounds(361, 26, 294, 257);
		add(button_admin);

		JButton button_user2 = new JButton("사용자2");
		button_user2.setBounds(361, 287, 294, 257);
		add(button_user2);

		textField_sentence = new JTextField();
		textField_sentence.setBounds(12, 554, 949, 21);
		add(textField_sentence);
		textField_sentence.setColumns(10);

		textField_sentence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sendMsg = textField_sentence.getText();
				try {
					serverBw.write("관리자: " + sendMsg + "\n");
					serverBw.flush();
					textField_sentence.setText("");

				} catch (IOException | NullPointerException e2) {
					textArea_content.append("채팅방이 닫혀있습니다.\n");
					textField_sentence.setText("");
				}

			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "참가자", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 102, 343, 113);
		add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 17, 326, 86);
		panel_1.add(scrollPane);

		list_participant = new JList();
		scrollPane.setViewportView(list_participant);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "대화 내용", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(12, 225, 343, 322);
		add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 17, 325, 295);
		panel.add(scrollPane_1);

		textArea_content = new JTextArea();
		scrollPane_1.setViewportView(textArea_content);
		textArea_content.setEditable(false);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(30, 26, 77, 15);
		add(lblNewLabel);
		textField_portnum = new JTextField();
		textField_portnum.setBounds(109, 23, 116, 21);
		add(textField_portnum);
		textField_portnum.setColumns(10);

		button_create = new JButton("개설");
		button_create.setBounds(30, 69, 97, 23);
		button_create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkPort() != -1) {
					int port = checkPort();
					textField_portnum.setEditable(false);
					button_create.setEnabled(false);
					button_exit.setEnabled(true);
					textArea_content.setText("");
					serverStart = new AdminChatConnect(admin, port);
				}
			}
		});
		add(button_create);

		button_exit = new JButton("종료");
		button_exit.setBounds(139, 69, 97, 23);
		button_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serverStart.broadcast("관리자가 서버를 종료합니다.");
					serverStart.ExitServer();
					System.out.println(1);
					serverBw.close();
					System.out.println(2);
					serverBr.close();
					System.out.println(3);
					mySocket.close();
					System.out.println(4);
					textField_portnum.setEditable(true);
					button_create.setEnabled(true);
					button_exit.setEnabled(false);
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "회의방을 먼저 개설하세요");
				} catch (IOException e1) {
					System.out.println("관리자가 종료하는데 소켓에 문제가 생겼습니다.");
				}
			}

		});
		button_exit.setEnabled(false);
		add(button_exit);

	}

	// /////////////////////////////////////////////////////// 방개설 채팅

	public void admin_chatFrame(int port) {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			mySocket = new Socket(ip, port);
			serverBr = new BufferedReader(new InputStreamReader(
					mySocket.getInputStream()));
			serverBw = new BufferedWriter(new OutputStreamWriter(
					mySocket.getOutputStream()));
		} catch (NumberFormatException e) {
			System.out.println("서버가 채팅하고싶은데  ip랑 port의 자료형이 맞지 않는다.");
		} catch (IOException e) {
			System.out.println("포트 중복인데 일어날일이 없다.");
			System.out.println("이상없음");
			System.out.println(e.getMessage());
			// ServerListen.interrupted();
		}

		new ServerListen().start();

	}

	class ServerListen extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					System.out.println("msg를 받기전");
					String msg = serverBr.readLine();
					System.out.println("msg : " + msg);

					if (msg != null) {

						StringTokenizer sToken = new StringTokenizer(msg, ":");
						String str = sToken.nextToken();
						System.out.println(msg + ", 여길 안타???ㄷㄷㄷ");

						if (str.equals("NewUser")) {
							name.add(sToken.nextToken());
							list_participant.setListData(name);
							System.out.println("JList에 " + name
									+ "가 추가됨, name의 크기 : " + name.size());
						} else if (str.equals("OutUser")) {
							System.out.println("요까지옴");
							String tempName = sToken.nextToken();
							name.remove(Integer.parseInt(tempName));
							list_participant.setListData(name);
							textArea_content.append(tempName + "님이 종료했습니다.\n");
						} else if (msg.equals("관리자가 서버를 종료합니다.")) {
							name.removeAllElements();
							list_participant.setListData(name);
							textArea_content.setText("");
						} else {

							textArea_content.append(msg + "\n");
							textArea_content.setCaretPosition(textArea_content
									.getText().length());

						}

					} else {
						throw new IOException();
					}

				} catch (IOException e) {
					try {
						ServerListen.interrupted();
						serverBr.close();
						serverBw.close();
						mySocket.close();

						break;

					} catch (IOException e1) {
					}

				}

			}

		}
	}

	public int checkPort() {
		String getPort = textField_portnum.getText();
		int port;
		ServerSocket checkSocket = null;
		try {
			port = Integer.parseInt(getPort);
			if (port > 2000 && port <= 64000) {
				checkSocket = new ServerSocket(port);
				checkSocket.close();
			} else {
				textField_portnum.setText("");
				JOptionPane.showMessageDialog(null, "포트번호를 제대로 입력했는지 확인하세요");
				port = -1;
			}
		} catch (NumberFormatException e) {
			textField_portnum.setText("");
			JOptionPane.showMessageDialog(null, "숫자를 입력하세요");
			port = -1;
		} catch (IOException e) {
			textField_portnum.setText("");
			JOptionPane.showMessageDialog(null, "이미 사용되고 있는 포트번호 입니다.");
			port = -1;
		}
		return port;
	}

}
