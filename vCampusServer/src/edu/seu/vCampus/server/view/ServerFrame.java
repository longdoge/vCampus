package edu.seu.vCampus.server.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import edu.seu.vCampus.server.biz.thread.MainThread;

public class ServerFrame {

    private JFrame frame;
    JButton btn_Startup;
    JButton btn_Shutdown;
    private MainThread mth;
    JLabel lbl_State;

    /**
     * Create the application.
     */
    public ServerFrame() {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setSize(247, 287);
        frame.setTitle("服务器管理 - 东南大学虚拟校园系统");
        frame.setResizable(false);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = frame.getSize();
        int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        btn_Shutdown = new JButton("\u5173\u95ED\u670D\u52A1\u5668");
        btn_Shutdown.setEnabled(false);
        btn_Startup = new JButton("\u5F00\u542F\u670D\u52A1\u5668");
        btn_Startup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mth = new MainThread();
                btn_Startup.setEnabled(false);
                btn_Shutdown.setEnabled(true);
                lbl_State.setText("服务器状态：开启");
            }
        });
        btn_Startup.setBounds(62, 88, 103, 23);
        frame.getContentPane().add(btn_Startup);

        btn_Shutdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mth.close();
                btn_Startup.setEnabled(true);
                btn_Shutdown.setEnabled(false);
                lbl_State.setText("服务器状态：关闭");
            }
        });
        btn_Shutdown.setBounds(62, 133, 103, 23);
        frame.getContentPane().add(btn_Shutdown);

        lbl_State = new JLabel("\u670D\u52A1\u5668\u72B6\u6001\uFF1A\u5173\u95ED");
        lbl_State.setBounds(62, 46, 115, 15);
        frame.getContentPane().add(lbl_State);
    }
}
