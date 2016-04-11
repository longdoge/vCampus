/**
 * ��¼���������
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
            	// �첽ִ��
            	SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						// �����¼�¼�
		                // System.out.println("Login button clicked.");
		                // System.out.println(frame.getUserId());
		                // System.out.println(frame.getPassword());
		                LoginModel loginModel = new LoginModel(frame.getUserId(), frame.getPassword());
		                MessageContainer loginRet = loginModel.userLogin();
		                if (loginRet.strCommand.equals(CommandProtocol.ERROR)) {
		                	frame.setAllEnabled(true);
		                    switch (loginRet.getStrParameters()[0]) {
		                    case CommandProtocol.EMPTY_INS:
		                        MessageDisplay.showError("����д�û���������");
		                        return;
		                    case CommandProtocol.FAIL_CONN:
		                        MessageDisplay.showError("����������ʧ��");
		                        return;
		                    case CommandProtocol.CRED_ERROR:
		                        MessageDisplay.showError("�û������������");
		                        return;
		                    default:
		                        MessageDisplay.showError("δ֪����");
		                    }
		                }
		                if (loginRet.strCommand.equals(CommandProtocol.SUCCESS)) {
		                    // MessageDisplay.showInfo("��¼�ɹ�����ʱӦ�������Ա�����棨��δ��ɣ�");
		                    // frame.disposeMe();
		                	// �첽ִ��
		                	new Thread(new Runnable() {
		                        @Override
		                        public void run() {
		                        	new MainController(frame);
		                        }
		                    }).start();
		                    // �˳�ʱ�Զ�ע��
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
                // ����ע���¼�
                // System.out.println("Register button clicked.");
                frame.disposeMe();
                new RegisterController();
            }
        });
    }
}
