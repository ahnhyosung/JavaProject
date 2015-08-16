package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import user.UserPanel;
import admin.AdminPanel;

public class MainMenuBar extends JMenuBar {
	private JMenuItem menuItem_admin; // �α��� �޴��� ������ ������
	private JMenuItem menuItem_user; // �α��� �޴��� ����� ������
	private JMenuItem menu_info; // ���� �޴��� ���α׷� ���� ������

	public MainMenuBar() {
		JMenu munu_login = new JMenu("�α���");
		add(munu_login);

		MainActionListener action = new MainActionListener();

		menuItem_admin = new JMenuItem("������ ����");
		menuItem_admin.addActionListener(action);
		munu_login.add(menuItem_admin);

		menuItem_user = new JMenuItem("����� ����");
		menuItem_user.addActionListener(action);
		munu_login.add(menuItem_user);

		JMenu menu_help = new JMenu("����");
		add(menu_help);

		menu_info = new JMenuItem("���α׷� ����");
		menu_info.addActionListener(action);
		menu_help.add(menu_info);
	}

	class MainActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == menuItem_admin) { // �α��� �޴��� ������ �������� ���� ��
				MainFrame.contentPane = new AdminPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_user) { // �α��� �޴��� ����� �������� ����
															// ��
				MainFrame.contentPane = new UserPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menu_info) { // ���� �޴��� ���α׷� ���� �������� ���ý�
				MainFrame.contentPane = new MainHelpPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
