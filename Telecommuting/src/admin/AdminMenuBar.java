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
	private JMenuItem menuItem_regist; // ���� �޴��� ��� ������
	private JMenuItem menuItem_search; // ���� �޴��� ��ȸ ������
	private JMenuItem menuItem_chat; // ���� �޴��� ȸ�ǹ� ���� ������
	private JMenuItem menuItem_logout; // ���� �޴��� �α׾ƿ� ������
	private JMenuItem menu_info; // ���� �޴��� ���α׷� ���� ������

	public AdminMenuBar() {
		JMenu menu_file = new JMenu("����");
		add(menu_file);

		AdminActionListener action = new AdminActionListener();

		menuItem_regist = new JMenuItem("���");
		menuItem_regist.addActionListener(action);
		menu_file.add(menuItem_regist);

		menuItem_search = new JMenuItem("����� ��ȸ");
		menuItem_search.addActionListener(action);
		menu_file.add(menuItem_search);

		menuItem_chat = new JMenuItem("ȸ�ǹ� ����");
		menuItem_chat.addActionListener(action);
		menu_file.add(menuItem_chat);

		menu_file.addSeparator();

		menuItem_logout = new JMenuItem("�α׾ƿ�");
		menuItem_logout.addActionListener(action);
		menu_file.add(menuItem_logout);

		JMenu menu_help = new JMenu("����");
		add(menu_help);

		menu_info = new JMenuItem("���α׷� ����");
		menu_info.addActionListener(action);
		menu_help.add(menu_info);

		// ������ ȯ�� ���� ���
		JLabel label_empty = new JLabel("                  ");
		add(label_empty);
		JLabel label_welcome = new JLabel("<<< ������ �� ȯ���մϴ�. >>>");
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
			if (e.getSource() == menuItem_regist) { // ���� �޴��� ��� ������ ���� ��

				cameraRunningCheck();

				MainFrame.contentPane = new AdminRegistPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_search) { // ���� �޴��� ��ȸ ������ ���� ��

				cameraRunningCheck();

				MainFrame.contentPane = new AdminSearchPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_chat) { // ���� �޴��� ȸ�ǹ� ���� ������ ����
															// ��

				cameraRunningCheck();

				MainFrame.contentPane = new AdminChatPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);

			} else if (e.getSource() == menuItem_logout) { // ���� �޴��� �α׾ƿ� ������
															// ���ý�
				int num = JOptionPane.showConfirmDialog(MainFrame.frame,
						"�α׾ƿ� �Ͻðڽ��ϱ�?", "�α׾ƿ�", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null);
				if (num == 0) {

					cameraRunningCheck();

					MainFrame.frame.setJMenuBar(new MainMenuBar());
					MainFrame.contentPane.removeAll();
					MainFrame.contentPane.repaint();
					MainFrame.frame.setTitle("���ñٹ����� ���α׷�");
					MainFrame.frame.setVisible(true);
				}
			} else if (e.getSource() == menu_info) { // ���� �޴��� ���α׷� ���� ������

				cameraRunningCheck();

				MainFrame.contentPane = new MainHelpPanel();
				MainFrame.frame.setContentPane(MainFrame.contentPane);
				MainFrame.frame.setVisible(true);
			}
		}

	}
}
