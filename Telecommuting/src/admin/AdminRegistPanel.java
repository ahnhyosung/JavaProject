package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminRegistPanel extends JPanel {
	private JTextField textField_name; // �̸� �ؽ�Ʈ �ʵ�
	private JButton button_regist; // �� ��� ��ư

	/**
	 * Create the panel.
	 */
	public AdminRegistPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel lblNewLabel = new JLabel("�̸�");
		lblNewLabel.setBounds(115, 60, 57, 15);
		add(lblNewLabel);

		textField_name = new JTextField();
		textField_name.setBounds(151, 57, 116, 21);
		add(textField_name);
		textField_name.setColumns(10);

		button_regist = new JButton("�� ���");
		button_regist.addActionListener(new AdminActionListener());
		button_regist.setBounds(151, 108, 97, 23);
		add(button_regist);
		
		JPanel panel = new JPanel();
		panel.setBounds(57, 164, 805, 452);
		add(panel);

	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_regist) { // ���� �޴��� ��� ������ ���� ��
				System.out.println("���� ��� �Ϸ�!");

			}
		}

	}
}
