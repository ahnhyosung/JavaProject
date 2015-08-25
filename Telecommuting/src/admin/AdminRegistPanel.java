package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminRegistPanel extends JPanel {
	private JTextField textField_name; // 이름 텍스트 필드
	private JButton button_regist; // 얼굴 등록 버튼

	/**
	 * Create the panel.
	 */
	public AdminRegistPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setBounds(115, 60, 57, 15);
		add(lblNewLabel);

		textField_name = new JTextField();
		textField_name.setBounds(151, 57, 116, 21);
		add(textField_name);
		textField_name.setColumns(10);

		button_regist = new JButton("얼굴 등록");
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
			if (e.getSource() == button_regist) { // 파일 메뉴의 등록 아이템 선택 시
				System.out.println("패턴 등록 완료!");

			}
		}

	}
}
