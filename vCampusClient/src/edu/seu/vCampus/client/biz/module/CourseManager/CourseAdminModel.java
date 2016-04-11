package edu.seu.vCampus.client.biz.module.CourseManager;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class CourseAdminModel {
	public static MessageContainer addCourse(CourseInfoContainer cinfo) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.C_ADD;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.entityParameters = new EntityModel[] { cinfo };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		return retmsg;
	}

	public static MessageContainer modifyCourse(CourseInfoContainer cinfo) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.C_MODIFY;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.entityParameters = new EntityModel[] { cinfo };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		return retmsg;
	}

	public static MessageContainer delCourse(Integer nCourseID) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.C_DELETE;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.strParameters = new String[] { String.valueOf(nCourseID) };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		return retmsg;
	}
	
	public static CourseInfoContainer viewCourse(Integer nCourseID) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.C_LOOKUP;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.strParameters = new String[] { String.valueOf(nCourseID) };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		if (retmsg == null || retmsg.entityParameters == null || retmsg.entityParameters.length == 0)
			return null;
		return (CourseInfoContainer) retmsg.entityParameters[0];
	}
	
	public static CourseInfoContainer[] viewCourse_tId(Integer tId) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strParameters = new String[] {String.valueOf(tId)};
		msgToServer.strCommand = CommandProtocol.C_LOOKUPTID;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		if (retmsg == null || retmsg.entityParameters == null)
			return null;
		CourseInfoContainer[] c = new CourseInfoContainer[retmsg.entityParameters.length];
		for (int i = 0; i < c.length; ++i)
			c[i] = (CourseInfoContainer) retmsg.entityParameters[i];
		return c;
	}
	
	public static CourseInfoContainer[] viewAvaiCourseForMe() {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.C_LOOKUP_MY;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		if (retmsg == null || retmsg.entityParameters == null)
			return null;
		CourseInfoContainer[] c = new CourseInfoContainer[retmsg.entityParameters.length];
		for (int i = 0; i < c.length; ++i)
			c[i] = (CourseInfoContainer) retmsg.entityParameters[i];
		return c;
	}
}
