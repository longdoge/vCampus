package edu.seu.vCampus.client.view.admin.Library;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.BookBorrowInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.BookInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.module.vLibrary.BookAdminModel;
import edu.seu.vCampus.client.biz.module.vLibrary.BookBorrowModel;
import edu.seu.vCampus.client.view.util.FadingPanel;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Font;

public class AdminLibraryView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	private CardLayout cl_jpAdminLibrary = new CardLayout();
	private JTextField txt_ISBN, txt_id2; // , txt_id3;
	private JTextField txt_name;
	private JTextField txt_author;
	private JTextField txt_press;
	private JTextField txt_Amount;
	private JTable tblBooks;
	private JTable tblBorrow;
	private BookInfoContainer[] binfo, binfo_org;
	private BookBorrowInfoContainer[] bbinfo, bbinfo_org;
	final JPanel jpAdminLibrary = new JPanel();
	private JPopupMenu popupMenu;
	private JMenuItem tableNameItem;
	private JMenuItem selectItem;
	private JButton btn_Inquiry;

	/**
	 * 获取填写的图书ID
	 * 
	 * @return 图书ID
	 */
	public String getBookId() {
		return txt_ISBN.getText();
	}

	/**
	 * 获取填写的书名
	 * 
	 * @return 书名
	 */
	public String getBookName() {
		return txt_name.getText();
	}

	/**
	 * 获取填写的作者
	 * 
	 * @return 作者
	 */
	public String getAuthor() {
		return txt_author.getText();
	}

	/**
	 * 获取填写的出版社
	 * 
	 * @return 出版社
	 */
	public String getBookPress() {
		return txt_press.getText();
	}

	/**
	 * 获取填写的可借数量
	 * 
	 * @return 可借数量
	 */
	public String getBookNum() {
		return txt_Amount.getText();
	}

	public AdminLibraryView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBackground(Color.WHITE);
		setLayout(null);

		jpAdminLibrary.setBounds(113, 0, 527, 412);
		jpAdminLibrary.setBackground(Color.lightGray);
		add(jpAdminLibrary);

		JPanel jpAdLiMain = new JPanel();
		jpAdLiMain.setBackground(Color.WHITE);
		jpAdLiMain.setBounds(0, 0, 500, 438);

		// 用户中心
		JPanel jpUser = new JPanel();
		jpUser.setBackground(Color.WHITE);
		// 增加书籍
		JPanel jpAddBooks = new JPanel();
		jpAddBooks.setBackground(Color.WHITE);
		// 借书情况
		JPanel jpBorrowCondition = new JPanel();
		jpBorrowCondition.setBackground(Color.WHITE);

		jpAdminLibrary.setLayout(cl_jpAdminLibrary);

		// 定义子界面
		// jpAdminLibrary.add(jpAdLiMain, "0");
		jpAdminLibrary.add(jpUser, "3");
		jpAdminLibrary.add(jpAddBooks, "1");
		jpAdminLibrary.add(jpBorrowCondition, "2");

		popupMenu = new JPopupMenu();

		tableNameItem = new JMenuItem("删除");
		tableNameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteBook();
			}
		});
		tableNameItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));

		selectItem = new JMenuItem("查看借阅记录");
		selectItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateBorrow();
			}
		});
		selectItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));

		popupMenu.add(tableNameItem);
		popupMenu.add(selectItem);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 70, 515, 320);
		tblBooks = new JTable();
		scrollPane.setViewportView(tblBooks);
		tblBooks.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int row = e.getY() / tblBooks.getRowHeight();
					tblBooks.clearSelection();
					tblBooks.addRowSelectionInterval(row, row);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		// jpAdLiMain.setLayout(null);
		jpUser.setLayout(null);
		jpUser.add(scrollPane);

		jpAddBooks.setLayout(null);
		jpBorrowCondition.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 30, 515, 360);
		tblBorrow = new JTable();
		scrollPane.setViewportView(tblBorrow);
		jpBorrowCondition.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 42, 114, 438);
		add(panel);
		panel.setLayout(null);

		// 用户中心
		final JButton btn_User = new JButton("图书管理");
		btn_User.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_User.setBounds(15, 60, 90, 27);
		panel.add(btn_User);
		btn_User.setContentAreaFilled(false);
		btn_User.setBorderPainted(true);

		btn_User.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 监听按钮“添加书籍”
				cl_jpAdminLibrary.show(jpAdminLibrary, "3");
			}
		});

		// 添加书籍
		final JButton btn_AddBooks = new JButton("增加图书");
		btn_AddBooks.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_AddBooks.setBounds(15, 168, 90, 27);
		panel.add(btn_AddBooks);
		btn_AddBooks.setContentAreaFilled(false);
		btn_AddBooks.setBorderPainted(true);

		btn_AddBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 监听按钮“添加书籍”
				cl_jpAdminLibrary.show(jpAdminLibrary, "1");
			}
		});

		// 增加书籍子界面
		final JButton btn_AddConfirm = new JButton("确认增加");
		btn_AddConfirm.setContentAreaFilled(false);
		btn_AddConfirm.setBorderPainted(true);
		btn_AddConfirm.setBounds(190, 315, 150, 50);
		btn_AddConfirm.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		jpAddBooks.add(btn_AddConfirm);
		btn_AddConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmAddBook();
			}
		});

		JLabel lblISBN = new JLabel("\u4E66\u7C4DISBN\uFF1A");
		lblISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblISBN.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblISBN.setBounds(125, 87, 100, 30);
		jpAddBooks.add(lblISBN);

		JLabel lblAuthor = new JLabel("\u4F5C        \u8005\uFF1A");
		lblAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAuthor.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblAuthor.setBounds(125, 140, 100, 30);
		jpAddBooks.add(lblAuthor);

		JLabel lblName = new JLabel("\u4E66        \u540D\uFF1A");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblName.setBounds(125, 25, 100, 35);
		jpAddBooks.add(lblName);

		JLabel lblPress = new JLabel("\u51FA  \u7248  \u793E\uFF1A");
		lblPress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPress.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblPress.setBounds(125, 195, 100, 30);
		jpAddBooks.add(lblPress);

		JLabel lblAmount = new JLabel("\u53EF\u501F\u6570\u91CF\uFF1A");
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblAmount.setBounds(125, 252, 100, 30);
		jpAddBooks.add(lblAmount);

		txt_ISBN = new JTextField();
		txt_ISBN.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		txt_ISBN.setBounds(240, 87, 180, 30);
		jpAddBooks.add(txt_ISBN);
		txt_ISBN.setColumns(10);

		txt_name = new JTextField();
		txt_name.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		txt_name.setColumns(10);
		txt_name.setBounds(240, 30, 180, 30);
		jpAddBooks.add(txt_name);

		txt_author = new JTextField();
		txt_author.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		txt_author.setColumns(10);
		txt_author.setBounds(240, 143, 112, 30);
		jpAddBooks.add(txt_author);

		txt_press = new JTextField();
		txt_press.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		txt_press.setColumns(10);
		txt_press.setBounds(240, 198, 112, 30);
		jpAddBooks.add(txt_press);

		txt_Amount = new JFormattedTextField(NumberFormat.getIntegerInstance());
		txt_Amount.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		txt_Amount.setColumns(10);
		txt_Amount.setBounds(240, 255, 60, 30);
		jpAddBooks.add(txt_Amount);

		// 图书信息情况子界面
		JLabel lblid_1 = new JLabel("图书名称：");
		lblid_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblid_1.setBounds(50, 20, 112, 30);
		jpUser.add(lblid_1);

		txt_id2 = new JTextField();
		txt_id2.setBounds(160, 20, 152, 30);
		jpUser.add(txt_id2);
		txt_id2.setColumns(10);
		txt_id2.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 输入时按回车，即可提交
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btn_Inquiry.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		btn_Inquiry = new JButton("查询");
		btn_Inquiry.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		// 查询接口
		btn_Inquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inquiryBooks();// 查询实现方法
			}
		});
		btn_Inquiry.setContentAreaFilled(false);
		btn_Inquiry.setBorderPainted(true);
		btn_Inquiry.setFocusable(false);
		btn_Inquiry.setBounds(350, 20, 70, 30);
		jpUser.add(btn_Inquiry);
		// jpUser.setLayout(null);

		updateBooks();
	}

	/**
	 * 刷新图书信息
	 */
	private void updateBooks() {
		binfo_org = binfo = BookAdminModel.viewAvaiBookForMe();
		renderBookInfo();
	}

	/**
	 * 查询借阅记录
	 */
	private void updateBorrow() {
		int n = tblBooks.getSelectedRowCount();
		if (n != 1)
			return;
		bbinfo_org = bbinfo = BookBorrowModel.viewBorrowByBook(binfo[tblBooks.getSelectedRow()].nBookId);
		Object[] name = { "书籍名称", "用户ID", "借阅时间", "归还时间" };
		Object[][] data = new Object[bbinfo.length][name.length];
		for (int i = 0; i < bbinfo.length; ++i) {
			data[i][0] = BookAdminModel.viewBook(bbinfo[i].nBookId).strName;
			data[i][1] = String.valueOf(bbinfo[i].nUserId);
			data[i][2] = bbinfo[i].strBorrowDate;
			data[i][3] = bbinfo[i].strReturnDate;
		}
		DefaultTableModel model = new DefaultTableModel();
		model.setDataVector(data, name);
		tblBorrow.setModel(model);
		tblBorrow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cl_jpAdminLibrary.show(jpAdminLibrary, "2");
	}

	private void renderBookInfo() {
		Object[] name = { "书籍名称", "作者", "出版社", "ISBN", "总数", "可借数量" };
		Object[][] data = new Object[binfo.length][name.length];
		for (int i = 0; i < binfo.length; ++i) {
			data[i][0] = binfo[i].strName;
			data[i][1] = binfo[i].strAuthor;
			data[i][2] = binfo[i].strPress;
			data[i][3] = binfo[i].strISBN;
			data[i][4] = binfo[i].nAmount;
			data[i][5] = binfo[i].nNum;
		}
		DefaultTableModel model = new DefaultTableModel();
		model.setDataVector(data, name);
		tblBooks.setModel(model);
		tblBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	// 确认增加接口
	private void confirmAddBook() {
		try {
			BookInfoContainer bkinfo = new BookInfoContainer();
			bkinfo.nAmount = Integer.parseInt(txt_Amount.getText());
			bkinfo.strAuthor = txt_author.getText();
			bkinfo.strISBN = txt_ISBN.getText();
			bkinfo.strName = txt_name.getText();
			bkinfo.strPress = txt_press.getText();
			MessageContainer ret = BookAdminModel.addBook(bkinfo);
			if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
				JOptionPane.showMessageDialog(null, "图书添加成功！");
				updateBooks();
				cl_jpAdminLibrary.show(jpAdminLibrary, "3");
				clearAll();
			} else
				JOptionPane.showMessageDialog(null, "输入有误，请改正！");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "输入有误，请改正！");
		}
	}

	// 查询接口
	private void inquiryBooks() {
		// System.out.println("searching");
		String key = this.txt_id2.getText().trim().toLowerCase();
		if (key.length() == 0) {
			binfo = binfo_org;
		} else {
			Vector<BookInfoContainer> sel_binfo = new Vector<BookInfoContainer>();
			for (int i = 0; i < binfo_org.length; ++i) {
				if (binfo_org[i].strName.toLowerCase().contains(key)) {
					sel_binfo.add(binfo_org[i]);
				}
			}
			binfo = sel_binfo.toArray(new BookInfoContainer[0]);
		}
		renderBookInfo();
	}

	/**
	 * 删除图书信息
	 */
	private void deleteBook() {
		int n = tblBooks.getSelectedRowCount();
		if (n != 1)
			return;
		int dialogResult = JOptionPane.showConfirmDialog(null, "确认删除此书籍吗？", "请确认", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			MessageContainer ret = BookAdminModel.delBook(binfo[tblBooks.getSelectedRow()].nBookId);
			if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
				updateBooks();
			} else {
				JOptionPane.showMessageDialog(null, "删除书籍失败", "错误提示", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void clearAll() {
		txt_ISBN.setText("");
		txt_name.setText("");
		txt_author.setText("");
		txt_press.setText("");
		txt_Amount.setText("");
	}
}
