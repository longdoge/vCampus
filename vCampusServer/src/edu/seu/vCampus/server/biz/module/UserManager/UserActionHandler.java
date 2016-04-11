/**
 * 用户行为服务类
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
        if (msg.strParameters != null && msg.strParameters.length >= 2) { // 使用strParameters存储用户输入的相关信息
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
     * 将用户请求（登录，注册，注销）分发给不同的方法
     * 
     * @return 给客户端的消息
     */
    public MessageContainer handle() {
        System.out.println("handle() of UserActionHandler is called. cmd = " + strCommand);
        switch (strCommand) {
        case CommandProtocol.CMD_LOGIN: // 用户登录
            if (TokenHandler.checkToken(strToken) == null) {
                // 用户未登录，进行身份信息检查工作
                UserActionInfoContainer userActionInfo = new UserActionInfoContainer();
                userActionInfo.setStrUsername(strUsername);
                userActionInfo.setStrPassword(strPassword);
                UserInfoContainer ret = userLogin(userActionInfo);
                if (ret == null)
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CRED_ERROR); // 若检查失败，返回错误信息
                else {
                    MessageContainer retMessage = new MessageContainer();
                    retMessage.setStrCommand(CommandProtocol.SUCCESS);
                    retMessage.setStrToken(TokenHandler.addToPool(ret));
                    retMessage.setEntityParameters(new EntityModel[] { ret });
                    return retMessage; // 若检查成功，获取用户信息，并颁发令牌
                }
            } else {
                // 用户已登录
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
            }
        case CommandProtocol.CMD_LOGOUT: // 用户注销
            TokenHandler.revokeToken(strToken);
            return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_LOGOUT);
        case CommandProtocol.CMD_REG: // 用户注册
            UserActionInfoContainer userActionInfo = new UserActionInfoContainer();
            userActionInfo.setStrUsername(strUsername);
            userActionInfo.setStrPassword(strPassword);
            return userRegister(userActionInfo, strStuNo); // 仅向学生开放，需要提供正确的学号
        case CommandProtocol.CMD_CHANGE_PWD: // 修改密码
            if (TokenHandler.checkToken(strToken) == null) // 未登录
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
            userChangePwd(strPassword, TokenHandler.checkToken(strToken).nUserID);
            return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_CHANGE_PWD);
        }
        return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
    }

    /**
     * 使用所给信息进行用户登录操作，并返回用户信息
     * 
     * @param userActionInfo
     *            用户行为信息，包括用户名与密码
     * 
     * @return 成功返回用户信息，失败返回null
     */
    public UserInfoContainer userLogin(UserActionInfoContainer userActionInfo) {
        userActionInfo.strUsername = StringUtils.stringFilter(userActionInfo.strUsername); // 替换特殊字符，防止SQL注入
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
     * 修改用户密码
     * 
     * @param strNewPwd
     *            新密码
     * @param nUserId
     *            用户ID
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
     * 使用所给的信息进行注册，并返回注册结果
     * 
     * @param userActionInfo
     *            注册的用户信息
     * @param strStuNo
     *            学生学号
     * 
     * @return 注册结果
     */
    public MessageContainer userRegister(UserActionInfoContainer userActionInfo, String strStuNo) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblStuInfo WHERE sID='" + strStuNo + "'");
            if (ret.size() > 0) {
                Vector<HashMap<String, String>> rett = DatabaseManager
                        .queryWithResult("SELECT * FROM tblUser WHERE uId=" + ret.get(0).get("uId"));
                if (rett.size() == 0 || !StringUtils.isStrEmptySafe(rett.get(0).get("uUsername")) || !StringUtils.isStrEmptySafe(rett.get(0).get("uPwd"))) { // tblUser里的记录不存在，或者用户名密码不为空
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.REG_BEFOR);
                } else {
                    userActionInfo.strUsername = StringUtils.stringFilter(userActionInfo.strUsername); // 替换特殊字符，防止SQL注入
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
        // 没有记录，则未注册，以注册信息更新uId对应的用户信息记录
        // 是，则取得对应的uId，去tblUsers里检查是否已被注册（判断用户名、密码字段是否为空）
        // 否则，返回错误信息，提示学号已被注册
        // 学号不存在，也返回错误信息
    }
}
