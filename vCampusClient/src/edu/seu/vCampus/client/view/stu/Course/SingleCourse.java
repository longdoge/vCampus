package edu.seu.vCampus.client.view.stu.Course;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class SingleCourse extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txt_CourseName;
	private JTextField txt_Teacher;
	private JTextField txt_CourseNumber;
	private JTextField txt_CourseTime;
	private JTextField txt_Credit;
	JButton btn_choose;
	JButton btn_Cancel;
	
	public SingleCourse() {		
		setBackground(Color.WHITE);
		setLayout(null);
		this.setBorder(BorderFactory.createEtchedBorder());
		
		JLabel label = new JLabel("¿Î³ÌÃû³Æ");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		label.setBounds(75, 10, 54, 15);
		add(label);
		
		JLabel label_1 = new JLabel("¿Î³Ì±àºÅ");
		label_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		label_1.setBounds(75, 42, 54, 15);
		add(label_1);
		
		JLabel label_2 = new JLabel("ÈÎ¿ÎÀÏÊ¦");
		label_2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		label_2.setBounds(235, 10, 54, 15);
		add(label_2);
		
		JLabel label_3 = new JLabel("¿ÎÊ±");
		label_3.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		label_3.setBounds(235, 42, 30, 15);
		add(label_3);
		
		JLabel label_4 = new JLabel("Ñ§·Ö");
		label_4.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		label_4.setBounds(327, 42, 30, 15);
		add(label_4);
		
		txt_CourseName = new JTextField(null);
		txt_CourseName.setEditable(false);
		txt_CourseName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		txt_CourseName.setBounds(139, 10, 78, 15);
		add(txt_CourseName);
		txt_CourseName.setColumns(10);
		
		txt_Teacher = new JTextField(null);
		txt_Teacher.setEditable(false);
		txt_Teacher.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		txt_Teacher.setColumns(10);
		txt_Teacher.setBounds(294, 10, 78, 15);
		add(txt_Teacher);
		
		txt_CourseNumber = new JTextField(null);
		txt_CourseNumber.setEditable(false);
		txt_CourseNumber.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		txt_CourseNumber.setColumns(10);
		txt_CourseNumber.setBounds(139, 42, 78, 15);
		add(txt_CourseNumber);
		
		txt_CourseTime = new JTextField(null);
		txt_CourseTime.setEditable(false);
		txt_CourseTime.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		txt_CourseTime.setColumns(10);
		txt_CourseTime.setBounds(268, 42, 30, 15);
		add(txt_CourseTime);
		
		txt_Credit = new JTextField(null);
		txt_Credit.setEditable(false);
		txt_Credit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		txt_Credit.setColumns(10);
		txt_Credit.setBounds(363, 42, 30, 15);
		add(txt_Credit);
	}
}
