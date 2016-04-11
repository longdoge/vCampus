package edu.seu.vCampus.client.view.stu.Grade;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Font;

import java.util.Vector;
import java.awt.CardLayout;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.client.biz.module.StudentManager.GradeModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.stu.Course.CourseCellRenderer;
import edu.seu.vCampus.client.view.stu.Course.CourseInfoWithSelectionInfo;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;
import edu.seu.vCampus.client.view.util.FadingPanel;
import edu.seu.vCampus.client.view.util.MyScrollBarUI;

public class GradeView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout = new CardLayout();
	public JButton btn_Return = new JButton();
	private final JTable tbl_Grade;
	private JTextField txt_Name;
	private JScrollPane scrollPane;
	private Component lblNoGrade;
	/**
	 * Create the application.
	 */
	public GradeView() {
		setBackground(Color.WHITE);
		setLayout(null);

		//JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setBounds(10, 30, 620, 350);
		//add(scrollPane);
		
		//scrollPane.setViewportView(tbl_Grade);
		
		final JPanel CardGrade = new JPanel();
		CardGrade.setBackground(Color.WHITE);
		CardGrade.setBounds(10, 30, 620, 350);
		add(CardGrade);
		CardGrade.setLayout(null);
		
		lblNoGrade = new JLabel("暂无成绩信息~");
		lblNoGrade.setBounds(220, 130, 200, 50);
		lblNoGrade.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		CardGrade.add(lblNoGrade);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 620, 340);
		CardGrade.add(scrollPane);

		JPanel pal_GradeMain = new JPanel();
		pal_GradeMain.setBackground(Color.WHITE);
		CardGrade.add(pal_GradeMain, "0");
		pal_GradeMain.setLayout(null);
		
		tbl_Grade = new JTable();
		scrollPane.setViewportView(tbl_Grade);
		scrollPane.getViewport().setBackground(Color.WHITE);
		tbl_Grade.setTableHeader(null);
		tbl_Grade.setRowHeight(80);
		tbl_Grade.setRowSelectionAllowed(false);
		tbl_Grade.setShowGrid(false);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		
		/*JLabel lbl_name = new JLabel("姓名");
		lbl_name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lbl_name.setBounds(20,5, 80, 40);
		lbl_name.setHorizontalAlignment(SwingConstants.RIGHT);
		pal_GradeMain.add(lbl_name);

        txt_Name = new JTextField();
        txt_Name.setEditable(false);
        txt_Name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        txt_Name.setColumns(10);
        txt_Name.setBounds(180, 5, 80, 40);
        pal_GradeMain.add(txt_Name);*/
		
		showgrade();

	}
	
	public void showgrade(){
		GradeModel gramodel = new GradeModel();
		int uId = UserLoginInfoManager.getnUserID();
		vGrade grade = new vGrade();
		grade.setuId(uId);
		Vector<vGrade> gradevector = gramodel.scangradewithuid(grade);
		if (gradevector.size() == 0) {
			lblNoGrade.setVisible(true);
			return;
		}
		lblNoGrade.setVisible(false);
		/*Object[] name = { "姓名", "课程", "学分", "分数", "任课教师", "课程类型", "课程学期" };
        Object[][] data = new Object[gradevector.size()][name.length];
        for (int i = 0; i < gradevector.size(); ++i) {
            data[i][0] = UserLoginInfoManager.getUserInfo().getStrUsername();
            data[i][1] = gradevector.get(i).getcName();
            data[i][2] = gradevector.get(i).getCredit();
            data[i][3] = gradevector.get(i).getGrRaw();
            data[i][4] = gradevector.get(i).gettName();
            data[i][5] = gradevector.get(i).getcType();
            data[i][6] = gradevector.get(i).getcTime();
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(data, name);
        tbl_Grade.setModel(model);*/
		vGrade[] grades = (vGrade[])gradevector.toArray(new vGrade[0]);
		tbl_Grade.setModel(new CustomizableTableModel<vGrade>(grades, vGrade.class));
		tbl_Grade.setDefaultRenderer(vGrade.class, new GradeCellRenderer(this));
		tbl_Grade.setDefaultEditor(vGrade.class, new GradeCellRenderer(this));
	}
}