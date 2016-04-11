package edu.seu.vCampus.client.view.admin.UserInfoManager;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;
import edu.seu.vCampus.client.view.util.FadingPanel;

public class ManagerView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	public CardLayout cardLayout = new CardLayout();
	private JTextField txt_Search;
	private static JTable tbl_User;
	Object[] name = { "用户名", "密码", "姓名", "权限组", "商店权限" };

	public ManagerView() {
		setBackground(Color.WHITE);
		setLayout(null);

		final JPanel CardManager = new JPanel();
		CardManager.setBackground(Color.WHITE);
		CardManager.setBounds(105, 10, 535, 407);
		add(CardManager);
		CardManager.setLayout(cardLayout);

		final JPanel pal_ManagerMain = new JPanel();
		pal_ManagerMain.setBackground(Color.WHITE);
		CardManager.add(pal_ManagerMain, "0");
		pal_ManagerMain.setLayout(null);

		// textfield
		txt_Search = new JTextField();
		txt_Search.setBounds(59, 10, 189, 33);
		pal_ManagerMain.add(txt_Search);
		txt_Search.setColumns(10);

		CardManager.add(pal_ManagerMain, "0");

		// all User
		final JPanel pal_alluser = new JPanel();
		pal_alluser.setBackground(Color.WHITE);
		CardManager.add(pal_alluser, "1");
		pal_alluser.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 515, 340);
		pal_alluser.add(scrollPane);
		pal_alluser.add(txt_Search);

		tbl_User = new JTable();
		scrollPane.setViewportView(tbl_User);

		// button_search
		final JButton btn_search = new JButton("查询");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		btn_search.setContentAreaFilled(false);
		btn_search.setBorderPainted(true);
		btn_search.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_search.setBounds(290, 10, 93, 33);
		btn_search.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 输入时按回车，即可提交
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btn_search.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		pal_ManagerMain.add(btn_search);
		pal_alluser.add(btn_search);

		// All Button Panel
		JPanel pal_btn = new JPanel();
		pal_btn.setBackground(Color.WHITE);
		pal_btn.setBounds(0, 31, 106, 407);
		add(pal_btn);
		pal_btn.setLayout(null);

		// 所有用户
		final JButton btn_alluser = new JButton("所有用户");
		btn_alluser.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_alluser.setContentAreaFilled(false);
		btn_alluser.setBorderPainted(true);
		btn_alluser.setBounds(10, 60, 96, 35);
		pal_btn.add(btn_alluser);

		// 添加用户
		final JButton btn_adduser = new JButton("添加用户");
		btn_adduser.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_adduser.setContentAreaFilled(false);
		btn_adduser.setBorderPainted(true);
		btn_adduser.setBounds(10, 140, 96, 35);
		pal_btn.add(btn_adduser);
		// 修改用户
		final JButton btn_modifyuser = new JButton("修改用户");
		btn_modifyuser.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_modifyuser.setContentAreaFilled(false);
		btn_modifyuser.setBorderPainted(true);
		btn_modifyuser.setBounds(10, 220, 96, 35);
		pal_btn.add(btn_modifyuser);
		// 删除用户
		final JButton btn_deleteuser = new JButton("删除用户");
		btn_deleteuser.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_deleteuser.setContentAreaFilled(false);
		btn_deleteuser.setBorderPainted(true);
		btn_deleteuser.setBounds(10, 300, 96, 35);
		pal_btn.add(btn_deleteuser);

		btn_alluser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(CardManager, "1");
				showalluser();
			}
		});

		btn_adduser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adduser();
			}
		});

		btn_modifyuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyuser();
			}
		});

		btn_deleteuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteuser();
			}
		});

	}

	public static UserInfoContainer getChosenRow() {
		int count = tbl_User.getSelectedRowCount();
		if (count == 0) {
			JOptionPane.showMessageDialog(null, "请选择信息!");
		} else if (count > 1) {
			JOptionPane.showMessageDialog(null, "无法选择多条信息\r\n请选择一条信息");
		} else {

		}
		return null;
	}

	public void showalluser() {

	}

	public void adduser() {

	}

	public void modifyuser() {

	}

	public void deleteuser() {

	}
}