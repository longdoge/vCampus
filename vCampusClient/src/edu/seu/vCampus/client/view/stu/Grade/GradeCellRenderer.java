package edu.seu.vCampus.client.view.stu.Grade;

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
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseSelectionModel;

public class GradeCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JButton showButton;

	vGrade feed;
	private JLabel lblCourseName;
	private JLabel lblTeacherName;
	private JLabel lblType;
	private JLabel lblStat;
	private JLabel lblCredit;
	private JLabel lblScore;
	
	public GradeCellRenderer(final GradeView vw) {
		int xoffset = -10;
		int yoffset = 8;
		// this.vw = vw;
		panel = new JPanel();
		panel.setLayout(null);

		lblScore = new JLabel("100");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 24));
		lblScore.setBounds(544 + xoffset, 19 + yoffset, 60, 30);
		panel.add(lblScore);
		
		lblCourseName = new JLabel("\u79BB\u6563\u6570\u5B66");
		lblCourseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 24));
		lblCourseName.setBounds(0, 0 + yoffset, 300 + xoffset, 60);
		panel.add(lblCourseName);

		lblTeacherName = new JLabel("\u6F06\u6842\u6797");
		lblTeacherName.setForeground(new Color(0, 100, 0));
		lblTeacherName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblTeacherName.setBounds(310 + xoffset, 0 + yoffset, 65, 30);
		panel.add(lblTeacherName);

		lblType = new JLabel("\u7B2CX\u5B66\u671F \u4E13\u4E1A\u5FC5\u4FEE\u8BFE");
		lblType.setForeground(new Color(0, 139, 139));
		lblType.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblType.setBounds(310 + xoffset, 30 + yoffset, 150, 30);
		panel.add(lblType);

		lblStat = new JLabel("10 / 30");
		lblStat.setHorizontalAlignment(SwingConstants.CENTER);
		lblStat.setForeground(new Color(220, 20, 60));
		lblStat.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblStat.setBounds(380 + xoffset, 19 + yoffset, 60, 30);
		panel.add(lblStat);

		lblCredit = new JLabel("4\u5B66\u5206");
		lblCredit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredit.setForeground(new Color(30, 144, 255));
		lblCredit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblCredit.setBounds(455 + xoffset, 19 + yoffset, 60, 30);
		panel.add(lblCredit);
	}

	private void updateData(vGrade feed, boolean isSelected, JTable table) {
		this.feed = feed;
		
		lblCourseName.setText(feed.cName);
		lblTeacherName.setText(feed.tName);
		lblType.setText(feed.cTime);
		lblStat.setText(feed.cType);
		lblCredit.setText(String.valueOf(feed.credit) + "Ñ§·Ö");
		lblScore.setText(String.valueOf(feed.grRaw));

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
