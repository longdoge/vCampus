package edu.seu.vCampus.client.view.admin.Teacher;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.SharedComponents.vo.vStudent;
import edu.seu.vCampus.client.biz.module.StudentManager.GradeModel;
import edu.seu.vCampus.client.biz.module.StudentManager.StuInfoModel;
import edu.seu.vCampus.client.view.util.DateChooser;
import edu.seu.vCampus.client.view.util.FadingPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class AdminSchoolRoleView extends FadingPanel {
    private static final long serialVersionUID = 1L;
    private CardLayout cl_CardAdminSchoolRoll = new CardLayout();
    public JButton btn_Return = new JButton("");
    private JTable tblStuInfo;
    private JTextField txt_aNameID;
    private JTextField txt_aName;
    private JTextField txt_aID;
    private JTextField txt_aSex;
    private JTextField txt_aNativePlace;
    private JTextField txt_aBirthday;
    private JTextField txt_aDepartment;
    private JTextField txt_aClass;
    private JComboBox cbo_StudentSex;
    private final static String sex[] = {"Ů", "��"};

    private JTextField txt_aStudentID;
    private JTextField txt_aOriginalPwd;
    private Vector<vStudent> stuInfo;
    private JPopupMenu popupMenu;
    private JMenuItem tableNameItem;
    private JPanel CardAdminSchoolRoll;

    /**
     * Create the application.
     */
    public AdminSchoolRoleView() {

        setBackground(Color.WHITE);
        setLayout(null);

        CardAdminSchoolRoll = new JPanel();
        CardAdminSchoolRoll.setBackground(Color.WHITE);
        CardAdminSchoolRoll.setBounds(105, 0, 535, 407);
        add(CardAdminSchoolRoll);
        CardAdminSchoolRoll.setLayout(cl_CardAdminSchoolRoll);

        final JPanel pal_AllSchoolRoll = new JPanel();
        pal_AllSchoolRoll.setBackground(Color.WHITE);
        CardAdminSchoolRoll.add(pal_AllSchoolRoll, "1");
        pal_AllSchoolRoll.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 515, 387);
        pal_AllSchoolRoll.add(scrollPane);

        tblStuInfo = new JTable();
        scrollPane.setViewportView(tblStuInfo);
        
        popupMenu = new JPopupMenu();

        tableNameItem = new JMenuItem("ɾ��ѧ��");
        tableNameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "ɾ����ѧ�������ѧ����������˻����ᱻɾ����\n\n�Ƿ������", "��ȷ��", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    delStuInfo();
                }
            }
        });
        tableNameItem.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        tblStuInfo.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = e.getY() / tblStuInfo.getRowHeight();
                    tblStuInfo.clearSelection();
                    tblStuInfo.addRowSelectionInterval(row, row);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        popupMenu.add(tableNameItem);

        JPanel pal_Register = new JPanel();
        pal_Register.setBackground(Color.white);
        CardAdminSchoolRoll.add(pal_Register, "2");
        pal_Register.setLayout(null);

        final JButton btn_aAddConfirm = new JButton("ȷ��");
        btn_aAddConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                confirmAddStuInfo();
            }
        });
        btn_aAddConfirm.setContentAreaFilled(false);
        btn_aAddConfirm.setBorderPainted(true);
        btn_aAddConfirm.setBackground(Color.WHITE);
        btn_aAddConfirm.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        btn_aAddConfirm.setBounds(160, 333, 78, 27);
        pal_Register.add(btn_aAddConfirm);

        final JButton btn_Cancel = new JButton("ȡ��");
        btn_Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                txt_aNameID.setText("");
                txt_aBirthday.setText("");
                txt_aName.setText("");
                txt_aNativePlace.setText("");
                txt_aID.setText("");
                txt_aDepartment.setText("");
                txt_aClass.setText("");
            }
        });
        btn_Cancel.setContentAreaFilled(false);
        btn_Cancel.setBorderPainted(true);
        btn_Cancel.setBackground(Color.WHITE);
        btn_Cancel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        btn_Cancel.setBounds(380, 333, 78, 27);
        pal_Register.add(btn_Cancel);

        // ����ѧ������
        JLabel lbl_aPhoto = new JLabel("һ��ͨ:");
        lbl_aPhoto.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aPhoto.setBounds(65, 50, 74, 42);
        pal_Register.add(lbl_aPhoto);

        txt_aNameID = new JTextField();
        txt_aNameID.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aNameID.setColumns(10);
        txt_aNameID.setBounds(120, 60, 140, 27);
        pal_Register.add(txt_aNameID);

        // ����
        JLabel lbl_aName = new JLabel("������");
        lbl_aName.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aName.setBounds(284, 50, 74, 40);
        pal_Register.add(lbl_aName);

        txt_aName = new JTextField();
        txt_aName.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aName.setColumns(10);
        txt_aName.setBounds(350, 60, 140, 27);
        pal_Register.add(txt_aName);

        // ѧ��
        JLabel lbl_aID = new JLabel("ѧ�ţ�");
        lbl_aID.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aID.setBounds(65, 123, 74, 42);
        pal_Register.add(lbl_aID);

        txt_aID = new JTextField();
        txt_aID.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aID.setColumns(10);
        txt_aID.setBounds(120, 130, 140, 27);
        pal_Register.add(txt_aID);

        // �Ա�
        JLabel lbl_aSex = new JLabel("�Ա�");
        lbl_aSex.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aSex.setBounds(284, 122, 74, 42);
        pal_Register.add(lbl_aSex);

        cbo_StudentSex = new JComboBox();
        cbo_StudentSex.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        cbo_StudentSex.setBackground(Color.WHITE);
        cbo_StudentSex.setModel(new DefaultComboBoxModel(sex));
        cbo_StudentSex.setBounds(350, 130, 45, 27);
        pal_Register.add(cbo_StudentSex);
        // txt_aSex = new JTextField();
        // txt_aSex.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        // txt_aSex.setColumns(10);
        // txt_aSex.setBounds(350, 130, 140, 27);
        // pal_Register.add(txt_aSex);

        // ����
        JLabel lbl_aNativePlace = new JLabel("����:");
        lbl_aNativePlace.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aNativePlace.setBounds(65, 196, 74, 42);
        pal_Register.add(lbl_aNativePlace);

        txt_aNativePlace = new JTextField();
        txt_aNativePlace.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aNativePlace.setColumns(10);
        txt_aNativePlace.setBounds(120, 207, 140, 27);
        pal_Register.add(txt_aNativePlace);

        // ��������
        JLabel lbl_aBirthday = new JLabel("��������:");
        lbl_aBirthday.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aBirthday.setBounds(284, 196, 74, 42);
        pal_Register.add(lbl_aBirthday);


        DateChooser dateChooser = DateChooser.getInstance("yyyy-MM-dd");
        txt_aBirthday = new JTextField();
        txt_aBirthday.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aBirthday.setColumns(10);
        txt_aBirthday.setBounds(350, 207, 140, 27);
        dateChooser.register(txt_aBirthday);
        pal_Register.add(txt_aBirthday);

        JLabel lbl_aDepartment = new JLabel("Ժϵ:");
        lbl_aDepartment.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aDepartment.setBounds(65, 278, 74, 42);
        pal_Register.add(lbl_aDepartment);

        txt_aDepartment = new JTextField();
        txt_aDepartment.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aDepartment.setColumns(10);
        txt_aDepartment.setBounds(120, 290, 140, 27);
        pal_Register.add(txt_aDepartment);

        // �༶
        JLabel lbl_aClass = new JLabel("�༶:");
        lbl_aClass.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aClass.setBounds(284, 278, 74, 42);
        pal_Register.add(lbl_aClass);

        txt_aClass = new JTextField();
        txt_aClass.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aClass.setColumns(10);
        txt_aClass.setBounds(350, 290, 140, 27);
        pal_Register.add(txt_aClass);

        // ע���˻�
        JPanel pal_Recharge = new JPanel();
        pal_Recharge.setBackground(Color.white);
        CardAdminSchoolRoll.add(pal_Recharge, "3");
        pal_Recharge.setLayout(null);

        txt_aStudentID = new JTextField();
        txt_aStudentID.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        txt_aStudentID.setColumns(10);
        txt_aStudentID.setBounds(220, 106, 201, 33);
        pal_Recharge.add(txt_aStudentID);

        JLabel lbl_aStudentID = new JLabel("һ��ͨ:");
        lbl_aStudentID.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aStudentID.setBounds(88, 101, 106, 42);
        pal_Recharge.add(lbl_aStudentID);

        final JButton btn_RegisterConfirm = new JButton("ע��");
        btn_RegisterConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // ����û���Ϣ��֤

            }
        });
        btn_RegisterConfirm.setContentAreaFilled(false);
        btn_RegisterConfirm.setBorderPainted(true);
        btn_RegisterConfirm.setBackground(Color.WHITE);
        btn_RegisterConfirm.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        btn_RegisterConfirm.setBounds(166, 274, 90, 30);
        pal_Recharge.add(btn_RegisterConfirm);

        JLabel lbl_aOriginalPwd = new JLabel("��ʼ����");
        lbl_aOriginalPwd.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        lbl_aOriginalPwd.setBounds(88, 190, 106, 42);
        pal_Recharge.add(lbl_aOriginalPwd);

        txt_aOriginalPwd = new JTextField();
        txt_aOriginalPwd.setBounds(220, 197, 201, 33);
        pal_Recharge.add(txt_aOriginalPwd);
        txt_aOriginalPwd.setColumns(10);

        JPanel pal_btn = new JPanel();
        pal_btn.setBackground(Color.WHITE);
        pal_btn.setBounds(0, 31, 106, 407);
        add(pal_btn);
        pal_btn.setLayout(null);

        final JButton btn_AllSchoolRoll = new JButton("����ѧ��");
        btn_AllSchoolRoll.setBackground(Color.WHITE);
        btn_AllSchoolRoll.setContentAreaFilled(false);
        btn_AllSchoolRoll.setBorderPainted(true);
        btn_AllSchoolRoll.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        btn_AllSchoolRoll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // �����鿴����ѧ��
                cl_CardAdminSchoolRoll.show(CardAdminSchoolRoll, "1");
            }
        });
        btn_AllSchoolRoll.setBounds(5, 34, 100, 29);
        pal_btn.add(btn_AllSchoolRoll);

        final JButton btn_AddSchoolRoll = new JButton("����ѧ��");
        btn_AddSchoolRoll.setContentAreaFilled(false);
        btn_AddSchoolRoll.setBorderPainted(true);
        btn_AddSchoolRoll.setBackground(Color.WHITE);
        btn_AddSchoolRoll.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        btn_AddSchoolRoll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl_CardAdminSchoolRoll.show(CardAdminSchoolRoll, "2");
            }
        });
        btn_AddSchoolRoll.setBounds(5, 162, 100, 29);
        pal_btn.add(btn_AddSchoolRoll);

        final JButton btn_Register = new JButton("ע���˻�");
        btn_Register.setContentAreaFilled(false);
        btn_Register.setBorderPainted(true);
        btn_Register.setBackground(Color.WHITE);
        btn_Register.setFont(new Font("΢���ź�", Font.PLAIN, 14));
        btn_Register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl_CardAdminSchoolRoll.show(CardAdminSchoolRoll, "3");
            }
        });
        btn_Register.setBounds(5, 267, 100, 29);
        // pal_btn.add(btn_Register);
        initialize();
        updateStuInfo();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        JPanel jpAdminSchoolRoll = new JPanel();
        jpAdminSchoolRoll.setBackground(Color.WHITE);
        jpAdminSchoolRoll.setBounds(0, 42, 640, 438);
    }
    
    /**
     * ˢ��ѧ����Ϣ
     */
    private void updateStuInfo() {
        stuInfo = new StuInfoModel().scanstuinfo();
        Object[] name = { "һ��ͨ", "����", "ѧ��", "�Ա�", "����", "����", "Ժϵ", "�༶" };
        Object[][] data = new Object[stuInfo.size()][name.length];
        for (int i = 0; i < stuInfo.size(); ++i) {
            data[i][0] = stuInfo.get(i).cardnum;
            data[i][1] = stuInfo.get(i).name;
            data[i][2] = stuInfo.get(i).num;
            data[i][3] = sex[stuInfo.get(i).sex];
            data[i][4] = stuInfo.get(i).nation;
            data[i][5] = stuInfo.get(i).birthday;
            data[i][6] = stuInfo.get(i).scollege;
            data[i][7] = stuInfo.get(i).sclass;
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(data, name);
        tblStuInfo.setModel(model);
        tblStuInfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    // ȷ�����ӽӿ�
    private void confirmAddStuInfo() {
        vStudent stuinfo = new vStudent();
        stuinfo.setCardnum(txt_aNameID.getText());
        stuinfo.setBirthday(txt_aBirthday.getText());
        stuinfo.setName(txt_aName.getText());
        stuinfo.setNation(txt_aNativePlace.getText());
        stuinfo.setNum(txt_aID.getText());
        stuinfo.setSclass(txt_aClass.getText());
        stuinfo.setScollege(txt_aDepartment.getText());
        stuinfo.setSex(cbo_StudentSex.getSelectedIndex());
        String ret = new StuInfoModel().addstuinfo(stuinfo);
        if (ret.equals(CommandProtocol.SUCCESS)) {
            JOptionPane.showMessageDialog(null, "ѧ����ӳɹ���\n\n�Ѵ�����֮������ѧ���˺ţ����ƾѧ��ֱ��ע��ʹ�á�");
            updateStuInfo();
            cl_CardAdminSchoolRoll.show(CardAdminSchoolRoll, "1");
        } else
            JOptionPane.showMessageDialog(null, "���������������");
    }
    
    /**
     * ɾ��ѧ����Ϣ
     */
    private void delStuInfo() {
        if (tblStuInfo.getSelectedRowCount() != 1)
            return;
        if (!new StuInfoModel().deletestuinfo(stuInfo.get(tblStuInfo.getSelectedRow())).equals(CommandProtocol.SUCCESS)) {
            JOptionPane.showMessageDialog(null, "ɾ��ѧ����Ϣʧ�ܣ�", "������ʾ", JOptionPane.ERROR_MESSAGE);
        }
        updateStuInfo();
    }
}