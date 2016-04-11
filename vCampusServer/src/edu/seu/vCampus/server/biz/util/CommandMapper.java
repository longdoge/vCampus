package edu.seu.vCampus.server.biz.util;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.MessageEncryptor;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.server.biz.module.CourseManager.CourseAdminHandler;
import edu.seu.vCampus.server.biz.module.CourseManager.CourseSelectionAdminModel;
import edu.seu.vCampus.server.biz.module.StudentManager.GradeManager;
import edu.seu.vCampus.server.biz.module.StudentManager.StuInfoManager;
import edu.seu.vCampus.server.biz.module.UserManager.UserActionHandler;
import edu.seu.vCampus.server.biz.module.vBank.BankActionHandler;
import edu.seu.vCampus.server.biz.module.vLibrary.BookAdminHandler;
import edu.seu.vCampus.server.biz.module.vLibrary.BookBorrowHandler;
import edu.seu.vCampus.server.biz.module.vStore.GoodHandler;
import edu.seu.vCampus.server.biz.module.vStore.OrderHandler;

public class CommandMapper implements Server.ObjectAction {
	@Override
	public Object doAction(Object rev) {
		MessageContainer messageFromClient = (MessageContainer) rev;
		messageFromClient = MessageEncryptor.DecryptMessage(messageFromClient.binaryParameters); // 解密数据
        MessageContainer messageToClient = null;
        if (messageFromClient.strCommand == null) {
            messageToClient = new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_CMD);
        } else {
            System.out.println("Client cmd = " + messageFromClient.strCommand);
            // 依据command，将请求交给对应的模块处理
            switch (messageFromClient.strCommand.toLowerCase()) {
            case CommandProtocol.CMD_LOGIN: // 用户登录
            case CommandProtocol.CMD_REG: // 用户注销
            case CommandProtocol.CMD_LOGOUT: // 用户注册
            case CommandProtocol.CMD_CHANGE_PWD: // 修改密码
                messageToClient = new UserActionHandler(messageFromClient).handle(); // 交给UserActionHandler处理
                break;
            case CommandProtocol.CMD_SCANGOODS:
            case CommandProtocol.CMD_SCANGOODSWITHUID:
            case CommandProtocol.CMD_SCANGOODSWITHGID:
            case CommandProtocol.CMD_ADDGOOD:
            case CommandProtocol.CMD_DELETEGOOD:
            case CommandProtocol.CMD_UPDATEGOOD:
            case CommandProtocol.CMD_SCANGOODSWITHNAME:
            case CommandProtocol.CMD_SCANGOODSWITHTYPE:
                messageToClient = new GoodHandler(messageFromClient).Handle();
                break;  
            case CommandProtocol.CMD_SCANORDERBWITHOUT:
            case CommandProtocol.CMD_SCANORDERMWITHUIDB:
            case CommandProtocol.CMD_SCANORDERMWITHUIDM:
            case CommandProtocol.CMD_ADDORDER:
            case CommandProtocol.CMD_DELETEORDER:
            case CommandProtocol.CMD_UPDATEORDER:
                messageToClient = new OrderHandler(messageFromClient).Handle();
                break;  
            case CommandProtocol.CMD_SCANSTUINFO:
            case CommandProtocol.CMD_SCANSTUINFOWITHUID:
            case CommandProtocol.CMD_ADDSTUINFO:
            case CommandProtocol.CMD_DELETESTUINFO:
            case CommandProtocol.CMD_UPDATESTUINFO:
            	messageToClient = new StuInfoManager(messageFromClient).Handle();
                break;  
            case CommandProtocol.CMD_SCANGRADE:
            case CommandProtocol.CMD_SCANGRADEWITHUID:
            case CommandProtocol.CMD_ADDGRADE:
            case CommandProtocol.CMD_DELETEGRADE:
            case CommandProtocol.CMD_UPDATEGRADE:
            	messageToClient = new GradeManager(messageFromClient).Handle();
                break;
            case CommandProtocol.C_ADD:
            case CommandProtocol.C_DELETE:
            case CommandProtocol.C_LOOKUP:
            case CommandProtocol.C_LOOKUPTID:
            case CommandProtocol.C_LOOKUP_MY:
            case CommandProtocol.C_MODIFY:
                messageToClient = CourseAdminHandler.handle(messageFromClient);
                break;
            case CommandProtocol.CS_ADD:
            case CommandProtocol.CS_DELETE:
            case CommandProtocol.CS_VIEWALL:
            case CommandProtocol.CSADMIN_ADD:
            case CommandProtocol.CSADMIN_DELETE:
            case CommandProtocol.CSADMIN_VIEWALL:
                messageToClient = CourseSelectionAdminModel.handle(messageFromClient);
                break;
            case CommandProtocol.B_ADD:
            case CommandProtocol.B_DELETE:
            case CommandProtocol.B_LOOKUP:
            case CommandProtocol.B_LOOKUP_MY:
            case CommandProtocol.B_MODIFY:
                messageToClient = BookAdminHandler.handle(messageFromClient);
                break;
            case CommandProtocol.BB_ADD:
            case CommandProtocol.BB_DELETE:
            case CommandProtocol.BB_VIEW:
            case CommandProtocol.BB_VIEW_BID:
            case CommandProtocol.BB_VIEW_UID:
                messageToClient = BookBorrowHandler.handle(messageFromClient);
                break;
            case CommandProtocol.CMD_QUERY:
            case CommandProtocol.CMD_TRAN:
            case CommandProtocol.CMD_VIEW:
            	messageToClient = new BankActionHandler(messageFromClient).handle();
            	break;
            default:
                messageToClient = new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
            }
        }
        if (messageToClient == null) {
        	messageToClient = new MessageContainer(); //空消息
        } else {
            System.out.println("Server responded with " + messageToClient.strCommand);
            messageToClient = new MessageContainer(MessageEncryptor.EncryptMessage(messageToClient)); //加密数据
        }
        return messageToClient;
	}
}
