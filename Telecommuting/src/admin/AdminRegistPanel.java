package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Luxand.FSDK;

public class AdminRegistPanel extends JPanel {
	private JTextField textField_name; // 이름 텍스트 필드
	private JButton button_regist; // 얼굴 등록 버튼

	private int count = 1;

	/**
	 * Create the panel.
	 */
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
		
		String[] tempImage = {"test_1.jpg", "test_2.jpg", "test_3.jpg"};
		for (String str : tempImage) {
			File f = new File("C:\\Temp\\" + str);
			f.delete();
		}
		
	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_regist) { // 파일 메뉴의 등록 아이템 선택 시

					System.out.println("패턴 등록 완료!");

					InputStream in;
					try {
						in = new FileInputStream("C:\\Temp\\test.jpg");
						OutputStream out = new FileOutputStream(
								"C:\\Temp\\test" + "_" + count + ".jpg");

						int bData;

						while (true) {
							bData = in.read();
							if (bData == -1)
								break;

							out.write(bData);
						}
						in.close();
						out.close();

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					count++;
					
					if (count == 4) {
						button_regist.setEnabled(false);
						textField_name.setEnabled(false);
					}
					
			}
		}

	}
}
