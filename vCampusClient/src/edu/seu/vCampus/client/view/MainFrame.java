/**
 * 主界面
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.view;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import edu.seu.vCampus.client.view.util.FadingPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.util.Vector;

public class MainFrame {
    private Vector<JButton> btnModules = new Vector<JButton>();
    private Vector<FadingPanel> pans = new Vector<FadingPanel>();
    private int nCurrModule = -1;
    private JFrame frame;
    private JButton btn_LogOff;
    private JButton btn_ChangePw;
    private JLabel label;
    public UserPwChgView pal_chgpwd;
    private CardLayout cardLayout = new CardLayout();
    private final JPanel jpCard = new JPanel();
    private boolean onSwitch = false;
	private JButton btn_User;
	private FadingPanel jpm;
	private JLabel lbl_Balance;

    /**
     * Create the application.
     */
    public MainFrame() {
        initialize();
    }
    
    /**
     * 设置注销按钮响应代码
     * 
     * @param al
     *          响应代码
     */
    public void setLogoutAction(ActionListener al) {
        btn_LogOff.addActionListener(al);
    }
    
    /**
     * 设置更改密码按钮响应代码
     * 
     * @param al
     *          响应代码
     */
    public void setChangePwAction(ActionListener al) {
        btn_ChangePw.addActionListener(al);
    }
    
    /**
     * 显示窗体
     */
    public void showMe() {
        frame.setVisible(true);
    }
    
    /**
     * 注销整个窗体
     */
    public void disposeMe() {
        frame.dispose();
    }
    
    /**
     * 设置状态提示
     * 
     * @param strTip
     *          提示
     */
    public void setTip(String strTip) {
        label.setText(strTip);
    }
    
    /**
     * 设置余额提示
     * 
     * @param strBalance
     *          余额
     */
    public void setBalance(String strBalance) {
        lbl_Balance.setText(strBalance);
    }
    
    /**
     * 是否显示余额提示
     * 
     * @param isShown
     *          是否显示
     */
    public void setBalanceShown(boolean isShown) {
        lbl_Balance.setVisible(isShown);
    }
    
    /**
     * 添加显示的模块
     */
    public void addModule(final String strName, final FadingPanel panelClass, final String strIconPath, final boolean showBalance) {
    	if (btnModules.size() >= 6)
    		return;
    	final int nImgSize = 160;
		final int nNameHeight = 40;
		final int nNameWidth = 160;
    	int nWSpace = (jpm.getWidth() - nImgSize * 3) / 4;
    	int nHSpace = (jpm.getHeight() - nImgSize * 2) / 3;
    	final int nNameSpace = (nImgSize - nNameWidth) / 2;
    	final int nMyId = btnModules.size();
        final JButton btnAdd = new JButton();
        final JLabel lblName = new JLabel(strName);
        lblName.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        lblName.setOpaque(true);
        lblName.setBackground(new Color(255,255,255,240));
        lblName.setVerticalAlignment(SwingUtilities.TOP);
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusable(false);
        btnAdd.setIcon(new ImageIcon(strIconPath));
        btnAdd.setBounds(nWSpace + nMyId % 3 * (nWSpace + nImgSize), nHSpace + nMyId / 3 * (nHSpace + nImgSize), nImgSize, nImgSize);
        // lblName.setBounds(btnAdd.getX() + nNameSpace, btnAdd.getY() + nImgSize - nNameHeight, nNameWidth, nNameHeight);
        lblName.setBounds(btnAdd.getX() + nNameSpace, btnAdd.getY() + nImgSize, nNameWidth, 0);
        btnAdd.addMouseListener(new MouseAdapter() {
        	private long nIntv = 10, nMiliSec = 200;
            private long start;
            private boolean isAnimating = false;
            private boolean isShown = false;
            private boolean isMouseIn = false;
            private int[] cursor = new int[4];
            private int[] cursor_upper = new int[] {btnAdd.getX() + nNameSpace, btnAdd.getY() + nImgSize - nNameHeight, nNameWidth, nNameHeight};
            private int[] cursor_lower = new int[] {btnAdd.getX() + nNameSpace, btnAdd.getY() + nImgSize, nNameWidth, 0};

			public void mouseEntered(final MouseEvent arg0) {
				isMouseIn = true;
				if (isAnimating || isShown)
					return;
				isAnimating = true;
				cursor = cursor_lower.clone();
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
                            if (cursor[3] <= cursor_upper[3]) {
                            	cursor = cursor_upper.clone();
                            	lblName.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                            }
                            jpm.repaint();
                            if (!isMouseIn)
                            	mouseExited(arg0);
                        } else {
                            cursor[1] = (int) ((cursor_upper[1] - cursor_lower[1]) * elapsed / nMiliSec + cursor_lower[1]);
                            cursor[3] = (int) ((cursor_upper[3] - cursor_lower[3]) * elapsed / nMiliSec + cursor_lower[3]);
                            lblName.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                            jpm.repaint();
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
            	// cursor = cursor_upper.clone();
            	start = System.currentTimeMillis();
            	final Timer t = new Timer((int) nIntv, null);
                t.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	int pxMove = (int) (nNameHeight * nIntv / nMiliSec);
                        long elapsed = System.currentTimeMillis() - start;
                        if (elapsed > nMiliSec) {
                            start = 0;
                            isAnimating = false;
                            isShown = false;
                            t.stop();
                            if (cursor[3] > 0) {
                            	cursor = cursor_lower.clone();
                            	lblName.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                            }
                            jpm.repaint();
                        	if (isMouseIn)
                            	mouseEntered(arg0);
                        	return;
                        }
                        cursor[1] = (int) ((cursor_lower[1] - cursor_upper[1]) * elapsed / nMiliSec + cursor_upper[1]);
                        cursor[3] = (int) ((cursor_lower[3] - cursor_upper[3]) * elapsed / nMiliSec + cursor_upper[3]);
                        lblName.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                        jpm.repaint();
                    }
                });
                t.start();
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (onSwitch)
                    return;
                onSwitch = true;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        jpm.fade(0.3f, true);
                    }
                });
                final Timer t = new Timer(250, null);
                t.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nCurrModule = nMyId;
                        cardLayout.show(jpCard, String.valueOf(nMyId));
                        setBalanceShown(showBalance);
                        t.stop();
                    }
                });
                t.start();
                final Timer t2 = new Timer(200, null);
                t2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                pans.get(nMyId).fade(0.3f, false);
                            }
                        });
                        t2.stop();
                    }
                });
                t2.start();
                final Timer t3 = new Timer(600, null);
                t3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                onSwitch = false;
                            }
                        });
                        t3.stop();
                    }
                });
                t3.start();
            }
        });
        jpm.add(lblName);
        jpm.add(btnAdd);
        pans.add(panelClass);
        jpCard.add(panelClass, String.valueOf(nMyId));
        btnModules.add(btnAdd);
        
        final JButton btn_GoBack = new JButton(new ImageIcon("images/backarrow.png"));
        btn_GoBack.setContentAreaFilled(false);
        btn_GoBack.setBorderPainted(false);
        btn_GoBack.setBounds(10, 5, 24, 24);
        btn_GoBack.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		switchToFirstCard();
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
                            panelClass.repaint();
                            if (!isMouseIn)
                            	mouseExited(arg0);
                        } else {
                            cursor[2] = (int) ((cursor_upper - cursor_lower) * elapsed / nMiliSec);
                            btn_GoBack_tip.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                            panelClass.repaint();
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
                            panelClass.repaint();
                        	if (isMouseIn)
                            	mouseEntered(arg0);
                        	return;
                        }
                        cursor[2] = cursor_upper - (int) ((cursor_upper - cursor_lower) * elapsed / nMiliSec);
                        btn_GoBack_tip.setBounds(cursor[0], cursor[1], cursor[2], cursor[3]);
                        panelClass.repaint();
                    }
                });
                t.start();
            }
        });
        
		panelClass.add(btn_GoBack);
		panelClass.add(btn_GoBack_tip);
		panelClass.setComponentZOrder(btn_GoBack_tip, 0);
    }

    public void switchToFirstCard() {
    	setBalanceShown(false);
    	if (nCurrModule >= 0) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                pans.get(nCurrModule).fade(0.3f, true);
	            }
	        });
    	} else if (nCurrModule == -2) {
    		SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                pal_chgpwd.fade(0.3f, true);
	            }
	        });
    	} else {
    		return;
    	}
	    final Timer t = new Timer(250, null);
	    t.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            nCurrModule = -1;
	            cardLayout.show(jpCard, "mainCard");
	            t.stop();
	        }
	    });
	    t.start();
	    final Timer t2 = new Timer(200, null);
	    t2.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                    jpm.fade(0.3f, false);
	                }
	            });
	            t2.stop();
	        }
	    });
	    t2.start();
	    final Timer t3 = new Timer(600, null);
	    t3.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                    onSwitch = false;
	                }
	            });
	            t3.stop();
	        }
	    });
	    t3.start();
    }
    
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	int offset = 0;
        frame = new JFrame();
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SEU vCampus");
        // frame.setTitle("东南大学虚拟校园系统");
        frame.setIconImage(frame.getGraphicsConfiguration().createCompatibleImage(frame.getWidth(), frame.getHeight(), Transparency.BITMASK));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.setBackground(Color.WHITE);
        setDragable();

        // 控制界面居中
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = frame.getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        frame.setLocation(x, y);

        JPanel pal_Title = new JPanel();
        pal_Title.setBackground(Color.WHITE);
        pal_Title.setBounds(offset, 0, 640, 42);
        frame.getContentPane().add(pal_Title);
        pal_Title.setLayout(null);

        btn_User = new JButton(new ImageIcon("images/user.png"));
        btn_User.setBounds(offset + 10, 10, 24, 24);
        btn_User.setBorderPainted(false);
        btn_User.setContentAreaFilled(false);
        btn_User.setFocusable(false);
        pal_Title.add(btn_User);
        label = new JLabel();
        label.setBounds(offset + 40, 0, 500, 42);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        pal_Title.add(label);

        lbl_Balance = new JLabel();
        lbl_Balance.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lbl_Balance.setBounds(500 + offset, 0, 100, 32);
        pal_Title.add(lbl_Balance);
        
        btn_LogOff = new JButton(new ImageIcon("images/logout.png"));
        btn_LogOff.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent arg0) { // 鼠标移过，改变图片
                ImageIcon icon3 = new ImageIcon("images/logout_hover.png");
                btn_LogOff.setIcon(icon3);
            }

            public void mouseExited(MouseEvent arg0) {
                ImageIcon icon3 = new ImageIcon("images/logout.png");
                btn_LogOff.setIcon(icon3);
            }
        });

        btn_LogOff.setContentAreaFilled(false);
        btn_LogOff.setBorderPainted(false);
        btn_LogOff.setBounds(600 + offset, 0, 32, 32);
        btn_LogOff.setFocusable(false);
        pal_Title.add(btn_LogOff);
        
        btn_ChangePw = new JButton(new ImageIcon("images/lock.png"));
        btn_ChangePw.setContentAreaFilled(false);
        btn_ChangePw.setBorderPainted(false);
        btn_ChangePw.setBounds(570 + offset, 5, 24, 24);
        // pal_Title.add(btn_ChangePw);

        jpCard.setLayout(null);
        jpCard.setBounds(offset, 42, 640, 438);
        jpCard.setBackground(Color.WHITE);
        frame.getContentPane().add(jpCard);

        jpm = new FadingPanel();
        jpm.setBackground(Color.WHITE);
        jpm.setBounds(0, 0, 640, 400);
        jpm.setLayout(null);

        pal_chgpwd = new UserPwChgView(this);
        jpCard.setLayout(cardLayout);
        jpCard.add(jpm, "mainCard");
        jpCard.add(pal_chgpwd, "changePwd");
        
        btn_User.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (nCurrModule == -2)
            		return;
            	if (nCurrModule > 0) {
            		SwingUtilities.invokeLater(new Runnable() {
	    	            @Override
	    	            public void run() {
	    	            	pans.get(nCurrModule).fade(0.3f, true);
	    	            }
	    	        });
            	} else {
	        		SwingUtilities.invokeLater(new Runnable() {
	    	            @Override
	    	            public void run() {
	    	                jpm.fade(0.3f, true);
	    	            }
	    	        });
            	}
        	    final Timer t = new Timer(250, null);
        	    t.addActionListener(new ActionListener() {
        	        @Override
        	        public void actionPerformed(ActionEvent e) {
        	        	nCurrModule = -2;
        	            cardLayout.show(jpCard, "changePwd");
        	            t.stop();
        	        }
        	    });
        	    t.start();
        	    final Timer t2 = new Timer(200, null);
        	    t2.addActionListener(new ActionListener() {
        	        @Override
        	        public void actionPerformed(ActionEvent e) {
        	            SwingUtilities.invokeLater(new Runnable() {
        	                @Override
        	                public void run() {
        	                	pal_chgpwd.fade(0.3f, false);
        	                }
        	            });
        	            t2.stop();
        	        }
        	    });
        	    t2.start();
        	    final Timer t3 = new Timer(600, null);
        	    t3.addActionListener(new ActionListener() {
        	        @Override
        	        public void actionPerformed(ActionEvent e) {
        	            SwingUtilities.invokeLater(new Runnable() {
        	                @Override
        	                public void run() {
        	                    onSwitch = false;
        	                }
        	            });
        	            t3.stop();
        	        }
        	    });
        	    t3.start();
            }
        });
    }
    
    Point loc = null;
    Point tmp = null;
    boolean isDragged = false;
    
    private void setDragable() {
        frame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
               isDragged = false;
               frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mousePressed(java.awt.event.MouseEvent e) {
               tmp = new Point(e.getX(), e.getY());
               isDragged = true;
               frame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        frame.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
               if(isDragged) {
                   loc = new Point(frame.getLocation().x + e.getX() - tmp.x,
                		   frame.getLocation().y + e.getY() - tmp.y);
                   frame.setLocation(loc);
               }
            }
        });
    }
}
