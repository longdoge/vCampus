package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.SharedComponents.vo.vStudent;
import edu.seu.vCampus.client.biz.module.StudentManager.GradeModel;
import edu.seu.vCampus.client.biz.module.StudentManager.StuInfoModel;
import edu.seu.vCampus.client.biz.module.vStore.goodManager;
import edu.seu.vCampus.client.view.admin.Shop.Goods;
import edu.seu.vCampus.client.view.util.MessageDisplay;

public class ChangeGradeDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	JLabel jl2, jl3, jl4, jl5;
	JButton jb1, jb2;
	JTextField jtf1, jtf2, jtf3, jtf4, jtf5;
	JPanel jp1, jp2, jp3;

	// 构造函数
	public ChangeGradeDialog(final UpdateGradeDialog dia, String arg1, boolean arg2, final vGrade currGrade) {

		// super(arg0, arg1, arg2);
		this.getContentPane().setBackground(Color.WHITE);
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);
		jp3.setBackground(Color.WHITE);

		jl2 = new JLabel("学生姓名:", JLabel.CENTER);
		jl2.setFont(new Font("微软雅黑", Font.PLAIN, 14));// goodsName
		jl3 = new JLabel("课程名称:", JLabel.CENTER);
		jl3.setFont(new Font("微软雅黑", Font.PLAIN, 14));// goodsCount
		jl4 = new JLabel("成绩:", JLabel.CENTER);
		jl4.setFont(new Font("微软雅黑", Font.PLAIN, 14));// goodsType

		String str = new String();
		StuInfoModel stuinfomodel = new StuInfoModel();
		vStudent stu = new vStudent();
		stu.uId = currGrade.uId;
		Vector<vStudent> stuvector = stuinfomodel.scanstuinfowithuid(stu);
		if (stuvector.size() != 0)
			str = stuvector.get(0).name;
		else
			str = "查无此人";

		jtf2 = new JTextField(str);
		jtf3 = new JTextField(currGrade.cName);
		jtf4 = new JTextField(String.valueOf(currGrade.grRaw));

		jtf2.setEditable(false); // 修改商品信息时，ID不可更改
		jtf3.setEditable(false); // 修改商品信息时，名称不可更改

		jb1 = new JButton("确认");
		jb1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		jb1.setContentAreaFilled(false);
		jb1.setBorderPainted(true);

		jb2 = new JButton("取消");
		jb2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		jb2.setContentAreaFilled(false);
		jb2.setBorderPainted(true);
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		jp1.setLayout(new GridLayout(3, 1, 4, 4));
		jp2.setLayout(new GridLayout(3, 1, 4, 4));

		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);

		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);

		jp3.add(jb1);
		jp3.add(jb2);

		this.add(jp1, BorderLayout.WEST);
		this.add(jp2, BorderLayout.CENTER);
		this.add(jp3, BorderLayout.SOUTH);

		this.setSize(300, 200);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize = getSize();
		int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
		setLocation(x, y);

		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String str4 = jtf4.getText().trim();
	
					GradeModel a = new GradeModel();
					vGrade grade = new vGrade();
					grade.grId = currGrade.grId;
					grade.grRaw = Float.valueOf(str4);
	
					a.updategrade(grade);
					dia.update();
					
					dispose();
				} catch (Exception e1) {
					MessageDisplay.showError("输入有误！");
				}
				// inquiryGoods(str);
			}
		});

		this.setVisible(true);
	}
}
