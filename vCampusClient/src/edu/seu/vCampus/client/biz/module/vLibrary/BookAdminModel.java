package edu.seu.vCampus.client.biz.module.vLibrary;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.BookInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class BookAdminModel {
    public static MessageContainer addBook(BookInfoContainer binfo) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.B_ADD;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.entityParameters = new EntityModel[] { binfo };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        return retmsg;
    }

    public static MessageContainer modifyBook(BookInfoContainer binfo) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.B_MODIFY;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.entityParameters = new EntityModel[] { binfo };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        return retmsg;
    }

    public static MessageContainer delBook(Integer nBookID) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.B_DELETE;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.strParameters = new String[] { String.valueOf(nBookID) };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        return retmsg;
    }
    
    public static BookInfoContainer viewBook(Integer nBookID) {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.B_LOOKUP;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        msgToServer.strParameters = new String[] { String.valueOf(nBookID) };
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        if (retmsg == null || retmsg.entityParameters == null || retmsg.entityParameters.length == 0)
            return null;
        return (BookInfoContainer) retmsg.entityParameters[0];
    }
    
    public static BookInfoContainer[] viewAvaiBookForMe() {
        MessageContainer msgToServer = new MessageContainer();
        msgToServer.strCommand = CommandProtocol.B_LOOKUP_MY;
        msgToServer.strToken = UserLoginInfoManager.strToken;
        MessageContainer retmsg = RequestHandler.sendRequest(msgToServer);
        if (retmsg == null || retmsg.entityParameters == null)
            return null;
        BookInfoContainer[] c = new BookInfoContainer[retmsg.entityParameters.length];
        for (int i = 0; i < c.length; ++i)
            c[i] = (BookInfoContainer) retmsg.entityParameters[i];
        return c;
    }
}
