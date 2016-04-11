package edu.seu.vCampus.server.biz.module.vBank;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.SharedComponents.vo.TransactionInfoContainer;
import edu.seu.vCampus.server.biz.module.UserManager.UserInfoHandler;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;


public class BankActionHandler {
	private String strCommand = null, nlInID = null, nlOutID = null, fAmount = null, strToken = null, strUName = null, strMemo = null;

	public BankActionHandler(MessageContainer msg) {
		strCommand = msg.strCommand;
		strToken = msg.strToken;
		nlInID = msg.strParameters[0];// 若调用查询余额和交易记录功能，则nlInID作为用户
										// ID，若进行转账功能，则nlInID作为进账的用户ID
		if (msg.strParameters.length == 2) {
			nlOutID = msg.strParameters[0];// 支出账户的ID
			fAmount = msg.strParameters[1];// 操作金额
		}
		if (msg.strParameters.length > 2) {
			// nlOutID = msg.strParameters[1];// 支出账户的ID
			// fAmount = msg.strParameters[2];// 操作金额
			fAmount = msg.strParameters[0];
			strUName = msg.strParameters[1];
			strMemo = msg.strParameters[2];
		}
	}

	/**
	 * 判断用户身份。将用户请求（查询余额、转账、查询交易记录）分发给不同的方法
	 * 
	 * @return 给客户端的消息
	 */
	public MessageContainer handle() {
		if (TokenHandler.checkToken(strToken) == null) {
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
		}
		switch (strCommand) {
		case CommandProtocol.CMD_QUERY:
			return Query(Integer.valueOf(nlInID));// 查询余额
		case CommandProtocol.CMD_TRAN:
			TransactionInfoContainer transaction = new TransactionInfoContainer();
			// transaction.setlInID(Integer.valueOf(nlInID));
			// transaction.setlOutID(Integer.valueOf(nlOutID));
			transaction.setfAmount(Float.valueOf(fAmount));
			transaction.setStrMemo(strMemo);
			int nUserId = UserInfoHandler.getId(strUName);
			if (nUserId <= 0) {
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);
			}
			transaction.setlInID(nUserId);
			transaction.setlOutID(TokenHandler.checkToken(strToken).nUserID);
			transaction.setlOperation(1);
			return Tran(transaction); // 转账功能
		case CommandProtocol.CMD_VIEW:
			return View(Integer.valueOf(nlInID));// 查询交易记录功能
		case CommandProtocol.CMD_CHARGE:
			if (!StringUtils.inArray(TokenHandler.checkToken(strToken).nGroupIDs, CommandProtocol.ROLE_FINANCIAL_ADMIN)) // 权限检查
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NO_PREV);
			switch (Charge(Integer.valueOf(nlOutID), Float.valueOf(fAmount))) {
			case 0:
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_CHARGE);
			case 1:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);
			case 2:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ARITH_ERROR);
			default:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.CMD_CHARGE);
			}
		}
		return null;
	}

	/**
	 * 查询余额
	 * 
	 * @return 给客户端的消息
	 */
	public static MessageContainer Query(int nUserId) {
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager// 检查
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + nUserId);
			if (ret.size() > 0) {
				MessageContainer rett = new MessageContainer();
				rett.setStrCommand(CommandProtocol.SUCCESS);
				String[] tem = new String[] { ret.get(0).get("uBalance") };
				rett.setStrParameters(tem);
				return rett;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 进行转账操作（用户与用户之间）
	 * 
	 * @return 给客户端的消息
	 */
	public static MessageContainer Tran(TransactionInfoContainer trans) {
		try {
			Vector<HashMap<String, String>> ret1 = DatabaseManager// 在tblUser中查询进账ID是否存在
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + Integer.valueOf((int) trans.lInID));
			Vector<HashMap<String, String>> ret2 = DatabaseManager// 在tblUser中查询支出ID是否存在
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + Integer.valueOf((int) trans.lOutID));
			if (ret1.size() == 0) {
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);
			}
			if (ret2.size() == 0) {
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_OUTID);
			}
			if (ret1.size() > 0 && ret2.size() > 0) {// 若账号都存在，则取得二者的余额
				float balanceOfout = Float.valueOf(ret2.get(0).get("uBalance"));
				float balanceOfin = Float.valueOf(ret1.get(0).get("uBalance"));
				balanceOfout = balanceOfout - trans.fAmount;// 计算支出账户现余额
				balanceOfin = balanceOfin + trans.fAmount;// 计算入账账户现余额
				if (balanceOfout < 0 || balanceOfin < 0) {
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ARITH_ERROR);// 转账后余额小于0
				}
				DatabaseManager.queryWithoutResult(
						"INSERT INTO tblTransaction (uId_o,uId_i,tAmount,tBalanceOut,tBalanceIn,tMemo,tOpr) VALUES(" + trans.lOutID
								+ "," + trans.lInID + "," + trans.fAmount + "," + balanceOfout + "," + balanceOfin + ",'" + trans.strMemo + "'," + trans.lOperation
								+ ")");
				// 把交易数据插入表中//tblTransaction表做了修改,删除tBalance字段，加了单精度型名称为tBalaceOut和tBalanceIn的字段
				DatabaseManager.queryWithoutResult("UPDATE tblUser SET uBalance=" + balanceOfout + " WHERE uId="
						+ trans.lOutID);// 更新tblUser中的用户余额
				DatabaseManager.queryWithoutResult("UPDATE tblUser SET uBalance=" + balanceOfin + " WHERE uId="
						+ trans.lInID);// 更新tblUser中的用户余额
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_TRAN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 进行查询交易记录操作
	 * 
	 * @return 给客户端的消息
	 */
	public MessageContainer View(int nID) {
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager// 查找用户ID是否存在
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + nID);
			if (ret.size() == 0) {
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);
			}
			Vector<HashMap<String, String>> ret1 = DatabaseManager.queryWithResult("SELECT * FROM tblTransaction WHERE uId_o=" + nID); // 查找用户账户作为输出账户的交易记录
			Vector<HashMap<String, String>> ret2 = DatabaseManager.queryWithResult("SELECT * FROM tblTransaction WHERE uId_i=" + nID); // 查找用户账户作为进账账户的交易记录
			if (ret1.size() == 0 && ret2.size() == 0) {// 若无记录，返回无记录信息
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.NODATA);
			}
			TransactionInfoContainer[] tran_array = new TransactionInfoContainer[ret1.size() + ret2.size()];
			MessageContainer retmsg = new MessageContainer();
			for (int i = 0; i < ret1.size(); ++i) {// 将记录存放在数组中
				tran_array[i] = new TransactionInfoContainer();
				tran_array[i].fAmount = Float.valueOf(ret1.get(i).get("tAmount"));
				tran_array[i].lInID = Integer.valueOf(ret1.get(i).get("uId_o"));
				tran_array[i].lOutID = Integer.valueOf(ret1.get(i).get("uId_i"));
				tran_array[i].strMemo = "支出：" + ret1.get(i).get("tMemo");
				// tran_array[i].strMemo = "支付";// 将作为输出账户的交易记录的备注改为支出
				tran_array[i].fBalanceout = Float.valueOf(ret1.get(i).get("tBalanceOut"));
				tran_array[i].fBalancein = Float.valueOf(ret1.get(i).get("tBalanceIn"));
				tran_array[i].strName = UserInfoHandler.getName(tran_array[i].lOutID);
				tran_array[i].lOperation = Integer.valueOf(ret1.get(i).get("tOpr"));
				tran_array[i].strDate = ret1.get(i).get("tDate");
			}
			for (int i = 0; i < ret2.size(); ++i) {
				tran_array[i + ret1.size()] = new TransactionInfoContainer();
				tran_array[i + ret1.size()].fAmount = Float.valueOf(ret2.get(i).get("tAmount"));
				tran_array[i + ret1.size()].lInID = Integer.valueOf(ret2.get(i).get("uId_o"));
				tran_array[i + ret1.size()].lOutID = Integer.valueOf(ret2.get(i).get("uId_i"));
				tran_array[i + ret1.size()].strMemo = "收入：" + ret2.get(i).get("tMemo");
				// tran_array[i + ret1.size()].strMemo = "进账";// 将作为收入账户的交易记录的被指改为进账
				tran_array[i + ret1.size()].fBalanceout = Float.valueOf(ret2.get(i).get("tBalanceOut"));
				tran_array[i + ret1.size()].fBalancein = Float.valueOf(ret2.get(i).get("tBalanceIn"));
				tran_array[i + ret1.size()].strName = UserInfoHandler.getName(tran_array[i + ret1.size()].lInID);
				tran_array[i + ret1.size()].lOperation = Integer.valueOf(ret2.get(i).get("tOpr"));
				tran_array[i + ret1.size()].strDate = ret2.get(i).get("tDate");
			}
			EntityModel[] ent = new EntityModel[tran_array.length];
			for (int i = 0; i < tran_array.length; ++i)
				ent[i] = tran_array[i];
			retmsg.setStrCommand(CommandProtocol.SUCCESS);
			retmsg.setEntityParameters(ent);// 将记录的数组存入返回对象retmsg的Entity数组中
			return retmsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int Charge(int uid, float amount) {
		Vector<HashMap<String, String>> ret;
		try {
			ret = DatabaseManager.queryWithResult("SELECT * FROM tblUser WHERE uId=" + uid);
			if (ret.size() == 0) {
				return 1;
			}
			if (amount <= 0)
				return 2;
			DatabaseManager.queryWithoutResult(
					"INSERT INTO tblTransaction (uId_o,uId_i,tAmount,tBalanceOut,tBalanceIn,tMemo,tOpr) VALUES(" + TokenHandler.checkToken(strToken).nUserID
							+ "," + uid + "," + amount + "," + 0 + "," + (Float.valueOf(ret.get(0).get("uBalance")) + amount) + ",'人工充值',2)"); // 把交易数据插入表中
			DatabaseManager.queryWithoutResult("UPDATE tblUser SET uBalance=uBalance+" + amount + " WHERE uId=" + uid); // 更新tblUser中的用户余额
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
}