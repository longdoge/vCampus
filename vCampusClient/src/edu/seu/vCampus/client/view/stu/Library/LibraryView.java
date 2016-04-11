package edu.seu.vCampus.client.view.stu.Library;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.BookBorrowInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.BookInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.module.vLibrary.BookAdminModel;
import edu.seu.vCampus.client.biz.module.vLibrary.BookBorrowModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.util.FadingPanel;

public class LibraryView extends FadingPanel {
    private static final long serialVersionUID = 1L;

    public CardLayout cardLayout = new CardLayout();
    public JButton btn_Return = new JButton();
    private JTextField txt_SearchAll;
    private JTextField txt_SearchInfo;
    private JTable tblBooks;
    private JTable tblBorrow;
    private BookInfoContainer[] binfo, binfo_org;
	private BookBorrowInfoContainer[] bbinfo, bbinfo_org;
    private String[] bbNames, bbNames_org;
    private JPopupMenu popupMenu;
    private JMenuItem tableNameItem;
    private JButton btn_SearchAll;
    private JButton btn_SearchBorrow;
    private JPopupMenu popupMenu2;
    private JMenuItem tableNameItem2;

    /**
     * Create the application.
     */
    public LibraryView() {
        setBackground(Color.WHITE);
        setLayout(null);

        final JPanel CardLibrary = new JPanel();
        CardLibrary.setBackground(Color.WHITE);
        CardLibrary.setBounds(105, 31, 535, 407);
        add(CardLibrary);
        CardLibrary.setLayout(cardLayout);

        // 没按按钮时界面显示
        JPanel pal_LibraryMain = new JPanel();
        pal_LibraryMain.setBackground(Color.WHITE);
        // CardLibrary.add(pal_LibraryMain, "0");
        pal_LibraryMain.setLayout(null);

        JLabel lblNewLabel = new JLabel("图书馆背景");
        lblNewLabel.setIcon(new ImageIcon("images/library_bg.jpg"));
        lblNewLabel.setBounds(75, 28, 400, 267);
        pal_LibraryMain.add(lblNewLabel);

        // 按下书籍信息时显示
        final JPanel pal_AllBooks = new JPanel();
        pal_AllBooks.setBackground(Color.WHITE);
        CardLibrary.add(pal_AllBooks, "1");
        pal_AllBooks.setLayout(null);

        /*final JButton btn_Borrow = new JButton("借阅");
        btn_Borrow.setContentAreaFilled(false);
        btn_Borrow.setBorderPainted(true);
        btn_Borrow.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btn_Borrow.setBounds(434, 10, 80, 33);
        pal_AllBooks.add(btn_Borrow);*/

        JLabel label = new JLabel("按书籍名查找");

        label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label.setBounds(10, 10, 144, 31);
        pal_AllBooks.add(label);

        txt_SearchAll = new JTextField();
        txt_SearchAll.setBounds(180, 10, 124, 33);
        pal_AllBooks.add(txt_SearchAll);
        txt_SearchAll.setColumns(10);
        txt_SearchAll.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 输入时按回车，即可提交
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btn_SearchAll.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

        btn_SearchAll = new JButton("查询");
        btn_SearchAll.setContentAreaFilled(false);
        btn_SearchAll.setBorderPainted(true);
        btn_SearchAll.setFocusable(false);
        btn_SearchAll.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btn_SearchAll.setBounds(332, 10, 80, 33);
        pal_AllBooks.add(btn_SearchAll);
        btn_SearchAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_SearchInfo.setText(txt_SearchAll.getText());
                inquiryBooks();
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 70, 525, 300);
        pal_AllBooks.add(scrollPane);

        tblBooks = new JTable();
        scrollPane.setViewportView(tblBooks);
        
        popupMenu = new JPopupMenu();

