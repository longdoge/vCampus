/**
 * 登录界面
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Transparency;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginFrame {
    private JFrame frame;
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton Jb_login;
    private JButton Jb_reg;
    String type = null;
	private JLabel[] lblDots;
	private long[] starts;
	private long start;
	private int nIntv;
	private int nMiliSec;
	private Timer t;

    /**
     * 类构造函数：初始化窗体并显示
     */
    public LoginFrame() {
        initialize();
        this.frame.setVisible(true);
    }

    /**
     * 注销整个窗体
     */
    public void disposeMe() {
        frame.dispose();
    }

    /**
     * 设置登录按钮响应代码
     * 
     * @param al
     *            响应代码
     */
    public void setLoginAction(ActionListener al) {
        Jb_login.addActionListener(al);
    }

    /**
     * 设置注册按钮响应代码
     * 
     * @param al
     *            响应代码
     */
    public void setRegisterAction(ActionListener al) {
        Jb_reg.addActionListener(al);
    }

    /**
     * 获取填写的用户ID
     * 
     * @return 用户ID
     */
    public String getUserId() {
        return userIdField.getText();
    }

    /**
     * 获取填写的密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    /**
     * 是否启用全部按钮、文本框
     * 
     */
    public void setAllEnabled(boolean isEnabled) {
        userIdField.setEnabled(isEnabled);
        passwordField.setEnabled(isEnabled);
        Jb_login.setEnabled(isEnabled);
        Jb_reg.setEnabled(isEnabled);
        setAnimationEnabled(!isEnabled);
    }
    
    /**
     * 是否启用动画
     * 
     */
    public void setAnimationEnabled(boolean isEnabled) {
        if (isEnabled && !t.isRunning()) {
        	start = System.currentTimeMillis();
            for (int i = 0; i < 5; ++i) {
            	starts[i] = start - i * 100;
            }
        	t.start();
        }
        if (!isEnabled && t.isRunning()) {
        	t.stop();
        }
    }

    /**
     * 初始化窗体内容
     */
    private void initialize() {
        /*frame = new JFrame();
        frame.setResizable(false);
        // frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE); // 采用指定的窗口装饰风格
        frame.setSize(510, 300);
        frame.setTitle("登录");
        // frame.setShape(new RoundRectangle2D.Double(0, 0, 510, 300, 30, 30));

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = frame.getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setContentPane(new JLabel(new ImageIcon("images/login_background.jpg"))); // 设置背景
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().setLayout(null);*/
    	
    	frame = new JFrame();
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SEU vCampus");
        // frame.setTitle("东南大学虚拟校园系统");
        frame.setIconImage(frame.getGraphicsConfiguration().createCompatibleImage(frame.getWidth(), frame.getHeight(), Transparency.BITMASK));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.getContentPane().setBackground(Color.WHITE);
        setDragable();

        // 控制界面居中
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = frame.getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        frame.setLocation(x, y);

        JLabel lblNewLabel = new JLabel("东南大学虚拟校园系统");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 32));
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setBounds(110, 60, 420, 60);
        frame.getContentPane().add(lblNewLabel);

        JLabel lbl_User = new JLabel("用户名：");
        lbl_User.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lbl_User.setForeground(Color.BLACK);
        lbl_User.setBounds(178, 175, 90, 30);
        lbl_User.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(lbl_User);

        JLabel lblPassword = new JLabel("密    码：");
        lblPassword.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBounds(178, 236, 90, 30);
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(lblPassword);

        userIdField = new JTextField("");
        userIdField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userIdField.setBounds(274, 170, 160, 40);
        frame.getContentPane().add(userIdField);
        userIdField.setColumns(10);
        userIdField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 输入时按回车，即可提交
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Jb_login.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        passwordField = new JPasswordField("");
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        passwordField.setBounds(274, 231, 160, 40);
        frame.getContentPane().add(passwordField);
        
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 输入时按回车，即可提交
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Jb_login.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        Jb_login = new JButton("\u767B\u5F55");
        Jb_login.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        Jb_login.setBounds(358, 323, 120, 50);
        Jb_login.setContentAreaFilled(false);
        Jb_login.setBorderPainted(true);
        Jb_login.setFocusable(false);
        frame.getContentPane().add(Jb_login);

        Jb_reg = new JButton("注册");
        Jb_reg.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        Jb_reg.setBounds(178, 323, 120, 50);
        Jb_reg.setContentAreaFilled(false);
        Jb_reg.setBorderPainted(true);
        Jb_reg.setFocusable(false);
        frame.getContentPane().add(Jb_reg);
        
        lblDots = new JLabel[5];
        for (int i = 0; i < 5; ++i) {
        	lblDots[i] = dotFactory();
        	frame.add(lblDots[i]);
        }
        starts = new long[5];
        nIntv = 10;
    	nMiliSec = 3000;
		t = new Timer((int) nIntv , null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 5; ++i) {
					long elapsed = System.currentTimeMillis() - starts[i];
					if (elapsed >= nMiliSec) {
						starts[i] = System.currentTimeMillis();
						// t.stop();
					}
					int w = (int) Math.round(hybrid((double)(elapsed - nMiliSec / 2) / (double)(nMiliSec / 2)) * 350);
					if (Math.abs(w) > 230)
						lblDots[i].setVisible(false);
					else
						lblDots[i].setVisible(true);
					w += 320;
					lblDots[i].setBounds(w + (i - 2) * 10, 400, 20, 20);
				}
				frame.repaint();
            }
        });
        // t.start();
    }
    
	private double hybrid(double n) {
		if (Math.abs(n) <= 0.4)
			return n * 0.16;
		return n * n * n;
	}

	private JLabel dotFactory() {
		JLabel lblDot = new JLabel("\u25CF");
        lblDot.setHorizontalAlignment(SwingConstants.CENTER);
        lblDot.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        return lblDot;
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