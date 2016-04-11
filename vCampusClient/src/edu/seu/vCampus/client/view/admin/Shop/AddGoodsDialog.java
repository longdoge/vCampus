/**
 * �����Ʒ���ڣ��ڵ��ͼ���������ġ������Ʒ������ʾ
 */
package edu.seu.vCampus.client.view.admin.Shop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.client.biz.module.vStore.goodManager;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.admin.Shop.Goods;
import edu.seu.vCampus.client.view.util.MessageDisplay;

public class AddGoodsDialog extends JDialog {

	/***
	 * MessageType {
	 * 
	 * //ͨ�ź��� }
	 ***/

	JLabel jl1, jl2, jl3, jl4, jl5, jl6;
	JButton jb1, jb2;
	JTextField jtf1, jtf2, jtf3, jtf4, jtf6;
	JPanel jp1, jp2, jp3;
	private JComboBox jtf5;

	// ���캯��
	public AddGoodsDialog(final AdminShopView asv, String arg1, boolean arg2) {

		// super(arg0, arg1, arg2);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();

		jl2 = new JLabel("��Ʒ����:", JLabel.CENTER);
		jl2.setFont(new Font("΢���ź�", Font.PLAIN, 14));// goodsName
		jl3 = new JLabel("��Ʒ����:", JLabel.CENTER);
		jl3.setFont(new Font("΢���ź�", Font.PLAIN, 14));// goodsCount
		jl4 = new JLabel("��Ʒ�۸�:", JLabel.CENTER);
		jl4.setFont(new Font("΢���ź�", Font.PLAIN, 14));// goodsType
		jl5 = new JLabel("��Ʒ����:", JLabel.CENTER);
		jl5.setFont(new Font("΢���ź�", Font.PLAIN, 14));// goodsPrice
		jl6 = new JLabel("��Ʒ���:", JLabel.CENTER);
		jl6.setFont(new Font("΢���ź�", Font.PLAIN, 14));

		jtf2 = new JTextField();
		jtf3 = new JTextField();
		jtf4 = new JTextField();
		// jtf5 = new JTextField();
		jtf5 = new JComboBox();
		jtf5.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		jtf5.setBackground(Color.WHITE);
		jtf5.setModel(new DefaultComboBoxModel(new String[] { "������Ʒ", "����", "����", "������Ʒ", "ʳƷ" }));
		jtf6 = new JTextField();

		jb1 = new JButton("ȷ�����");
		jb1.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		jb1.setContentAreaFilled(false);
		jb1.setBorderPainted(true);
		// jb1.addActionListener(this);

		jb2 = new JButton("ȡ�����");
		jb2.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		jb2.setContentAreaFilled(false);
		jb2.setBorderPainted(true);
		// jb2.addActionListener(this);

		jp1.setLayout(new GridLayout(6, 1, 4, 4));
		jp2.setLayout(new GridLayout(6, 1, 4, 4));

		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);

		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		jp2.add(jtf6);

		jp3.add(jb1);
		jp3.add(jb2);

		this.add(jp1, BorderLayout.WEST);
		this.add(jp2, BorderLayout.CENTER);
		this.add(jp3, BorderLayout.SOUTH);

		this.setSize(300, 300);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize = getSize();
		int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
		setLocation(x, y);

		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * ������ť"�����Ʒ"
				 */
				try {
					String str2 = jtf2.getText().trim();
					String str3 = jtf3.getText().trim();
					String str4 = jtf4.getText().trim();
					// String str5 = jtf5.getText().trim();
					String str6 = jtf6.getText().trim();
	
					goodManager a = new goodManager();
					vGood good = new vGood();
					good.setgName(str2);
					// good.setgId(Integer.valueOf(str1));
					good.setuId(UserLoginInfoManager.getnUserID());
					good.setgIntro(str6);
					good.setgPrice(Float.valueOf(str4));
					good.setgStock(Integer.valueOf(str3));
					good.setgType(jtf5.getSelectedIndex());
	
					String info = a.addgood(good);
					// System.out.println(info);
					// inquiryGoods(str);
					asv.showallgood();
					dispose();
				} catch (Exception e1) {
					MessageDisplay.showError("��������");
				}
			}
		});
		
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});

		this.setVisible(true);
	}
}
