package edu.seu.vCampus.server.biz.module.vLibrary;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.BookInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class BookAdminHandler {
    public static int addBook(BookInfoContainer binfo) {
        binfo.strAuthor = StringUtils.stringFilter(binfo.strAuthor);
        binfo.strISBN = StringUtils.stringFilter(binfo.strISBN);
        binfo.strName = StringUtils.stringFilter(binfo.strName);
        binfo.strPress = StringUtils.stringFilter(binfo.strPress);
        try {
            DatabaseManager.queryWithoutResult("INSERT INTO tblBook (bName, bAuthor, bPress, bAmount, bNum, bISBN) VALUES ('" + binfo.strName + "', '" + binfo.strAuthor + "', '" + binfo.strPress + "', " + binfo.nAmount + ", " + binfo.nAmount + ", '" + binfo.strISBN + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
    
    public static int modifyBook(BookInfoContainer binfo) {
        binfo.strAuthor = StringUtils.stringFilter(binfo.strAuthor);
        binfo.strISBN = StringUtils.stringFilter(binfo.strISBN);
        binfo.strName = StringUtils.stringFilter(binfo.strName);
        binfo.strPress = StringUtils.stringFilter(binfo.strPress);
        try {
            DatabaseManager.queryWithoutResult("UPDATE tblBook SET (bName, bAuthor, bPress, bAmount, bNum, bISBN) VALUES ('" + binfo.strName + "', '" + binfo.strAuthor + "', '" + binfo.strPress + "', " + binfo.nAmount + ", " + binfo.nAmount + ", '" + binfo.strISBN + "') WHERE bID=" + binfo.nBookId);
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
    
    public static int delBook(int nBookID) {
        try {
            DatabaseManager.queryWithoutResult("DELETE FROM tblBook WHERE bID=" + nBookID);
            DatabaseManager.queryWithoutResult("DELETE FROM tblBookBorrow WHERE bId=" + nBookID);
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
    
    public static BookInfoContainer viewBook(int nBookID) {
        Vector<HashMap<String, String>> ret;
        try {
            ret = DatabaseManager.queryWithResult("SELECT * FROM tblBook WHERE bID=" + nBookID);
            if (ret.size() == 0)
                return null;
            BookInfoContainer cinfo = new BookInfoContainer();
            cinfo.setnAmount(Integer.valueOf(ret.get(0).get("bAmount")));
            cinfo.setnBookId(Integer.valueOf(ret.get(0).get("bID")));
            cinfo.setnNum(Integer.valueOf(ret.get(0).get("bNum")));
            cinfo.setStrAuthor(ret.get(0).get("bAuthor"));
            cinfo.setStrName(ret.get(0).get("bName"));
            cinfo.setStrPress(ret.get(0).get("bPress"));
            cinfo.setStrISBN(ret.get(0).get("bISBN"));
            return cinfo;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BookInfoContainer[] viewBooks() {
        Vector<HashMap<String, String>> ret;
        try {
            ret = DatabaseManager.queryWithResult("SELECT * FROM tblBook");
            // if (ret.size() == 0)
                // return null;
            BookInfoContainer[] binfo_arr = new BookInfoContainer[ret.size()];
            for (int i = 0; i < ret.size(); ++i) {
                binfo_arr[i] = new BookInfoContainer();
                binfo_arr[i].setnAmount(Integer.valueOf(ret.get(i).get("bAmount")));
                binfo_arr[i].setnBookId(Integer.valueOf(ret.get(i).get("bID")));
                binfo_arr[i].setnNum(Integer.valueOf(ret.get(i).get("bNum")));
                binfo_arr[i].setStrAuthor(ret.get(i).get("bAuthor"));
                binfo_arr[i].setStrName(ret.get(i).get("bName"));
                binfo_arr[i].setStrPress(ret.get(i).get("bPress"));
                binfo_arr[i].setStrISBN(ret.get(i).get("bISBN"));
            }
            return binfo_arr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BookInfoContainer[] viewBooks(int nUserID) {
        Vector<BookInfoContainer> books = new Vector<BookInfoContainer>();
        BookInfoContainer[] all_books = viewBooks();
        for (int i = 0; i < all_books.length; ++i) {
            try {
                Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT borId FROM tblBookBorrow WHERE uId=" + nUserID + " AND bId=" + all_books[i].nBookId + " AND (retDate is null)");
                if (ret.size() == 0) {
                    books.add(all_books[i]);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        BookInfoContainer[] selected_books = new BookInfoContainer[books.size()];
        books.toArray(selected_books);
        return selected_books;
    }
    
    public static boolean hasLibraryAdminPrev(String strToken) {
        return StringUtils.inArray(TokenHandler.checkToken(strToken).nGroupIDs, CommandProtocol.ROLE_LIBRARIAN);
    }
    
    public static MessageContainer handle(MessageContainer msg) {
        if (TokenHandler.checkToken(msg.strToken) == null)
            return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
        switch (msg.strCommand) {
        case CommandProtocol.B_ADD:
            if (!hasLibraryAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.entityParameters == null || msg.entityParameters.length == 0)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            switch (addBook((BookInfoContainer) msg.entityParameters[0])) {
            case -1:
            case 1:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            default:
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.B_ADD);
            }

        case CommandProtocol.B_MODIFY:
            if (!hasLibraryAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.entityParameters == null || msg.entityParameters.length == 0)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            switch (modifyBook((BookInfoContainer) msg.entityParameters[0])) {
            case -1:
            case 1:
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            default:
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.B_MODIFY);
            }

        case CommandProtocol.B_DELETE:
            if (!hasLibraryAdminPrev(msg.strToken))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            if (delBook(Integer.valueOf(msg.strParameters[0])) == 0)
                return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.B_DELETE);
            else
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);

        case CommandProtocol.B_LOOKUP:
            // if (!hasLibraryAdminPrev(msg.strToken))
                // return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
            if (msg.strParameters == null || msg.strParameters.length == 0
                    || !StringUtils.isOnlyDigit(msg.strParameters[0]))
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            BookInfoContainer cinfo = viewBook(Integer.valueOf(msg.strParameters[0]));
            if (cinfo == null)
                return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FORMAT_ERROR);
            else {
                MessageContainer msgGo = new MessageContainer();
                msgGo.setStrCommand(CommandProtocol.SUCCESS);
                msgGo.entityParameters = new EntityModel[] { cinfo };
                return msgGo;
            }
        
        case CommandProtocol.B_LOOKUP_MY:
            if (hasLibraryAdminPrev(msg.strToken)) {
                BookInfoContainer[] info = viewBooks();
                if (info == null)
                    return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
                else {
                    MessageContainer msgGo = new MessageContainer();
                    msgGo.setStrCommand(CommandProtocol.SUCCESS);
                    msgGo.entityParameters = info;
                    return msgGo;
                }
            }
            BookInfoContainer[] info = viewBooks(TokenHandler.checkToken(msg.strToken).nUserID);
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
}
