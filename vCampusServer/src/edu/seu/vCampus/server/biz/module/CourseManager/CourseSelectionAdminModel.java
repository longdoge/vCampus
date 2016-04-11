package edu.seu.vCampus.server.biz.module.CourseManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.CourseInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.CourseSelectionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class CourseSelectionAdminModel {
    public static int addCourse(CourseSelectionInfoContainer csinfo) {
        CourseInfoContainer[] avaiCourses = CourseAdminHandler.viewCourseForStudent(csinfo.nStudentID); // 方便而低效的方法
        for (int i = 0; i < avaiCourses.length; ++i) {
            if (avaiCourses[i].nCourseID == csinfo.nTeacherID) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                /*
                 * try { Date bDate = sdf.parse(avaiCourses[i].strBeginDate);
                 * Date eDate = sdf.parse(avaiCourses[i].strEndDate); Date
                 * currDate = new Date(); if (bDate.after(currDate) ||
                 * eDate.before(currDate)) { return 2; } } catch (ParseException
                 * e) { e.printStackTrace(); return -1; }
                 */
                if (avaiCourses[i].nStuNum >= avaiCourses[i].nMaxStuNum)
                    return 3;
                // TODO: 检查与所选课程是否有时间冲突 : return 4;
                if (addCourse_admin(csinfo))
                    return 0;
                else
                    return -1;
            }
        }
        return 1;
    }
    
    public static boolean addCourse_admin(CourseSelectionInfoContainer csinfo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DatabaseManager.queryWithoutResult(
                    "INSERT INTO tblCourseSelect (uId, cId, csDate, csType) VALUES (" + csinfo.nStudentID + ", "
                            + csinfo.nTeacherID + ", '" + sdf.format(new Date()) + "', " + csinfo.nType + ")");
            DatabaseManager.queryWithoutResult("UPDATE tblCourse SET cStuNum=cStuNum+1 WHERE cId=" + csinfo.nTeacherID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delCourse(int nCsID) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblCourseSelect WHERE csId=" + nCsID);
            if (ret.size() == 0)
                return true;
            int nCourseID = Integer.valueOf(ret.get(0).get("cId"));
            DatabaseManager.queryWithoutResult("UPDATE tblCourse SET cStuNum=cStuNum-1 WHERE cId=" + nCourseID);
            DatabaseManager.queryWithoutResult("DELETE FROM tblCourseSelect WHERE csId=" + nCsID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int delCourseWithCheck(int nCourseID, int nCurrUId) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblCourseSelect WHERE csId=" + nCourseID);
            if (ret.size() == 0)
                return 0;
            if (ret.get(0).get("csType").equals("1"))
                return 1;
            int nStuID = Integer.valueOf(ret.get(0).get("uId"));
            if (nStuID != nCurrUId)
                return 2;
            if (delCourse(nCourseID))
                return 0;
            else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static CourseSelectionInfoContainer[] viewCourse(int nUserId) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblCourseSelect WHERE uId=" + nUserId);
            CourseSelectionInfoContainer[] csinfo_arr = new CourseSelectionInfoContainer[ret.size()];
            for (int i = 0; i < ret.size(); ++i) {
                csinfo_arr[i] = new CourseSelectionInfoContainer();
                csinfo_arr[i].nCsID = Integer.valueOf(ret.get(i).get("csId"));
                csinfo_arr[i].nStudentID = Integer.valueOf(ret.get(i).get("uId"));
                csinfo_arr[i].nTeacherID = Integer.valueOf(ret.get(i).get("cId"));
                csinfo_arr[i].nType = Integer.valueOf(ret.get(i).get("csType"));
                csinfo_arr[i].strDate = ret.get(i).get("csDate");
            }
            return csinfo_arr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static CourseSelectionInfoContainer[] viewCourseSelection(int nCourseId) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblCourseSelect WHERE cId=" + nCourseId);
            CourseSelectionInfoContainer[] csinfo_arr = new CourseSelectionInfoContainer[ret.size()];
            for (int i = 0; i < ret.size(); ++i) {
                csinfo_arr[i] = new CourseSelectionInfoContainer();
                csinfo_arr[i].nCsID = Integer.valueOf(ret.get(i).get("csId"));
                csinfo_arr[i].nStudentID = Integer.valueOf(ret.get(i).get("uId"));
                csinfo_arr[i].nTeacherID = Integer.valueOf(ret.get(i).get("cId"));
                csinfo_arr[i].nType = Integer.valueOf(ret.get(i).get("csType"));
                csinfo_arr[i].strDate = ret.get(i).get("csDate");
            }
            return csinfo_arr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MessageContainer handle(MessageContainer msg) {
        if (TokenHandler.checkToken(msg.strToken) == null)
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
        switch (msg.strCommand) {
        case CommandProtocol.CS_ADD:
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            CourseSelectionInfoContainer csinfo = new CourseSelectionInfoContainer();
            csinfo.setnStudentID(TokenHandler.checkToken(msg.strToken).nUserID);
            csinfo.setnType(0);
            csinfo.setnTeacherID(Integer.valueOf(msg.strParameters[0])); // 这里的nTeacherID其实是课程ID
            switch (addCourse(csinfo)) {
            case 0:
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CS_ADD);
            case 1:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CS_UNAVAI);
            case 3:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CS_EXCEED);
            default:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            }

        case CommandProtocol.CS_DELETE:
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            switch (delCourseWithCheck(Integer.valueOf(msg.strParameters[0]),
                    TokenHandler.checkToken(msg.strToken).nUserID)) {
            case 0:
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CS_DELETE);
            case 1:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CS_MANDOTARY);
            case 2:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            default:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            }
        
        case CommandProtocol.CS_VIEWALL:
            CourseSelectionInfoContainer[] info = viewCourse(TokenHandler.checkToken(msg.strToken).nUserID);
            if (info == null)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            else {
                MessageContainer msgGo = new MessageContainer();
                msgGo.setStrCommand(CommandProtocol.SUCCESS);
                msgGo.entityParameters = info;
                return msgGo;
            }
        
        case CommandProtocol.CSADMIN_ADD:
            if (!CourseAdminHandler.hasCourseAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.entityParameters == null || msg.entityParameters.length == 0)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            if (addCourse_admin((CourseSelectionInfoContainer) msg.entityParameters[0]))
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CSADMIN_ADD);
            else
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);

        case CommandProtocol.CSADMIN_DELETE:
            if (!CourseAdminHandler.hasCourseAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            if (delCourse(Integer.valueOf(msg.strParameters[0])))
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CSADMIN_DELETE);
            else
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
        
        case CommandProtocol.CSADMIN_VIEWALL:
            if (!CourseAdminHandler.hasCourseAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            CourseSelectionInfoContainer[] info2 = viewCourse(Integer.valueOf(msg.strParameters[0]));
            if (info2 == null)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            else {
                MessageContainer msgGo = new MessageContainer();
                msgGo.setStrCommand(CommandProtocol.SUCCESS);
                msgGo.entityParameters = info2;
                return msgGo;
            }
            
        case CommandProtocol.CSADMIN_VIEWALL_BY_CLASS:
            if (!CourseAdminHandler.hasCourseAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            CourseSelectionInfoContainer[] info3 = viewCourseSelection(Integer.valueOf(msg.strParameters[0]));
            if (info3 == null)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            else {
                MessageContainer msgGo = new MessageContainer();
                msgGo.setStrCommand(CommandProtocol.SUCCESS);
                msgGo.entityParameters = info3;
                return msgGo;
            }
            
        default:
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
        }
    }
}
