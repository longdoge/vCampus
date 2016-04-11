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

	// ���캯��
	public ChangeGradeDialog(final UpdateGradeDialog dia, String arg1, boolean arg2, final vGrade currGrade) {

		// super(arg0, arg1, arg2);
		this.getContentPane().setBackground(Color.WHITE);
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);
		jp3.setBackground(Color.WHITE);

		jl2 = new JLabel("ѧ������:", JLabel.CENTER);
		jl2.setFont(new Font("΢���ź�", Font.PLAIN, 14));// goodsName
		jl3 = new JLabel("�γ�����:", JLabel.CENTER);
		jl3.setFont(new Font("΢���ź�", Font.PLAIN, 14));// goodsCount
		jl4 = new JLabel("�ɼ�:", JLabel.CENTER);
		jl4.setFont(new Font("΢���ź�", Font.PLAIN, 14));// goodsType

		String str = new String();
		StuInfoModel stuinfomodel = new StuInfoModel();
		vStudent stu = new vStudent();
		stu.uId = currGrade.uId;
		Vector<vStudent> stuvector = stuinfomodel.scanstuinfowithuid(stu);
		if (stuvector.size() != 0)
			str = stuvector.get(0).name;
		else
			str = "���޴���";

		jtf2 = new JTextField(str);
		jtf3 = new JTextField(currGrade.cName);
		jtf4 = new JTextField(String.valueOf(currGrade.grRaw));

		jtf2.setEditable(false); // �޸���Ʒ��Ϣʱ��ID���ɸ���
		jtf3.setEditable(false); // �޸���Ʒ��Ϣʱ�����Ʋ��ɸ���

		jb1 = new JButton("ȷ��");
		jb1.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		jb1.setContentAreaFilled(false);
		jb1.setBorderPainted(true);

		jb2 = new JButton("ȡ��");
		jb2.setFont(new Font("΢���ź�", Font.PLAIN, 14));
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
					MessageDisplay.showError("��������");
				}
				// inquiryGoods(str);
			}
		});

		this.setVisible(true);
	}
}
