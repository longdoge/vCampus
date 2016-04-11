package edu.seu.vCampus.server.biz.module.vLibrary;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.BookBorrowInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.BookInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class BookBorrowHandler {
    public static int addBook(BookBorrowInfoContainer bbinfo) {
        BookInfoContainer[] bAvai = BookAdminHandler.viewBooks(bbinfo.nUserId);
        for (int i = 0; i < bAvai.length; ++i) {
            if (bAvai[i].nBookId == bbinfo.nBookId) {
                try {
                    Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT bId FROM tblBookBorrow WHERE uId=" + bbinfo.nUserId + " AND (retDate is null)");
                    if (ret.size() > 10)
                        return 1;
                    for (int j = 0; j < ret.size(); ++j) {
                        if (Integer.valueOf(ret.get(j).get("bId")) == bbinfo.nBookId)
                            return 2;
                    }
                    ret = DatabaseManager.queryWithResult("SELECT bAmount, bNum FROM tblBook WHERE bID=" + bbinfo.nBookId);
                    if (ret.size() == 0)
                        return 3;
                    if (Integer.valueOf(ret.get(0).get("bNum")) <= 0)
                        return 4;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    DatabaseManager.queryWithoutResult("INSERT INTO tblBookBorrow (uId, bId, bDate) VALUES (" + bbinfo.nUserId + ", " + bbinfo.nBookId + ", '" + sdf.format(new Date()) + "')");
                    DatabaseManager.queryWithoutResult("UPDATE tblBook SET bNum=bNum-1 WHERE bID=" + bbinfo.nBookId);
                    return 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }
        return 1;
    }
    
    public static boolean returnBook(int nBorrowID) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblBookBorrow WHERE borId=" + nBorrowID + "AND (retDate is null)");
            if (ret.size() == 0)
                return true;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int nBookID = Integer.valueOf(ret.get(0).get("bId"));
            DatabaseManager.queryWithoutResult("UPDATE tblBook SET bNum=bNum+1 WHERE bId=" + nBookID);
            DatabaseManager.queryWithoutResult("UPDATE tblBookBorrow SET retDate='" + sdf.format(new Date()) + "' WHERE borId=" + nBorrowID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static int returnBookWithCheck(int nBorrowID, int nCurrUId) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblBookBorrow WHERE borId=" + nBorrowID);
            if (ret.size() == 0)
                return 0;
            int nStuID = Integer.valueOf(ret.get(0).get("uId"));
            if (nStuID != nCurrUId)
                return 1;
            if (returnBook(nBorrowID))
                return 0;
            else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public static BookBorrowInfoContainer viewBorrow(int nBorrowID) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblBookBorrow WHERE borId=" + nBorrowID);
            BookBorrowInfoContainer[] bbinfo_arr = new BookBorrowInfoContainer[ret.size()];
            for (int i = 0; i < ret.size(); ++i) {
                bbinfo_arr[i] = new BookBorrowInfoContainer();
                bbinfo_arr[i].nBookId = Integer.valueOf(ret.get(i).get("bId"));
                bbinfo_arr[i].nBorId = Integer.valueOf(ret.get(i).get("borId"));
                bbinfo_arr[i].nUserId = Integer.valueOf(ret.get(i).get("uId"));
                bbinfo_arr[i].strBorrowDate = ret.get(i).get("bDate");
                bbinfo_arr[i].strReturnDate = ret.get(i).get("retDate");
            }
            if (ret.size() > 0)
                return bbinfo_arr[0];
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static BookBorrowInfoContainer viewBorrowWithCheck(int nBorrowID, int nCurrUId) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblBookBorrow WHERE borId=" + nBorrowID);
            if (ret.size() == 0)
                return null;
            int nStuID = Integer.valueOf(ret.get(0).get("uId"));
            if (nStuID != nCurrUId)
                return null;
            return viewBorrow(nBorrowID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BookBorrowInfoContainer[] viewBorrowByUser(int nUserId) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblBookBorrow WHERE uId=" + nUserId);
            BookBorrowInfoContainer[] bbinfo_arr = new BookBorrowInfoContainer[ret.size()];
            for (int i = 0; i < ret.size(); ++i) {
                bbinfo_arr[i] = new BookBorrowInfoContainer();
                bbinfo_arr[i].nBookId = Integer.valueOf(ret.get(i).get("bId"));
                bbinfo_arr[i].nBorId = Integer.valueOf(ret.get(i).get("borId"));
                bbinfo_arr[i].nUserId = Integer.valueOf(ret.get(i).get("uId"));
                bbinfo_arr[i].strBorrowDate = ret.get(i).get("bDate");
                bbinfo_arr[i].strReturnDate = ret.get(i).get("retDate");
            }
            return bbinfo_arr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BookBorrowInfoContainer[] viewBorrowByBook(int nBookId) {
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblBookBorrow WHERE bId=" + nBookId);
            BookBorrowInfoContainer[] bbinfo_arr = new BookBorrowInfoContainer[ret.size()];
            for (int i = 0; i < ret.size(); ++i) {
                bbinfo_arr[i] = new BookBorrowInfoContainer();
                bbinfo_arr[i].nBookId = Integer.valueOf(ret.get(i).get("bId"));
                bbinfo_arr[i].nBorId = Integer.valueOf(ret.get(i).get("borId"));
                bbinfo_arr[i].nUserId = Integer.valueOf(ret.get(i).get("uId"));
                bbinfo_arr[i].strBorrowDate = ret.get(i).get("bDate");
                bbinfo_arr[i].strReturnDate = ret.get(i).get("retDate");
            }
            return bbinfo_arr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static MessageContainer handle(MessageContainer msg) {
        if (TokenHandler.checkToken(msg.strToken) == null)
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
        switch (msg.strCommand) {
        case CommandProtocol.BB_ADD:
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            BookBorrowInfoContainer bbinfo = new BookBorrowInfoContainer();
            bbinfo.setnUserId(TokenHandler.checkToken(msg.strToken).nUserID);
            bbinfo.setnBookId(Integer.valueOf(msg.strParameters[0]));
            switch (addBook(bbinfo)) {
            case 0:
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.BB_ADD);
            case 1:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.BB_EXCEED);
            case 4:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.BB_UNAVAI);
            default:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            }

        case CommandProtocol.BB_DELETE:
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            switch (returnBookWithCheck(Integer.valueOf(msg.strParameters[0]),
                    TokenHandler.checkToken(msg.strToken).nUserID)) {
            case 0:
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.BB_DELETE);
            case 1:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            default:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            }
        
        case CommandProtocol.BB_VIEW_UID:
            int nUserID2Go = TokenHandler.checkToken(msg.strToken).nUserID;
            if (BookAdminHandler.hasLibraryAdminPrev(msg.strToken)) {
                if (msg.strParameters == null || msg.strParameters.length == 0
                        || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
                nUserID2Go = Integer.valueOf(msg.strParameters[0]);
            }
            BookBorrowInfoContainer[] info = viewBorrowByUser(nUserID2Go);
            if (info == null)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            else {
                MessageContainer msgGo = new MessageContainer();
                msgGo.setStrCommand(CommandProtocol.SUCCESS);
                msgGo.entityParameters = info;
                return msgGo;
            }
        
        case CommandProtocol.BB_VIEW_BID:
            if (!BookAdminHandler.hasLibraryAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            BookBorrowInfoContainer[] info1 = viewBorrowByBook(Integer.valueOf(msg.strParameters[0]));
            if (info1 == null)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            else {
                MessageContainer msgGo = new MessageContainer();
                msgGo.setStrCommand(CommandProtocol.SUCCESS);
                msgGo.entityParameters = info1;
                return msgGo;
            }
        
        case CommandProtocol.BB_VIEW:
            if (msg.strParameters == null || msg.strParameters.length == 0
            || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            BookBorrowInfoContainer binfo = viewBorrowWithCheck(Integer.valueOf(msg.strParameters[0]),
                    TokenHandler.checkToken(msg.strToken).nUserID);
            if (binfo == null)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            else {
                MessageContainer msgGo = new MessageContainer();
                msgGo.setStrCommand(CommandProtocol.SUCCESS);
                msgGo.entityParameters = new EntityModel[] { binfo };
                return msgGo;
            }
            
        default:
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
        }
    }
}
