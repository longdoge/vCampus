/**
 * 修改密码界面控制器
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
                    // 密码相同，继续
                    if (strNewPwd.length() == 0) {
                        MessageDisplay.showError("请输入密码！");
                    } else {
                        if (LoginModel.userChangePassword(strNewPwd).strCommand.equals(CommandProtocol.SUCCESS)) {
                            MessageDisplay.showInfo("修改成功，请牢记新密码！");
                            mc.setViewDefault();
                        } else {
                            MessageDisplay.showError("出现未知错误，请重试！");
                        }
                        ucp.clearPasswords();
                    }
                } else {
                    MessageDisplay.showError("密码不一致！");
                }
            }
        });
    }
}
