package edu.seu.vCampus.client.biz.module.CourseManager;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.CourseSelectionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class CourseSelectionAdminModel {
	public MessageContainer addCourse(CourseSelectionInfoContainer csinfo) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.CSADMIN_ADD;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.entityParameters = new EntityModel[] { csinfo };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		return retmsg;
	}
	
	public MessageContainer delCourse(Integer nCsID) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.CSADMIN_DELETE;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.strParameters = new String[] { String.valueOf(nCsID) };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		return retmsg;
	}
	
	public CourseSelectionInfoContainer[] viewCourse(Integer nUserId) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.CSADMIN_VIEWALL;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.strParameters = new String[] { String.valueOf(nUserId) };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		if (retmsg == null || retmsg.entityParameters == null)
			return null;
		CourseSelectionInfoContainer[] csel = new CourseSelectionInfoContainer[retmsg.entityParameters.length];
		for (int i = 0; i < csel.length; ++i)
			csel[i] = (CourseSelectionInfoContainer) retmsg.entityParameters[i];
		return csel;
	}
	
	public CourseSelectionInfoContainer[] viewCourseSelection(Integer nCourseId) {
		MessageContainer msgToServer = new MessageContainer();
		msgToServer.strCommand = CommandProtocol.CSADMIN_VIEWALL_BY_CLASS;
		msgToServer.strToken = UserLoginInfoManager.strToken;
		msgToServer.strParameters = new String[] { String.valueOf(nCourseId) };
		MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
		if (retmsg == null || retmsg.entityParameters == null)
			return null;
		CourseSelectionInfoContainer[] csel = new CourseSelectionInfoContainer[retmsg.entityParameters.length];
		for (int i = 0; i < csel.length; ++i)
			csel[i] = (CourseSelectionInfoContainer) retmsg.entityParameters[i];
		return csel;
	}
}
