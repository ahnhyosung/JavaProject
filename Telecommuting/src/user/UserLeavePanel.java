package user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.MainFrame;
import main.MainMenuBar;
import camera.FaceTrackingView;
import db.DBProcess;

public class UserLeavePanel extends JPanel {

	private JButton button_leave; // Ãâ±Ù ¹öÆ°
	public JPanel panel;
	
	public FileClient fc;

	public UserLeavePanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		panel = new FaceTrackingView();
		
		
		panel.setBounds(120, 30, 650, 599);
		add(panel);

		button_leave = new JButton("Åð±Ù");
		button_leave.setBounds(820, 214, 139, 126);
		button_leave.addActionListener(new UserActionListener2());
		add(button_leave);
	}

	class UserActionListener2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_leave) { // Åð±Ù ¹öÆ°À» ´©¸¦ ½Ã
				
				int num = JOptionPane.showConfirmDialog(MainFrame.frame,
						"Åð±Ù(·Î±×¾Æ¿ô) ÇÏ½Ã°Ú½À´Ï±î?", "Åð±Ù", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null);
				if (num == 0) {
					System.out.println("Åð±Ù!");
					new DBProcess().fileIO(null, 1);
					fc = new FileClient(1);
				}
				
				

			}
		}
	}
}
