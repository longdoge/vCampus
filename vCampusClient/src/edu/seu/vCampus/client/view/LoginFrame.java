/**
 * ��¼����
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
     * �๹�캯������ʼ�����岢��ʾ
     */
    public LoginFrame() {
        initialize();
        this.frame.setVisible(true);
    }

    /**
     * ע����������
     */
    public void disposeMe() {
        frame.dispose();
    }

    /**
     * ���õ�¼��ť��Ӧ����
     * 
     * @param al
     *            ��Ӧ����
     */
    public void setLoginAction(ActionListener al) {
        Jb_login.addActionListener(al);
    }

    /**
     * ����ע�ᰴť��Ӧ����
     * 
     * @param al
     *            ��Ӧ����
     */
    public void setRegisterAction(ActionListener al) {
        Jb_reg.addActionListener(al);
    }

    /**
     * ��ȡ��д���û�ID
     * 
     * @return �û�ID
     */
    public String getUserId() {
        return userIdField.getText();
    }

    /**
     * ��ȡ��д������
     * 
     * @return ����
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    /**
     * �Ƿ�����ȫ����ť���ı���
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
     * �Ƿ����ö���
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
     * ��ʼ����������
     */
    private void initialize() {
        /*frame = new JFrame();
        frame.setResizable(false);
        // frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE); // ����ָ���Ĵ���װ�η��
        frame.setSize(510, 300);
        frame.setTitle("��¼");
        // frame.setShape(new RoundRectangle2D.Double(0, 0, 510, 300, 30, 30));

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = frame.getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setContentPane(new JLabel(new ImageIcon("images/login_background.jpg"))); // ���ñ���
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().setLayout(null);*/
    	
    	frame = new JFrame();
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SEU vCampus");
        // frame.setTitle("���ϴ�ѧ����У԰ϵͳ");
        frame.setIconImage(frame.getGraphicsConfiguration().createCompatibleImage(frame.getWidth(), frame.getHeight(), Transparency.BITMASK));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.getContentPane().setBackground(Color.WHITE);
        setDragable();

        // ���ƽ������
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = frame.getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        frame.setLocation(x, y);

        JLabel lblNewLabel = new JLabel("���ϴ�ѧ����У԰ϵͳ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 32));
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setBounds(110, 60, 420, 60);
        frame.getContentPane().add(lblNewLabel);

        JLabel lbl_User = new JLabel("�û�����");
        lbl_User.setFont(new Font("΢���ź�", Font.PLAIN, 18));
        lbl_User.setForeground(Color.BLACK);
        lbl_User.setBounds(178, 175, 90, 30);
        lbl_User.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(lbl_User);

        JLabel lblPassword = new JLabel("��    �룺");
        lblPassword.setFont(new Font("΢���ź�", Font.PLAIN, 18));
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBounds(178, 236, 90, 30);
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.getContentPane().add(lblPassword);

        userIdField = new JTextField("");
        userIdField.setFont(new Font("΢���ź�", Font.PLAIN, 18));
        userIdField.setBounds(274, 170, 160, 40);
        frame.getContentPane().add(userIdField);
        userIdField.setColumns(10);
        userIdField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // ����ʱ���س��������ύ
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
        passwordField.setFont(new Font("΢���ź�", Font.PLAIN, 18));
        passwordField.setBounds(274, 231, 160, 40);
        frame.getContentPane().add(passwordField);
        
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // ����ʱ���س��������ύ
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
        Jb_login.setFont(new Font("΢���ź�", Font.PLAIN, 18));
        Jb_login.setBounds(358, 323, 120, 50);
        Jb_login.setContentAreaFilled(false);
        Jb_login.setBorderPainted(true);
        Jb_login.setFocusable(false);
        frame.getContentPane().add(Jb_login);

        Jb_reg = new JButton("ע��");
        Jb_reg.setFont(new Font("΢���ź�", Font.PLAIN, 18));
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
        lblDot.setFont(new Font("΢���ź�", Font.PLAIN, 18));
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