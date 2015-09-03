package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import admin.AdminPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import user.*;

public class UserChatPanel extends JPanel {

	
	private JTextField textField_sentence; // 대화 입력 텍스트 필드
	public JTextField textField_ip; // IP 입력 테스트 필드
	public JTextField textField_portnum; // 포트 번호 입력 테스트 필드
	public JButton button_participate; // 회의방 참가 버튼
	public JButton button_exit; // 회의방 종료 버튼
	private JPanel panel;		// 대화내용 패널
	public JTextArea textArea_content;		//대화 내용 area 소켓하고 연결		
	JList list_participant; // 채팅방 참가자들 목록
	
	private BufferedReader br;
	private BufferedWriter bw; 
	Socket socket;
	private Listen listen;
	private UserChatPanel user = this;
	private String userName;
	/**
	 * Create the panel.
	 */

	public void setTextArea_content(String msg) {
		textArea_content.append(msg + "\n");
		textArea_content.setCaretPosition(textArea_content.getText().length());
		System.out.println(msg);
	}
	
	public UserChatPanel(String userName) {
		this.userName = userName;
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
		textField_sentence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sendMsg = textField_sentence.getText();
				try {
					bw.write(userName+": "+sendMsg + "\n");
					bw.flush();
					textField_sentence.setText("");
				} catch (IOException e1) {
					textArea_content.append("채팅방이 닫혔습니다.\n");
				}
			}
		});
		add(textField_sentence);
		textField_sentence.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "참가자", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 147, 343, 113);
		add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 17, 326, 86);
		panel_1.add(scrollPane);
		
		list_participant = new JList();
		scrollPane.setViewportView(list_participant);
//		list_participant.setAutoscrolls(true);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "대화 내용", TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel.setBounds(12, 269, 343, 278);
		add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 17, 325, 251);
		panel.add(scrollPane_1);
		
		textArea_content = new JTextArea();
		scrollPane_1.setViewportView(textArea_content);
		textArea_content.setEditable(false);
//		textArea_content.setAutoscrolls(true);
		
		JLabel lblNewLabel = new JLabel("IP");
		lblNewLabel.setBounds(81, 41, 77, 15);
		add(lblNewLabel);

		textField_ip = new JTextField();
		textField_ip.setBounds(160, 38, 116, 21);
		add(textField_ip);
		textField_ip.setColumns(10);
		
		JLabel label = new JLabel("Port Number");
		label.setBounds(81, 69, 77, 15);
		add(label);

		textField_portnum = new JTextField();
		textField_portnum.setColumns(10);
		textField_portnum.setBounds(160, 66, 116, 21);
		add(textField_portnum);

		button_participate = new JButton("참가");
		button_participate.setBounds(70, 114, 97, 23);
		button_participate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = textField_ip.getText();
				String port = textField_portnum.getText();
				try {
					socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
					System.out.println("접속되었습니다.");
					textField_ip.setEditable(false);
					textField_portnum.setEditable(false);
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

					bw.write("NewUser:" + userName + "\n");
					bw.flush();
					
					listen = new Listen(user, br);	
					button_participate.setEnabled(false);
					button_exit.setEnabled(true);
					
				} catch (NumberFormatException e1) {
					System.out.println("ip와 port 형변환 문제!");
				} catch (UnknownHostException e1) {
					System.out.println("해당 ip에 방이 존재하지 않습니다.");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "connerct error");
				}
				
			}
		});
		add(button_participate);

		button_exit = new JButton("종료");
		button_exit.setBounds(179, 114, 97, 23);
		button_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textField_ip.setEditable(true);
					textField_portnum.setEditable(true);
					
					listen.interrupt();
					bw.close();
					br.close();
					socket.close();
					
					list_participant.setModel(new DefaultListModel());
					textArea_content.setText(null);
					
				} catch (NullPointerException e1) {
					System.out.println("null");
				} catch (IOException e1) {
					while(true){
						System.out.println("소켓이 안끊김");
					}
				}
				
				button_participate.setEnabled(true);
				button_exit.setEnabled(false);
					
			}
		});
		button_exit.setEnabled(false);
		add(button_exit);
	}

	public void setListParticipant(Vector<String> name) {
		list_participant.setListData(name);
	}
	
	public void enableTextField() {
		System.out.println("음???");
		textField_ip.setEnabled(true);
		textField_portnum.setEnabled(true);
	}
	
}


