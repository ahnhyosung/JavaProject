package admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import main.MainFrame;
import main.MainHelpPanel;
import main.MainMenuBar;
import camera.FaceTrackingView;

public class AdminMenuBar extends JMenuBar {
	private JMenuItem menuItem_regist; // 파일 메뉴의 등록 아이템
	private JMenuItem menuItem_search; // 파일 메뉴의 조회 아이템
	private JMenuItem menuItem_chat; // 파일 메뉴의 회의방 개설 아이템
	private JMenuItem menuItem_logout; // 파일 메뉴의 로그아웃 아이템
	private JMenuItem menu_info; // 도움말 메뉴의 프로그램 정보 아이템

	public AdminMenuBar() {
		JMenu menu_file = new JMenu("파일");
		add(menu_file);

		AdminActionListener action = new AdminActionListener();

		menuItem_regist = new JMenuItem("등록");
		menuItem_regist.addActionListener(action);
		menu_file.add(menuItem_regist);

		menuItem_search = new JMenuItem("사용자 조회");
		menuItem_search.addActionListener(action);
		menu_file.add(menuItem_search);

		menuItem_chat = new JMenuItem("회의방 개설");
		menuItem_chat.addActionListener(action);
		menu_file.add(menuItem_chat);

		menu_file.addSeparator();

		menuItem_logout = new JMenuItem("로그아웃");
		menuItem_logout.addActionListener(action);
		menu_file.add(menuItem_logout);

		JMenu menu_help = new JMenu("도움말");
		add(menu_help);

		menu_info = new JMenuItem("프로그램 정보");
		menu_info.addActionListener(action);
		menu_help.add(menu_info);

		// 관리자 환영 문구 출력
		JLabel label_empty = new JLabel("                  ");
		add(label_empty);
		JLabel label_welcome = new JLabel("<<< 관리자 님 환영합니다. >>>");
		add(label_welcome);
	}

	class AdminActionListener implements ActionListener {

		public void cameraRunningCheck() {
			if (FaceTrackingView.drawingTimer != null
					&& FaceTrackingView.drawingTimer.isRunning()) {
				FaceTrackingView.drawingTimer.stop();
				FaceTrackingView.closeCamera();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == menuItem_regist) { // 파일 메뉴의 등록 아이템 선택 시

				cameraRunningCheck();

				MainFrame.contentPane = new AdminRegistPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_search) { // 파일 메뉴의 조회 아이템 선택 시

				cameraRunningCheck();

				MainFrame.contentPane = new AdminSearchPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_chat) { // 파일 메뉴의 회의방 개설 아이템 선택
															// 시

				cameraRunningCheck();

				MainFrame.contentPane = new AdminChatPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_logout) { // 파일 메뉴의 로그아웃 아이템
															// 선택시
				int num = JOptionPane.showConfirmDialog(MainFrame.frame,
						"로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null);
				if (num == 0) {

					cameraRunningCheck();

					MainFrame.frame.setJMenuBar(new MainMenuBar());
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.repaint();
					MainFrame.frame.setTitle("재택근무관리 프로그램");
					MainFrame.frame.setVisible(true);
				}
			} else if (e.getSource() == menu_info) { // 도움말 메뉴의 프로그램 정보 아이템

				cameraRunningCheck();

				MainFrame.contentPane = new MainHelpPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
