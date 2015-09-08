package user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import camera.FaceTrackingView;
import db.DBProcess;

public class UserPanel extends JPanel {

	private JButton button_gotowork; // ��� ��ư
	public JPanel panel;
	
	public FileClient fc;

	public UserPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		panel = new FaceTrackingView();
		
		
		panel.setBounds(120, 30, 650, 599);
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
	
				new DBProcess().fileIO(null, 1);
				fc = new FileClient(0);

			}
		}
	}
}
