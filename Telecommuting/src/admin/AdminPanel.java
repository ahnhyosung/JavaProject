package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.MainFrame;

public class AdminPanel extends JPanel {
	private JTextField textfield_id; // ID 텍스트 필드
	private JButton button_connect; // 접속 버튼
	private JPasswordField textfield_pw;

	/**
	 * Create the panel.
	 */
	public AdminPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(320, 217, 57, 15);
		add(lblNewLabel);

		JLabel lblPw = new JLabel("PW");
		lblPw.setBounds(320, 268, 57, 15);
		add(lblPw);

		textfield_id = new JTextField("admin");
		textfield_id.setBounds(362, 214, 116, 21);
		add(textfield_id);
		textfield_id.setColumns(30);

		button_connect = new JButton("접속");
		button_connect.setBounds(526, 213, 88, 70);
		button_connect.addActionListener(new AdminActionListener());
		add(button_connect);

		textfield_pw = new JPasswordField("1234");
		textfield_pw.setBounds(362, 265, 116, 21);
		add(textfield_pw);

	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_connect) { // 접속 버튼을 누를 시
				adminCheck(); // 관리자 ID, PW 체크하는 함수 호출
			}
		}

		private void adminCheck() { // 관리자 ID, PW 체크하는 함수
			if (textfield_id.getText().equals("admin")
					&& textfield_pw.getText().equals("1234")) {
				System.out.println("접속!");
				MainFrame.frame.setJMenuBar(new AdminMenuBar());
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.repaint();
				MainFrame.frame.setTitle("재택근무관리 프로그램 (관리자)");
				MainFrame.frame.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(MainFrame.frame,
						"ID 또는 PW를 다시 확인하세요.", "경고",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}
}
