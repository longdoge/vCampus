package edu.seu.vCampus.client.view.admin.Bank;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.TransactionInfoContainer;
import edu.seu.vCampus.client.biz.module.CourseManager.CourseSelectionModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class TransactionCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JButton showButton;
	TransactionInfoContainer feed;
	private int nUserId;
	private JLabel lblMemo;
	private JLabel lblType;
	private JLabel lblDate;
	private JLabel lblAmount;
	private JLabel lblName;
	private JLabel lblBalance;

	public TransactionCellRenderer(final int uid) {
		nUserId = uid;
		panel = new JPanel(); // new FlowLayout(FlowLayout.LEFT)
		panel.setLayout(null);

		lblName = new JLabel("\u571F\u8C6A");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 24));
		lblName.setBounds(0, 0, 150, 60);
		panel.add(lblName);
		
		lblAmount = new JLabel("+150.0");
		lblAmount.setForeground(new Color(0, 128, 0));
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmount.setVerticalAlignment(SwingConstants.BOTTOM);
		lblAmount.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 24));
		lblAmount.setBounds(157, 0, 150, 40);
		panel.add(lblAmount);
		
		lblBalance = new JLabel("=150.0");
		lblBalance.setForeground(new Color(0, 128, 0));
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setVerticalAlignment(SwingConstants.TOP);
		lblBalance.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblBalance.setBounds(157, 40, 150, 40);
		panel.add(lblBalance);
		
		lblDate = new JLabel("\u4F59\uFFE51150.0");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setForeground(Color.DARK_GRAY);
		lblDate.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		lblDate.setBounds(317, 0, 150, 33);
		panel.add(lblDate);
		
		lblType = new JLabel("\u5145\u503C");
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		lblType.setForeground(new Color(30, 144, 255));
		lblType.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblType.setBounds(317, 29, 150, 33);
		panel.add(lblType);
		
		lblMemo = new JLabel("<html><body align=\"center\">\u201C\u4F60\u8FD8\u597D\u5417\u201D</body></html>");
		lblMemo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMemo.setForeground(new Color(0, 0, 0));
		lblMemo.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblMemo.setBounds(454, 0, 150, 60);
		panel.add(lblMemo);
	}

	private void updateData(TransactionInfoContainer tinfo, boolean isSelected, JTable table) {
		this.feed = tinfo;

		String flag = null;
		Color color = null;
		String[] types = { "¹ºÎï", "×ªÕË", "³äÖµ" };
		if (tinfo == null)
			return;
		float balance;
		if (nUserId == tinfo.lInID) {
			balance = tinfo.fBalanceout;
			flag = "-";
			color = new Color(128, 0, 0);
		} else {
			balance = tinfo.fBalancein;
			flag = "+";
			color = new Color(0, 128, 0);
		}
        lblName.setText(tinfo.strName);
        lblAmount.setText(flag + "£¤" + tinfo.fAmount);
        lblAmount.setForeground(color);
        lblDate.setText(tinfo.strDate);
        lblBalance.setText("=£¤" + String.valueOf(balance));
        lblBalance.setForeground(color);
        lblType.setText(types[(int) tinfo.lOperation]);
        lblMemo.setText("<html><body align=\"center\">" + tinfo.strMemo + "</body></html>");

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		TransactionInfoContainer feed = (TransactionInfoContainer) value;
		updateData(feed, true, table);
		return panel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		TransactionInfoContainer feed = (TransactionInfoContainer) value;
		updateData(feed, isSelected, table);
		return panel;
	}
}
