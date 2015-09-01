package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import user.*;

public class UserChatPanel extends JPanel {

	
	private JTextField textField_sentence; // ��ȭ �Է� �ؽ�Ʈ �ʵ�
	private JTextField textField_ip; // IP �Է� �׽�Ʈ �ʵ�
	private JTextField textField_portnum; // ��Ʈ ��ȣ �Է� �׽�Ʈ �ʵ�
	private JButton button_participate; // ȸ�ǹ� ���� ��ư
	private JButton button_exit; // ȸ�ǹ� ���� ��ư
	private JPanel panel;		// ��ȭ���� �г�
	private JTextArea textArea_content;		//��ȭ ���� area �����ϰ� ����		
	private JTextArea textArea_participant; // ä�ù� �����ڵ� ���
	
	private BufferedReader br;
	private BufferedWriter bw; 
	Socket socket;
	private Listen listen;
	private UserChatPanel user = this;
	
	/**
	 * Create the panel.
	 */

	public void setTextArea_content(String msg) {
		textArea_content.append(msg + "\n");
		textArea_content.setCaretPosition(textArea_content.getText().length());
		System.out.println(msg);
	}

	public void setTextArea_participant(String msg) {
		textArea_participant.append(msg + "\n");
		textArea_participant.setCaretPosition(textArea_participant.getText().length());
	}
	
	public UserChatPanel() {
		
		setLayout(null);

		JButton button_user3 = new JButton("�����3");
		button_user3.setBounds(667, 287, 294, 257);
		add(button_user3);

		JButton button_user1 = new JButton("�����1");
		button_user1.setBounds(667, 26, 294, 257);
		add(button_user1);

		JButton button_admin = new JButton("������");
		button_admin.setBounds(361, 26, 294, 257);
		add(button_admin);

		JButton button_user2 = new JButton("�����2");
		button_user2.setBounds(361, 287, 294, 257);
		add(button_user2);

		textField_sentence = new JTextField();
		textField_sentence.setBounds(12, 554, 949, 21);
		textField_sentence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sendMsg = textField_sentence.getText();
				try {
					bw.write(sendMsg + "\n");
					bw.flush();
					textField_sentence.setText("");
				} catch (IOException e1) {
					textArea_content.append("ä�ù��� �������ϴ�.\n");
				}
			}
		});
		add(textField_sentence);
		textField_sentence.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "������", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 147, 343, 113);
		add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 17, 326, 86);
		panel_1.add(scrollPane);
		
		textArea_participant = new JTextArea();
		scrollPane.setViewportView(textArea_participant);
//		list_participant.setAutoscrolls(true);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "��ȭ ����", TitledBorder.LEADING,TitledBorder.TOP, null, null));
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

		button_participate = new JButton("����");
		button_participate.setBounds(70, 114, 97, 23);
		button_participate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = textField_ip.getText();
				String port = textField_portnum.getText();
				try {
					socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
					System.out.println("���ӵǾ����ϴ�.");
					textField_ip.setEditable(false);
					textField_portnum.setEditable(false);
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

					bw.write("���� id�� ������" + "\n");
					bw.flush();
					
				} catch (NumberFormatException e1) {
					System.out.println("ip�� port ����ȯ ����!");
				} catch (UnknownHostException e1) {
					System.out.println("�ش� ip�� ���� �������� �ʽ��ϴ�.");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "connerct error");
				}
				
				listen = new Listen(user, br);				
			}
		});
		add(button_participate);

		button_exit = new JButton("����");
		button_exit.setBounds(179, 114, 97, 23);
		button_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textField_ip.setEditable(true);
					textField_portnum.setEditable(true);
					bw.close();
					socket.close();
					
					
					
					listen.interrupted();
				} catch (NullPointerException e1) {
					System.out.println("null");
				} catch (IOException e1) {
					while(true){
						System.out.println("������ �Ȳ���");
					}
				}
					
			}
		});
		add(button_exit);
	}
	
}


