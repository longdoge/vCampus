package edu.seu.vCampus.client.biz.module.vLibrary;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.BookBorrowInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class BookBorrowModel {
    public static MessageContainer addBook(Integer nBookID) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.BB_ADD;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.strParameters = new String[] { String.valueOf(nBookID) };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        return retmsg;
    }
    
    public static MessageContainer returnBook(Integer nBorrowID) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.BB_DELETE;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.strParameters = new String[] { String.valueOf(nBorrowID) };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        return retmsg;
    }
    
    public static BookBorrowInfoContainer viewBorrow(int nBorrowId) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.BB_VIEW;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.strParameters = new String[] { String.valueOf(nBorrowId) };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        if (retmsg == null || retmsg.entityParameters == null || retmsg.entityParameters.length == 0)
            return null;
        return (BookBorrowInfoContainer) retmsg.entityParameters[0];
    }
    
    public static BookBorrowInfoContainer[] viewBorrowByUser(int nUserId) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.BB_VIEW_UID;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.strParameters = new String[] { String.valueOf(nUserId) };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        if (retmsg == null || retmsg.entityParameters == null)
            return null;
        BookBorrowInfoContainer[] csel = new BookBorrowInfoContainer[retmsg.entityParameters.length];
        for (int i = 0; i < csel.length; ++i)
            csel[i] = (BookBorrowInfoContainer) retmsg.entityParameters[i];
        return csel;
    }
    
    public static BookBorrowInfoContainer[] viewBorrowByBook(int nBookId) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.BB_VIEW_BID;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.strParameters = new String[] { String.valueOf(nBookId) };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        if (retmsg == null || retmsg.entityParameters == null)
            return null;
        BookBorrowInfoContainer[] csel = new BookBorrowInfoContainer[retmsg.entityParameters.length];
        for (int i = 0; i < csel.length; ++i)
            csel[i] = (BookBorrowInfoContainer) retmsg.entityParameters[i];
        return csel;
    }
}
