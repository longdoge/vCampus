package edu.seu.vCampus.client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import edu.seu.vCampus.client.view.util.FadingPanel;

public class UserPwChgView extends FadingPanel {
    private static final long serialVersionUID = 1L;
    public JButton btnModify = new JButton();
    private JPasswordField txtNewPwd;
    private JPasswordField txtCfmPwd;
    private MainFrame theMainFrame;
    
    /**
     * 设置修改密码按钮响应代码
     * 
     * @param al
     *            响应代码
     */
    public void setModifyAction(ActionListener al) {
        btnModify.addActionListener(al);
    }

    /**
     * 获取填写的密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return new String(txtNewPwd.getPassword());
    }

    /**
     * 获取填写的确认密码
     * 
     * @return 确认密码
     */
    public String getConfirmPassword() {
        return new String(txtCfmPwd.getPassword());
    }
    
    /**
     * Create the application.
     */
    public UserPwChgView(final MainFrame theMainFrame) {
    	this.theMainFrame = theMainFrame;
    	setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbl_StudnetID = new JLabel("\u65B0 \u5BC6 \u7801 \uFF1A");
        lbl_StudnetID.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_StudnetID.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lbl_StudnetID.setBounds(172, 110, 120, 21);
        add(lbl_StudnetID);

        txtNewPwd = new JPasswordField();
        txtNewPwd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtNewPwd.setColumns(10);
        txtNewPwd.setBounds(302, 110, 127, 31);
        add(txtNewPwd);
        
        txtNewPwd.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 输入时按回车，即可提交
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnModify.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        
        JLabel lbl_Course = new JLabel("确认密码：");
        lbl_Course.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_Course.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        lbl_Course.setBounds(172, 178, 120, 21);
        add(lbl_Course);

        txtCfmPwd = new JPasswordField();
        txtCfmPwd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtCfmPwd.setColumns(10);
        txtCfmPwd.setBounds(302, 175, 127, 31);
        add(txtCfmPwd);
        
        txtCfmPwd.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 输入时按回车，即可提交
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnModify.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        btnModify = new JButton("立即修改");
        btnModify.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btnModify.setContentAreaFilled(false);
        btnModify.setBorderPainted(true);
        btnModify.setFocusable(false);
        btnModify.setBounds(234, 258, 169, 47);
        add(btnModify);
        
        final JButton btn_GoBack = new JButton(new ImageIcon("images/backarrow.png"));
        btn_GoBack.setContentAreaFilled(false);
        btn_GoBack.setBorderPainted(false);
        btn_GoBack.setBounds(10, 5, 24, 24);
        btn_GoBack.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		theMainFrame.switchToFirstCard();
			}
        });
        btn_GoBack.setFocusable(false);
        
        final JLabel btn_GoBack_tip = new JLabel("<html><body><nobr>返回主页</nobr></body></html>");
        // btn_GoBack_tip.setBounds(35, 5, 0, 24);
        btn_GoBack_tip.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        btn_GoBack_tip.setFocusable(false);
        btn_GoBack_tip.setHorizontalAlignment(SwingUtilities.LEFT);
        
        btn_GoBack.addMouseListener(new MouseAdapter() {
        	private long nIntv = 10, nMiliSec = 500;
            private long start;
            private boolean isAnimating = false;
            private boolean isShown = false;
            private boolean isMouseIn = false;
            private int[] cursor = new int[] {38, 5, 0, 24};
            private int cursor_upper = 150;
            private int cursor_lower = 0;

			public void mouseEntered(final MouseEvent arg0) {
				isMouseIn = true;
				if (isAnimating || isShown)
					return;
				isAnimating = true;
				cursor[2] = cursor_lower;
            	start = System.currentTimeMillis();
            	final Timer t = new Timer((int) nIntv, null);
                t.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long elapsed = System.currentTimeMillis() - start;
                        if (elapsed > nMiliSec) {
                            start = 0;
                            isAnimating = false;
                            isShown = true;
                            t.stop();
                            if (cursor[2] <= cursor_upper) {
                            	cursor[2] = cursor_upper;
                            	btn_GoBack_tip.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                            }
                            repaint();
                            if (!isMouseIn)
                            	mouseExited(arg0);
                        } else {
                            cursor[2] = (int) ((cursor_upper - cursor_lower) * elapsed / nMiliSec);
                            btn_GoBack_tip.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                            repaint();
                        }
                    }
                });
                t.start();
            }

            public void mouseExited(final MouseEvent arg0) {
            	isMouseIn = false;
            	if (isAnimating || !isShown)
					return;
            	isAnimating = true;
            	start = System.currentTimeMillis();
            	final Timer t = new Timer((int) nIntv, null);
                t.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long elapsed = System.currentTimeMillis() - start;
                        if (elapsed > nMiliSec) {
                            start = 0;
                            isAnimating = false;
                            isShown = false;
                            t.stop();
                            if (cursor[2] > cursor_lower) {
                            	cursor[2] = cursor_lower;
                            	btn_GoBack_tip.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                            }
                            repaint();
                        	if (isMouseIn)
                            	mouseEntered(arg0);
                        	return;
                        }
                        cursor[2] = cursor_upper - (int) ((cursor_upper - cursor_lower) * elapsed / nMiliSec);
                        btn_GoBack_tip.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                        repaint();
                    }
                });
                t.start();
            }
        });
        
		add(btn_GoBack);
		add(btn_GoBack_tip);
		setComponentZOrder(btn_GoBack_tip, 0);
        
        initialize();
    }
    

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        JPanel jpChgPwd = new JPanel();
        jpChgPwd.setBackground(Color.WHITE);
        jpChgPwd.setBounds(0, 42, 640, 438);
    }
    
    public void clearPasswords() {
    	txtNewPwd.setText("");
    	txtCfmPwd.setText("");
    }
}