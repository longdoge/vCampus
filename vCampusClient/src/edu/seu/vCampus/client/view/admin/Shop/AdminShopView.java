package edu.seu.vCampus.client.view.admin.Shop;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.client.biz.module.vStore.goodManager;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.admin.Shop.Goods;

import edu.seu.vCampus.client.view.util.CustomizableTableModel;
import edu.seu.vCampus.client.view.util.FadingPanel;
import edu.seu.vCampus.client.view.util.MessageDisplay;
import edu.seu.vCampus.client.view.util.MyScrollBarUI;

public class AdminShopView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	private CardLayout cl_pal_AdminShop = new CardLayout();
	private static JTable tbl_Goods;
	private static JComboBox cbo_Shop;
	private JTextField txt_Inquiry;
	Object[] name = { "商品Id", "商品名称", "商品存货", "商品价格", "商店名称", "商品介绍", "商品类型" };
	public static vGood currGood;

	/**
	 * Create the application.
	 */
	public AdminShopView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBackground(Color.WHITE);
		setLayout(null);

		final JPanel pal_AdminShop = new JPanel();
		pal_AdminShop.setBounds(113, 0, 527, 412);
		pal_AdminShop.setBackground(Color.WHITE);
		add(pal_AdminShop);

		JPanel pal_AdminShopMain = new JPanel();
		JPanel pal_Goods = new JPanel();
		pal_Goods.setBackground(Color.WHITE);

		pal_AdminShopMain.setBackground(Color.WHITE);
		pal_AdminShopMain.setBounds(0, 42, 640, 438);

		pal_AdminShop.setLayout(cl_pal_AdminShop);

		// pal_AdminShop.add(pal_AdminShopMain, "0");
		pal_AdminShop.add(pal_Goods, "1");

		pal_Goods.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 20, 550, 341);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		pal_Goods.add(scrollPane);

		tbl_Goods = new JTable();
		scrollPane.setViewportView(tbl_Goods);
		tbl_Goods.setTableHeader(null);
		tbl_Goods.setRowHeight(80);
		tbl_Goods.setRowSelectionAllowed(false);
		tbl_Goods.setShowGrid(false);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		cbo_Shop = new JComboBox();
		cbo_Shop.setModel(new DefaultComboBoxModel(
				new String[] { "\u5546\u54C1ID", "\u5546\u54C1\u7C7B\u578B", "\u5546\u54C1\u540D\u79F0" }));
		cbo_Shop.setBackground(Color.WHITE);
		cbo_Shop.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		cbo_Shop.setBounds(0, 20, 81, 29);
		// pal_Goods.add(cbo_Shop);

		txt_Inquiry = new JTextField();
		txt_Inquiry.setBounds(96, 20, 163, 29);
		// pal_Goods.add(txt_Inquiry);
		txt_Inquiry.setColumns(10);

		final JButton btn_Inquiry = new JButton("查询");
		btn_Inquiry.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_Inquiry.setContentAreaFilled(false);
		btn_Inquiry.setBorderPainted(true);
		btn_Inquiry.setBounds(265, 20, 70, 29);
		// pal_Goods.add(btn_Inquiry);
		pal_AdminShopMain.setLayout(null);
		btn_Inquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * 监听按钮“查询” this.jTextField:文本域对象 this.jCheckBox:组合框对象
				 */
				String str = txt_Inquiry.getText().trim();
				inquiryGoods(str);
			}
		});

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 42, 114, 438);
		add(panel);
		panel.setLayout(null);

		final JButton btn_ALLGoods = new JButton("所有商品");
		btn_ALLGoods.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_ALLGoods.setContentAreaFilled(false);
		btn_ALLGoods.setBorderPainted(true);
		btn_ALLGoods.setBounds(10, 60, 96, 35);
		panel.add(btn_ALLGoods);

		final JButton btn_AddGoods = new JButton("添加商品");
		btn_AddGoods.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_AddGoods.setContentAreaFilled(false);
		btn_AddGoods.setBorderPainted(true);
		btn_AddGoods.setBounds(10, 140, 96, 35);
		panel.add(btn_AddGoods);

		final JButton btn_ChangeGoods = new JButton("修改商品");
		btn_ChangeGoods.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_ChangeGoods.setContentAreaFilled(false);
		btn_ChangeGoods.setBorderPainted(true);
		btn_ChangeGoods.setBounds(10, 220, 96, 35);
		// panel.add(btn_ChangeGoods);

		final JButton btn_DeleteGoods = new JButton("删除商品");
		btn_DeleteGoods.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_DeleteGoods.setContentAreaFilled(false);
		btn_DeleteGoods.setBorderPainted(true);
		btn_DeleteGoods.setBounds(10, 300, 96, 35);
		// panel.add(btn_DeleteGoods);

		btn_ALLGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_pal_AdminShop.show(pal_AdminShop, "1");
				showallgood();
			}
		});

		btn_AddGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGoods();
			}
		});
		showallgood();
	}

	// 获取所选行信息
	public static vGood getChosenRow() {
		int count = tbl_Goods.getSelectedRowCount();
		if (count == 0) {
			JOptionPane.showMessageDialog(null, "请选择信息！");
		} else if (count > 1) {
			JOptionPane.showMessageDialog(null, "无法选择多个信息\r\n请选择一信息！");
		} else {
			int index = tbl_Goods.getSelectedRow();
			vGood mGoods = new vGood();

			mGoods.setgId((int) tbl_Goods.getValueAt(index, 0));
			mGoods.setgName((String) tbl_Goods.getValueAt(index, 1));
			mGoods.setgStock((int) tbl_Goods.getValueAt(index, 2));
			mGoods.setShopName((String) tbl_Goods.getValueAt(index, 4));
			mGoods.setgPrice((Float) tbl_Goods.getValueAt(index, 3));
			mGoods.setgIntro((String) tbl_Goods.getValueAt(index, 5));
			mGoods.setgType((int) tbl_Goods.getValueAt(index, 6));
			return mGoods;
		}
		return null;
	}

	/**
	 * 
	 */
	public void showallgood() {

		goodManager a = new goodManager();
		vGood uinfo = new vGood();
		uinfo.setuId(UserLoginInfoManager.getnUserID());
		Vector<vGood> v = a.scangoodswithuid(uinfo);
		vGood[] goodarr = (vGood[]) v.toArray(new vGood[0]);
		/*
		 * Object[][] data = new Object[v.size()][name.length]; for (int i = 0;
		 * i < v.size(); ++i) { data[i][0] = v.get(i).getgId(); data[i][1] =
		 * v.get(i).getgName(); data[i][2] = v.get(i).getgStock(); data[i][3] =
		 * v.get(i).getgPrice(); data[i][4] = v.get(i).getShopName(); data[i][5]
		 * = v.get(i).getgIntro(); data[i][6] = v.get(i).getgType(); }
		 */
		// DefaultTableModel model = new DefaultTableModel();
		// model.setDataVector(data, name);
		// tbl_Goods.setModel(model);
		tbl_Goods.setModel(new CustomizableTableModel<vGood>(goodarr, vGood.class));
		tbl_Goods.setDefaultRenderer(vGood.class, new AdminShopCellRenderer(this));
		tbl_Goods.setDefaultEditor(vGood.class, new AdminShopCellRenderer(this));
		tbl_Goods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	}

	/**
	 * 添加商品
	 */
	private void addGoods() {
		@SuppressWarnings("unused")
		AddGoodsDialog addDialog = new AddGoodsDialog(this, "添加商品", true);
		showallgood();
		// 调用添加商品函数
	}

	/**
	 * 修改商品
	 */
	public void modifyGoods(vGood good) {
		currGood = good;
		ShopModDialog modDialog = new ShopModDialog(null, "修改商品", true);

		if (modDialog.isOK()) {
			vGood mGoods = modDialog.getInpGoods();
			mGoods.setgId(good.getgId());
			mGoods.setgName(good.getgName());
			goodManager a = new goodManager();
			a.updategood(mGoods);
			showallgood();
			// 调用修改商品函数
		}
	}

	/**
	 * 查询商品
	 */
	private void inquiryGoods(String str) {
		try {
			int sel = cbo_Shop.getSelectedIndex();
			// 查询方法
			goodManager a = new goodManager();
			vGood good = new vGood();
			Vector<vGood> v = new Vector<vGood>();
			switch (sel) {
			case 0:
				good.setgId(Integer.valueOf(str));
	
				v = a.scangoodswithgid(good);
				break;
			case 1:
				good.setgType(Integer.valueOf(str));
	
				v = a.scangoodswithtype(good);
				break;
			case 2:
				good.setgName(str);
	
				v = a.scangoodswithname(good);
				break;
			}
	
			Object[][] data = new Object[v.size()][name.length];
			for (int i = 0; i < v.size(); ++i) {
				data[i][0] = v.get(i).getgId();
				data[i][1] = v.get(i).getgName();
				data[i][2] = v.get(i).getgStock();
				data[i][3] = v.get(i).getgPrice();
				data[i][4] = v.get(i).getShopName();
				data[i][5] = v.get(i).getgIntro();
				data[i][6] = v.get(i).getgType();
	
			}
			DefaultTableModel model = new DefaultTableModel();
			model.setDataVector(data, name);
			tbl_Goods.setModel(model);
			tbl_Goods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		} catch (Exception e) {
			MessageDisplay.showError("输入有误！");
		}
	}

	/**
	 * 删除商品
	 */
	public void deleteGoods(vGood good) {
		currGood = good;

		if (currGood == null)
			return;
		goodManager a = new goodManager();
		a.deletegood(currGood);
		showallgood();
		String goodsId = String.valueOf(currGood.getgId());
		// 删除商品
	}
}
