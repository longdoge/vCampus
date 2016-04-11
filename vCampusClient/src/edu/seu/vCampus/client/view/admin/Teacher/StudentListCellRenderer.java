package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.seu.vCampus.SharedComponents.vo.CourseSelectionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.SharedComponents.vo.vStudent;
import edu.seu.vCampus.client.biz.module.StudentManager.GradeModel;
import edu.seu.vCampus.client.biz.module.StudentManager.StuInfoModel;
import edu.seu.vCampus.client.view.util.MessageDisplay;

public class StudentListCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JButton change_grade;
	CourseSelectionInfoContainer feed;

	private JLabel lblStuName;
	private JLabel lblCourseName;
	private JLabel lblCourseGrade;

	public StudentListCellRenderer(final StudentListDialog stulstdlg) {
		panel = new JPanel();
		panel.setLayout(null);

		lblStuName = new JLabel("\u5546\u54C1");
		lblStuName.setForeground(Color.BLACK);
		lblStuName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStuName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblStuName.setBounds(28, 5, 72, 30);
		panel.add(lblStuName);

		lblCourseName = new JLabel("\u8BA2\u5355\u4EF7\u683C");
		lblCourseName.setForeground(new Color(0, 0, 255));
		lblCourseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblCourseName.setBounds(129, 5, 150, 30);
		panel.add(lblCourseName);

		change_grade = new JButton("录入成绩");
		change_grade.setBounds(340, 8, 120, 23);
		change_grade.setBorderPainted(true);
		change_grade.setContentAreaFilled(false);
		change_grade.setFocusable(false);
		change_grade.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel.add(change_grade);
		change_grade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = JOptionPane.showInputDialog("请输入成绩，范围为0至100：");
				if (temp == null || temp.length() == 0)
					return;
				float num = 0;
				try {
					num = Float.parseFloat(temp);
				} catch (Exception e1) {
					MessageDisplay.showError("成绩填写错误！");
					return;
				}
				if (num < 0 || num > 100) {
					JOptionPane.showMessageDialog(null, "成绩范围为0至100！");
				} else {
					Calendar cal = Calendar.getInstance();
					final String[] csSubj = new String[] { "选修", "必修", "限选" };
					vGrade gr = new vGrade();
					gr.cId = stulstdlg.info.nCourseID;
					gr.cName = stulstdlg.info.strName;
					gr.credit = stulstdlg.info.fCredit;
					gr.cTime = cal.get(Calendar.YEAR) + "-" + stulstdlg.info.nSemester;
					gr.cType = csSubj[feed.nType];
					gr.grRaw = num;
					gr.tName = stulstdlg.info.strIntro;
					gr.uId = feed.nStudentID;
					if (new GradeModel().addgrade(gr))
						MessageDisplay.showInfo("成绩已录入！");
					else
						MessageDisplay.showError("成绩录入失败！");
				}
			}
		});
		panel.add(change_grade);
	}

	private void updateData(CourseSelectionInfoContainer feed, boolean isSelected, JTable table) {
		this.feed = feed;

		StuInfoModel stuinfomodel = new StuInfoModel();
		vStudent stu = new vStudent();
		stu.uId = feed.nStudentID;
		Vector<vStudent> stuvector = stuinfomodel.scanstuinfowithuid(stu);
		if (stuvector.size() == 0) {
			lblStuName.setText("查无此人");
			lblCourseName.setText("");
		} else {
			lblStuName.setText(stuvector.get(0).name);
			lblCourseName.setText(stuvector.get(0).num);
		}

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		CourseSelectionInfoContainer feed = (CourseSelectionInfoContainer) value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		CourseSelectionInfoContainer feed = (CourseSelectionInfoContainer) value;
		updateData(feed, isSelected, table);
		return panel;
	}
}
