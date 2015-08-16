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
	private JTable table; // ���̺�
	private JComboBox comboBox_name; // �̸� �޺��ڽ�
	private JComboBox comboBox_date; // ��¥ �޺��ڽ�
	private JComboBox comboBox_state; // ������ �޺��ڽ�
	private JButton button_search; // �˻� ��ư

	/**
	 * Create the panel.
	 */
	public AdminSearchPanel() {
		setLayout(null);
		setBackground(Color.WHITE);

		Border border = BorderFactory.createTitledBorder("�˻� ����");
		JPanel panel = new JPanel();
		panel.setBorder(border);
		panel.setBounds(52, 36, 514, 95);
		panel.setBackground(Color.WHITE);
		add(panel);
		panel.setLayout(null);

		comboBox_name = new JComboBox();
		comboBox_name.setBounds(35, 32, 120, 36);
		comboBox_name.setModel(new DefaultComboBoxModel(new String[] { "�̸�" }));
		panel.add(comboBox_name);

		comboBox_date = new JComboBox();
		comboBox_date.setModel(new DefaultComboBoxModel(new String[] { "��¥" }));
		comboBox_date.setBounds(188, 32, 120, 36);
		panel.add(comboBox_date);

		comboBox_state = new JComboBox();
		comboBox_state.setModel(new DefaultComboBoxModel(
				new String[] { "������" }));
		comboBox_state.setBounds(342, 32, 120, 36);
		panel.add(comboBox_state);

		button_search = new JButton("�˻�");
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
				{ "�����", "2015/08/14", "����" }, { "�̻��", "2015/08/14", "O" },
				{ "������", "2015/08/14", "�Ἦ" }, { "��ȿ��", "2015/08/14", "O" }, },
				new String[] { "�̸�", "��¥(Date)", "������" }));

	}

	class AdminActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_search) { // �˻� ��ư�� ���� ��
				System.out.println("�˻�!");

			}
		}

	}
}
