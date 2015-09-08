package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import db.DBProcess;

import javax.swing.JLabel;

import main.MainFrame;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;

public class test extends JPanel {
	private JTable table; // ���̺�
	private JComboBox comboBox_name; // �̸� �޺��ڽ�
	private JComboBox comboBox_state; // ������ �޺��ڽ�
	private JButton button_search; // �˻� ��ư
	private DBProcess dbprocess;

	JPanel panel = new JPanel();
	JScrollPane scrollPane;
	private JTextField textField_date;

	/**
	 * Create the panel.
	 */
	public test() {
		setLayout(null);
		setBackground(Color.WHITE);

		dbprocess = new DBProcess();

		Border border = BorderFactory.createTitledBorder("�˻� ����");
		panel = new JPanel();
		panel.setBorder(border);
		panel.setBounds(52, 10, 893, 280);
		panel.setBackground(Color.WHITE);
		add(panel);
		panel.setLayout(null);

		comboBox_name = new JComboBox();
		comboBox_name.setBounds(629, 52, 170, 36);

		comboBox_name.setModel(new DefaultComboBoxModel(dbprocess
				.searchUserAll()));
		panel.add(comboBox_name);

		comboBox_state = new JComboBox();
		comboBox_state.setModel(new DefaultComboBoxModel(new String[] { "",
				"���", "���(����)", "���", "���(�ٹ��ð�����)" }));
		comboBox_state.setBounds(629, 144, 170, 36);
		panel.add(comboBox_state);

		JLabel label = new JLabel("\uC774\uB984 :");
		label.setBounds(551, 64, 66, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("\uB0A0\uC9DC :");
		label_1.setBounds(551, 110, 66, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("\uCD9C\uACB0\uC0C1\uD0DC :");
		label_2.setBounds(551, 156, 66, 15);
		panel.add(label_2);

		button_search = new JButton("�˻�");
		button_search.setBounds(551, 191, 248, 37);
		panel.add(button_search);
		
		textField_date = new JTextField();
		textField_date.setBounds(629, 98, 170, 36);
		panel.add(textField_date);
		textField_date.setColumns(10);
		
		admin.SwingCalender swingCalender = new admin.SwingCalender();
		swingCalender.setBackground(Color.WHITE);
		swingCalender.setBounds(67, 10, 442, 260);
		panel.add(swingCalender);
		button_search.addActionListener(new AdminActionListener());
		add(new JScrollPane());

		scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBounds(52, 300, 893, 280);
		add(scrollPane);

		Vector<Vector<String>> userList = dbprocess.searchUser("", "", "");
		Vector<String> columnName = new Vector<String>();
		columnName.add("�̸�");
		columnName.add("��¥(Date)");
		columnName.add("������");

		table = new JTable();
		JTableHeader tHeader = table.getTableHeader();
		tHeader.setBackground(Color.WHITE);
		
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(userList, columnName));

	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_search) { // �˻� ��ư�� ���� ��
				System.out.println("�˻�!");
				Vector<Vector<String>> userList = dbprocess.searchUser((String)comboBox_name.getSelectedItem(), textField_date.getText(), (String)comboBox_state.getSelectedItem());
				Vector<String> columnName = new Vector<String>();
				columnName.add("�̸�");
				columnName.add("��¥(Date)");
				columnName.add("������");

				table = new JTable();
				JTableHeader tHeader = table.getTableHeader();
				tHeader.setBackground(Color.WHITE);
				scrollPane.setViewportView(table);
				table.setAutoCreateRowSorter(true);
				table.setModel(new DefaultTableModel(userList, columnName));
			}
		}

	}
}
