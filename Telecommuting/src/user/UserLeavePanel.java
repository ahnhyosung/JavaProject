package user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.MainFrame;
import main.MainMenuBar;

public class UserLeavePanel extends JPanel {

	private JButton button_leave; // ��� ��ư

	public UserLeavePanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		ImageIcon img_user = new ImageIcon("img/user.PNG");
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(183, 10, 624, 596);
		lblNewLabel.setIcon(img_user);
		add(lblNewLabel);

		button_leave = new JButton("���");
		button_leave.setBounds(719, 214, 139, 126);
		button_leave.addActionListener(new UserActionListener());
		add(button_leave);

	}

	class UserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_leave) { // ��� ��ư�� ���� ��

				int num = JOptionPane.showConfirmDialog(MainFrame.frame,
						"���(�α׾ƿ�) �Ͻðڽ��ϱ�?", "���", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null);
				if (num == 0) {
					System.out.println("���!");

					MainFrame.frame.setJMenuBar(new MainMenuBar());
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.repaint();
					MainFrame.frame.setTitle("���ñٹ����� ���α׷�");
					MainFrame.frame.setVisible(true);
				}
			}
		}

	}
}
