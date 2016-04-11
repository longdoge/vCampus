/**
 * 用户信息相关的服务类
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.biz.module.UserManager;

import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.*;

import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class UserInfoModel {
	public static int getUidByName(String name) {
		MessageContainer vie = new MessageContainer();
		vie.setStrCommand(CommandProtocol.CMD_GET_UID);
		vie.strToken = UserLoginInfoManager.strToken;
		String[] tem = new String[] { name };
		vie.setStrParameters(tem);
		MessageContainer ret = RequestHandler.sendRequest(vie);
		try {
			return Integer.valueOf(ret.strParameters[0]);
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static String getNameByUid(int nUserId) {
		MessageContainer vie = new MessageContainer();
		vie.setStrCommand(CommandProtocol.CMD_GET_NAME);
		vie.strToken = UserLoginInfoManager.strToken;
		String[] tem = new String[] { String.valueOf(nUserId) };
		vie.setStrParameters(tem);
		MessageContainer ret = RequestHandler.sendRequest(vie);
		try {
			return ret.strParameters[0];
		} catch (Exception e) {
			return "";
		}
	}
	
	public Vector<UserInfoContainer> scanuser(){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANUSERINFO;
		req.strToken = UserLoginInfoManager.getStrToken();
		
        MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<UserInfoContainer> userinfovector = new Vector<UserInfoContainer>();
		for(int i = 0; i < ret.entityParameters.length;i++)
			userinfovector.add((UserInfoContainer)ret.entityParameters[i]);
		return userinfovector;
	}
	
	public String adduser(UserInfoContainer userinfo){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_ADDUSERINFO;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = userinfo;
        req.setEntityParameters(term);
        MessageContainer ret = RequestHandler.sendRequest(req);
        return ret.strCommand;
	}
}
