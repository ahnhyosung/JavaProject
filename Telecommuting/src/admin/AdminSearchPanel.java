package admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class AdminSearchPanel extends JPanel {
	private JTable table; // 테이블
	private JComboBox comboBox_name; // 이름 콤보박스
	private JComboBox comboBox_date; // 날짜 콤보박스
	private JComboBox comboBox_state; // 출결상태 콤보박스
	private JButton button_search; // 검색 버튼

	/**
	 * Create the panel.
	 */
	public AdminSearchPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		Border border = BorderFactory.createTitledBorder("검색 조건");
		JPanel panel = new JPanel();
		panel.setBorder(border);
		panel.setBounds(52, 36, 514, 95);
		panel.setBackground(Color.WHITE);
		add(panel);
		panel.setLayout(null);

		comboBox_name = new JComboBox();
		comboBox_name.setBounds(35, 32, 120, 36);
		comboBox_name.setModel(new DefaultComboBoxModel(new String[] { "이름" }));
		panel.add(comboBox_name);

		comboBox_date = new JComboBox();
		comboBox_date.setModel(new DefaultComboBoxModel(new String[] { "날짜" }));
		comboBox_date.setBounds(188, 32, 120, 36);
		panel.add(comboBox_date);

		comboBox_state = new JComboBox();
		comboBox_state.setModel(new DefaultComboBoxModel(
				new String[] { "출결상태" }));
		comboBox_state.setBounds(342, 32, 120, 36);
		panel.add(comboBox_state);

		button_search = new JButton("검색");
		button_search.addActionListener(new AdminActionListener());
		button_search.setBounds(578, 64, 97, 44);
		add(button_search);
		add(new JScrollPane());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 149, 893, 403);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(new Object[][] {
				{ "양우정", "2015/08/14", "조퇴" }, { "이상근", "2015/08/14", "O" },
				{ "우정현", "2015/08/14", "결석" }, { "안효성", "2015/08/14", "O" }, },
				new String[] { "이름", "날짜(Date)", "출결상태" }));

	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_search) { // 검색 버튼을 선택 시
				System.out.println("검색!");

			}
		}

	}
}
