package edu.seu.vCampus.client.view.stu.SchoolRoll;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.CardLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

//import net.Socketwork;
//import bz.StuInfo.StuInfoRealize;

import javax.swing.UIManager;

import edu.seu.vCampus.SharedComponents.vo.vStudent;
import edu.seu.vCampus.client.biz.module.StudentManager.StuInfoModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.MainFrame;
import edu.seu.vCampus.client.view.util.FadingPanel;

public class SchoolRollView extends FadingPanel {
	private static final long serialVersionUID = 1L;
	public CardLayout cardLayout = new CardLayout();
    public JButton btn_Return = new JButton();
    private JTextField txt_Name;
    private JTextField txt_Sex;
    private JTextField txt_Birthday;
    private JTextField txt_Department;
    private JTextField txt_Class;
    private JTextField txt_ID;
    private JTextField txt_NativePlace;
    private JTextField txt_AccountType;
    private JTextField txt_Hobby;
    private JTextField txt_Phone;
    private JTextField txt_Email;
    private JTextField txt_StuID;

    /**
     * Create the application.
     */
    public SchoolRollView() {
        setBackground(Color.WHITE);
        setLayout(null);

        final JPanel CardSchoolRoll = new JPanel();
        CardSchoolRoll.setBackground(Color.WHITE);
        CardSchoolRoll.setBounds(50, 10, 535, 407);
        add(CardSchoolRoll);
        CardSchoolRoll.setLayout(cardLayout);

        JPanel pal_SchoolRollMain = new JPanel();
        pal_SchoolRollMain.setBackground(Color.WHITE);
        // CardSchoolRoll.add(pal_SchoolRollMain, "0");

        // ∞¥œ¬∏ˆ»À—ßºÆ–≈œ¢ ±ΩÁ√Ê
        final JPanel pal_SelfSchoolRoll = new JPanel();
        pal_SelfSchoolRoll.setBackground(Color.WHITE);
        CardSchoolRoll.add(pal_SelfSchoolRoll, "1");
        pal_SelfSchoolRoll.setLayout(null);

        JLabel lbl_Name = new JLabel("–’√˚");
        lbl_Name.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Name.setBounds(59, 66, 87, 42);
        pal_SelfSchoolRoll.add(lbl_Name);
        
        JLabel lbl_StuID = new JLabel("—ß∫≈");
        lbl_StuID.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_StuID.setBounds(264, 66, 87, 42);
        lbl_StuID.setHorizontalAlignment(SwingConstants.RIGHT);
        pal_SelfSchoolRoll.add(lbl_StuID);

        JLabel lbl_Sex = new JLabel("–‘±");
        lbl_Sex.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Sex.setBounds(264, 140, 87, 42);
        lbl_Sex.setHorizontalAlignment(SwingConstants.RIGHT);
        pal_SelfSchoolRoll.add(lbl_Sex);

        JLabel lbl_Birthday = new JLabel("…˙»’");
        lbl_Birthday.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Birthday.setBounds(264, 213, 87, 42);
        lbl_Birthday.setHorizontalAlignment(SwingConstants.RIGHT);
        pal_SelfSchoolRoll.add(lbl_Birthday);

        txt_Name = new JTextField();
        txt_Name.setEditable(false);
        txt_Name.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_Name.setColumns(10);
        txt_Name.setBounds(167, 67, 87, 40);
        pal_SelfSchoolRoll.add(txt_Name);
        
        txt_StuID = new JTextField();
        txt_StuID.setEditable(false);
        txt_StuID.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
        txt_StuID.setColumns(10);
        txt_StuID.setBounds(372, 67, 87, 40);
        pal_SelfSchoolRoll.add(txt_StuID);

        txt_Sex = new JTextField();
        txt_Sex.setEditable(false);
        txt_Sex.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_Sex.setColumns(10);
        txt_Sex.setBounds(372, 141, 87, 40);
        pal_SelfSchoolRoll.add(txt_Sex);

        txt_Birthday = new JTextField();
        txt_Birthday.setEditable(false);
        txt_Birthday.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
        txt_Birthday.setColumns(10);
        txt_Birthday.setBounds(372, 214, 87, 40);
        pal_SelfSchoolRoll.add(txt_Birthday);

        txt_Department = new JTextField();
        txt_Department.setEditable(false);
        txt_Department.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_Department.setColumns(10);
        txt_Department.setBounds(167, 297, 87, 40);
        pal_SelfSchoolRoll.add(txt_Department);

        JLabel lbl_Department = new JLabel("‘∫œµ");
        lbl_Department.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Department.setBounds(59, 295, 87, 42);
        pal_SelfSchoolRoll.add(lbl_Department);

        txt_Class = new JTextField();
        txt_Class.setEditable(false);
        txt_Class.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_Class.setColumns(10);
        txt_Class.setBounds(372, 297, 87, 40);
        pal_SelfSchoolRoll.add(txt_Class);

        JLabel lbl_Class = new JLabel("∞‡º∂");
        lbl_Class.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Class.setBounds(264, 295, 87, 42);
        lbl_Class.setHorizontalAlignment(SwingConstants.RIGHT);
        pal_SelfSchoolRoll.add(lbl_Class);

        JLabel lbl_ID = new JLabel("“ªø®Õ®∫≈");
        lbl_ID.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_ID.setBounds(59, 140, 87, 42);
        pal_SelfSchoolRoll.add(lbl_ID);

        txt_ID = new JTextField();
        txt_ID.setEditable(false);
        txt_ID.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
        txt_ID.setColumns(10);
        txt_ID.setBounds(167, 141, 87, 40);
        pal_SelfSchoolRoll.add(txt_ID);

        txt_NativePlace = new JTextField();
        txt_NativePlace.setEditable(false);
        txt_NativePlace.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_NativePlace.setColumns(10);
        txt_NativePlace.setBounds(167, 214, 87, 40);
        pal_SelfSchoolRoll.add(txt_NativePlace);

        JLabel lbl_NativePlace = new JLabel("ºÆπ·");
        lbl_NativePlace.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_NativePlace.setBounds(59, 213, 87, 42);
        pal_SelfSchoolRoll.add(lbl_NativePlace);

        // ∞¥œ¬∏ˆ»À–≈œ¢ ±µƒΩÁ√Êœ‘ æ
        JPanel pal_SelfInfo = new JPanel();
        pal_SelfInfo.setBackground(Color.WHITE);
        CardSchoolRoll.add(pal_SelfInfo, "2");
        pal_SelfInfo.setLayout(null);

        JLabel lbl_AccountType = new JLabel("’Àªß¿‡–Õ");
        lbl_AccountType.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_AccountType.setBounds(142, 38, 96, 49);
        pal_SelfInfo.add(lbl_AccountType);

        txt_AccountType = new JTextField();
        txt_AccountType.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_AccountType.setBounds(271, 44, 158, 41);
        pal_SelfInfo.add(txt_AccountType);
        txt_AccountType.setColumns(10);

        JLabel lbl_Hobby = new JLabel("–À»§∞Æ∫√");
        lbl_Hobby.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Hobby.setBounds(142, 107, 96, 49);
        pal_SelfInfo.add(lbl_Hobby);

        JLabel lbl_Phone = new JLabel("¡™œµµÁª∞");
        lbl_Phone.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Phone.setBounds(142, 178, 96, 49);
        pal_SelfInfo.add(lbl_Phone);

        JLabel lbl_Email = new JLabel("” œ‰µÿ÷∑");
        lbl_Email.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        lbl_Email.setBounds(142, 250, 96, 49);
        pal_SelfInfo.add(lbl_Email);

        txt_Hobby = new JTextField();
        txt_Hobby.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_Hobby.setBounds(271, 113, 158, 41);
        pal_SelfInfo.add(txt_Hobby);
        txt_Hobby.setColumns(10);

        txt_Phone = new JTextField();
        txt_Phone.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_Phone.setBounds(271, 184, 158, 41);
        pal_SelfInfo.add(txt_Phone);
        txt_Phone.setColumns(10);

        txt_Email = new JTextField();
        txt_Email.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        txt_Email.setBounds(271, 256, 158, 41);
        pal_SelfInfo.add(txt_Email);
        txt_Email.setColumns(10);

        // À˘”–∞¥≈•
        final JButton btn_ModifyInfo = new JButton("–ﬁ∏ƒ–≈œ¢");
        btn_ModifyInfo.setBackground(Color.WHITE);

        btn_ModifyInfo.setContentAreaFilled(false);
        btn_ModifyInfo.setBorderPainted(true);

        btn_ModifyInfo.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
        btn_ModifyInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ModifySelfInfo();
            }
        });
        btn_ModifyInfo.setBounds(204, 325, 168, 41);
        pal_SelfInfo.add(btn_ModifyInfo);

        JPanel pal_btn = new JPanel();
        pal_btn.setBackground(Color.WHITE);
        pal_btn.setBounds(0, 31, 106, 407);
        add(pal_btn);
        pal_btn.setLayout(null);

        // À˘”–∞¥≈•
        /*final JButton btn_SelfSchoolRoll = new JButton("—ßºÆ–≈œ¢");
        btn_SelfSchoolRoll.setBackground(Color.WHITE);
        btn_SelfSchoolRoll.setContentAreaFilled(false);
        btn_SelfSchoolRoll.setBorderPainted(true);
        btn_SelfSchoolRoll.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
        btn_SelfSchoolRoll.setContentAreaFilled(false);

        ////////

        btn_SelfSchoolRoll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {// ∏ˆ»À—ßºÆ–≈œ¢µƒ’π æ œÏ”¶
                cardLayout.show(CardSchoolRoll, "1");
            }
        });
        btn_SelfSchoolRoll.setBounds(5, 50, 95, 33);
        pal_btn.add(btn_SelfSchoolRoll);*/

        /*final JButton btn_SelfInfo = new JButton("–ﬁ∏ƒ—ßºÆ");

        btn_SelfInfo.setContentAreaFilled(false);
        btn_SelfInfo.setBorderPainted(true);

        btn_SelfInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(CardSchoolRoll, "2");
                String[] selfInfo = new String[7];
                // selfInfo = is.AccountInfo(Socketwork.getStuId());
                txt_AccountType.setText(selfInfo[0]);
                txt_Hobby.setText(selfInfo[1]);
                txt_Phone.setText(selfInfo[2]);
                txt_Email.setText(selfInfo[3]);

            }
        });

        btn_SelfInfo.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
        btn_SelfInfo.setBackground(Color.WHITE);
        btn_SelfInfo.setBounds(5, 163, 95, 33);
        pal_btn.add(btn_SelfInfo);*/
        
        showstuinfo();
    }
    
    public void showstuinfo(){
    	StuInfoModel info = new StuInfoModel();
    	vStudent student = new vStudent();
    	vStudent stu = new vStudent();
    	int uId = UserLoginInfoManager.getnUserID();
    	student.setuId(uId);
    	Vector<vStudent> stuvector = info.scanstuinfowithuid(student);
    	stu = stuvector.get(0);
    	txt_Name.setText(stu.getName());
    	if(stu.getSex() == 1)
    		txt_Sex.setText("ƒ–");
    	else
    		txt_Sex.setText("≈Æ");
    	txt_Birthday.setText(stu.getBirthday());
    	txt_Department.setText(stu.getScollege());
    	txt_Class.setText(stu.getSclass());
    	txt_ID.setText(stu.getCardnum());
    	txt_NativePlace.setText(stu.nation);
    	txt_StuID.setText(stu.getNum());
    }
}
