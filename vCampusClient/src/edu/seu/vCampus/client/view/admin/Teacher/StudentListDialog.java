package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.seu.vCampus.SharedComponents.vo.BookInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.CourseSelectionInfoContainer;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseSelectionAdminModel;
import edu.seu.vCampus.client.biz.module.StudentManager.GradeModel;
import edu.seu.vCampus.client.biz.module.vLibrary.BookAdminModel;
import edu.seu.vCampus.client.biz.module.vStore.goodManager;
import edu.seu.vCampus.client.view.admin.Shop.Goods;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;

public class StudentListDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	JTable tbl_grade;
	public CourseInfoContainer info;

	public StudentListDialog(Frame arg0, String arg1, boolean arg2, CourseInfoContainer info) {

		// super(arg0, arg1, arg2);

		this.info = info;

		this.setSize(500, 600);
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 500, 300);
		getContentPane().add(scrollPane);

		tbl_grade = new JTable();
		tbl_grade.setBounds(29, 26, 443, 264);
		scrollPane.setViewportView(tbl_grade);
		// getContentPane().add(tbl_grade);
		scrollPane.getViewport().setBackground(Color.WHITE);

		tbl_grade.setTableHeader(null);
		tbl_grade.setRowHeight(40);
		tbl_grade.setRowSelectionAllowed(false);
		tbl_grade.setShowGrid(false);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		// 控制界面居中
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        setLocation(x, y);
        
		update();
		this.setVisible(true);
	}

	public void update() {
		CourseSelectionInfoContainer[] csinfo = new CourseSelectionAdminModel().viewCourseSelection(info.nCourseID);
		tbl_grade.setModel(new CustomizableTableModel<CourseSelectionInfoContainer>(csinfo, CourseSelectionInfoContainer.class));
		tbl_grade.setDefaultRenderer(CourseSelectionInfoContainer.class, new StudentListCellRenderer(this));
		tbl_grade.setDefaultEditor(CourseSelectionInfoContainer.class, new StudentListCellRenderer(this));
	}
}
