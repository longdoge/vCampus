package edu.seu.vCampus.server.biz.module.StudentManager;

import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vGrade;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class GradeManager {
	private String strCommand = "", strToken = null;
	private int uId = 0;
	public vGrade grade = null;

	public GradeManager(MessageContainer msg) {
		strToken = msg.strToken;
		strCommand = msg.strCommand;
		if (msg.entityParameters != null && msg.entityParameters.length >= 1) {
			grade = (vGrade) msg.entityParameters[0];
		}
		if (msg.strParameters != null && msg.strParameters.length >= 1)
			uId = Integer.parseInt(msg.strParameters[0]);
	}

	public MessageContainer Handle() {
		MessageContainer ret = new MessageContainer();
		int bool = 0;
		switch (strCommand) {
		case CommandProtocol.CMD_SCANGRADE:
			Vector<vGrade> vg = scangrade();
			EntityModel[] ve = new EntityModel[vg.size()];
			for (int i = 0; i < vg.size(); ++i)
				ve[i] = ((EntityModel) vg.get(i));
			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve;
			return ret;
		case CommandProtocol.CMD_SCANGRADEWITHUID:
			Vector<vGrade> vg2 = scangradewithuid(grade);
			EntityModel[] ve2 = new EntityModel[vg2.size()];
			for (int i = 0; i < vg2.size(); ++i)
				ve2[i] = ((EntityModel) vg2.get(i));
			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve2;
			return ret;
		case CommandProtocol.CMD_SCANGRADEWITHCID:
			Vector<vGrade> vg3 = scangradewithcid(grade);
			EntityModel[] ve3 = new EntityModel[vg3.size()];
			for (int i = 0; i < vg3.size(); ++i)
				ve3[i] = ((EntityModel) vg3.get(i));
			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve3;
			return ret;
		case CommandProtocol.CMD_ADDGRADE:
			bool = addgrade(grade);
			if (bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;
		case CommandProtocol.CMD_DELETEGRADE:
			bool = deletegrade(grade);
			if (bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;
		case CommandProtocol.CMD_UPDATEGRADE:
			bool = updategrade(grade);
			if (bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;
		}
		return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
	}

	public Vector<vGrade> scangrade() {
		Vector<vGrade> gradevector = new Vector<vGrade>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblStuGrade ");
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGrade gra = new vGrade();
					gra.setcId(Integer.valueOf(ret.get(i).get("cId")));
					gra.setGrId(Integer.valueOf(ret.get(i).get("grId")));
					
					gra.setGrRaw(Float.valueOf(ret.get(i).get("grRaw")));//femshu
					gra.setuId(Integer.valueOf(ret.get(i).get("uId")));//yonghuid
					gra.setcName(ret.get(i).get("cName"));//course name
					gra.setcTime(ret.get(i).get("cTime"));//course time
					gra.settName(ret.get(i).get("tName"));//teacher name
					gra.setCredit(Float.valueOf(ret.get(i).get("cCredit")));//credit
					gra.setcType(ret.get(i).get("cType"));//course type
					gradevector.add(gra);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradevector;
	}

	public Vector<vGrade> scangradewithuid(vGrade grade) {
		Vector<vGrade> gradevector = new Vector<vGrade>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblStuGrade WHERE uId=" + grade.uId);
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGrade gra = new vGrade();
					gra.setcId(Integer.valueOf(ret.get(i).get("cId")));
					gra.setGrId(Integer.valueOf(ret.get(i).get("grId")));
					
					gra.setGrRaw(Float.valueOf(ret.get(i).get("grRaw")));//femshu
					gra.setuId(Integer.valueOf(ret.get(i).get("uId")));//yonghuid
					gra.setcName(ret.get(i).get("cName"));//course name
					gra.setcTime(ret.get(i).get("cTime"));//course time
					gra.settName(ret.get(i).get("tName"));//teacher name
					gra.setCredit(Float.valueOf(ret.get(i).get("cCredit")));//credit
					gra.setcType(ret.get(i).get("cType"));//course type
					gradevector.add(gra);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradevector;
	}

	public Vector<vGrade> scangradewithcid(vGrade grade) {
		Vector<vGrade> gradevector = new Vector<vGrade>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblStuGrade WHERE cId=" + grade.cId);
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGrade gra = new vGrade();
					gra.setcId(Integer.valueOf(ret.get(i).get("cId")));
					gra.setGrId(Integer.valueOf(ret.get(i).get("grId")));
					
					gra.setGrRaw(Float.valueOf(ret.get(i).get("grRaw")));//femshu
					gra.setuId(Integer.valueOf(ret.get(i).get("uId")));//yonghuid
					gra.setcName(ret.get(i).get("cName"));//course name
					gra.setcTime(ret.get(i).get("cTime"));//course time
					gra.settName(ret.get(i).get("tName"));//teacher name
					gra.setCredit(Float.valueOf(ret.get(i).get("cCredit")));//credit
					gra.setcType(ret.get(i).get("cType"));//course type
					gradevector.add(gra);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradevector;
	}
	public int addgrade(vGrade grade) {
		int bool = 0;
		try {
			DatabaseManager.queryWithoutResult("INSERT INTO tblStuGrade (uId,cId,grRaw,cName,cCredit,cTime,cType,tName) VALUES (" + grade.uId + ","
					+ grade.cId + "," + grade.grRaw + ",'" + grade.cName + "'," + grade.credit + ",'"+grade.cTime+"','" + grade.cType + "','" + grade.tName + "')");
			bool = 1;
			System.out.println("add finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	public int deletegrade(vGrade grade) {
		int bool = 0;
		try {
			DatabaseManager.queryWithoutResult("DELETE * FROM tblStuGrade WHERE grId=" + grade.grId);
			bool = 1;
		} catch (Exception e) {
			e.printStackTrace();
			bool = 0;
		}

		return bool;
	}

	public int updategrade(vGrade grade) {
		int bool = 0;
		try {
			DatabaseManager
					.queryWithoutResult("UPDATE tblStuGrade SET grRaw =" + grade.grRaw + " WHERE grId = " + grade.grId);
			bool = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
}
