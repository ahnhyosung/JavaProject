package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class test extends JPanel {
	private JTextField textField_name; // �̸� �ؽ�Ʈ �ʵ�
	private JButton button_regist; // �� ��� ��ư

	/**
	 * Create the panel.
	 */
	public test() {
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel lblNewLabel = new JLabel("�̸�");
		lblNewLabel.setBounds(23, 107, 57, 15);
		add(lblNewLabel);

		textField_name = new JTextField();
		textField_name.setBounds(59, 104, 116, 21);
		add(textField_name);
		textField_name.setColumns(10);

		button_regist = new JButton("�� ���");
		button_regist.addActionListener(new AdminActionListener());
		button_regist.setBounds(59, 155, 97, 23);
		add(button_regist);

		JPanel panel = new FaceTrackingView();
		panel.setBounds(187, 10, 780, 599);
		add(panel);
		
		JLabel lblNewLabel_1 = new JLabel();
//		lblNewLabel_1.setIcon(new ImageIcon("/img/checkdisable.PNG"));
		lblNewLabel_1.setBounds(1, 191, 50, 43);
		add(lblNewLabel_1);
		
		JLabel label = new JLabel();
//		label.setIcon(new ImageIcon("/img/checkdisable.PNG"));
		label.setBounds(59, 190, 50, 43);
		add(label);
		
		JLabel label_1 = new JLabel();
//		label_1.setIcon(new ImageIcon("/img/checkdisable.PNG"));
		label_1.setBounds(121, 193, 50, 43);
		add(label_1);

	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_regist) { // ���� �޴��� ��� ������ ���� ��
				System.out.println("���� ��� �Ϸ�!");

				InputStream in;
				try {
					in = new FileInputStream("C:\\Temp\\test.jpg");
					OutputStream out = new FileOutputStream(
							"C:\\Temp\\test_copy.jpg");

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
			}
		}

	}
}
