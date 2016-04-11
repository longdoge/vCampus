/**
 * �޸�������������
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.client.biz.module.UserManager.LoginModel;
import edu.seu.vCampus.client.view.UserPwChgView;
import edu.seu.vCampus.client.view.util.MessageDisplay;

public class PasswordChangeController {
    public PasswordChangeController (final UserPwChgView ucp, final MainController mc) {
        ucp.setModifyAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strNewPwd = ucp.getPassword();
                String strCfmPwd = ucp.getConfirmPassword();
                if (strNewPwd.equals(strCfmPwd)) {
                    // ������ͬ������
                    if (strNewPwd.length() == 0) {
                        MessageDisplay.showError("���������룡");
                    } else {
                        if (LoginModel.userChangePassword(strNewPwd).strCommand.equals(CommandProtocol.SUCCESS)) {
                            MessageDisplay.showInfo("�޸ĳɹ������μ������룡");
                            mc.setViewDefault();
                        } else {
                            MessageDisplay.showError("����δ֪���������ԣ�");
                        }
                        ucp.clearPasswords();
                    }
                } else {
                    MessageDisplay.showError("���벻һ�£�");
                }
            }
        });
    }
}