        tableNameItem = new JMenuItem("借阅");
        tableNameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                borrowBook();
            }
        });
        tableNameItem.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        popupMenu.add(tableNameItem);

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

        JPanel pal_BorrowCondition = new JPanel();
        pal_BorrowCondition.setBackground(Color.WHITE);
        CardLibrary.add(pal_BorrowCondition, "2");
        pal_BorrowCondition.setLayout(null);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(0, 70, 525, 300);
        pal_BorrowCondition.add(scrollPane_1);

        tblBorrow = new JTable();
        scrollPane_1.setViewportView(tblBorrow);
        
        popupMenu2 = new JPopupMenu();

        tableNameItem2 = new JMenuItem("归还");
        tableNameItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                returnBook();
            }
        });
        tableNameItem2.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        popupMenu2.add(tableNameItem2);

        tblBorrow.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = e.getY() / tblBooks.getRowHeight();
                    tblBorrow.clearSelection();
                    tblBorrow.addRowSelectionInterval(row, row);
                    popupMenu2.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        JLabel label_1 = new JLabel("按书籍名查找");
        label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        label_1.setBounds(10, 10, 144, 31);
        pal_BorrowCondition.add(label_1);

        txt_SearchInfo = new JTextField();
        txt_SearchInfo.setColumns(10);
        txt_SearchInfo.setBounds(180, 10, 124, 33);
        pal_BorrowCondition.add(txt_SearchInfo);
        txt_SearchInfo.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 输入时按回车，即可提交
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btn_SearchBorrow.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

        btn_SearchBorrow = new JButton("查询");
        btn_SearchBorrow.setContentAreaFilled(false);
        btn_SearchBorrow.setBorderPainted(true);
        btn_SearchBorrow.setFocusable(false);
        btn_SearchBorrow.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btn_SearchBorrow.setBounds(332, 10, 80, 33);
        pal_BorrowCondition.add(btn_SearchBorrow);
        btn_SearchBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_SearchAll.setText(txt_SearchInfo.getText());
                inquiryBooks();
            }
        });

        // 所有按钮
        JPanel pal_btn = new JPanel();
        pal_btn.setBackground(Color.WHITE);
        pal_btn.setBounds(0, 31, 106, 407);
        add(pal_btn);
        pal_btn.setLayout(null);

        final JButton btn_AllBooks = new JButton("可借书籍");
        btn_AllBooks.setContentAreaFilled(false);
        btn_AllBooks.setBorderPainted(true);
        btn_AllBooks.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        btn_AllBooks.setBounds(8, 70, 95, 33);
        pal_btn.add(btn_AllBooks);

        btn_AllBooks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardLibrary, "1");
            }
        });

        final JButton btn_BorrowCondition = new JButton("借阅情况");
        btn_BorrowCondition.setContentAreaFilled(false);
        btn_BorrowCondition.setBorderPainted(true);
        btn_BorrowCondition.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        btn_BorrowCondition.setBounds(8, 200, 95, 33);
        pal_btn.add(btn_BorrowCondition);

        btn_BorrowCondition.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardLibrary, "2");
            }
        });
        
        updateBooks();
        updateBorrow();
    }
    
    /**
     * 刷新图书信息
     */
    private void updateBooks() {
    	binfo_org = binfo = BookAdminModel.viewAvaiBookForMe();
		renderBookInfo();
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
    
    /**
     * 查询借阅记录
     */
    private void updateBorrow() {
        bbinfo_org = bbinfo = BookBorrowModel.viewBorrowByUser(UserLoginInfoManager.getnUserID());
        renderBorrowInfo();
        bbNames_org = bbNames;
    }
    
    private void renderBorrowInfo() {
    	Object[] name = { "书籍名称", "作者", "出版社", "ISBN", "借阅时间", "归还时间" };
        Object[][] data = new Object[bbinfo.length][name.length];
        bbNames = new String[bbinfo.length];
        for (int i = 0; i < bbinfo.length; ++i) {
            BookInfoContainer bkinfo = BookAdminModel.viewBook(bbinfo[i].nBookId);
            bbNames[i] = bkinfo.strName;
            data[i][0] = bkinfo.strName;
            data[i][1] = bkinfo.strAuthor;
            data[i][2] = bkinfo.strPress;
            data[i][3] = bkinfo.strISBN;
            data[i][4] = bbinfo[i].strBorrowDate;
            data[i][5] = bbinfo[i].strReturnDate;
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(data, name);
        tblBorrow.setModel(model);
        tblBorrow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
    
    /**
     * 借阅图书
     */
    private void borrowBook() {
        int n = tblBooks.getSelectedRowCount();
        if (n != 1)
            return;
        MessageContainer ret = BookBorrowModel.addBook(binfo[tblBooks.getSelectedRow()].nBookId);
        if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
            updateBooks();
            updateBorrow();
        } else {
            JOptionPane.showMessageDialog(null, "借阅书籍失败", "错误提示", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 归还图书
     */
    private void returnBook() {
        int n = tblBorrow.getSelectedRowCount();
        if (n != 1)
            return;
        MessageContainer ret = BookBorrowModel.returnBook(bbinfo[tblBorrow.getSelectedRow()].nBorId);
        if (ret.strCommand.equals(CommandProtocol.SUCCESS)) {
            updateBooks();
            updateBorrow();
        } else {
            JOptionPane.showMessageDialog(null, "归还书籍失败", "错误提示", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 查询接口
    private void inquiryBooks() {
        // System.out.println("searching");
        String key = this.txt_SearchInfo.getText().trim().toLowerCase();
        if (key.length() == 0) {
        	binfo = binfo_org;
        	bbinfo = bbinfo_org;
        } else {
        	Vector<BookInfoContainer> sel_binfo = new Vector<BookInfoContainer>();
			for (int i = 0; i < binfo_org.length; ++i) {
				if (binfo_org[i].strName.toLowerCase().contains(key)) {
					sel_binfo.add(binfo_org[i]);
				}
			}
			binfo = sel_binfo.toArray(new BookInfoContainer[0]);
	        
			Vector<BookBorrowInfoContainer> sel_bbinfo = new Vector<BookBorrowInfoContainer>();
			for (int i = 0; i < bbinfo_org.length; ++i) {
				if (bbNames_org[i].toLowerCase().contains(key)) {
					sel_bbinfo.add(bbinfo_org[i]);
				}
			}
			bbinfo = sel_bbinfo.toArray(new BookBorrowInfoContainer[0]);
        }
        renderBorrowInfo();
        renderBookInfo();
    }
}
