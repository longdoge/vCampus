package edu.seu.vCampus.client.view.admin.Shop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.client.view.stu.Course.CourseInfoWithSelectionInfo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.seu.vCampus.SharedComponents.vo.vGood;

public class AdminShopCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JButton deleteorder;
	JButton updateorder;
	
	vGood feed = new vGood();
	
	private JLabel lblGoodName;
	private JLabel lblGoodPrice;
	private JLabel lblGoodStock;
	private JLabel lblGoodInrto;
	private JLabel lblGoodType;
	private JLabel lblShopName;
	
	
	public AdminShopCellRenderer(final AdminShopView asv){
		int xoffset = -10;
		int yoffset = 8;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		
		lblGoodName = new JLabel("\u5546\u5E97\u540D\u79F0");
		lblGoodName.setForeground(Color.BLACK);
		lblGoodName.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoodName.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
		lblGoodName.setBounds(10, 16, 94, 43);
		panel.add(lblGoodName);
		
		lblGoodPrice = new JLabel("\u5546\u54C1\u4EF7\u683C");
		lblGoodPrice.setForeground(Color.BLACK);
		lblGoodPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoodPrice.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		lblGoodPrice.setBounds(132, 11, 82, 30);
		panel.add(lblGoodPrice);

		lblGoodStock = new JLabel("\u5546\u54C1\u5B58\u8D27");
		lblGoodStock.setForeground(new Color(220, 20, 60));
		lblGoodStock.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		lblGoodStock.setBounds(244, 11, 56, 30);
		panel.add(lblGoodStock);

		lblGoodInrto = new JLabel("\u5546\u54C1\u4ECB\u7ECD");
		lblGoodInrto.setForeground(new Color(0, 139, 139));
		lblGoodInrto.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		lblGoodInrto.setBounds(132, 40, 168, 30);
		panel.add(lblGoodInrto);

		lblGoodType = new JLabel("\u5546\u54C1\u7C7B\u578B");
		lblGoodType.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoodType.setForeground(new Color(0, 100, 0));
		lblGoodType.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		lblGoodType.setBounds(299, 12, 93, 29);
		panel.add(lblGoodType);

		lblShopName = new JLabel("\u5546\u5E97\u540D\u79F0");
		lblShopName.setHorizontalAlignment(SwingConstants.CENTER);
		lblShopName.setForeground(new Color(30, 144, 255));
		lblShopName.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		lblShopName.setBounds(402, 5, 88, 43);
		panel.add(lblShopName);
		
		updateorder = new JButton("\u4FEE\u6539");
		updateorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				asv.modifyGoods(feed);
			}
		});
		updateorder.setBounds(299, 46, 93, 23);
		updateorder.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		updateorder.setContentAreaFilled(false);
		updateorder.setBorderPainted(true);
		panel.add(updateorder);
		
		deleteorder = new JButton("\u5220\u9664");
		deleteorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				asv.deleteGoods(feed);
			}
		});
		deleteorder.setBounds(397, 46, 93, 23);
		deleteorder.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
		deleteorder.setContentAreaFilled(false);
		deleteorder.setBorderPainted(true);
		panel.add(deleteorder);
	}
	
	private void updateData(vGood feed, boolean isSelected, JTable table) {
		this.feed = feed;
		
		lblGoodName.setText(feed.gName);
		lblGoodPrice.setText(String.valueOf(feed.gPrice));
		lblGoodStock.setText(String.valueOf(feed.gStock));
		lblGoodInrto.setText(feed.gIntro);
		lblGoodType.setText(typetostring(feed.gType));
		lblShopName.setText(feed.getShopName());

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		vGood feed = (vGood) value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		vGood feed = (vGood) value;
		updateData(feed, isSelected, table);
		return panel;
	}
	
	public String typetostring(int type) {
		String[] types = new String[] { "∆‰”‡…Ã∆∑", "µÁ∆˜", "∑˛ Œ", "…˙ªÓ”√∆∑", " ≥∆∑" };
		return types[type];
	}
}