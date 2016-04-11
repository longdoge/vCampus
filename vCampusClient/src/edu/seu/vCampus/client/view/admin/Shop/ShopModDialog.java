/**
 * 修改商品窗口，在点击商店主窗体的“修改商品”后显示
 */
package edu.seu.vCampus.client.view.admin.Shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.client.view.admin.Shop.Goods;

public class ShopModDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean OK = false; // 成员变量，标记是否确认修改商品

	public boolean isOK() {
		return OK;
	}

	public void setOK(boolean oK) {
		OK = oK;
	}

	JLabel jl1, jl2, jl3, jl4, jl5, jl6;
	JButton jb1, jb2;
	JTextField jtf1, jtf2, jtf3, jtf4, jtf6;
	JPanel jp1, jp2, jp3;

	vGood mGoods = null;
	private JComboBox jtf5;

	public ShopModDialog(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);

		jl1 = new JLabel("商品Id", JLabel.CENTER); // goodsId
		jl2 = new JLabel("商品名称", JLabel.CENTER); // goodsName
		jl3 = new JLabel("商品库存", JLabel.CENTER); // goodsCount
		jl4 = new JLabel("商品价格", JLabel.CENTER); // goodsPrice
		jl5 = new JLabel("商品种类", JLabel.CENTER); // goodsType
		jl6 = new JLabel("商品简介:", JLabel.CENTER);
		jl6.setFont(new Font("微软雅黑", Font.PLAIN, 14));

		mGoods = AdminShopView.currGood;
		if (mGoods == null)
			return;
		jtf1 = new JTextField(String.valueOf(mGoods.getgId()));
		jtf2 = new JTextField(mGoods.getgName());
		jtf3 = new JTextField(String.valueOf(mGoods.getgStock()));
		jtf4 = new JTextField(String.valueOf(mGoods.getgPrice()));
		jtf5 = new JComboBox();
		jtf5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		jtf5.setBackground(Color.WHITE);
		jtf5.setModel(new DefaultComboBoxModel(new String[] { "其余商品", "电器", "服饰", "生活用品", "食品" }));
		jtf5.setSelectedIndex(mGoods.gType);
		jtf6 = new JTextField(mGoods.gIntro);

		jtf1.setEditable(false); // 修改商品信息时，ID不可更改
		jtf2.setEditable(false); // 修改商品信息时，名称不可更改
		jb1 = new JButton("确认");
		jb1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		jb1.setContentAreaFilled(false);
		jb1.setBorderPainted(true);
		jb1.addActionListener(this);

		jb2 = new JButton("取消");
		jb2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		jb2.setContentAreaFilled(false);
		jb2.setBorderPainted(true);
		jb2.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);
		jp3.setBackground(Color.WHITE);

		jp1.setLayout(new GridLayout(6, 1, 4, 4));
		jp2.setLayout(new GridLayout(6, 1, 4, 4));

		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);

		jp2.add(jtf1);
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
		this.setVisible(true);
	}

	/**
	 * 获取弹出对话框的信息，并进行封装
	 * 
	 * @return 封装的信息
	 */
	public vGood getInpGoods() {
		vGood mGoods = new vGood();

		try {
			// mGoods.setgId( Integer.valueOf(jtf1.getText().trim()) );
			// mGoods.setgName( jtf2.getText().trim() );
			mGoods.setgStock(Integer.valueOf(jtf3.getText().trim()));
			mGoods.setgPrice(Float.valueOf(jtf4.getText().trim()));
			mGoods.setgType(jtf5.getSelectedIndex());
			mGoods.setgIntro(jtf6.getText());
		} catch (Exception e) {
			return null;
		}

		return mGoods;
	}

	/**
	 * 工具函数，判断信息录入是否合乎规范
	 * 
	 * @return 合格返回true，否则返回false
	 */
	private boolean isQualified() {
		vGood mGoods = new vGood();

		mGoods = getInpGoods();

		if (mGoods == null || (String.valueOf(mGoods.getgStock()).equals("")) || (String.valueOf(mGoods.getgType()).equals(""))
				|| (String.valueOf(mGoods.getgPrice()).equals(""))) {
			JOptionPane.showMessageDialog(null, "输入有误！");
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == jb1) { // 点击确认之后的响应
			if (isQualified()) {
				this.setOK(true);
				this.dispose();
			}
		} else if (arg0.getSource() == jb2) { // 点击取消之后的响应
			this.setOK(false);
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "YOU WOULD NEVER SEE THIS!");
		}

	}

}
