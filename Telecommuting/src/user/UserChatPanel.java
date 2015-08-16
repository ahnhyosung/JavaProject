package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class UserChatPanel extends JPanel {

	private JTextField textField_sentence; // 대화 입력 텍스트 필드
	private JTextField textField_ip; // IP 입력 테스트 필드
	private JTextField textField_portnum; // 포트 번호 입력 테스트 필드
	private JButton button_participate; // 회의방 참가 버튼
	private JButton button_exit; // 회의방 종료 버튼

	/**
	 * Create the panel.
	 */
	public UserChatPanel() {
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

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "참가자", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 147, 343, 113);
		add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 17, 326, 86);
		panel_1.add(scrollPane);

		JList list_participant = new JList();
		scrollPane.setViewportView(list_participant);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "대화 내용", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(12, 269, 343, 278);
		add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 17, 325, 251);
		panel.add(scrollPane_1);

		JTextArea textArea_content = new JTextArea();
		scrollPane_1.setViewportView(textArea_content);

		JLabel lblNewLabel = new JLabel("IP");
		lblNewLabel.setBounds(81, 41, 77, 15);
		add(lblNewLabel);

		textField_ip = new JTextField();
		textField_ip.setBounds(160, 38, 116, 21);
		add(textField_ip);
		textField_ip.setColumns(10);

		UserActionListener action = new UserActionListener();

		button_participate = new JButton("참가");
		button_participate.setBounds(70, 114, 97, 23);
		button_participate.addActionListener(action);
		add(button_participate);

		button_exit = new JButton("종료");
		button_exit.setBounds(179, 114, 97, 23);
		button_exit.addActionListener(action);
		add(button_exit);

		JLabel label = new JLabel("Port Number");
		label.setBounds(81, 69, 77, 15);
		add(label);

		textField_portnum = new JTextField();
		textField_portnum.setColumns(10);
		textField_portnum.setBounds(160, 66, 116, 21);
		add(textField_portnum);
	}

	class UserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_participate) { // 회의방 참가 버튼 선택 시
				System.out.println("방 참가!");

			} else if (e.getSource() == button_exit) { // 회의방 종료 버튼 선택 시
				System.out.println("방 종료!");

			}
		}

	}

}
