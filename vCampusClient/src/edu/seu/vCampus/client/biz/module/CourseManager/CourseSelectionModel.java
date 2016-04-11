package edu.seu.vCampus.client.biz.module.CourseManager;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.CourseSelectionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class CourseSelectionModel {
	public static MessageContainer addCourse(Integer nCourseID) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.CS_ADD;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.strParameters = new String[] { String.valueOf(nCourseID) };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		return retmsg;
	}
	
	public static MessageContainer delCourse(Integer nCsID) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.CS_DELETE;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.strParameters = new String[] { String.valueOf(nCsID) };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		return retmsg;
	}
	
	public static CourseSelectionInfoContainer[] viewCourse() {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.CS_VIEWALL;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		if (retmsg == null || retmsg.entityParameters == null)
			return null;
		CourseSelectionInfoContainer[] csel = new CourseSelectionInfoContainer[retmsg.entityParameters.length];
		for (int i = 0; i < csel.length; ++i)
			csel[i] = (CourseSelectionInfoContainer) retmsg.entityParameters[i];
		return csel;
	}
}
