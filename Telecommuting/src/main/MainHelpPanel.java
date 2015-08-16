package main;

import java.awt.Color;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MainHelpPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainHelpPanel() {
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("만든이");
		lblNewLabel.setBounds(65, 92, 57, 15);
		add(lblNewLabel);

		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "양우정", "이상근", "유정현", "안효성" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(134, 92, 218, 132);
		add(list);

	}

}
