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
	private JTextField textfield_id; // ID �ؽ�Ʈ �ʵ�
	private JButton button_connect; // ���� ��ư
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

		button_connect = new JButton("����");
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
			if (e.getSource() == button_connect) { // ���� ��ư�� ���� ��
				adminCheck(); // ������ ID, PW üũ�ϴ� �Լ� ȣ��
			}
		}

		private void adminCheck() { // ������ ID, PW üũ�ϴ� �Լ�
			if (textfield_id.getText().equals("admin")
					&& textfield_pw.getText().equals("1234")) {
				System.out.println("����!");
				MainFrame.frame.setJMenuBar(new AdminMenuBar());
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.repaint();
				MainFrame.frame.setTitle("���ñٹ����� ���α׷� (������)");
				MainFrame.frame.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(MainFrame.frame,
						"ID �Ǵ� PW�� �ٽ� Ȯ���ϼ���.", "���",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}
}
