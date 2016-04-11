package edu.seu.vCampus.server.biz.module.StudentManager;

import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vStudent;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class StuInfoManager {
	private int uId = 0;
	private String strCommand = "", strToken = null, stuname = "";
	public vStudent stu = new vStudent();
	
	public StuInfoManager(MessageContainer msg){
		strToken = msg.strToken;
		strCommand = msg.strCommand;
		if (msg.entityParameters != null && msg.entityParameters.length >= 1){
			stu = (vStudent) msg.entityParameters[0];
		}
		if (msg.strParameters != null && msg.strParameters.length >= 1)
			uId = Integer.parseInt(msg.strParameters[0]);
	}
	
	public MessageContainer Handle(){
		MessageContainer ret = new MessageContainer();
		int bool = 0;
		switch(strCommand){
		case CommandProtocol.CMD_SCANSTUINFO:
			Vector<vStudent> vg = scanstuinfo();
			EntityModel[] ve = new EntityModel[vg.size()];
			for (int i = 0; i < vg.size(); ++i)
				ve[i] = ((EntityModel) vg.get(i));
			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve;
			return ret;
		case CommandProtocol.CMD_SCANSTUINFOWITHUID:
			Vector<vStudent> vg2 = scanstuinfowithuid(stu);
			EntityModel[] ve2 = new EntityModel[vg2.size()];
			for (int i = 0; i < vg2.size(); ++i)
				ve2[i] = ((EntityModel) vg2.get(i));
			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve2;
			return ret;
		case CommandProtocol.CMD_ADDSTUINFO:
			bool = addstuinfo(stu);
			if(bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;
		case CommandProtocol.CMD_DELETESTUINFO:
			bool = deletestuinfo(stu);
			if(bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;
		case CommandProtocol.CMD_UPDATESTUINFO:
			bool = updatestuinfo(stu);
			if(bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;			
		}
		return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
	}
	
	public Vector<vStudent> scanstuinfowithuid(vStudent student) {
		Vector<vStudent> stuvector = new Vector<vStudent>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblStuInfo WHERE uId=" + student.uId);
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vStudent stu = new vStudent();
					stu.setAge(Integer.valueOf(ret.get(i).get("sAge")));
					stu.setCardnum(ret.get(i).get("sCardnum"));
					stu.setName(ret.get(i).get("sName"));
					stu.setNation(ret.get(i).get("sNation"));;
					stu.setNum(ret.get(i).get("sID"));
					stu.setPhonenum(ret.get(i).get("sPhoneNum"));
					stu.setSex(Integer.valueOf(ret.get(i).get("sSex")));
					stu.setuId(Integer.valueOf(ret.get(i).get("uId")));
					stu.setBirthday(ret.get(i).get("sBirthday").split(" ")[0]);
					stu.setSclass(ret.get(i).get("sClass"));
					stu.setScollege(ret.get(i).get("sCollege"));
					
					stuvector.add(stu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stuvector;
	}
	public Vector<vStudent> scanstuinfo() {
		Vector<vStudent> stuvector = new Vector<vStudent>();
	
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblStuInfo");
			if (ret.size() > 0) {
				System.out.println(ret);
				
				for (int i = 0; i < ret.size(); ++i) {
					vStudent stu = new vStudent();
					stu.setAge(Integer.valueOf(ret.get(i).get("sAge")));
					stu.setCardnum(ret.get(i).get("sCardnum"));
					stu.setName(ret.get(i).get("sName"));
					stu.setNation(ret.get(i).get("sNation"));;
					stu.setNum(ret.get(i).get("sID"));
					stu.setPhonenum(ret.get(i).get("sPhoneNum"));
					stu.setSex(Integer.valueOf(ret.get(i).get("sSex")));
					stu.setuId(Integer.valueOf(ret.get(i).get("uId")));
					stu.setBirthday(ret.get(i).get("sBirthday"));
					stu.setSclass(ret.get(i).get("sClass"));
					stu.setScollege(ret.get(i).get("sCollege"));
					stuvector.add(stu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stuvector;
	}
	
	public int addstuinfo(vStudent stu){
		int bool = 0;
		try {
		    int uid = DatabaseManager.queryForId("INSERT INTO tblUser (uRole) VALUES (" + CommandProtocol.ROLE_STUDENT + ")");
			DatabaseManager.queryWithoutResult("INSERT INTO tblStuInfo (uId,sCardnum,sName,sAge,sPhoneNum,sSex,sID,sNation,sBirthday,sCollege,sClass) VALUES (" + uid + ","
					+ stu.cardnum + ",'" + stu.name + "'," + stu.age + ",'" + stu.phonenum + "'," + stu.sex + ",'" + stu.num + "','" + stu.nation + "','" + stu.birthday + "','" + stu.scollege + "','" + stu.sclass + "')");
			bool = 1;
			System.out.println("add finished");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bool;
	}
	public int deletestuinfo(vStudent stu){
		int bool = 0;
		try {
			DatabaseManager.queryWithoutResult("DELETE * FROM tblStuInfo WHERE uId=" + stu.uId);
			bool = 1;
		} catch (Exception e) {
			e.printStackTrace();
			bool = 0;
		}
		return bool;
	}
	
	public int updatestuinfo(vStudent stu){
		int bool = 0;
		try{
			DatabaseManager.queryWithoutResult("UPDATE tblStuInfo SET sAge =" + stu.age +", "
					+ "sSex = " + stu.sex + " WHERE uId = " + stu.uId);
			bool = 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return bool;
	}
}
