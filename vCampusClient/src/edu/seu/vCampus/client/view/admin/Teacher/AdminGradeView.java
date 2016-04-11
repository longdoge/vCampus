package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.CardLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseAdminModel;
import edu.seu.vCampus.client.biz.module.StudentManager.GradeModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.stu.Shop.ShopCellRenderer;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;
import edu.seu.vCampus.client.view.util.FadingPanel;

//import client.view.admin.subjelect.ScoreModify;

public class AdminGradeView extends FadingPanel {
	JButton deleteorder;
	public CardLayout cardLayout = new CardLayout();
	public JButton btn_Return = new JButton("");
	private static JTable tbl_course;
	public int curruId;
	Object[] name = { "课程名称", "学分", "课程介绍" };

	/**
	 * Create the application.
	 */
	public AdminGradeView() {

		setBackground(Color.WHITE);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 30, 615, 400);
		add(scrollPane);

		tbl_course = new JTable();
		scrollPane.setViewportView(tbl_course);
		scrollPane.getViewport().setBackground(Color.WHITE);

		tbl_course.setTableHeader(null);
		tbl_course.setRowHeight(80);
		tbl_course.setRowSelectionAllowed(false);
		tbl_course.setShowGrid(false);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		updatecourse();
	}

	public void updatecourse() {
		curruId = UserLoginInfoManager.getnUserID();
		CourseInfoContainer[] arr = CourseAdminModel.viewAvaiCourseForMe();
		tbl_course.setModel(new CustomizableTableModel<CourseInfoContainer>(arr, CourseInfoContainer.class));
		tbl_course.setDefaultRenderer(CourseInfoContainer.class, new CourseCellRender(this));
		tbl_course.setDefaultEditor(CourseInfoContainer.class, new CourseCellRender(this));
	}

	/**
	 * 获得当前选中的成绩行
	 * 
	 * @return 所选择的成绩
	 */
	// public static Score getChosenScore() {

	// return null;
	// }
}