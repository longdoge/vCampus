package edu.seu.vCampus.client.view.stu.Shop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

public class addCountDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextField tf_count;
	String count = "1";
	int num = 1;
	boolean clickFlag = false;

	/**
	 * Create the dialog.
	 
	 */
	
	/**
	*向Shop界面传递count变量
	*/
	public String getCount(){
		count = tf_count.getText();
		return count;		
	}
	/**
	*关闭Dialog
	*/
	public void shut(){
		this.dispose();
	}
	public void setClickFlag(){
		clickFlag = true;
	}
	public boolean getClickFlag(){
		return clickFlag;
	}
	public addCountDialog() {
		this.setSize(236, 205);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();     
        Dimension framesize = this.getSize();
        int x = (int)screensize.getWidth()/2 - (int)framesize.getWidth()/2;   
        int y = (int)screensize.getHeight()/2 - (int)framesize.getHeight()/2;   
        this.setLocation(x,y); 
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btn_OK = new JButton("-");
			btn_OK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(num > 1){
						num = num - 1;
						count = Integer.toString(num);
						tf_count.setText(count);
					}
					else{
						JOptionPane.showMessageDialog(null,"请至少选择1件商品！","数量有误",JOptionPane.ERROR_MESSAGE);					}
				}
			});
			btn_OK.setBounds(37, 58, 46, 30);
			contentPanel.add(btn_OK);
		}
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num = num + 1;
				count = Integer.toString(num);
				tf_count.setText(count);
			}
		});
		button.setBounds(134, 58, 46, 30);
		contentPanel.add(button);
		
		tf_count = new JTextField(count);
		tf_count.setEditable(false);
		tf_count.setHorizontalAlignment(SwingConstants.CENTER);
		
		tf_count.setBounds(85, 63, 46, 21);
		contentPanel.add(tf_count);
		tf_count.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btn_OK = new JButton("确认");
				btn_OK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setClickFlag();
						
						//shut();
					}
				});
				buttonPane.add(btn_OK);
				getRootPane().setDefaultButton(btn_OK);
			}
			{
				JButton btn_cancel = new JButton("取消");
				btn_cancel.setActionCommand("Cancel");
				buttonPane.add(btn_cancel);
			}
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true); 
	}
}

