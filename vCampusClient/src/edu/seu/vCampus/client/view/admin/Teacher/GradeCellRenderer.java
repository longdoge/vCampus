package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.SharedComponents.vo.vStudent;
import edu.seu.vCampus.client.biz.module.StudentManager.StuInfoModel;

public class GradeCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JButton change_grade;
	vGrade feed;

	private JLabel lblStuName;
	private JLabel lblCourseName;
	private JLabel lblCourseGrade;

	public GradeCellRenderer(final UpdateGradeDialog dia, final JTable table) {
		int xoffset = -10;
		int yoffset = 8;

		panel = new JPanel();
		panel.setLayout(null);

		lblStuName = new JLabel("\u5546\u54C1");
		lblStuName.setForeground(Color.BLACK);
		lblStuName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStuName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		lblStuName.setBounds(28, 23, 72, 30);
		panel.add(lblStuName);

		lblCourseName = new JLabel("\u8BA2\u5355\u4EF7\u683C");
		lblCourseName.setForeground(new Color(0, 0, 255));
		lblCourseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		lblCourseName.setBounds(129, 24, 82, 30);
		panel.add(lblCourseName);

		lblCourseGrade = new JLabel("\u8BA2\u5355\u65F6\u95F4");
		lblCourseGrade.setForeground(new Color(0, 100, 0));
		lblCourseGrade.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		lblCourseGrade.setBounds(254, 24, 72, 30);
		panel.add(lblCourseGrade);

		change_grade = new JButton("ÐÞ¸Ä³É¼¨");
		change_grade.setBounds(340, 30, 120, 23);
		change_grade.setBorderPainted(true);
		change_grade.setContentAreaFilled(false);
		change_grade.setFocusable(false);
		change_grade.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		panel.add(change_grade);
		change_grade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangeGradeDialog(dia, "Â¼Èë³É¼¨", true, feed);
				dia.update();
			}
		});
		panel.add(change_grade);
	}

	private void updateData(vGrade feed, boolean isSelected, JTable table) {
		this.feed = feed;

		String str = new String();
		StuInfoModel stuinfomodel = new StuInfoModel();
		vStudent stu = new vStudent();
		stu.uId = feed.uId;
		Vector<vStudent> stuvector = stuinfomodel.scanstuinfowithuid(stu);
		if (stuvector.size() != 0)
			str = stuvector.get(0).name;
		else
			str = "²éÎÞ´ËÈË";
		lblStuName.setText(str);
		lblCourseName.setText(feed.cName);
		lblCourseGrade.setText(String.valueOf(feed.grRaw));

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		vGrade feed = (vGrade) value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		vGrade feed = (vGrade) value;
		updateData(feed, isSelected, table);
		return panel;
	}
}
