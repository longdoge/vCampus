/**
 * 注册界面
 * @author Xuheng Wang, Yi Yang
 * @version 2.0
 */

package edu.seu.vCampus.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Transparency;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RegisterFrame {
    private JFrame frame;
    private JTextField userIdField;// 一卡通，教职工号
    private JTextField userNameField;// 用户名
    private JPasswordField passwordField;// 输入密码
    private JPasswordField passwordFieldCon;
    private JButton Jb_sign;
    private JButton Jb_cancel;

    /**
     * 类构造函数：初始化窗体并显示
     */
    public RegisterFrame() {
        initialise();
        this.frame.setVisible(true);
    }

    /**
     * 设置注册按钮响应代码
     * 
     * @param al
     *            响应代码
     */
    public void setRegAction(ActionListener al) {
        Jb_sign.addActionListener(al);
    }

    /**
     * 设置撤销按钮响应代码
     * 
     * @param al
     *            响应代码
     */
    public void setCanclAction(ActionListener al) {
        Jb_cancel.addActionListener(al);
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
     * 获取填写的用户ID
     * 
     * @return 用户ID
     */
    public String getUsername() {
        return userNameField.getText();
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
     * 注销整个窗体
     */
    public void disposeMe() {
        frame.dispose();
    }
    /**
     * 获取填写的确认密码
     * 
     * @return 密码
     */
    public String getPasswordcon(){
    	return new String(passwordFieldCon.getPassword());
    }

    /**
     * 初始化窗体内容
     */
    private void initialise() {
    	frame = new JFrame();
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SEU vCampus");
        frame.setIconImage(frame.getGraphicsConfiguration().createCompatibleImage(frame.getWidth(), frame.getHeight(), Transparency.BITMASK));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.getContentPane().setBackground(Color.WHITE);
        // setDragable();

        // 控制界面居中
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = frame.getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        frame.setLocation(x, y);

        JLabel lblNewLabel = new JLabel("\u5B66\u751F\u7528\u6237\u6CE8\u518C");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 32));
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setBounds(180, 30, 280, 40);
        frame.getContentPane().add(lblNewLabel);

        JLabel lbl_UserID = new JLabel("\u5B66       \u53F7\uFF1A");
        lbl_UserID.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_UserID.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lbl_UserID.setForeground(Color.BLACK);
        lbl_UserID.setBounds(180, 97, 100, 40);
        frame.getContentPane().add(lbl_UserID);

        JLabel lbl_User = new JLabel("\u7528  \u6237  \u540D\uFF1A");
        lbl_User.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_User.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lbl_User.setForeground(Color.BLACK);
        lbl_User.setBounds(180, 157, 100, 40);
        frame.getContentPane().add(lbl_User);
        
        JLabel lblPassword = new JLabel("\u5BC6       \u7801\uFF1A");
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPassword.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBounds(180, 217, 100, 40);
        frame.getContentPane().add(lblPassword);

        JLabel lblPassword_2 = new JLabel("确认密码：");
        lblPassword_2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPassword_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblPassword_2.setForeground(Color.BLACK);
        lblPassword_2.setBounds(180, 277, 100, 40);
        frame.getContentPane().add(lblPassword_2);

        // 一卡通
        userIdField = new JTextField("");
        userIdField.setBounds(298, 97, 160, 40);
        frame.getContentPane().add(userIdField);
        userIdField.setColumns(10);
        userIdField.setFont(new Font("微软雅黑", Font.PLAIN, 18));

        // 用户名
        userNameField = new JTextField("");
        userNameField.setBounds(298, 157, 160, 40);
        frame.getContentPane().add(userNameField);
        userNameField.setColumns(10);
        userNameField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        
        // 输入密码
        passwordField = new JPasswordField("");
        passwordField.setBounds(298, 217, 160, 40);
        frame.getContentPane().add(passwordField);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 18));

        // 确认密码
        passwordFieldCon = new JPasswordField("");
        passwordFieldCon.setBounds(298, 277, 160, 40);
        frame.getContentPane().add(passwordFieldCon);
        passwordFieldCon.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        
        Jb_sign = new JButton("注册");
        Jb_sign.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        // Jb_sign.setForeground(Color.gray);
        // Jb_sign.setText("");
        Jb_sign.setBounds(356, 350, 120, 50);
        Jb_sign.setContentAreaFilled(false);
        Jb_sign.setBorderPainted(true);
        Jb_sign.setFocusable(false);
        frame.getContentPane().add(Jb_sign);

        Jb_cancel = new JButton("取消");
        Jb_cancel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        // Jb_cancel.setForeground(Color.gray);
        Jb_cancel.setBounds(181, 350, 120, 50);
        Jb_cancel.setContentAreaFilled(false);
        Jb_cancel.setBorderPainted(true);
        Jb_cancel.setFocusable(false);
        frame.getContentPane().add(Jb_cancel);
        
        KeyListener enterToSubmit;
        
        userIdField.addKeyListener(enterToSubmit = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 输入时按回车，即可提交
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                	Jb_sign.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });
        userNameField.addKeyListener(enterToSubmit);
        passwordField.addKeyListener(enterToSubmit);
        passwordFieldCon.addKeyListener(enterToSubmit);
    }
}