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
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.client.biz.module.StudentManager.GradeModel;
import edu.seu.vCampus.client.biz.module.vLibrary.BookAdminModel;
import edu.seu.vCampus.client.biz.module.vStore.goodManager;
import edu.seu.vCampus.client.view.admin.Shop.Goods;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;

public class UpdateGradeDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	JTable tbl_grade;
	CourseInfoContainer info;

	public UpdateGradeDialog(Frame arg0, String arg1, boolean arg2, CourseInfoContainer info) {

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
		tbl_grade.setRowHeight(80);
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

		JButton change_grade = new JButton("录入成绩");
		change_grade.setBounds(407, 30, 93, 23);
		this.add(change_grade);
		change_grade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ChangeGradeDialog changegrade = new
				// ChangeGradeDialog(null,"录入成绩",true,feed);
			}
		});

	}

	public void update() {
		GradeModel a = new GradeModel();
		vGrade grade = new vGrade();
		grade.setcId(info.nCourseID);
		Vector<vGrade> gradevector = a.scangradewithcid(grade);

		vGrade[] gradearr = gradevector.toArray(new vGrade[0]);
		/*
		 * Object[] name = { "学生姓名", "课程名称", "成绩", "学分" }; Object[][] data = new
		 * Object[gradevector.size()][name.length]; for (int i = 0; i <
		 * gradevector.size(); ++i) {
		 * 
		 * data[i][0] = gradevector.get(i).uId; data[i][1] =
		 * gradevector.get(i).cName; data[i][2] = gradevector.get(i).grRaw;
		 * data[i][3] = gradevector.get(i).credit;
		 * 
		 * } DefaultTableModel model = new DefaultTableModel();
		 * model.setDataVector(data, name);
		 */
		tbl_grade.setModel(new CustomizableTableModel<vGrade>(gradearr, vGrade.class));
		tbl_grade.setDefaultRenderer(vGrade.class, new GradeCellRenderer(this, tbl_grade));
		tbl_grade.setDefaultEditor(vGrade.class, new GradeCellRenderer(this, tbl_grade));
		// tbl_grade.setModel(model);
		tbl_grade.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void actionPerformed(ActionEvent arg0) {

		/*
		 * if (arg0.getSource() == jb1) { if (isQualified()) {
		 * //this.setOK(true); this.dispose(); String str1 =
		 * jtf1.getText().trim(); String str2 = jtf2.getText().trim(); String
		 * str3 = jtf3.getText().trim(); String str4 = jtf4.getText().trim();
		 * String str5 = jtf5.getText().trim();
		 * 
		 * System.out.println("aaa");
		 * 
		 * goodManager a = new goodManager(); vGood good = new vGood();
		 * good.setgName(str2); good.setgId(Integer.valueOf(str1));
		 * good.setgIntro("default"); good.setgPrice(Float.valueOf(str4));
		 * good.setgStock(Integer.valueOf(str3));
		 * good.setgType(Integer.valueOf(str5));
		 * 
		 * String info = a.addgood(good); System.out.println(info);
		 * 
		 * } } else if (arg0.getSource() == jb2) { //this.setOK(false);
		 * this.dispose(); } else { JOptionPane.showMessageDialog(this,
		 * "YOU WOULD NEVER SEE THIS!"); }
		 */
	}
}
