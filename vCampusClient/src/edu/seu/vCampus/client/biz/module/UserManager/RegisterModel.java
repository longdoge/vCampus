/**
 * 用户注册相关的服务类
 * @author Yi Yang
 * @version 2.0
 */

package edu.seu.vCampus.client.biz.module.UserManager;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;

public class RegisterModel {
    String username;
    String userpassword;
    String stunumber;

    public RegisterModel(String userName, String userPassword, String stuNumber) {
        username = userName;
        userpassword = userPassword;
        stunumber = stuNumber;
    }

    public MessageContainer Register() {
        if (username.length() == 0 || userpassword.length() == 0 || stunumber.length() == 0) {
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.EMPTY_INS);
        }
        String[] strParams = new String[] { username, userpassword, stunumber };
        MessageContainer msg = new MessageContainer();
        msg.setStrParameters(strParams);
        msg.setStrCommand(CommandProtocol.CMD_REG);
        MessageContainer retmsg = RequestHandler.sendRequest(msg);
        if (retmsg == null) {
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);
        } else {
            return retmsg;
        }
    }
}
