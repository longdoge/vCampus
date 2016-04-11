package edu.seu.vCampus.client.biz.module.StudentManager;

import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.SharedComponents.vo.vStudent;
import edu.seu.vCampus.client.biz.util.RequestHandler;

public class StuInfoModel {
	public Vector<vStudent> scanstuinfo() {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANSTUINFO;
		MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vStudent> student = new Vector<vStudent>();
		if (ret.entityParameters.length > 0)
			for (int i = 0; i < ret.entityParameters.length; ++i)
				student.add((vStudent) ret.entityParameters[i]);
		return student;
	}

	public Vector<vStudent> scanstuinfowithuid(vStudent stu) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANSTUINFOWITHUID;
		EntityModel[] e = new EntityModel[1];
		e[0] = (EntityModel) stu;
		req.setEntityParameters(e);
		MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vStudent> student = new Vector<vStudent>();
		if (ret.entityParameters.length > 0)
			for (int i = 0; i < ret.entityParameters.length; i++)
				student.add((vStudent) ret.entityParameters[i]);
		return student;
	}

	public String addstuinfo(vStudent stu) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_ADDSTUINFO;
		req.strToken = "fake_token";
		EntityModel[] term = new EntityModel[1];
		term[0] = stu;
		req.setEntityParameters(term);
		MessageContainer ret = RequestHandler.sendRequest(req);
		return ret.strCommand;
	}

	public String deletestuinfo(vStudent stu) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_DELETESTUINFO;
		req.strToken = "fake_token";
		EntityModel[] term = new EntityModel[1];
		term[0] = stu;
		req.setEntityParameters(term);
		MessageContainer ret = RequestHandler.sendRequest(req);
		return ret.strCommand;
	}

	public String updatestuinfo(vStudent stu) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_UPDATESTUINFO;
		req.strToken = "fake_token";
		EntityModel[] term = new EntityModel[1];
		term[0] = stu;
		req.setEntityParameters(term);
		MessageContainer ret = RequestHandler.sendRequest(req);
		return ret.strCommand;
	}
}
