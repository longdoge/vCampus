package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.SharedComponents.vo.vOrder;
import edu.seu.vCampus.client.view.stu.Course.CourseInfoWithSelectionInfo;
import edu.seu.vCampus.client.view.stu.Shop.ShopView;

public class CourseCellRender extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JButton change_grade;
	CourseInfoContainer feed;

	private JLabel lblCourseName;
	private JLabel lblCourseCredit;
	private JLabel lblCourseIntro;
	private JButton btn_stu_list;

	public CourseCellRender(AdminGradeView agv) {
		int xoffset = -10;
		int yoffset = 8;

		panel = new JPanel();
		panel.setLayout(null);

		lblCourseName = new JLabel("\u5546\u54C1");
		lblCourseName.setForeground(Color.BLACK);
		lblCourseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblCourseName.setBounds(22, 23, 201, 30);
		panel.add(lblCourseName);

		lblCourseCredit = new JLabel("\u8BA2\u5355\u4EF7\u683C");
		lblCourseCredit.setForeground(new Color(0, 0, 255));
		lblCourseCredit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseCredit.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblCourseCredit.setBounds(272, 24, 82, 30);
		panel.add(lblCourseCredit);

		lblCourseIntro = new JLabel("\u8BA2\u5355\u65F6\u95F4");
		lblCourseIntro.setForeground(new Color(0, 100, 0));
		lblCourseIntro.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseIntro.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblCourseIntro.setBounds(384, 24, 72, 30);
		panel.add(lblCourseIntro);

		change_grade = new JButton("查看成绩列表");
		change_grade.setBounds(467, 45, 143, 23);
		change_grade.setBorderPainted(true);
		change_grade.setContentAreaFilled(false);
		change_grade.setFocusable(false);
		change_grade.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel.add(change_grade);
		change_grade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UpdateGradeDialog(null, "成绩列表", true, feed);
			}
		});

		btn_stu_list = new JButton("查看学生列表");
		btn_stu_list.setBounds(467, 15, 143, 23);
		btn_stu_list.setBorderPainted(true);
		btn_stu_list.setContentAreaFilled(false);
		btn_stu_list.setFocusable(false);
		btn_stu_list.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel.add(btn_stu_list);
		btn_stu_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StudentListDialog(null, "学生列表", true, feed);
			}
		});
	}

	private void updateData(CourseInfoContainer feed, boolean isSelected, JTable table) {
		this.feed = feed;

		lblCourseName.setText(feed.getStrName());
		lblCourseCredit.setText(String.valueOf(feed.getfCredit()) + "学分");
		lblCourseIntro.setText(feed.strIntro);

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		CourseInfoContainer feed = (CourseInfoContainer) value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		CourseInfoContainer feed = (CourseInfoContainer) value;
		updateData(feed, isSelected, table);
		return panel;
	}
}
