/**
 * ע����������
 * @author Yang Yi
 * @version 1.0
 */
package edu.seu.vCampus.client.controller;

import edu.seu.vCampus.client.view.RegisterFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.view.util.MessageDisplay;
import edu.seu.vCampus.client.biz.module.UserManager.RegisterModel;
import edu.seu.vCampus.client.controller.LoginController;

public class RegisterController {
    private RegisterFrame frame1;

    public RegisterController() {
        frame1 = new RegisterFrame();
        frame1.setRegAction(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // ����ע���¼�
                // System.out.println("Register button clicked.");
                // new ModifySelfInfo();
                //System.out.println(frame1.getUserId());
                //System.out.println(frame1.getUsername());
                //System.out.println(frame1.getPassword());
                //System.out.println(frame1.getPasswordcon());
                if (frame1.getPassword().equals(frame1.getPasswordcon()) == false) {
                    //System.out.println("DONOTEQUAL");
                    MessageDisplay.showError("ȷ���������");
                    return;
                }
                RegisterModel test1 = new RegisterModel(frame1.getUsername(), frame1.getPassword(), frame1.getUserId());//
                MessageContainer test2 = test1.Register();
                //System.out.println(test2.strCommand);
                if (test2.strCommand.equals(CommandProtocol.ERROR)) {
                    switch (test2.getStrParameters()[0]) {
                    case CommandProtocol.EMPTY_INS:
                        //System.out.println("EMPTY");
                        MessageDisplay.showError("����дȫ����Ϣ");
                        return;
                    case CommandProtocol.FAIL_CONN:
                        //System.out.println("FAILCONN");
                        MessageDisplay.showError("����������ʧ��");
                        return;
                    case CommandProtocol.REG_BEFOR:
                        //System.out.println("REGISTERED");
                        MessageDisplay.showError("��ע���û�");
                        return;
                    case CommandProtocol.NONEXIST_STUNUM:
                        //System.out.println("NONEXISTSTUDENTNUMBER");
                        MessageDisplay.showError("�����ڸ�ѧ��");
                        return;
                    default:
                        //System.out.println("UNKNOWNERROR");
                        MessageDisplay.showError("δ֪����");
                        return;
                    }
                } else {
                    //System.out.println("SUCCESS");
                    MessageDisplay.showInfo("ע��ɹ�");
                    frame1.disposeMe();
                    new LoginController();
                }
            }
        });
        frame1.setCanclAction(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame1.disposeMe();
                new LoginController();
            }
        });
    }
}
