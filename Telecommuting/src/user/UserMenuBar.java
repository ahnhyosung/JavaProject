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

	private JMenuItem menuItem_paticipate; // 파일 메뉴의 회의방 참가 아이템
	private JMenuItem menuItem_logout; // 파일 메뉴의 로그아웃 아이템
	private JMenuItem menu_info; // 도움말 메뉴의 프로그램 정보 아이템

	String userName;
	public UserMenuBar(String match_file_name) {
		JMenu menu_file = new JMenu("파일");
		add(menu_file);
		
		userName = match_file_name;

		UserActionListener action = new UserActionListener();

		menuItem_paticipate = new JMenuItem("회의방 참가");
		menuItem_paticipate.addActionListener(action);
		menu_file.add(menuItem_paticipate);

		menu_file.addSeparator();

		menuItem_logout = new JMenuItem("퇴근");
		menuItem_logout.addActionListener(action);
		menu_file.add(menuItem_logout);

		JMenu menu_help = new JMenu("도움말");
		add(menu_help);

		menu_info = new JMenuItem("프로그램 정보");
		menu_info.addActionListener(action);
		menu_help.add(menu_info);

		JLabel label_empty = new JLabel("                  ");
		add(label_empty);
		JLabel label_welcome = new JLabel("<<< " + userName
				+ " 님 환영합니다. >>>");
		add(label_welcome);
	}

	class UserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == menuItem_paticipate) { // 파일 메뉴의 회의방 참가 아이템을 선택
														// 시
				new CloseCamera().close();

				MainFrame.contentPane = new UserChatPanel(userName);
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_logout) { // 파일 메뉴의 퇴근 아이템을 선택
															// 시
				new CloseCamera().close();

				MainFrame.contentPane = new UserLeavePanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menu_info) { // 도움말 메뉴의 프로그램 정보 아이템을 선택
														// 시
				new CloseCamera().close();

				MainFrame.contentPane = new MainHelpPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
