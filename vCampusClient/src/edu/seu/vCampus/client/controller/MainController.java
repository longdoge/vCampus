/**
 * �����������
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.module.UserManager.LoginModel;
import edu.seu.vCampus.client.biz.module.vBank.BankInfoModel;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;
import edu.seu.vCampus.client.view.LoginFrame;
import edu.seu.vCampus.client.view.MainFrame;
import edu.seu.vCampus.client.view.admin.Bank.AdminBankView;
import edu.seu.vCampus.client.view.admin.Library.AdminLibraryView;
import edu.seu.vCampus.client.view.admin.Shop.AdminShopView;
import edu.seu.vCampus.client.view.admin.Teacher.AdminCourseView;
import edu.seu.vCampus.client.view.admin.Teacher.AdminGradeView;
import edu.seu.vCampus.client.view.admin.Teacher.AdminSchoolRoleView;
import edu.seu.vCampus.client.view.admin.UserInfoManager.ManagerView;
import edu.seu.vCampus.client.view.stu.Bank.BankView;
import edu.seu.vCampus.client.view.stu.Course.CourseView;
import edu.seu.vCampus.client.view.stu.Grade.GradeView;
import edu.seu.vCampus.client.view.stu.Library.LibraryView;
import edu.seu.vCampus.client.view.stu.SchoolRoll.SchoolRollView;
import edu.seu.vCampus.client.view.stu.Shop.ShopView;

public class MainController {
    private MainFrame frame;

    public MainController(final LoginFrame lf) {
    	boolean isFacaulty = false, isFinancialAdmin = false;
    	frame = new MainFrame();
    	updateBalance();
    	setBalanceShown(false);
        frame.setTip(UserLoginInfoManager.getUserInfo().strName);
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_SYS_ADMIN)) {
            // ���ϵͳ����Ա��Ӧ��ģ��
        	frame.addModule("�û�����", new ManagerView(), "images/users.png", false);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_FACAULTY_ADMIN)) {
            // ��ӽ�����ʦ��Ӧ��ģ��
            frame.addModule("ѧ������", new AdminSchoolRoleView(), "images/documents.png", false);
            frame.addModule("�γ̹���", new AdminCourseView(), "images/class.png", false);
            frame.addModule("�ɼ�����", new AdminGradeView(), "images/grade.png", false);
            isFacaulty = true;
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_FINANCIAL_ADMIN)) {
            // ��Ӳ�������Ӧ��ģ��
        	isFinancialAdmin = true;
        	frame.addModule("�������", new AdminBankView(), "images/bank.png", false);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_STUDENT)) {
            // ���ѧ����Ӧ��ģ��
        	if (!isFacaulty) {
        		// �����������ų�
	            frame.addModule("ѧ��", new SchoolRollView(), "images/documents.png", false);
	            frame.addModule("�γ�", new CourseView(), "images/class.png", false);
	            frame.addModule("�ɼ�", new GradeView(), "images/grade.png", false);
        	}
            frame.addModule("ͼ���", new LibraryView(), "images/library.png", false);
            frame.addModule("�̵�", new ShopView(this), "images/store.png", true);
            if (!isFinancialAdmin) // �����������ų�
            	frame.addModule("һ��ͨ����", new BankView(this), "images/bank.png", true);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_LIBRARIAN)) {
            // ���ͼ���Ա��Ӧ��ģ��
            frame.addModule("ͼ�����", new AdminLibraryView(), "images/librarian.png", false);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_SHOPOWNER)) {
        	// ��ӵ�����Ӧ��ģ��
            frame.addModule("�̵����", new AdminShopView(), "images/open.png", false);
        }
        frame.setLogoutAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LoginModel.userLogout();
                frame.disposeMe();
                new LoginController();
            }
        });
        new PasswordChangeController(frame.pal_chgpwd, this);
        lf.disposeMe();
        frame.showMe();
    }
    
    public void setViewDefault() {
        frame.switchToFirstCard();
    }
    
    public void updateBalance() {
    	MessageContainer ret = BankInfoModel.Query(UserLoginInfoManager.getnUserID());
    	if (ret.getStrCommand().equals(CommandProtocol.SUCCESS))
    		frame.setBalance("��" + ret.strParameters[0]);
    }
    
    public void setBalanceShown(boolean isShown) {
    	frame.setBalanceShown(isShown);
    }
}
