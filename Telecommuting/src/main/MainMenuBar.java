package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import user.UserPanel;
import admin.AdminPanel;

public class MainMenuBar extends JMenuBar {
	private JMenuItem menuItem_admin; // 로그인 메뉴의 관리자 아이템
	private JMenuItem menuItem_user; // 로그인 메뉴의 사용자 아이템
	private JMenuItem menu_info; // 도움말 메뉴의 프로그램 정보 아이템

	public MainMenuBar() {
		JMenu munu_login = new JMenu("로그인");
		add(munu_login);

		MainActionListener action = new MainActionListener();

		menuItem_admin = new JMenuItem("관리자 접속");
		menuItem_admin.addActionListener(action);
		munu_login.add(menuItem_admin);

		menuItem_user = new JMenuItem("사용자 접속");
		menuItem_user.addActionListener(action);
		munu_login.add(menuItem_user);

		JMenu menu_help = new JMenu("도움말");
		add(menu_help);

		menu_info = new JMenuItem("프로그램 정보");
		menu_info.addActionListener(action);
		menu_help.add(menu_info);
	}

	class MainActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == menuItem_admin) { // 로그인 메뉴의 관리자 아이템을 선택 시
				MainFrame.contentPane = new AdminPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_user) { // 로그인 메뉴의 사용자 아이템을 선택
															// 시
				MainFrame.contentPane = new UserPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menu_info) { // 도움말 메뉴의 프로그램 정보 아이템을 선택시
				MainFrame.contentPane = new MainHelpPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
