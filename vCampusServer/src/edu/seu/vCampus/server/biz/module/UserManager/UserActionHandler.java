/**
 * �û���Ϊ������
 * @author Wenyu, Yang Yi
 * @version 2.0
 */

package edu.seu.vCampus.server.biz.module.UserManager;

import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.MD5;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.UserActionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;

public class UserActionHandler {
    private String strUsername = null, strPassword = null, strStuNo = null, strToken = null, strCommand = null;

    public UserActionHandler(MessageContainer msg) {
        strToken = msg.strToken;
        if (msg.strParameters != null && msg.strParameters.length >= 2) { // ʹ��strParameters�洢�û�����������Ϣ
            strUsername = msg.strParameters[0];
            strPassword = msg.strParameters[1];
            if (msg.strParameters.length >= 3)
                strStuNo = msg.strParameters[2];
        }
        if (msg.strParameters != null && msg.strParameters.length == 1) {
            strPassword = msg.strParameters[0];
        }
        strCommand = msg.strCommand;
    }

    /**
     * ���û����󣨵�¼��ע�ᣬע�����ַ�����ͬ�ķ���
     * 
     * @return ���ͻ��˵���Ϣ
     */
    public MessageContainer handle() {
        System.out.println("handle() of UserActionHandler is called. cmd = " + strCommand);
        switch (strCommand) {
        case CommandProtocol.CMD_LOGIN: // �û���¼
            if (TokenHandler.checkToken(strToken) == null) {
                // �û�δ��¼�����������Ϣ��鹤��
                UserActionInfoContainer userActionInfo = new UserActionInfoContainer();
                userActionInfo.setStrUsername(strUsername);
                userActionInfo.setStrPassword(strPassword);
                UserInfoContainer ret = userLogin(userActionInfo);
                if (ret == null)
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CRED_ERROR); // �����ʧ�ܣ����ش�����Ϣ
                else {
                    MessageContainer retMessage = new MessageContainer();
                    retMessage.setStrCommand(CommandProtocol.SUCCESS);
                    retMessage.setStrToken(TokenHandler.addToPool(ret));
                    retMessage.setEntityParameters(new EntityModel[] { ret });
                    return retMessage; // �����ɹ�����ȡ�û���Ϣ�����䷢����
                }
            } else {
                // �û��ѵ�¼
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
            }
        case CommandProtocol.CMD_LOGOUT: // �û�ע��
            TokenHandler.revokeToken(strToken);
            return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_LOGOUT);
        case CommandProtocol.CMD_REG: // �û�ע��
            UserActionInfoContainer userActionInfo = new UserActionInfoContainer();
            userActionInfo.setStrUsername(strUsername);
            userActionInfo.setStrPassword(strPassword);
            return userRegister(userActionInfo, strStuNo); // ����ѧ�����ţ���Ҫ�ṩ��ȷ��ѧ��
        case CommandProtocol.CMD_CHANGE_PWD: // �޸�����
            if (TokenHandler.checkToken(strToken) == null) // δ��¼
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
            userChangePwd(strPassword, TokenHandler.checkToken(strToken).nUserID);
            return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_CHANGE_PWD);
        }
        return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
    }

    /**
     * ʹ��������Ϣ�����û���¼�������������û���Ϣ
     * 
     * @param userActionInfo
     *            �û���Ϊ��Ϣ�������û���������
     * 
     * @return �ɹ������û���Ϣ��ʧ�ܷ���null
     */
    public UserInfoContainer userLogin(UserActionInfoContainer userActionInfo) {
        userActionInfo.strUsername = StringUtils.stringFilter(userActionInfo.strUsername); // �滻�����ַ�����ֹSQLע��
        userActionInfo.strPassword = MD5.GetMD5Code(userActionInfo.strPassword);
        UserInfoContainer userInfo = null;
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblUser WHERE uUsername='" + userActionInfo.strUsername
                            + "' AND uPwd='" + userActionInfo.strPassword + "'");
            if (ret.size() > 0) {
                System.out.println(ret);
                userInfo = new UserInfoContainer();
                userInfo.setStrUsername(strUsername);
                userInfo.setnUserID(Integer.valueOf(ret.get(0).get("uId")));
                userInfo.setnGroupIDs(StringUtils.strArrToIntArr(ret.get(0).get("uRole").split(",")));
                userInfo.strName = ret.get(0).get("uName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * �޸��û�����
     * 
     * @param strNewPwd
     *            ������
     * @param nUserId
     *            �û�ID
     */
    public void userChangePwd(String strNewPwd, int nUserId) {
        strNewPwd = MD5.GetMD5Code(strNewPwd);
        try {
            DatabaseManager.queryWithoutResult("UPDATE tblUser SET uPwd='" + strNewPwd + "' WHERE uId=" + nUserId);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    /**
     * ʹ����������Ϣ����ע�ᣬ������ע����
     * 
     * @param userActionInfo
     *            ע����û���Ϣ
     * @param strStuNo
     *            ѧ��ѧ��
     * 
     * @return ע����
     */
    public MessageContainer userRegister(UserActionInfoContainer userActionInfo, String strStuNo) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblStuInfo WHERE sID='" + strStuNo + "'");
            if (ret.size() > 0) {
                Vector<HashMap<String, String>> rett = DatabaseManager
                        .queryWithResult("SELECT * FROM tblUser WHERE uId=" + ret.get(0).get("uId"));
                if (rett.size() == 0 || !StringUtils.isStrEmptySafe(rett.get(0).get("uUsername")) || !StringUtils.isStrEmptySafe(rett.get(0).get("uPwd"))) { // tblUser��ļ�¼�����ڣ������û������벻Ϊ��
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.REG_BEFOR);
                } else {
                    userActionInfo.strUsername = StringUtils.stringFilter(userActionInfo.strUsername); // �滻�����ַ�����ֹSQLע��
                    userActionInfo.strPassword = MD5.GetMD5Code(userActionInfo.strPassword);
                    DatabaseManager.queryWithoutResult(
                            "UPDATE tblUser set uUsername='" + userActionInfo.getStrUsername() + "', uPwd='"
                                    + userActionInfo.getStrPassword() + "' WHERE uId=" + rett.get(0).get("uId"));
                    return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_REG);
                }
            } else {
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_STUNUM);
            }
        } catch (Exception e) {
             e.printStackTrace();
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CMD_REG);
        }
        // û�м�¼����δע�ᣬ��ע����Ϣ����uId��Ӧ���û���Ϣ��¼
        // �ǣ���ȡ�ö�Ӧ��uId��ȥtblUsers�����Ƿ��ѱ�ע�ᣨ�ж��û����������ֶ��Ƿ�Ϊ�գ�
        // ���򣬷��ش�����Ϣ����ʾѧ���ѱ�ע��
        // ѧ�Ų����ڣ�Ҳ���ش�����Ϣ
    }
}
