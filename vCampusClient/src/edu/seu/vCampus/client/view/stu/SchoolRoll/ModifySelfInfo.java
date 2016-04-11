package edu.seu.vCampus.client.view.stu.SchoolRoll;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.Color;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ModifySelfInfo {

	private JFrame frame;
	private JTextField txt_ModifyHobby;
	private JTextField txt_ModifyPhone;
	private JTextField txt_ModifyEmail;


	/**
	 * Create the application.
	 */
	public ModifySelfInfo() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 640, 480);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_ModifyHobby = new JLabel("ÐËÈ¤°®ºÃ");
		lbl_ModifyHobby.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		lbl_ModifyHobby.setBounds(146, 57, 96, 49);
		frame.getContentPane().add(lbl_ModifyHobby);
		
		JLabel lbl_ModfyPhone = new JLabel("ÁªÏµµç»°");
		lbl_ModfyPhone.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		lbl_ModfyPhone.setBounds(146, 139, 96, 49);
		frame.getContentPane().add(lbl_ModfyPhone);
		
		JLabel lbl_ModifyEmail = new JLabel("ÓÊÏäµØÖ·");
		lbl_ModifyEmail.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		lbl_ModifyEmail.setBounds(146, 219, 96, 49);
		frame.getContentPane().add(lbl_ModifyEmail);
		
		txt_ModifyHobby = new JTextField();
		txt_ModifyHobby.setBounds(290, 58, 158, 41);
		frame.getContentPane().add(txt_ModifyHobby);
		txt_ModifyHobby.setColumns(10);
		
		txt_ModifyPhone = new JTextField();
		txt_ModifyPhone.setBounds(290, 145, 158, 41);
		frame.getContentPane().add(txt_ModifyPhone);
		txt_ModifyPhone.setColumns(10);
		
		txt_ModifyEmail = new JTextField();
		txt_ModifyEmail.setBounds(290, 225, 158, 41);
		frame.getContentPane().add(txt_ModifyEmail);
		txt_ModifyEmail.setColumns(10);
		
		final JButton btn_ModifyConfirm = new JButton("È·ÈÏÐÞ¸Ä");
		btn_ModifyConfirm.setBackground(Color.WHITE);
		
		btn_ModifyConfirm.setContentAreaFilled(false);
		btn_ModifyConfirm.setBorderPainted(true);
	
		
		btn_ModifyConfirm.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		btn_ModifyConfirm.setBounds(123, 319, 168, 41);
		frame.getContentPane().add(btn_ModifyConfirm);
		
		final JButton btn_ModifyCancel = new JButton("È¡ÏûÐÞ¸Ä");
		btn_ModifyCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		
		btn_ModifyCancel.setContentAreaFilled(false);
		btn_ModifyCancel.setBorderPainted(true);
		
		btn_ModifyCancel.setBackground(Color.WHITE);
		btn_ModifyCancel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		btn_ModifyCancel.setBounds(340, 319, 168, 41);
		frame.getContentPane().add(btn_ModifyCancel);
	}
}
