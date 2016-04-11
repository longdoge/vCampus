package edu.seu.vCampus.client.view.stu.Course;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseSelectionModel;

public class CourseCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JButton showButton;

	CourseInfoWithSelectionInfo feed;
	private JLabel lblCourseName;
	private JLabel lblTeacherName;
	private JLabel lblType;
	private JLabel lblStat;
	private JLabel lblCredit;
	private JLabel lblChosen;

	// private CourseView vw;
	private static final String[] courseSubj = new String[] { "基础必修课", "专业必修课", "专业限选课", "人文通识类", "经济管理类" };
	private static final String[] csSubj = new String[] { "选修", "必修", "限选" };

	public CourseCellRenderer(final CourseView vw) {
		int xoffset = -10;
		int yoffset = 8;
		// this.vw = vw;
		panel = new JPanel();
		panel.setLayout(null);

		showButton = new JButton("\u9009\u62E9");
		showButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (feed.csInfo == null) {
					MessageContainer ret = CourseSelectionModel.addCourse(feed.courseInfo.nCourseID);
					if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
						vw.updateCourses();
						// vw.updateSelectedCourse();
					} else {
						if (ret.strParameters == null || ret.strParameters.length == 0)
							JOptionPane.showMessageDialog(null, "选课失败", "错误提示", JOptionPane.ERROR_MESSAGE);
						else {
							vw.updateCourses();
							// vw.updateSelectedCourse();
							switch (ret.strParameters[0]) {
							case CommandProtocol.CS_EXCEED:
								JOptionPane.showMessageDialog(null, "名额已满！", "错误提示", JOptionPane.ERROR_MESSAGE);
								break;
							case CommandProtocol.CS_UNAVAI:
								JOptionPane.showMessageDialog(null, "无选此课程的资格！", "错误提示", JOptionPane.ERROR_MESSAGE);
								break;
							}
						}
					}
				} else {
					MessageContainer ret = CourseSelectionModel.delCourse(feed.csInfo.nCsID);
			        if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
			        	vw.updateCourses();
			        } else {
			            if (ret.strParameters == null || ret.strParameters.length == 0)
			                JOptionPane.showMessageDialog(null, "取消选课失败", "错误提示", JOptionPane.ERROR_MESSAGE);
			            else {
			            	vw.updateCourses();
			                switch (ret.strParameters[0]) {
			                case CommandProtocol.CS_MANDOTARY:
			                    JOptionPane.showMessageDialog(null, "无法取消选择必修课！", "错误提示", JOptionPane.ERROR_MESSAGE);
			                    break;
			                }
			            }
			        }
				}
			}
		});
		showButton.setBounds(534 + xoffset, 19 + yoffset, 70, 30);
		showButton.setContentAreaFilled(false);
		showButton.setBorderPainted(true);
		showButton.setFocusable(false);
		panel.add(showButton);

		lblChosen = new JLabel("已选");
		lblChosen.setHorizontalAlignment(SwingConstants.CENTER);
		lblChosen.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblChosen.setBounds(0, 0, 40, 20);
		panel.add(lblChosen);

		lblCourseName = new JLabel("\u79BB\u6563\u6570\u5B66");
		lblCourseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseName.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblCourseName.setBounds(0, 0 + yoffset, 300 + xoffset, 60);
		panel.add(lblCourseName);

		lblTeacherName = new JLabel("\u6F06\u6842\u6797");
		lblTeacherName.setForeground(new Color(0, 100, 0));
		lblTeacherName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblTeacherName.setBounds(310 + xoffset, 0 + yoffset, 65, 30);
		panel.add(lblTeacherName);

		lblType = new JLabel("\u7B2CX\u5B66\u671F \u4E13\u4E1A\u5FC5\u4FEE\u8BFE");
		lblType.setForeground(new Color(0, 139, 139));
		lblType.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblType.setBounds(310 + xoffset, 30 + yoffset, 150, 30);
		panel.add(lblType);

		lblStat = new JLabel("10 / 30");
		lblStat.setHorizontalAlignment(SwingConstants.CENTER);
		lblStat.setForeground(new Color(220, 20, 60));
		lblStat.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblStat.setBounds(380 + xoffset, 0 + yoffset, 60, 30);
		panel.add(lblStat);

		lblCredit = new JLabel("4\u5B66\u5206");
		lblCredit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredit.setForeground(new Color(30, 144, 255));
		lblCredit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblCredit.setBounds(455 + xoffset, 19 + yoffset, 60, 30);
		panel.add(lblCredit);
	}

	private void updateData(CourseInfoWithSelectionInfo feed, boolean isSelected, JTable table) {
		this.feed = feed;

		if (feed.csInfo == null) {
			lblType.setText("第" + feed.courseInfo.nSemester + "学期 " + courseSubj[feed.courseInfo.nTeacherID]);
			showButton.setText("选择");
			lblChosen.setVisible(false);
		} else {
			lblType.setText("第" + feed.courseInfo.nSemester + "学期 " + csSubj[feed.csInfo.nType]);
			showButton.setText("退选");
			lblChosen.setVisible(true);
		}
		lblCourseName.setText(feed.courseInfo.strName);
		lblTeacherName.setText(feed.courseInfo.strIntro);
		lblStat.setText(feed.courseInfo.nStuNum + " / " + feed.courseInfo.nMaxStuNum);
		lblCredit.setText(feed.courseInfo.fCredit + "学分");

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		CourseInfoWithSelectionInfo feed = (CourseInfoWithSelectionInfo) value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		CourseInfoWithSelectionInfo feed = (CourseInfoWithSelectionInfo) value;
		updateData(feed, isSelected, table);
		return panel;
	}
}
