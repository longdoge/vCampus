package edu.seu.vCampus.client.view.stu.Bank;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.TransactionInfoContainer;
import edu.seu.vCampus.client.biz.module.vBank.BankInfoModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.controller.MainController;
import edu.seu.vCampus.client.view.MainFrame;
import edu.seu.vCampus.client.view.admin.Bank.TransactionCellRenderer;
import edu.seu.vCampus.client.view.util.CustomizableTableModel;
import edu.seu.vCampus.client.view.util.FadingPanel;
import edu.seu.vCampus.client.view.util.MessageDisplay;
import edu.seu.vCampus.client.view.util.MyScrollBarUI;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BankView extends FadingPanel {
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout = new CardLayout();
    private JTextField txt_Ecard;
    private JTextField txt_InAccount;
    private JTextField txt_TransferMoney;
    private JTextField txt_Recharge;
    public JButton btn_Return = new JButton();
    private MainController mf;

    JTable jtBill = new JTable();// 显示账单的表格控件
    JTextField txt_BankAccount;
	private JTextField txt_Memo;
	private JButton btn_Transfer;

    /**
     * Create the application.
     */
    public BankView(final MainController mf) {
    	this.mf = mf;
        setBackground(Color.WHITE);
        setLayout(null);

        final JPanel CardBank = new JPanel();
        CardBank.setBackground(Color.WHITE);
        CardBank.setBounds(10, 30, 620, 407);
        add(CardBank);
        CardBank.setLayout(cardLayout);

        // 没按按钮时右边界面显示
        JPanel pal_BankMain = new JPanel();
        pal_BankMain.setBackground(Color.WHITE);
        // CardBank.add(pal_BankMain, "0");
        pal_BankMain.setLayout(null);

        JLabel lal_welcome = new JLabel("欢迎使用虚拟银行系统~");
        lal_welcome.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lal_welcome.setBounds(150, 152, 240, 30);
        pal_BankMain.add(lal_welcome);

        // 余额查询
        final JPanel pal_Balance = new JPanel();
        pal_Balance.setBackground(Color.WHITE);
        // CardBank.add(pal_Balance, "1");
        pal_Balance.setLayout(null);

        JLabel lal_Ecard = new JLabel("一卡通余额");
        lal_Ecard.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lal_Ecard.setBounds(118, 152, 120, 30);
        pal_Balance.add(lal_Ecard);

        txt_Ecard = new JTextField();
        txt_Ecard.setEditable(false);
        txt_Ecard.setColumns(9);
        txt_Ecard.setBounds(262, 152, 133, 24);
        pal_Balance.add(txt_Ecard);

        /*JLabel lbl_BankAccount = new JLabel("银行卡余额");
        lbl_BankAccount.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lbl_BankAccount.setBounds(118, 201, 120, 30);
        pal_Balance.add(lbl_BankAccount);

        txt_BankAccount = new JTextField();
        txt_BankAccount.setEditable(false);
        txt_BankAccount.setColumns(10);
        txt_BankAccount.setBounds(262, 201, 133, 24);
        pal_Balance.add(txt_BankAccount);*/

        // 交易明细
        JPanel pal_Bill = new JPanel();
        pal_Bill.setBackground(Color.WHITE);
        CardBank.add(pal_Bill, "2");
        pal_Bill.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 0, 620, 370);
        pal_Bill.add(scrollPane);

        scrollPane.setViewportView(jtBill);
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		
		jtBill = new JTable();
		scrollPane.setViewportView(jtBill);
        scrollPane.getViewport().setBackground(Color.WHITE);
        jtBill.setTableHeader(null);
        jtBill.setRowHeight(80);
        jtBill.setRowSelectionAllowed(false);
        jtBill.setShowGrid(false);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        // 转账
        JPanel pal_Transfer = new JPanel();
        pal_Transfer.setBackground(Color.WHITE);
        CardBank.add(pal_Transfer, "3");
        pal_Transfer.setLayout(null);

        JLabel lbl_InAccount = new JLabel("转入账号");
        lbl_InAccount.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lbl_InAccount.setBounds(180, 55, 120, 30);
        pal_Transfer.add(lbl_InAccount);

        txt_InAccount = new JTextField();
        txt_InAccount.setColumns(10);
        txt_InAccount.setBounds(300, 55, 120, 30);
        pal_Transfer.add(txt_InAccount);

        JLabel lbl_TransferMoney = new JLabel("转账金额");
        lbl_TransferMoney.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lbl_TransferMoney.setBounds(180, 115, 120, 30);
        pal_Transfer.add(lbl_TransferMoney);
        
        txt_TransferMoney = new JTextField();
        txt_TransferMoney.setColumns(10);
        txt_TransferMoney.setBounds(300, 115, 120, 30);
        pal_Transfer.add(txt_TransferMoney);

        JLabel lbl_Memo = new JLabel("附言");
        lbl_Memo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lbl_Memo.setBounds(180, 175, 120, 30);
        pal_Transfer.add(lbl_Memo);
        
        txt_Memo = new JTextField();
        txt_Memo.setColumns(10);
        txt_Memo.setBounds(300, 175, 120, 30);
        pal_Transfer.add(txt_Memo);

        final JButton btn_TransferConfirm = new JButton("确认");
        btn_TransferConfirm.setContentAreaFilled(false);
        btn_TransferConfirm.setBorderPainted(true);
        btn_TransferConfirm.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btn_TransferConfirm.setBounds(330, 280, 120, 40);
        btn_TransferConfirm.setFocusable(false);
        pal_Transfer.add(btn_TransferConfirm);
        
        final JButton btn_Cancel = new JButton("取消");
        btn_Cancel.setContentAreaFilled(false);
        btn_Cancel.setBorderPainted(true);
        btn_Cancel.setFocusable(false);
        btn_Cancel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btn_Cancel.setBounds(150, 280, 120, 40);
        pal_Transfer.add(btn_Cancel);
        btn_Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(CardBank, "2");
				btn_Transfer.setVisible(true);
			}
        });

        // 充值
        JPanel pal_Recharge = new JPanel();
        pal_Recharge.setBackground(Color.WHITE);
        // CardBank.add(pal_Recharge, "4");
        pal_Recharge.setLayout(null);

        txt_Recharge = new JTextField();
        txt_Recharge.setColumns(10);
        txt_Recharge.setBounds(264, 131, 201, 33);
        pal_Recharge.add(txt_Recharge);

        JLabel lbl_Recharge = new JLabel("充值金额");
        lbl_Recharge.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lbl_Recharge.setBounds(80, 134, 120, 30);
        pal_Recharge.add(lbl_Recharge);

        final JButton btn_RechargeConfirm = new JButton("确认");
        btn_RechargeConfirm.setContentAreaFilled(false);
        btn_RechargeConfirm.setBorderPainted(true);

        btn_RechargeConfirm.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btn_RechargeConfirm.setBounds(165, 260, 168, 41);
        pal_Recharge.add(btn_RechargeConfirm);
        
        btn_TransferConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isEmptyText(txt_InAccount) || isEmptyText(txt_TransferMoney)) {
					MessageDisplay.showError("转账信息不完整！");
					return;
				}
				TransactionInfoContainer tran = new TransactionInfoContainer();
				tran.setfAmount(Float.valueOf(txt_TransferMoney.getText()));
				tran.setStrName(txt_InAccount.getText());
				tran.setStrMemo(txt_Memo.getText());
				MessageContainer ret = BankInfoModel.Tran(tran);
				if (ret == null) {
					MessageDisplay.showError("网络错误！");
					return;
				}
				if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
					MessageDisplay.showInfo("转账成功！");
					updateTransactions();
					cardLayout.show(CardBank, "2");
					mf.updateBalance();
				} else {
					switch (ret.strParameters[0]) {
					case CommandProtocol.NONEXIST_TRANID:
						MessageDisplay.showError("收款人不存在！");
						break;
					case CommandProtocol.ARITH_ERROR:
						MessageDisplay.showError("余额不足！");
						break;
					default:
						MessageDisplay.showError("转账失败！");
					}
				}
			}
        });

        JPanel pal_btn = new JPanel();
        pal_btn.setBackground(Color.WHITE);
        pal_btn.setBounds(0, 31, 110, 461);
        pal_btn.setLayout(null);

        /*final JButton btn_Balance = new JButton("查询余额");
        btn_Balance.setContentAreaFilled(false);
        btn_Balance.setBorderPainted(true);
        btn_Balance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardBank, "1");
            }
        });
        btn_Balance.setBounds(10, 75, 95, 33);
        btn_Balance.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        // pal_btn.add(btn_Balance);
        */
        
        final JButton btn_Bill = new JButton("交易明细");
        btn_Bill.setContentAreaFilled(false);
        btn_Bill.setBorderPainted(true);
        btn_Bill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardBank, "2");
            }
        });
        btn_Bill.setBounds(10, 127, 95, 33);
        btn_Bill.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        // pal_btn.add(btn_Bill);

        btn_Transfer = new JButton(new ImageIcon("images/bank_cards.png"));
        btn_Transfer.setContentAreaFilled(false);
        btn_Transfer.setBorderPainted(false);
        btn_Transfer.setFocusable(false);
        btn_Transfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardBank, "3");
                btn_Transfer.setVisible(false);
            }
        });
        btn_Transfer.setBounds(595, 0, 24, 24);
        add(btn_Transfer);
        // btn_Transfer.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        // pal_btn.add(btn_Transfer);

        // 充值
        /*final JButton btn_Recharge = new JButton("充值");
        btn_Recharge.setContentAreaFilled(false);
        btn_Recharge.setBorderPainted(true);
        btn_Recharge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardBank, "4");
            }
        });
        btn_Recharge.setBounds(10, 227, 95, 33);
        btn_Recharge.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        pal_btn.add(btn_Recharge);*/
        // add(pal_btn);
        updateBalance();
        updateTransactions();
    }
    
    private void updateBalance() {
    	MessageContainer ret = BankInfoModel.Query(UserLoginInfoManager.getnUserID());
    	if (ret.getStrCommand().equals(CommandProtocol.SUCCESS))
    		txt_Ecard.setText(ret.strParameters[0]);
    }
    
    /*private void updateTransactions() {
		Object[] name = { "对方用户", "金额", "余额", "操作类型", "附言" };
		String flag = "";
		String[] types = { "购物", "转账", "充值" };
		MessageContainer ret = BankInfoModel.View(UserLoginInfoManager.getnUserID());
    	Object[][] data = null;
    	if (ret.getStrCommand().equals(CommandProtocol.SUCCESS)) {
    		if (ret.entityParameters != null || ret.strParameters == null || !ret.strParameters[0].equals(CommandProtocol.NODATA)) {
	            data = new Object[ret.entityParameters.length][name.length];
	    		TransactionInfoContainer[] ent = new TransactionInfoContainer[ret.entityParameters.length];
				for (int i = 0; i < ret.entityParameters.length; ++i)
					ent[i] = (TransactionInfoContainer) ret.entityParameters[i];
	    		TransactionInfoContainer[] tinfo = ent;
	    		for (int i = 0; i < ret.entityParameters.length; ++i) {
	    			TransactionInfoContainer tinfo = (TransactionInfoContainer) ret.entityParameters[i];
	    			if (tinfo == null)
	    				continue;
	    			long uid;
	    			float balance;
	    			if (UserLoginInfoManager.getnUserID() == tinfo.lInID) {
	    				uid = tinfo.lOutID;
	    				balance = tinfo.fBalanceout;
	    				flag = "-";
	    			} else {
	    				uid = tinfo.lInID;
	    				balance = tinfo.fBalancein;
	    				flag = "+";
	    			}
	                data[i][0] = tinfo.strName;
	                data[i][1] = flag + tinfo.fAmount;
	                data[i][2] = balance;
	                data[i][3] = types[(int) tinfo.lOperation];
	                data[i][4] = tinfo.strMemo;
	    		}
    		}
            DefaultTableModel model = new DefaultTableModel();
            model.setDataVector(data, name);
            jtBill.setModel(model);
            jtBill.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    	}
    }*/
    
    private void updateTransactions() {
    	int nUserId = UserLoginInfoManager.getnUserID();
		MessageContainer ret = BankInfoModel.View(nUserId);
		TransactionInfoContainer[] trans = null;
		if (ret.getStrCommand().equals(CommandProtocol.SUCCESS)) {
			if (ret.entityParameters != null || ret.strParameters == null || !ret.strParameters[0].equals(CommandProtocol.NODATA)) {
				trans = new TransactionInfoContainer[ret.entityParameters.length];
				for (int i = 0; i < ret.entityParameters.length; ++i)
	    			trans[i] = (TransactionInfoContainer) ret.entityParameters[i];
			}
    	}
		jtBill.setModel(new CustomizableTableModel<TransactionInfoContainer>(trans, TransactionInfoContainer.class));
		jtBill.setDefaultRenderer(TransactionInfoContainer.class, new TransactionCellRenderer(nUserId));
		jtBill.setDefaultEditor(TransactionInfoContainer.class, new TransactionCellRenderer(nUserId));
		jtBill.setRowHeight(80);
	}
    
    private boolean isEmptyText(final JTextField tf) {
    	return tf.getText().length() == 0;
    }
}
