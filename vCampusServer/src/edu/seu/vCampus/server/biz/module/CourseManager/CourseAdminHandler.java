package edu.seu.vCampus.server.biz.module.CourseManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class CourseAdminHandler {
	public static int addCourse(CourseInfoContainer csinfo) {
		csinfo.strName = StringUtils.stringFilter(csinfo.strName);
		csinfo.strIntro = StringUtils.stringFilter(csinfo.strIntro);
		String strStuNo_serialized = "";
		if (csinfo.strStuNo != null) {
			for (int i = 0; i < csinfo.strStuNo.length; ++i) {
				csinfo.strStuNo[i] = StringUtils.stringFilter(csinfo.strStuNo[i]).replace("|", "");
				strStuNo_serialized += csinfo.strStuNo[i];
				if (i < csinfo.strStuNo.length - 1)
					strStuNo_serialized += "|";
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		csinfo.strBeginDate = csinfo.strEndDate = sdf.format(new Date());
		/*
		 * try { sdf.parse(csinfo.strBeginDate); sdf.parse(csinfo.strEndDate); }
		 * catch (ParseException e) { e.printStackTrace(); return -1; }
		 */
		try {
			DatabaseManager.queryWithoutResult(
					"INSERT INTO tblCourse (cName, tId, cSemester, cDate, cBegin, cEnd, cCred, cIntro, cStuNum, cMaxStu, cStuNo) VALUES ('"
							+ csinfo.strName + "', " + csinfo.nTeacherID + ", " + csinfo.nSemester + ", '"
							+ csinfo.strCourseDate + "', '" + csinfo.strBeginDate + "', '" + csinfo.strEndDate + "', "
							+ csinfo.fCredit + ", '" + csinfo.strIntro + "', 0, " + csinfo.nMaxStuNum + ", '"
							+ strStuNo_serialized + "')");
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public static int modifyCourse(CourseInfoContainer csinfo) {
		csinfo.strName = StringUtils.stringFilter(csinfo.strName);
		csinfo.strIntro = StringUtils.stringFilter(csinfo.strIntro);
		String strStuNo_serialized = "";
		if (csinfo.strStuNo != null) {
			for (int i = 0; i < csinfo.strStuNo.length; ++i) {
				csinfo.strStuNo[i] = StringUtils.stringFilter(csinfo.strStuNo[i]).replace("|", "");
				strStuNo_serialized += csinfo.strStuNo[i];
				if (i < csinfo.strStuNo.length - 1)
					strStuNo_serialized += "|";
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sdf.parse(csinfo.strBeginDate);
			sdf.parse(csinfo.strEndDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		try {
			DatabaseManager.queryWithoutResult(
					"UPDATE tblCourse SET (cName, tId, cSemester, cDate, cBegin, cEnd, cCred, cIntro, cStuNum, cMaxStu, cStuNo) VALUES ('"
							+ csinfo.strName + "', " + csinfo.nTeacherID + ", " + csinfo.nSemester + ", '"
							+ csinfo.strCourseDate + "', '" + csinfo.strBeginDate + "', '" + csinfo.strEndDate + "', "
							+ csinfo.fCredit + ", '" + csinfo.strIntro + "', 0, " + csinfo.nMaxStuNum + ", '"
							+ strStuNo_serialized + "') WHERE cId=" + csinfo.nCourseID);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public static int deleteCourse(int nCourseID) {
		try {
			DatabaseManager.queryWithoutResult("UPDATE tblCourse SET cDeleted=1 WHERE cId=" + nCourseID);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public static CourseInfoContainer viewCourse(int nCourseID) {
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblCourse WHERE cId=" + nCourseID);
			if (ret.size() == 0)
				return null;
			CourseInfoContainer cinfo = new CourseInfoContainer();
			cinfo.setfCredit(Double.valueOf(ret.get(0).get("cCred")).floatValue());
			cinfo.setnCourseID(Integer.valueOf(ret.get(0).get("cId")));
			cinfo.setnMaxStuNum(Integer.valueOf(ret.get(0).get("cMaxStu")));
			cinfo.setnStuNum(Integer.valueOf(ret.get(0).get("cStuNum")));
			cinfo.setnTeacherID(Integer.valueOf(ret.get(0).get("tId")));
			cinfo.setnSemester(Integer.valueOf(ret.get(0).get("cSemester")));
			cinfo.setStrBeginDate(ret.get(0).get("cBegin"));
			cinfo.setStrEndDate(ret.get(0).get("cEnd"));
			cinfo.setStrIntro(ret.get(0).get("cIntro"));
			cinfo.setStrName(ret.get(0).get("cName"));
			cinfo.setStrStuNo(ret.get(0).get("cStuNo").split("|"));
			cinfo.setStrCourseDate(ret.get(0).get("cDate"));
			cinfo.setcDeleted(ret.get(0).get("cDeleted").equals("1"));
			return cinfo;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static CourseInfoContainer[] viewCourse_tId(int tId) {
		Vector<CourseInfoContainer> rcinfo = new Vector<CourseInfoContainer>();
		try {
			
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblCourse WHERE tId=" + tId);
			if (ret.size() == 0)
				return null;
			for(int i = 0; i < ret.size();++i){
				CourseInfoContainer cinfo = new CourseInfoContainer();
				cinfo.setfCredit(Double.valueOf(ret.get(i).get("cCred")).floatValue());
				cinfo.setnCourseID(Integer.valueOf(ret.get(i).get("cId")));
				cinfo.setnMaxStuNum(Integer.valueOf(ret.get(i).get("cMaxStu")));
				cinfo.setnStuNum(Integer.valueOf(ret.get(i).get("cStuNum")));
				cinfo.setnTeacherID(Integer.valueOf(ret.get(i).get("tId")));
				cinfo.setnSemester(Integer.valueOf(ret.get(i).get("cSemester")));
				cinfo.setStrBeginDate(ret.get(i).get("cBegin"));
				cinfo.setStrEndDate(ret.get(i).get("cEnd"));
				cinfo.setStrIntro(ret.get(i).get("cIntro"));
				cinfo.setStrName(ret.get(i).get("cName"));
				cinfo.setStrStuNo(ret.get(i).get("cStuNo").split("|"));
				cinfo.setStrCourseDate(ret.get(i).get("cDate"));
				cinfo.setcDeleted(ret.get(i).get("cDeleted").equals("1"));
				rcinfo.add(cinfo);
			}
			CourseInfoContainer[] cic = new CourseInfoContainer[rcinfo.size()];
			for (int i = 0; i < cic.length; ++i)
				cic[i] = (CourseInfoContainer) rcinfo.get(i);
			return cic;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static CourseInfoContainer[] viewCourseForStudent(int nStuUserID) {
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT sID from tblStuInfo WHERE uId=" + nStuUserID);
			if (ret.size() == 0)
				return null;
			Vector<CourseInfoContainer> rcinfo = new Vector<CourseInfoContainer>();
			String strStuID = ret.get(0).get("sID");
			ret = DatabaseManager.queryWithResult("SELECT * FROM tblCourse WHERE cDeleted<>1");
			for (int i = 0; i < ret.size(); ++i) {
				String[] avaiStuNumPref = ret.get(i).get("cStuNo").split("|");
				boolean avai = false;
				/*
				 * for (int j = 0; j < avaiStuNumPref.length; ++j) if
				 * (strStuID.startsWith(avaiStuNumPref[j])) { avai = true;
				 * break; }
				 */
				avai = true;
				if (avai) {
					Vector<HashMap<String, String>> ret1 = DatabaseManager
							.queryWithResult("SELECT * FROM tblCourseSelect WHERE uId=" + nStuUserID + " AND cId="
									+ ret.get(i).get("cId"));
					if (ret1.size() > 0)
						avai = false;
				}
				if (avai) {
					CourseInfoContainer cinfo = new CourseInfoContainer();
					cinfo.setfCredit(Double.valueOf(ret.get(i).get("cCred")).floatValue());
					cinfo.setnCourseID(Integer.valueOf(ret.get(i).get("cId")));
					cinfo.setnMaxStuNum(Integer.valueOf(ret.get(i).get("cMaxStu")));
					cinfo.setnStuNum(Integer.valueOf(ret.get(i).get("cStuNum")));
					cinfo.setnTeacherID(Integer.valueOf(ret.get(i).get("tId")));
					cinfo.setnSemester(Integer.valueOf(ret.get(i).get("cSemester")));
					cinfo.setStrBeginDate(ret.get(i).get("cBegin"));
					cinfo.setStrEndDate(ret.get(i).get("cEnd"));
					cinfo.setStrIntro(ret.get(i).get("cIntro"));
					cinfo.setStrName(ret.get(i).get("cName"));
					cinfo.setStrStuNo(ret.get(i).get("cStuNo").split("|"));
					cinfo.setStrCourseDate(ret.get(i).get("cDate"));
					cinfo.setcDeleted(ret.get(i).get("cDeleted").equals("1"));
					rcinfo.add(cinfo);
				}
			}
			CourseInfoContainer[] cic = new CourseInfoContainer[rcinfo.size()];
			for (int i = 0; i < cic.length; ++i)
				cic[i] = (CourseInfoContainer) rcinfo.get(i);
			return cic;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static CourseInfoContainer[] viewAllCourses() {
		try {
			Vector<CourseInfoContainer> rcinfo = new Vector<CourseInfoContainer>();
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblCourse WHERE cDeleted<>1");
			for (int i = 0; i < ret.size(); ++i) {
				CourseInfoContainer cinfo = new CourseInfoContainer();
				cinfo.setfCredit(Double.valueOf(ret.get(i).get("cCred")).floatValue());
				cinfo.setnCourseID(Integer.valueOf(ret.get(i).get("cId")));
				cinfo.setnMaxStuNum(Integer.valueOf(ret.get(i).get("cMaxStu")));
				cinfo.setnStuNum(Integer.valueOf(ret.get(i).get("cStuNum")));
				cinfo.setnTeacherID(Integer.valueOf(ret.get(i).get("tId")));
				cinfo.setnSemester(Integer.valueOf(ret.get(i).get("cSemester")));
				cinfo.setStrBeginDate(ret.get(i).get("cBegin"));
				cinfo.setStrEndDate(ret.get(i).get("cEnd"));
				cinfo.setStrIntro(ret.get(i).get("cIntro"));
				cinfo.setStrName(ret.get(i).get("cName"));
				cinfo.setStrStuNo(ret.get(i).get("cStuNo").split("|"));
				cinfo.setStrCourseDate(ret.get(i).get("cDate"));
				rcinfo.add(cinfo);
			}
			CourseInfoContainer[] cic = new CourseInfoContainer[rcinfo.size()];
			for (int i = 0; i < cic.length; ++i)
				cic[i] = (CourseInfoContainer) rcinfo.get(i);
			return cic;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static MessageContainer handle(MessageContainer msg) {
		if (TokenHandler.checkToken(msg.strToken) == null)
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
		switch (msg.strCommand) {
		case CommandProtocol.C_ADD:
			if (!hasCourseAdminPrev(msg.strToken))
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
			if (msg.entityParameters == null || msg.entityParameters.length == 0)
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			switch (addCourse((CourseInfoContainer) msg.entityParameters[0])) {
			case -1:
			case 1:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			default:
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.C_ADD);
			}

		case CommandProtocol.C_MODIFY:
			if (!hasCourseAdminPrev(msg.strToken))
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
			if (msg.entityParameters == null || msg.entityParameters.length == 0)
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			switch (modifyCourse((CourseInfoContainer) msg.entityParameters[0])) {
			case -1:
			case 1:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			default:
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.C_MODIFY);
			}

		case CommandProtocol.C_DELETE:
			if (!hasCourseAdminPrev(msg.strToken))
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
			if (msg.strParameters == null || msg.strParameters.length == 0
					|| !StringUtils.isOnlyDigit(msg.strParameters[0]))
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			if (deleteCourse(Integer.valueOf(msg.strParameters[0])) == 0)
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.C_DELETE);
			else
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);

		case CommandProtocol.C_LOOKUP:
			// if (!hasCourseAdminPrev(msg.strToken))
			// return new MessageContainer(CommandProtocol.ERROR,
			// CommandProtocol.NO_PREV);
			if (msg.strParameters == null || msg.strParameters.length == 0
					|| !StringUtils.isOnlyDigit(msg.strParameters[0]))
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			CourseInfoContainer cinfo = viewCourse(Integer.valueOf(msg.strParameters[0]));
			if (cinfo == null)
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			else {
				MessageContainer msgGo = new MessageContainer();
				msgGo.setStrCommand(CommandProtocol.SUCCESS);
				msgGo.entityParameters = new EntityModel[] { cinfo };
				return msgGo;
			}
		case CommandProtocol.C_LOOKUPTID:
			if (msg.strParameters == null || msg.strParameters.length == 0
					|| !StringUtils.isOnlyDigit(msg.strParameters[0]))
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			CourseInfoContainer[] cinfo2 = viewCourse_tId(Integer.valueOf(msg.strParameters[0]));
			if (cinfo2 == null)
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
			else {
				MessageContainer msgGo = new MessageContainer();
				msgGo.setStrCommand(CommandProtocol.SUCCESS);
				msgGo.entityParameters = cinfo2;
				return msgGo;
			}
		case CommandProtocol.C_LOOKUP_MY:
			if (hasCourseAdminPrev(msg.strToken)) {
				CourseInfoContainer[] info = viewAllCourses();
				if (info == null)
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
				else {
					MessageContainer msgGo = new MessageContainer();
					msgGo.setStrCommand(CommandProtocol.SUCCESS);
					msgGo.entityParameters = info;
					return msgGo;
				}
			}
			CourseInfoContainer[] info = viewCourseForStudent(TokenHandler.checkToken(msg.strToken).nUserID);
			if (info == null)
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
			else {
				MessageContainer msgGo = new MessageContainer();
				msgGo.setStrCommand(CommandProtocol.SUCCESS);
				msgGo.entityParameters = info;
				return msgGo;
			}

		default:
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
		}
	}

	public static boolean hasCourseAdminPrev(String strToken) {
		return StringUtils.inArray(TokenHandler.checkToken(strToken).nGroupIDs, CommandProtocol.ROLE_FACAULTY_ADMIN);
	}
}
