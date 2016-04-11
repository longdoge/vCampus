package edu.seu.vCampus.client.view.stu.Shop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class BuyView {

	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	/**
	 * Create the application.
	 */
	public BuyView() {
		initialize();
		this.frame.setVisible(true);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 300, 342, 266);
		
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("¹ºÂòÊýÁ¿");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		label.setBounds(47, 57, 106, 42);
		frame.getContentPane().add(label);
		
		JButton btn_BuyConfirm = new JButton("È·ÈÏ");
		btn_BuyConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				
			}
		});
		btn_BuyConfirm.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		btn_BuyConfirm.setBounds(27, 145, 120, 34);
		frame.getContentPane().add(btn_BuyConfirm);
		
		JButton btn_BuyCancel = new JButton("È¡Ïû");
		btn_BuyCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btn_BuyCancel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		btn_BuyCancel.setBounds(169, 145, 120, 34);
		frame.getContentPane().add(btn_BuyCancel);
		
		JSpinner spn_BuyAccount = new JSpinner();
		spn_BuyAccount.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 20));
		spn_BuyAccount.setBounds(157, 61, 85, 34);
		frame.getContentPane().add(spn_BuyAccount);
	}
	//public static void main(String[] args) {
		//new BuyView();
	//}
}
