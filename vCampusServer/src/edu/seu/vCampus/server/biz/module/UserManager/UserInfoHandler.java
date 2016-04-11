package edu.seu.vCampus.server.biz.module.UserManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class UserInfoHandler {
	public static String getName(int nUserId) {
		try {
			Vector<HashMap<String, String>> rett = DatabaseManager
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + nUserId);
			if (rett.size() == 0)
				return null;
			return rett.get(0).get("uName");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getId(String strName) {
		try {
			Vector<HashMap<String, String>> rett = DatabaseManager
					.queryWithResult("SELECT * FROM tblUser WHERE uUsername='" + strName + "'");
			if (rett.size() == 0)
				return -1;
			return Integer.valueOf(rett.get(0).get("uId"));
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static Vector<UserInfoContainer> scanuserinfo() {
		Vector<UserInfoContainer> userinfovector = new Vector<UserInfoContainer>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblUser");
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					UserInfoContainer userinfo = new UserInfoContainer();
					// userinfo.setnGroupIDs(ret.get(0).get("uRole"));
					userinfo.setnUserID(Integer.valueOf(ret.get(0).get("uId")));
					userinfo.setStrUsername(ret.get(i).get("uUsername"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userinfovector;
	}

	public static int adduserinfo(UserInfoContainer userinfo) {
		int bool = 0;
		try {
			DatabaseManager
					.queryWithoutResult("INSERT INTO tblUser (uUsername,uName,uRole,uBalance,shopName) VALUES ('"
							+ userinfo.getStrUsername() + "','" + userinfo.getStrName() + "'," + userinfo.getnGroupIDs() + ",'" + userinfo.getuBalance() + 
							"','" + userinfo.getShopname() + "')");
			bool = 1;
			//System.out.println("add finished");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bool;
	}

	public static MessageContainer handle(MessageContainer msg) {
		if (TokenHandler.checkToken(msg.strToken) == null)
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
		switch (msg.strCommand) {
		case CommandProtocol.CMD_GET_UID:
			if (msg.strParameters == null || msg.strParameters.length == 0)
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			MessageContainer msgGo = new MessageContainer();
			msgGo.setStrCommand(CommandProtocol.SUCCESS);
			msgGo.strParameters = new String[] { String.valueOf(getId(msg.strParameters[0])) };
			return msgGo;
		case CommandProtocol.CMD_GET_NAME:
			if (msg.strParameters == null || msg.strParameters.length == 0
					|| !StringUtils.isOnlyDigit(msg.strParameters[0]))
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			MessageContainer msgGo1 = new MessageContainer();
			msgGo1.setStrCommand(CommandProtocol.SUCCESS);
			msgGo1.strParameters = new String[] { getName(Integer.valueOf(msg.strParameters[0])) };
			return msgGo1;
		case CommandProtocol.CMD_SCANUSERINFO:
			Vector<UserInfoContainer> vg = scanuserinfo();
			EntityModel[] ve = new EntityModel[vg.size()];
			for (int i = 0; i < vg.size(); ++i)
				ve[i] = ((EntityModel) vg.get(i));
			MessageContainer ret = new MessageContainer();
			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve;
			return ret;
		case CommandProtocol.CMD_ADDUSERINFO:
			UserInfoContainer userinfo = new UserInfoContainer();
			MessageContainer ret2 = new MessageContainer();
			if(msg.entityParameters != null && msg.entityParameters.length >= 1)
				userinfo = (UserInfoContainer)msg.entityParameters[0];
			int bool = adduserinfo(userinfo);
			if (bool == 1)
				ret2.strCommand = CommandProtocol.SUCCESS;
			else
				ret2.strCommand = CommandProtocol.ERROR;
			return ret2;
		default:
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
		}
	}
}
