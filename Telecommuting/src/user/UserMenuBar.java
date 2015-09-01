package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.MainFrame;
import main.MainHelpPanel;
import camera.CloseCamera;

public class UserMenuBar extends JMenuBar {

	private JMenuItem menuItem_paticipate; // ���� �޴��� ȸ�ǹ� ���� ������
	private JMenuItem menuItem_logout; // ���� �޴��� �α׾ƿ� ������
	private JMenuItem menu_info; // ���� �޴��� ���α׷� ���� ������

	String userName;
	public UserMenuBar(String match_file_name) {
		JMenu menu_file = new JMenu("����");
		add(menu_file);
		
		userName = match_file_name;

		UserActionListener action = new UserActionListener();

		menuItem_paticipate = new JMenuItem("ȸ�ǹ� ����");
		menuItem_paticipate.addActionListener(action);
		menu_file.add(menuItem_paticipate);

		menu_file.addSeparator();

		menuItem_logout = new JMenuItem("���");
		menuItem_logout.addActionListener(action);
		menu_file.add(menuItem_logout);

		JMenu menu_help = new JMenu("����");
		add(menu_help);

		menu_info = new JMenuItem("���α׷� ����");
		menu_info.addActionListener(action);
		menu_help.add(menu_info);

		JLabel label_empty = new JLabel("                  ");
		add(label_empty);
		JLabel label_welcome = new JLabel("<<< " + userName
				+ " �� ȯ���մϴ�. >>>");
		add(label_welcome);
	}

	class UserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == menuItem_paticipate) { // ���� �޴��� ȸ�ǹ� ���� �������� ����
														// ��
				new CloseCamera().close();

				MainFrame.contentPane = new UserChatPanel(userName);
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_logout) { // ���� �޴��� ��� �������� ����
															// ��
				new CloseCamera().close();

				MainFrame.contentPane = new UserLeavePanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menu_info) { // ���� �޴��� ���α׷� ���� �������� ����
														// ��
				new CloseCamera().close();

				MainFrame.contentPane = new MainHelpPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
