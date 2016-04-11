/**
 * �޸���Ʒ���ڣ��ڵ���̵�������ġ��޸���Ʒ������ʾ
 */
package edu.seu.vCampus.client.view.admin.Shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.client.view.admin.Shop.Goods;

public class ShopModDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean OK = false; // ��Ա����������Ƿ�ȷ���޸���Ʒ

	public boolean isOK() {
		return OK;
	}

	public void setOK(boolean oK) {
		OK = oK;
	}

	JLabel jl1, jl2, jl3, jl4, jl5, jl6;
	JButton jb1, jb2;
	JTextField jtf1, jtf2, jtf3, jtf4, jtf6;
	JPanel jp1, jp2, jp3;

	vGood mGoods = null;
	private JComboBox jtf5;

	public ShopModDialog(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);

		jl1 = new JLabel("��ƷId", JLabel.CENTER); // goodsId
		jl2 = new JLabel("��Ʒ����", JLabel.CENTER); // goodsName
		jl3 = new JLabel("��Ʒ���", JLabel.CENTER); // goodsCount
		jl4 = new JLabel("��Ʒ�۸�", JLabel.CENTER); // goodsPrice
		jl5 = new JLabel("��Ʒ����", JLabel.CENTER); // goodsType
		jl6 = new JLabel("��Ʒ���:", JLabel.CENTER);
		jl6.setFont(new Font("΢���ź�", Font.PLAIN, 14));

		mGoods = AdminShopView.currGood;
		if (mGoods == null)
			return;
		jtf1 = new JTextField(String.valueOf(mGoods.getgId()));
		jtf2 = new JTextField(mGoods.getgName());
		jtf3 = new JTextField(String.valueOf(mGoods.getgStock()));
		jtf4 = new JTextField(String.valueOf(mGoods.getgPrice()));
		jtf5 = new JComboBox();
		jtf5.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		jtf5.setBackground(Color.WHITE);
		jtf5.setModel(new DefaultComboBoxModel(new String[] { "������Ʒ", "����", "����", "������Ʒ", "ʳƷ" }));
		jtf5.setSelectedIndex(mGoods.gType);
		jtf6 = new JTextField(mGoods.gIntro);

		jtf1.setEditable(false); // �޸���Ʒ��Ϣʱ��ID���ɸ���
		jtf2.setEditable(false); // �޸���Ʒ��Ϣʱ�����Ʋ��ɸ���
		jb1 = new JButton("ȷ��");
		jb1.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		jb1.setContentAreaFilled(false);
		jb1.setBorderPainted(true);
		jb1.addActionListener(this);

		jb2 = new JButton("ȡ��");
		jb2.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		jb2.setContentAreaFilled(false);
		jb2.setBorderPainted(true);
		jb2.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);
		jp3.setBackground(Color.WHITE);

		jp1.setLayout(new GridLayout(6, 1, 4, 4));
		jp2.setLayout(new GridLayout(6, 1, 4, 4));

		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);

		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		jp2.add(jtf6);

		jp3.add(jb1);
		jp3.add(jb2);

		this.add(jp1, BorderLayout.WEST);
		this.add(jp2, BorderLayout.CENTER);
		this.add(jp3, BorderLayout.SOUTH);

		this.setSize(300, 300);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize = getSize();
		int x = (int) screensize.getWidth() / 2 - (int) framesize.getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - (int) framesize.getHeight() / 2;
		setLocation(x, y);
		this.setVisible(true);
	}

	/**
	 * ��ȡ�����Ի������Ϣ�������з�װ
	 * 
	 * @return ��װ����Ϣ
	 */
	public vGood getInpGoods() {
		vGood mGoods = new vGood();

		try {
			// mGoods.setgId( Integer.valueOf(jtf1.getText().trim()) );
			// mGoods.setgName( jtf2.getText().trim() );
			mGoods.setgStock(Integer.valueOf(jtf3.getText().trim()));
			mGoods.setgPrice(Float.valueOf(jtf4.getText().trim()));
			mGoods.setgType(jtf5.getSelectedIndex());
			mGoods.setgIntro(jtf6.getText());
		} catch (Exception e) {
			return null;
		}

		return mGoods;
	}

	/**
	 * ���ߺ������ж���Ϣ¼���Ƿ�Ϻ��淶
	 * 
	 * @return �ϸ񷵻�true�����򷵻�false
	 */
	private boolean isQualified() {
		vGood mGoods = new vGood();

		mGoods = getInpGoods();

		if (mGoods == null || (String.valueOf(mGoods.getgStock()).equals("")) || (String.valueOf(mGoods.getgType()).equals(""))
				|| (String.valueOf(mGoods.getgPrice()).equals(""))) {
			JOptionPane.showMessageDialog(null, "��������");
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == jb1) { // ���ȷ��֮�����Ӧ
			if (isQualified()) {
				this.setOK(true);
				this.dispose();
			}
		} else if (arg0.getSource() == jb2) { // ���ȡ��֮�����Ӧ
			this.setOK(false);
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "YOU WOULD NEVER SEE THIS!");
		}

	}

}
