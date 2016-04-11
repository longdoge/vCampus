package edu.seu.vCampus.client.view.admin.Bank;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.TransactionInfoContainer;
import edu.seu.vCampus.client.biz.module.UserManager.UserInfoModel;
import edu.seu.vCampus.client.biz.module.vBank.BankInfoModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;
import edu.seu.vCampus.client.view.util.FadingPanel;
import edu.seu.vCampus.client.view.util.MessageDisplay;
import edu.seu.vCampus.client.view.util.MyScrollBarUI;

public class AdminBankView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textUsername;
	private JButton btnSearch;
	private JLabel lblBalance;
	private JTable table;
	private JButton btnCharge;
	private JLabel lblName;
	private int nUserId = 6;

	public AdminBankView() {
		setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel lblUsername = new JLabel("\u7528\u6237\u540D\uFF1A");
        lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsername.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lblUsername.setBounds(43, 32, 120, 35);
        add(lblUsername);
        
        textUsername = new JTextField();
        textUsername.setBounds(170, 36, 161, 31);
        add(textUsername);
        textUsername.setColumns(10);
        
        textUsername.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 输入时按回车，即可提交
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                	btnSearch.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });
        
        btnSearch = new JButton("\u641C\u7D22\u7528\u6237");
        btnSearch.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btnSearch.setFocusable(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(true);
        btnSearch.setBounds(430, 32, 120, 35);
        add(btnSearch);
        
        btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (textUsername.getText().length() == 0)
					return;
				updateInformation();
			}
        });
        
        lblBalance = new JLabel("\uFFE51000.0");
        lblBalance.setHorizontalAlignment(SwingConstants.LEFT);
        lblBalance.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lblBalance.setBounds(170, 80, 161, 35);
        lblBalance.setVisible(false);
        add(lblBalance);
        
        JScrollPane scrollPane0 = new JScrollPane();
		scrollPane0.setBounds(10, 126, 615, 260);
		add(scrollPane0);
		scrollPane0.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane0.getVerticalScrollBar().setBackground(Color.WHITE);
		
		table = new JTable();
		scrollPane0.setViewportView(table);
        scrollPane0.getViewport().setBackground(Color.WHITE);
        table.setTableHeader(null);
        table.setRowHeight(80);
        table.setRowSelectionAllowed(false);
        table.setShowGrid(false);
        scrollPane0.setBorder(new EmptyBorder(0, 0, 0, 0));
		
        btnCharge = new JButton("\u5145\u503C");
        btnCharge.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btnCharge.setFocusable(false);
        btnCharge.setContentAreaFilled(false);
        btnCharge.setBorderPainted(true);
        btnCharge.setBounds(430, 78, 120, 35);
        btnCharge.setVisible(false);
        add(btnCharge);
        btnCharge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String temp = JOptionPane.showInputDialog("请输入充值的金额：");
				float num = 0;
				try {
					num = Float.parseFloat(temp);
				} catch (Exception e1) {
					MessageDisplay.showError("金额填写错误！");
					return;
				}
				MessageContainer ret = BankInfoModel.Charge(nUserId, num);
				if (ret == null)
					MessageDisplay.showError("网络异常！");
				else {
					if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
						MessageDisplay.showInfo("充值成功！");
						updateInformation();
					} else {
						switch (ret.strParameters[0]) {
						case CommandProtocol.NO_PREV:
							MessageDisplay.showError("权限不足！");
							break;
						case CommandProtocol.NONEXIST_TRANID:
							MessageDisplay.showError("用户不存在！");
							break;
						case CommandProtocol.ARITH_ERROR:
							MessageDisplay.showError("金额错误！");
							break;
						default:
							MessageDisplay.showError("充值失败！");
						}
					}
				}
			}
        });
        
        lblName = new JLabel("\u5927\u571F\u8C6A");
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        lblName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lblName.setBounds(50, 80, 110, 35);
        lblName.setVisible(false);
        add(lblName);
	}
	
	private void updateTransactions() {
		MessageContainer ret = BankInfoModel.View(nUserId);
		TransactionInfoContainer[] trans = null;
		if (ret.getStrCommand().equals(CommandProtocol.SUCCESS)) {
			if (ret.entityParameters != null || ret.strParameters == null || !ret.strParameters[0].equals(CommandProtocol.NODATA)) {
				trans = new TransactionInfoContainer[ret.entityParameters.length];
				for (int i = 0; i < ret.entityParameters.length; ++i)
	    			trans[i] = (TransactionInfoContainer) ret.entityParameters[i];
			}
    	}
		table.setModel(new CustomizableTableModel<TransactionInfoContainer>(trans, TransactionInfoContainer.class));
		table.setDefaultRenderer(TransactionInfoContainer.class, new TransactionCellRenderer(nUserId));
		table.setDefaultEditor(TransactionInfoContainer.class, new TransactionCellRenderer(nUserId));
		table.setRowHeight(80);
	}
	
	private void updateInformation() {
		nUserId = UserInfoModel.getUidByName(textUsername.getText());
		if (nUserId <= 0) {
			MessageDisplay.showError("用户不存在！");
			return;
		}
		lblName.setText(UserInfoModel.getNameByUid(nUserId));
		MessageContainer ret = BankInfoModel.Query(nUserId);
    	if (ret.getStrCommand().equals(CommandProtocol.SUCCESS))
    		lblBalance.setText("￥" + ret.strParameters[0]);
    	lblBalance.setVisible(true);
    	btnCharge.setVisible(true);
    	lblName.setVisible(true);
    	updateTransactions();
	}
}
