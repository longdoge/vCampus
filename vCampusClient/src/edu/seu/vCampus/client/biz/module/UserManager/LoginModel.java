/**
 * �û���¼��صķ�����
 * @author Wenyu, Yi Yang
 * @version 2.0
 */

package edu.seu.vCampus.client.biz.module.UserManager;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class LoginModel {
    String username;
    String password;

    public LoginModel(String userName, String passWord) {
        username = userName;
        password = passWord;
    }

    public MessageContainer userLogin() {
        if (username.length() == 0 || password.length() == 0) {
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.EMPTY_INS);// ����δ��д�û��������룬���ش�����Ϣ
        }
        String[] strParams = new String[] { username, password };
        MessageContainer loginMessage = new MessageContainer();
        loginMessage.setStrCommand(CommandProtocol.CMD_LOGIN);
        loginMessage.setStrParameters(strParams);
        MessageContainer retMessage = RequestHandler.sendRequest(loginMessage);
        if (retMessage == null) {
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);// ���������ӷ�����ʧ�ܣ����ش�����Ϣ
        } else {
            if (retMessage.getStrCommand().equals(CommandProtocol.SUCCESS)) {
                System.out.println("OK. Token = " + retMessage.strToken);
                UserLoginInfoManager.setStrToken(retMessage.strToken);
                UserLoginInfoManager.setUserInfo((UserInfoContainer) retMessage.entityParameters[0]);
                //System.out.println(UserLoginInfoManager.getnGroupIDs()[0]);
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_LOGIN);// ��¼�ɹ������سɹ���Ϣ
            } else {
                System.out.println("Error. Msg = " + retMessage.getStrParameters()[0]);
                switch (retMessage.getStrParameters()[0]) {
                case CommandProtocol.CRED_ERROR:
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CRED_ERROR);// �û�������󣬷��ش�����Ϣ
                default:
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);// δ֪����
                }
            }
        }
    }
    
    public static void userLogout() {
        if (UserLoginInfoManager.getStrToken() == null || UserLoginInfoManager.getStrToken().length() == 0)
            return;
        MessageContainer logoutMessage = new MessageContainer();
        logoutMessage.setStrCommand(CommandProtocol.CMD_LOGOUT);
        logoutMessage.setStrToken(UserLoginInfoManager.getStrToken());
        RequestHandler.sendRequest(logoutMessage);
        UserLoginInfoManager.setStrToken(null);
        UserLoginInfoManager.setUserInfo(null);
    }
    
    public static MessageContainer userChangePassword(String strNewPwd) {
        if (strNewPwd.length() == 0) {
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.EMPTY_INS);// ����δ��д�û��������룬���ش�����Ϣ
        }
        String[] strParams = new String[] { strNewPwd };
        MessageContainer loginMessage = new MessageContainer();
        loginMessage.setStrCommand(CommandProtocol.CMD_CHANGE_PWD);
        loginMessage.setStrParameters(strParams);
        loginMessage.setStrToken(UserLoginInfoManager.getStrToken());
        MessageContainer retMessage = RequestHandler.sendRequest(loginMessage);
        if (retMessage == null) {
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);// ���������ӷ�����ʧ�ܣ����ش�����Ϣ
        } else {
            return retMessage;
        }
    }
}
