package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.MainFrame;
import camera.FaceTrackingView;
import db.DBProcess;

public class AdminRegistPanel extends JPanel {
	private JTextField textField_name; // 이름 텍스트 필드
	private JButton button_regist; // 얼굴 등록 버튼

	public DBProcess dbproc;

	private int count;
	public static String[] tempImage = { "test_1.jpg", "test_2.jpg",
			"test_3.jpg" };
	
	public AdminRegistPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setBounds(23, 107, 57, 15);
		add(lblNewLabel);

		textField_name = new JTextField();
		textField_name.setBounds(59, 104, 116, 21);
		add(textField_name);
		textField_name.setColumns(10);

		button_regist = new JButton("얼굴 등록");
		button_regist.addActionListener(new AdminActionListener());
		button_regist.setBounds(59, 155, 97, 23);
		add(button_regist);

		JPanel panel = new FaceTrackingView();
		panel.setBounds(187, 10, 780, 599);
		add(panel);

		for (String str : tempImage) {
			File f = new File("C:\\Temp\\" + str);
			f.delete();
		}

		dbproc = new DBProcess();

	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_regist) { // 파일 메뉴의 등록 아이템 선택 시
				if (!textField_name.getText().isEmpty()) {
					textField_name.setEnabled(false);

					count++;
					if (count < 4) {

						dbproc.fileIO(textField_name.getText(), count);

						if (count == 3) {
							button_regist.setEnabled(false);
							dbproc.closeCon();

							// for (int i = 0; i < tempImage.length; i++) {
							// File fimage = new File("C:\\Temp\\" +
							// tempImage[i]);
							// fimage.delete();
							// }
						}
					}

				} else {
					JOptionPane.showMessageDialog(MainFrame.frame,
							"이름을 입력해주세요!", "경고", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
}
