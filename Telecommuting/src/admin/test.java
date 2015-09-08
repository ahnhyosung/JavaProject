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

public class test extends JPanel {
	private JTable table; // 테이블
	private JComboBox comboBox_name; // 이름 콤보박스
	public static JComboBox comboBox_date; // 날짜 콤보박스
	private JComboBox comboBox_state; // 출결상태 콤보박스
	private JButton button_search; // 검색 버튼
	private DBProcess dbprocess;

	JPanel panel = new JPanel();
	JPanel panel_calander_tmp;
	JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public test() {
		setLayout(null);
		setBackground(Color.WHITE);

		dbprocess = new DBProcess();

		Border border = BorderFactory.createTitledBorder("검색 조건");
		panel = new JPanel();
		panel.setBorder(border);
		panel.setBounds(52, 10, 893, 280);
		panel.setBackground(Color.WHITE);
		add(panel);
		panel.setLayout(null);

		comboBox_name = new JComboBox();
		comboBox_name.setBounds(156, 54, 170, 36);

		comboBox_name.setModel(new DefaultComboBoxModel(dbprocess
				.searchUserAll()));
		panel.add(comboBox_name);

		comboBox_date = new JComboBox();
		comboBox_date.setModel(new DefaultComboBoxModel(new String[] { "" }));
		comboBox_date.setBounds(156, 100, 170, 36);
		panel.add(comboBox_date);

		comboBox_state = new JComboBox();
		comboBox_state.setModel(new DefaultComboBoxModel(new String[] { "",
				"출근", "출근(지각)", "퇴근", "퇴근(근무시간부족)" }));
		comboBox_state.setBounds(156, 146, 170, 36);
		panel.add(comboBox_state);

		JLabel label = new JLabel("\uC774\uB984 :");
		label.setBounds(78, 66, 66, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("\uB0A0\uC9DC :");
		label_1.setBounds(78, 112, 66, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("\uCD9C\uACB0\uC0C1\uD0DC :");
		label_2.setBounds(78, 158, 66, 15);
		panel.add(label_2);

		// JPanel panel_calander_tmp = new JPanel();
		panel_calander_tmp = new SwingCalender();
		panel_calander_tmp.setBackground(Color.WHITE);
		panel_calander_tmp.setBounds(358, 10, 442, 260);
		panel.add(panel_calander_tmp);

		button_search = new JButton("검색");
		button_search.setBounds(78, 193, 248, 37);
		panel.add(button_search);
		button_search.addActionListener(new AdminActionListener());
		add(new JScrollPane());

		scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 300, 893, 280);
		add(scrollPane);

		Vector<Vector<String>> userList = dbprocess.searchUser("", "", "");
		Vector<String> columnName = new Vector<String>();
		columnName.add("이름");
		columnName.add("날짜(Date)");
		columnName.add("출결상태");

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
			if (e.getSource() == button_search) { // 검색 버튼을 선택 시
				System.out.println("검색!");
				Vector<Vector<String>> userList = dbprocess.searchUser((String)comboBox_name.getSelectedItem(), (String)comboBox_date.getSelectedItem(), (String)comboBox_state.getSelectedItem());
				Vector<String> columnName = new Vector<String>();
				columnName.add("이름");
				columnName.add("날짜(Date)");
				columnName.add("출결상태");

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
