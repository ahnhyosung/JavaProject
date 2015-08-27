package user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import admin.FaceTrackingView;
import admin.FileClient;
import main.MainFrame;

public class UserPanel extends JPanel {

	private JButton button_gotowork; // ��� ��ư
	public JPanel panel;
	

	/**
	 * Create the panel.
	 */
	public UserPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		

		panel = new FaceTrackingView();
		
		
		panel.setBounds(120, 10, 650, 599);
		add(panel);

		button_gotowork = new JButton("���");
		button_gotowork.setBounds(820, 214, 139, 126);
		button_gotowork.addActionListener(new UserActionListener());
		add(button_gotowork);

	}

	class UserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_gotowork) { // ��� ��ư�� ���� ��
				FaceTrackingView.closeCamera();
				
				new FileClient();
				
				System.out.println("���!");

				MainFrame.frame.setJMenuBar(new UserMenuBar());
				MainFrame.contentPane.removeAll();
				MainFrame.contentPane.repaint();
				MainFrame.frame.setTitle("���ñٹ����� ���α׷� (�����)");
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
