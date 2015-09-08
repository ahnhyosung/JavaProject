package user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import camera.FaceTrackingView;
import db.DBProcess;

public class UserPanel extends JPanel {

	private JButton button_gotowork; // 출근 버튼
	public JPanel panel;
	
	public FileClient fc;

	public UserPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		panel = new FaceTrackingView();
		
		
		panel.setBounds(120, 30, 650, 599);
		add(panel);

		button_gotowork = new JButton("출근");
		button_gotowork.setBounds(820, 214, 139, 126);
		button_gotowork.addActionListener(new UserActionListener());
		add(button_gotowork);
	}

	class UserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_gotowork) { // 출근 버튼을 누를 시
	
				new DBProcess().fileIO(null, 1);
				fc = new FileClient(0);

			}
		}
	}
}
