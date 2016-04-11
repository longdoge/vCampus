package edu.seu.vCampus.client.view.stu.Shop;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.CardLayout;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.SharedComponents.vo.vOrder;
import edu.seu.vCampus.client.biz.module.vStore.goodManager;
import edu.seu.vCampus.client.biz.module.vStore.orderManager;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.controller.MainController;
import edu.seu.vCampus.client.view.stu.Grade.GradeCellRenderer;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;
import edu.seu.vCampus.client.view.util.FadingPanel;
import edu.seu.vCampus.client.view.util.MessageDisplay;
import edu.seu.vCampus.client.view.util.MyScrollBarUI;

public class ShopView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	public CardLayout cardLayout = new CardLayout();
	public JButton btn_Return = new JButton("");
	private JTextField txt_Search;
	private JTable tbl_Goods, tbl_Orders;
	private String[] goodCategory = new String[] { "δ����", "����", "����", "������Ʒ", "ʳƷ" };
	Object[] name = { "��Ʒ����", "��Ʒ���", "��Ʒ�۸�", "�̵�����", "��Ʒ����" };
	Object[] name2 = { "��Ʒ����", "�������", "����ʱ��", "�̵�����" }; // , "����״̬"
	private boolean isShowGoods = true;
	private Vector<vGood> currGoods;
	private Vector<vOrder> currOrders;
	private MainController mctl;

	/**
	 * Create the application.
	 */
	public ShopView(final MainController mctl) {
		this.mctl = mctl;
		setBackground(Color.WHITE);
		setLayout(null);
		final JPanel CardShop = new JPanel();
		CardShop.setBackground(Color.WHITE);
		CardShop.setBounds(105, 10, 535, 407);
		add(CardShop);
		CardShop.setLayout(cardLayout);

		JPanel pal_ShopMain = new JPanel();
		pal_ShopMain.setBackground(Color.WHITE);
		// CardShop.add(pal_ShopMain, "0");
		pal_ShopMain.setLayout(null);

		// �����ı���
		txt_Search = new JTextField();
		txt_Search.setBounds(59, 10, 189, 33);
		pal_ShopMain.add(txt_Search);
		txt_Search.setColumns(10);

		// ����������Ʒ��Ľ�����ʾ
		final JPanel pal_allgoods = new JPanel();
		
		pal_allgoods.setBackground(Color.WHITE);
		
		CardShop.add(pal_allgoods, "1");
		
		pal_allgoods.setLayout(null);
		

		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(10, 50, 515, 340);
		
		pal_allgoods.add(scrollPane);
		
		pal_allgoods.add(txt_Search);

		tbl_Goods = new JTable();
		scrollPane.setViewportView(tbl_Goods);
		scrollPane.getViewport().setBackground(Color.WHITE);
		tbl_Goods.setTableHeader(null);
		tbl_Goods.setRowHeight(80);
		tbl_Goods.setRowSelectionAllowed(false);
		tbl_Goods.setShowGrid(false);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
        
        
        
		//tbl_Orders.setTableHeader(null);
		//tbl_Orders.setRowHeight(80);
		//tbl_Orders.setRowSelectionAllowed(false);
		//tbl_Orders.setShowGrid(false);
  
		final JButton btn_Search = new JButton("��ѯ");
		btn_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = txt_Search.getText().trim();
				if (isShowGoods)
					showgoodwithname(str);
				else
					showorderswithname(str);
			}
		});
		btn_Search.setContentAreaFilled(false);
		btn_Search.setBorderPainted(true);
		btn_Search.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		btn_Search.setBounds(290, 10, 93, 33);
		btn_Search.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // ����ʱ���س��������ύ
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                	btn_Search.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });
		
		pal_ShopMain.add(btn_Search);
		pal_allgoods.add(btn_Search);

		final JButton btn_Buy = new JButton("����");
		btn_Buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������ť������
				vGood g = currGoods.get(tbl_Goods.getSelectedRow());
				String temp = JOptionPane.showInputDialog("�����빺��ļ�����");
				float num = 0;
				try {
					num = Float.parseFloat(temp);
				} catch (Exception e1) {
					MessageDisplay.showError("������д����");
					return;
				}
				if (g.gStock < num) {
					JOptionPane.showMessageDialog(null, "�����������");
				} else {
					vOrder orderInfo = new vOrder();
					orderInfo.setoAmount(num);
					orderInfo.setuId_m(g.gId);
					MessageContainer ret = orderManager.createOrder(orderInfo);
					if (ret == null)
						MessageDisplay.showError("�����쳣��");
					else {
						if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
							MessageDisplay.showInfo("����ɹ���");
							showorders();
							btn_Buy.setEnabled(false);
						} else {
							switch (ret.strParameters[0]) {
							case CommandProtocol.BALANCE_NOT_ENOUGH:
								MessageDisplay.showError("���㣡");
								break;
							case CommandProtocol.STOCK_NOT_ENOUGH:
								MessageDisplay.showError("��治�㣡");
								break;
							case CommandProtocol.ARITH_ERROR:
								MessageDisplay.showError("������д����");
								break;
							default:
								MessageDisplay.showError("����ʧ�ܣ�");
							}
							showallgood();
						}
					}
					mctl.updateBalance();
				}
			}
		});
		btn_Buy.setContentAreaFilled(false);
		btn_Buy.setBorderPainted(true);
		btn_Buy.setEnabled(true);
		btn_Buy.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		btn_Buy.setBounds(417, 10, 93, 33);
		pal_ShopMain.add(btn_Buy);
		pal_allgoods.add(btn_Buy);

		// ���а�ť
		JPanel pal_btn = new JPanel();
		pal_btn.setBackground(Color.WHITE);
		pal_btn.setBounds(0, 31, 106, 407);
		add(pal_btn);
		pal_btn.setLayout(null);

		final JButton btn_AllGoods = new JButton("������Ʒ");
		btn_AllGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {// ���������Ʒ����Ӧ
				cardLayout.show(CardShop, "1");
				showallgood();
				btn_Buy.setEnabled(true);
			}
		});

		btn_AllGoods.setContentAreaFilled(false);
		btn_AllGoods.setBorderPainted(true);
		btn_AllGoods.setFont(new Font("΢���ź�", Font.PLAIN, 14));

		btn_AllGoods.setBounds(10, 10, 93, 33);
		pal_btn.add(btn_AllGoods);

		final JButton btn_EleEquipment = new JButton("����");
		btn_EleEquipment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showGoodsByCategory(1);
				btn_Buy.setEnabled(true);
			}
		});
		btn_EleEquipment.setContentAreaFilled(false);
		btn_EleEquipment.setBorderPainted(true);
		btn_EleEquipment.setFont(new Font("΢���ź�", Font.PLAIN, 14));

		btn_EleEquipment.setBounds(10, 70, 93, 33);
		pal_btn.add(btn_EleEquipment);

		final JButton btn_Clothes = new JButton("����");
		btn_Clothes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showGoodsByCategory(2);
				btn_Buy.setEnabled(true);
			}
		});
		btn_Clothes.setContentAreaFilled(false);
		btn_Clothes.setBorderPainted(true);
		btn_Clothes.setFont(new Font("΢���ź�", Font.PLAIN, 14));

		btn_Clothes.setBounds(10, 140, 93, 33);
		pal_btn.add(btn_Clothes);

		final JButton btn_LifeGoods = new JButton("������Ʒ");
		btn_LifeGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showGoodsByCategory(3);
				btn_Buy.setEnabled(true);
			}
		});
		btn_LifeGoods.setContentAreaFilled(false);
		btn_LifeGoods.setBorderPainted(true);
		btn_LifeGoods.setFont(new Font("΢���ź�", Font.PLAIN, 14));

		btn_LifeGoods.setBounds(10, 200, 93, 33);
		pal_btn.add(btn_LifeGoods);

		final JButton btn_Foods = new JButton("ʳƷ");
		btn_Foods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showGoodsByCategory(4);
				btn_Buy.setEnabled(true);
			}
		});
		btn_Foods.setContentAreaFilled(false);
		btn_Foods.setBorderPainted(true);
		btn_Foods.setFont(new Font("΢���ź�", Font.PLAIN, 14));

		btn_Foods.setBounds(10, 260, 93, 33);
		pal_btn.add(btn_Foods);

		final JButton btn_Bought = new JButton("�ѹ�����Ʒ");
		btn_Bought.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showorders();
				btn_Buy.setEnabled(false);
			}
		});
		btn_Bought.setContentAreaFilled(false);
		btn_Bought.setBorderPainted(true);
		btn_Bought.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		btn_Bought.setBounds(10, 320, 93, 33);
		pal_btn.add(btn_Bought);
		showallgood();
	}

	
	public void showallgood() {
		isShowGoods = true;
		goodManager a = new goodManager();
		Vector<vGood> v = a.scangoods();
		/*Object[][] data = new Object[v.size()][name.length];
		for (int i = 0; i < v.size(); ++i) {
			data[i][0] = v.get(i).getgName();
			data[i][1] = v.get(i).getgStock();
			data[i][2] = v.get(i).getgPrice();
			data[i][3] = v.get(i).getShopName();
			data[i][4] = v.get(i).getgIntro();
		}
		DefaultTableModel model = new DefaultTableModel();
		model.setDataVector(data, name);*/
		
		
		vGood[] goodarr = (vGood[])v.toArray(new vGood[0]);
		tbl_Goods.setModel(new CustomizableTableModel<vGood>(goodarr, vGood.class));
		tbl_Goods.setDefaultRenderer(vGood.class, new ShopCellRenderer(this));
		tbl_Goods.setDefaultEditor(vGood.class, new ShopCellRenderer(this));
		
		currGoods = v;
	}

	public void showGoodsByCategory(int nType) {
		isShowGoods = true;
		goodManager a = new goodManager();
		Vector<vGood> v = a.scangoods();
		Vector<vGood> v2 = new Vector<vGood>();
		for (int i = 0; i < v.size(); ++i)
			if (v.get(i).gType == nType)
				v2.add(v.get(i));
		v = v2;
		vGood[] goodarr = (vGood[])v.toArray(new vGood[0]); 
		/*Object[][] data = new Object[v.size()][name.length];
		for (int i = 0; i < v.size(); ++i) {
			data[i][0] = v.get(i).getgName();
			data[i][1] = v.get(i).getgStock();
			data[i][2] = v.get(i).getgPrice();
			data[i][3] = v.get(i).getShopName();
			data[i][4] = v.get(i).getgIntro();
		}*/
		//DefaultTableModel model = new DefaultTableModel();
		//model.setDataVector(data, name);
		tbl_Goods.setModel(new CustomizableTableModel<vGood>(goodarr, vGood.class));
		tbl_Goods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		currGoods = v;
	}

	void showgoodwithname(String str) {
		isShowGoods = true;
		goodManager a = new goodManager();
		vGood good = new vGood();
		good.setgName(str);
		Vector<vGood> v = a.scangoodswithname(good);
		vGood[] goodarr = (vGood[])v.toArray(new vGood[0]); 
		/*Object[][] data = new Object[v.size()][name.length];
		for (int i = 0; i < v.size(); ++i) {
			data[i][0] = v.get(i).getgName();
			data[i][1] = v.get(i).getgStock();
			data[i][2] = v.get(i).getgPrice();
			data[i][3] = v.get(i).getShopName();
			data[i][4] = v.get(i).getgIntro();
		}*/
		//DefaultTableModel model = new DefaultTableModel();
		//model.setDataVector(data, name);
		tbl_Goods.setModel(new CustomizableTableModel<vGood>(goodarr, vGood.class));
		tbl_Goods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currGoods = v;
	}

	void showorders() {
		isShowGoods = false;
		Vector<vOrder> v = new orderManager().scanorderwithuidb(UserLoginInfoManager.getnUserID());
		vOrder[] orderarr = (vOrder[])v.toArray(new vOrder[0]); 
		Object[][] data = new Object[v.size()][name.length];
		/*for (int i = 0; i < v.size(); ++i) {
			data[i][0] = v.get(i).getoLists();
			data[i][1] = v.get(i).getoAmount();
			data[i][2] = v.get(i).getoTime();
			data[i][3] = v.get(i).getShopName();
			// data[i][4] = v.get(i).getoStatus();
		}*/
		//DefaultTableModel model = new DefaultTableModel();
		//model.setDataVector(data, name2);
		//tbl_Orders.setModel(model);
		tbl_Goods.setModel(new CustomizableTableModel<vOrder>(orderarr, vOrder.class));
		tbl_Goods.setDefaultRenderer(vOrder.class, new OrderCellRenderer(this));
		tbl_Goods.setDefaultEditor(vOrder.class, new OrderCellRenderer(this));
		tbl_Goods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currOrders = v;
	}

	void showorderswithname(String strName) {
		isShowGoods = false;
		Vector<vOrder> v = new orderManager().scanorderwithuidb(UserLoginInfoManager.getnUserID());
		Vector<vOrder> v2 = new Vector<vOrder>();
		for (int i = 0; i < v.size(); ++i)
			if (v.get(i).oLists.toLowerCase().contains(strName.toLowerCase()))
				v2.add(v.get(i));
		v = v2;
		vGood[] goodarr = (vGood[])v.toArray(new vGood[0]); 
		/*Object[][] data = new Object[v.size()][name.length];
		for (int i = 0; i < v.size(); ++i) {
			data[i][0] = v.get(i).getoLists();
			data[i][1] = v.get(i).getoAmount();
			data[i][2] = v.get(i).getoTime();
			data[i][3] = v.get(i).getShopName();
			// data[i][4] = v.get(i).getoStatus();
		}*/
		//DefaultTableModel model = new DefaultTableModel();
		//model.setDataVector(data, name2);
		tbl_Goods.setModel(new CustomizableTableModel<vGood>(goodarr, vGood.class));
		tbl_Goods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currOrders = v;
	}
}
