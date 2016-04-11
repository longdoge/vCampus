package edu.seu.vCampus.client.biz.module.StudentManager;

import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.client.biz.util.RequestHandler;

public class GradeModel {

    public Vector<vGrade> scangrade() {
        MessageContainer req = new MessageContainer();
        req.strCommand = CommandProtocol.CMD_SCANGRADE;
        MessageContainer ret = RequestHandler.sendRequest(req);
        Vector<vGrade> grade = new Vector<vGrade>();
        if (ret.entityParameters.length > 0)
            for (int i = 0; i < ret.entityParameters.length; ++i)
                grade.add((vGrade) ret.entityParameters[i]);
        return grade;
    }

    public Vector<vGrade> scangradewithuid(vGrade grade) {
        MessageContainer req = new MessageContainer();
        req.strCommand = CommandProtocol.CMD_SCANGRADEWITHUID;
        EntityModel[] e = new EntityModel[1];
        e[0] = grade;
        req.setEntityParameters(e);
        MessageContainer ret = RequestHandler.sendRequest(req);
        Vector<vGrade> gradevector = new Vector<vGrade>();
        if (ret.entityParameters.length > 0)
            for (int i = 0; i < ret.entityParameters.length; ++i)
                gradevector.add((vGrade) ret.entityParameters[i]);
        return gradevector;
    }
    
    public Vector<vGrade> scangradewithcid(vGrade grade) {
        MessageContainer req = new MessageContainer();
        req.strCommand = CommandProtocol.CMD_SCANGRADEWITHCID;
        EntityModel[] e = new EntityModel[1];
        e[0] = grade;
        req.setEntityParameters(e);
        MessageContainer ret = RequestHandler.sendRequest(req);
        Vector<vGrade> gradevector = new Vector<vGrade>();
        if (ret.entityParameters.length > 0)
            for (int i = 0; i < ret.entityParameters.length; ++i)
                gradevector.add((vGrade) ret.entityParameters[i]);
        return gradevector;
    }

    public boolean addgrade(vGrade grade) {
        MessageContainer req = new MessageContainer();
        req.strCommand = CommandProtocol.CMD_ADDGRADE;
        EntityModel[] e = new EntityModel[1];
        e[0] = (EntityModel) grade;
        req.setEntityParameters(e);
        MessageContainer ret = RequestHandler.sendRequest(req);
        if (ret == null)
        	return false;
        return ret.strCommand.equals(CommandProtocol.SUCCESS);
    }

    public String deletegrade(vGrade grade) {
        MessageContainer req = new MessageContainer();
        req.strCommand = CommandProtocol.CMD_DELETEGRADE;
        EntityModel[] e = new EntityModel[1];
        e[0] = (EntityModel) grade;
        req.setEntityParameters(e);
        MessageContainer ret = RequestHandler.sendRequest(req);
        return ret.strCommand;

    }

    public String updategrade(vGrade grade) {
        MessageContainer req = new MessageContainer();
        req.strCommand = CommandProtocol.CMD_UPDATEGRADE;
        EntityModel[] e = new EntityModel[1];
        e[0] = (EntityModel) grade;
        req.setEntityParameters(e);
        MessageContainer ret = RequestHandler.sendRequest(req);
        return ret.strCommand;

    }
}
