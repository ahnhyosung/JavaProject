package user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainFrame;

public class UserPanel extends JPanel {

	private JButton button_gotowork; // 출근 버튼

	/**
	 * Create the panel.
	 */
	public UserPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		ImageIcon img_user = new ImageIcon("img/user.PNG");
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(183, 10, 624, 596);
		lblNewLabel.setIcon(img_user);
		add(lblNewLabel);

		button_gotowork = new JButton("출근");
		button_gotowork.setBounds(719, 214, 139, 126);
		button_gotowork.addActionListener(new UserActionListener());
		add(button_gotowork);

	}

	class UserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_gotowork) { // 출근 버튼을 누를 시
				System.out.println("출근!");

				MainFrame.frame.setJMenuBar(new UserMenuBar());
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.repaint();
				MainFrame.frame.setTitle("재택근무관리 프로그램 (사용자)");
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
