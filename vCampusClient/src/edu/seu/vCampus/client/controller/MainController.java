/**
 * 主界面控制器
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
            // 添加系统管理员对应的模块
        	frame.addModule("用户管理", new ManagerView(), "images/users.png", false);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_FACAULTY_ADMIN)) {
            // 添加教务老师对应的模块
            frame.addModule("学籍管理", new AdminSchoolRoleView(), "images/documents.png", false);
            frame.addModule("课程管理", new AdminCourseView(), "images/class.png", false);
            frame.addModule("成绩管理", new AdminGradeView(), "images/grade.png", false);
            isFacaulty = true;
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_FINANCIAL_ADMIN)) {
            // 添加财务管理对应的模块
        	isFinancialAdmin = true;
        	frame.addModule("财务管理", new AdminBankView(), "images/bank.png", false);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_STUDENT)) {
            // 添加学生对应的模块
        	if (!isFacaulty) {
        		// 与教务管理相排斥
	            frame.addModule("学籍", new SchoolRollView(), "images/documents.png", false);
	            frame.addModule("课程", new CourseView(), "images/class.png", false);
	            frame.addModule("成绩", new GradeView(), "images/grade.png", false);
        	}
            frame.addModule("图书馆", new LibraryView(), "images/library.png", false);
            frame.addModule("商店", new ShopView(this), "images/store.png", true);
            if (!isFinancialAdmin) // 与财务管理相排斥
            	frame.addModule("一卡通中心", new BankView(this), "images/bank.png", true);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_LIBRARIAN)) {
            // 添加图书馆员对应的模块
            frame.addModule("图书管理", new AdminLibraryView(), "images/librarian.png", false);
        }
        if (StringUtils.inArray(UserLoginInfoManager.getnGroupIDs(), CommandProtocol.ROLE_SHOPOWNER)) {
        	// 添加店主对应的模块
            frame.addModule("商店管理", new AdminShopView(), "images/open.png", false);
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
    		frame.setBalance("￥" + ret.strParameters[0]);
    }
    
    public void setBalanceShown(boolean isShown) {
    	frame.setBalanceShown(isShown);
    }
}
