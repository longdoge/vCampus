package edu.seu.vCampus.client.view.stu.Shop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

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
import edu.seu.vCampus.SharedComponents.vo.vOrder;
import edu.seu.vCampus.client.view.stu.Course.CourseInfoWithSelectionInfo;

public class OrderCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	vOrder feed;
	
	private JLabel lblGoodName;
	private JLabel lblOrderPrice;
	private JLabel lblOrderTime;
	private JLabel lblShopName;
	
	public OrderCellRenderer(ShopView sv){
		int xoffset = -10;
		int yoffset = 8;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		lblGoodName = new JLabel("\u5546\u54C1");
		lblGoodName.setForeground(Color.BLACK);
		lblGoodName.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoodName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		lblGoodName.setBounds(66, 9, 150, 30);
		panel.add(lblGoodName);
		
		lblOrderPrice = new JLabel("\u8BA2\u5355\u4EF7\u683C");
		lblOrderPrice.setForeground(new Color(0, 0, 255));
		lblOrderPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrderPrice.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		lblOrderPrice.setBounds(260, 10, 82, 30);
		panel.add(lblOrderPrice);

		lblOrderTime = new JLabel("\u8BA2\u5355\u65F6\u95F4");
		lblOrderTime.setForeground(new Color(0, 100, 0));
		lblOrderTime.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		lblOrderTime.setBounds(332, 40, 217, 30);
		panel.add(lblOrderTime);

		lblShopName = new JLabel("\u5546\u5E97\u540D");
		lblShopName.setForeground(new Color(0, 139, 139));
		lblShopName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		lblShopName.setBounds(383, 10, 82, 30);
		panel.add(lblShopName);

		
		
		/*JButton btnNewButton = new JButton("\u4FEE\u6539");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(195, 46, 93, 23);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("\u5220\u9664");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(351, 46, 93, 23);
		panel.add(btnNewButton_1);*/
	}
	
	private void updateData(vOrder feed, boolean isSelected, JTable table) {
		this.feed = feed;
		
		lblGoodName.setText(feed.getoLists());
		lblOrderPrice.setText(String.valueOf(feed.getoAmount()));
		lblOrderTime.setText(feed.getoTime());
		lblShopName.setText(feed.getShopName());

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		vOrder feed = (vOrder) value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		vOrder feed = (vOrder) value;
		updateData(feed, isSelected, table);
		return panel;
	}
}
