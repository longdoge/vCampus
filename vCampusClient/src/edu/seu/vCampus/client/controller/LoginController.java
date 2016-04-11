/**
 * 登录界面控制器
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import edu.seu.vCampus.client.view.LoginFrame;
import edu.seu.vCampus.client.view.util.MessageDisplay;
import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.module.UserManager.*;

public class LoginController {
    private LoginFrame frame;

    public LoginController() {
        frame = new LoginFrame();
        frame.setLoginAction(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	frame.setAllEnabled(false);
            	// 异步执行
            	SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						// 处理登录事件
		                // System.out.println("Login button clicked.");
		                // System.out.println(frame.getUserId());
		                // System.out.println(frame.getPassword());
		                LoginModel loginModel = new LoginModel(frame.getUserId(), frame.getPassword());
		                MessageContainer loginRet = loginModel.userLogin();
		                if (loginRet.strCommand.equals(CommandProtocol.ERROR)) {
		                	frame.setAllEnabled(true);
		                    switch (loginRet.getStrParameters()[0]) {
		                    case CommandProtocol.EMPTY_INS:
		                        MessageDisplay.showError("请填写用户名与密码");
		                        return;
		                    case CommandProtocol.FAIL_CONN:
		                        MessageDisplay.showError("服务器连接失败");
		                        return;
		                    case CommandProtocol.CRED_ERROR:
		                        MessageDisplay.showError("用户名或密码错误");
		                        return;
		                    default:
		                        MessageDisplay.showError("未知错误");
		                    }
		                }
		                if (loginRet.strCommand.equals(CommandProtocol.SUCCESS)) {
		                    // MessageDisplay.showInfo("登录成功，此时应进入管理员主界面（暂未完成）");
		                    // frame.disposeMe();
		                	// 异步执行
		                	new Thread(new Runnable() {
		                        @Override
		                        public void run() {
		                        	new MainController(frame);
		                        }
		                    }).start();
		                    // 退出时自动注销
		                    Runtime.getRuntime().addShutdownHook(new Thread() {
		                        public void run() {
		                            LoginModel.userLogout();
		                        }
		                    });
		                }
					}
            	});
            }
        });
        frame.setRegisterAction(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // 处理注册事件
                // System.out.println("Register button clicked.");
                frame.disposeMe();
                new RegisterController();
            }
        });
    }
}
