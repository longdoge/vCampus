package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.CardLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseAdminModel;
import edu.seu.vCampus.client.view.util.FadingPanel;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

import java.awt.SystemColor;

import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AdminCourseView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	private CardLayout cl_CardAdminCourse = new CardLayout();
	public JButton btn_Return = new JButton("");
	private JTextField txt_CourseInfo;
	private JTextField txt_AddNum;
	private JTextField txt_AddName;
	private JTextField txt_AddTeacher;
	private JTextField txt_AddClassHour;
	private JTextField txt_AddCredit;
	private JComboBox comboBox_1;
	private static JTable table;
	private static final String[] courseSubj = new String[] { "基础必修课", "专业必修课", "专业限选课", "人文通识类", "经济管理类" };
	private CourseInfoContainer[] cinfo, cinfo_org;
	private JComboBox cbo_CourseInfo;
	private JButton btn_Search;

	/**
	 * Create the application.
	 */
	public AdminCourseView() {
		setBackground(Color.WHITE);
		setLayout(null);

		final JPanel CardAdminCourse = new JPanel();
		CardAdminCourse.setBackground(Color.WHITE);
		CardAdminCourse.setBounds(105, 0, 535, 407);
		add(CardAdminCourse);
		CardAdminCourse.setLayout(cl_CardAdminCourse);

		final JPanel pal_AdminCourse = new JPanel();
		pal_AdminCourse.setBackground(Color.WHITE);
		CardAdminCourse.add(pal_AdminCourse, "1");
		pal_AdminCourse.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 500, 280);
		pal_AdminCourse.add(scrollPane);

		table = new JTable();
		updateCourse();
		scrollPane.setViewportView(table);

		cbo_CourseInfo = new JComboBox();
		cbo_CourseInfo.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		cbo_CourseInfo.setBackground(Color.WHITE);
		cbo_CourseInfo.setModel(new DefaultComboBoxModel(new String[] { "任课教师", "课程名称" }));
		cbo_CourseInfo.setBounds(10, 50, 90, 27);
		pal_AdminCourse.add(cbo_CourseInfo);

		txt_CourseInfo = new JTextField();
		txt_CourseInfo.setBounds(120, 50, 153, 27);
		pal_AdminCourse.add(txt_CourseInfo);
		txt_CourseInfo.setColumns(10);
		txt_CourseInfo.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 输入时按回车，即可提交
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btn_Search.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		btn_Search = new JButton("查询");
		btn_Search.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btn_Search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchCourse();
			}
		});
		btn_Search.setContentAreaFilled(false);
		btn_Search.setBorderPainted(true);
		btn_Search.setFocusable(false);
		btn_Search.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_Search.setBounds(290, 50, 70, 27);
		pal_AdminCourse.add(btn_Search);

		// 所有课程
		final JButton btn_AllCourse = new JButton("所有课程");
		btn_AllCourse.setBackground(Color.WHITE);
		btn_AllCourse.setContentAreaFilled(false);
		btn_AllCourse.setBorderPainted(true);
		btn_AllCourse.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_AllCourse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 监听显示所有课程信息
				cl_CardAdminCourse.show(CardAdminCourse, "1");
			}
		});
		btn_AllCourse.setBounds(5, 160, 95, 33);
		add(btn_AllCourse);

		// 添加课程
		final JButton btn_AddCourse = new JButton("添加课程");
		btn_AddCourse.setBackground(Color.WHITE);
		btn_AddCourse.setContentAreaFilled(false);
		btn_AddCourse.setBorderPainted(true);
		btn_AddCourse.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_AddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 监听添加课程的按钮
				cl_CardAdminCourse.show(CardAdminCourse, "2");

			}
		});
		btn_AddCourse.setBounds(5, 230, 95, 33);
		add(btn_AddCourse);

		// 删除课程
		final JButton btn_DeleteCourse = new JButton("删除课程");
		btn_DeleteCourse.setBackground(Color.WHITE);
		btn_DeleteCourse.setContentAreaFilled(false);
		btn_DeleteCourse.setBorderPainted(true);
		btn_DeleteCourse.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_DeleteCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_CardAdminCourse.show(CardAdminCourse, "1");
				deleteCourse();
			}
		});
		btn_DeleteCourse.setBounds(5, 300, 95, 33);
		add(btn_DeleteCourse);

		// 子界面
		JPanel pal_Add = new JPanel();
		pal_Add.setBackground(Color.WHITE);
		CardAdminCourse.add(pal_Add, "2");
		pal_Add.setLayout(null);

		JLabel lbl_AddNum = new JLabel("最大可选人数");
		lbl_AddNum.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbl_AddNum.setBounds(86, 26, 100, 38);
		pal_Add.add(lbl_AddNum);

		txt_AddNum = new JTextField();
		txt_AddNum.setBounds(185, 26, 217, 32);
		pal_Add.add(txt_AddNum);
		txt_AddNum.setColumns(10);

		JLabel lbl_AddName = new JLabel("课程名称");
		lbl_AddName.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbl_AddName.setBounds(86, 74, 100, 38);
		pal_Add.add(lbl_AddName);

		JLabel lbl_AddTeacher = new JLabel("任课教师");
		lbl_AddTeacher.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbl_AddTeacher.setBounds(86, 122, 100, 38);
		pal_Add.add(lbl_AddTeacher);

		JLabel lbl_AddClassHour = new JLabel("上课学期");
		lbl_AddClassHour.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbl_AddClassHour.setBounds(86, 170, 100, 38);
		pal_Add.add(lbl_AddClassHour);

		JLabel lbl_AddCredit = new JLabel("学分");
		lbl_AddCredit.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbl_AddCredit.setBounds(86, 218, 100, 38);
		pal_Add.add(lbl_AddCredit);

		JLabel lbl_AddType = new JLabel("课程类型");
		lbl_AddType.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbl_AddType.setBounds(86, 266, 100, 38);
		pal_Add.add(lbl_AddType);

		txt_AddName = new JTextField();
		txt_AddName.setColumns(10);
		txt_AddName.setBounds(185, 74, 217, 32);
		pal_Add.add(txt_AddName);

		txt_AddTeacher = new JTextField();
		txt_AddTeacher.setColumns(10);
		txt_AddTeacher.setBounds(185, 122, 217, 32);
		pal_Add.add(txt_AddTeacher);

		txt_AddClassHour = new JTextField();
		txt_AddClassHour.setColumns(10);
		txt_AddClassHour.setBounds(185, 170, 217, 32);
		pal_Add.add(txt_AddClassHour);

		txt_AddCredit = new JTextField();
		txt_AddCredit.setColumns(10);
		txt_AddCredit.setBounds(185, 218, 217, 32);
		pal_Add.add(txt_AddCredit);

		comboBox_1 = new JComboBox();
		comboBox_1.setBackground(Color.WHITE);
		comboBox_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		comboBox_1.setModel(new DefaultComboBoxModel(courseSubj));
		comboBox_1.setBounds(185, 269, 217, 32);
		pal_Add.add(comboBox_1);

		// 添加课程子界面确认添加接口
		final JButton btn_AddConfirm = new JButton("确认添加");
		btn_AddConfirm.setBackground(Color.WHITE);
		btn_AddConfirm.setContentAreaFilled(false);
		btn_AddConfirm.setBorderPainted(true);
		btn_AddConfirm.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_AddConfirm.setBounds(124, 330, 105, 38);
		pal_Add.add(btn_AddConfirm);

		btn_AddConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CourseInfoContainer cinfo = new CourseInfoContainer();
					cinfo.fCredit = Double.valueOf(txt_AddCredit.getText()).floatValue();
					cinfo.nMaxStuNum = Integer.valueOf(txt_AddNum.getText());
					cinfo.nSemester = Integer.valueOf(txt_AddClassHour.getText());
					cinfo.strName = txt_AddName.getText();
					cinfo.nTeacherID = comboBox_1.getSelectedIndex();
					cinfo.strIntro = txt_AddTeacher.getText();
					MessageContainer ret = CourseAdminModel.addCourse(cinfo);
					if (ret.getStrCommand().equals(CommandProtocol.SUCCESS)) {
						JOptionPane.showMessageDialog(null, "课程添加成功！");
						updateCourse();
					} else
						JOptionPane.showMessageDialog(null, "输入有误，请改正！");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "输入有误或不完整，请改正！");
				}
			}
		});

		// 添加课程子界面取消添加接口
		final JButton btn_AddCancel = new JButton("取消添加");
		btn_AddCancel.setBackground(Color.WHITE);
		btn_AddCancel.setContentAreaFilled(false);
		btn_AddCancel.setBorderPainted(true);
		btn_AddCancel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_AddCancel.setBounds(297, 330, 105, 38);
		pal_Add.add(btn_AddCancel);
		btn_AddCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txt_AddNum.setText("");
				txt_AddName.setText("");
				txt_AddTeacher.setText("");
				txt_AddClassHour.setText("");
				txt_AddCredit.setText("");
				comboBox_1.setSelectedIndex(0);
			}
		});
		JPanel pal_btn = new JPanel();
		pal_btn.setBackground(Color.WHITE);
		pal_btn.setBounds(0, 30, 106, 407);
		add(pal_btn);
		pal_btn.setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JPanel jpAdminCourse = new JPanel();
		jpAdminCourse.setBackground(Color.WHITE);
		jpAdminCourse.setBounds(0, 42, 640, 438);
	}

	/**
	 * 刷新课程
	 */
	private void updateCourse() {
		cinfo_org = cinfo = CourseAdminModel.viewAvaiCourseForMe();
		renderCourse();
	}

	private void renderCourse() {
		Object[] name = { "课程名称", "任课教师", "上课学期", "已选人数", "最大可选人数", "学分", "课程类型" };
		Object[][] data = new Object[cinfo.length][name.length];
		for (int i = 0; i < cinfo.length; ++i) {
			data[i][0] = cinfo[i].strName;
			data[i][1] = cinfo[i].strIntro;
			data[i][2] = cinfo[i].nSemester;
			data[i][3] = cinfo[i].nStuNum;
			data[i][4] = cinfo[i].nMaxStuNum;
			data[i][5] = cinfo[i].fCredit;
			data[i][6] = courseSubj[cinfo[i].nTeacherID];
		}
		DefaultTableModel model = new DefaultTableModel();
		model.setDataVector(data, name);
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * 删除课程
	 */
	private void deleteCourse() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "确认删除此课程吗？", "请确认", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			int n = getChosenRow();
			if (n == -1)
				return;
			if (CourseAdminModel.delCourse(cinfo[n].nCourseID).strCommand.equals(CommandProtocol.SUCCESS)) {
				updateCourse();
			} else {
				JOptionPane.showMessageDialog(null, "删除失败", "错误提示", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * 寻找课程
	 */
	private void searchCourse() {
		System.out.println("searching");
		String key = this.txt_CourseInfo.getText().trim().toLowerCase();
		if (key.length() == 0) {
			cinfo = cinfo_org;
		} else {
			Vector<CourseInfoContainer> cinfo_sel = new Vector<CourseInfoContainer>();
			int sel = cbo_CourseInfo.getSelectedIndex();
			switch (sel) {
			case 0:
				for (int i = 0; i < cinfo_org.length; ++i) {
					if (cinfo_org[i].strIntro.toLowerCase().contains(key)) {
						cinfo_sel.add(cinfo_org[i]);
					}
				}
				break;
			case 1:
				for (int i = 0; i < cinfo_org.length; ++i) {
					if (cinfo_org[i].strName.toLowerCase().contains(key)) {
						cinfo_sel.add(cinfo_org[i]);
					}
				}
				break;
			}
			cinfo = cinfo_sel.toArray(new CourseInfoContainer[0]);
		}
		renderCourse();
	}

	// 获取所选行信息
	public static int getChosenRow() {
		int count = table.getSelectedRowCount();
		if (count == 0) {
			JOptionPane.showMessageDialog(null, "请选择一条记录");
		} else if (count > 1) {
			JOptionPane.showMessageDialog(null, "请选择一条记录");
		} else {
			return table.getSelectedRow();
		}
		return -1;
	}
}