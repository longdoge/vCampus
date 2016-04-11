package edu.seu.vCampus.client.biz.module.vBank;

import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.TransactionInfoContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class BankInfoModel {
	/**
	 * 使用所给交易ID查询余额，并返回信息
	 * 
	 * @param 交易ID
	 * 
	 * @return 成功返回用户余额信息，失败返回错误信息
	 */
	public static MessageContainer Query(int nUserID) { // nTransaction表示用户ID
		String[] strParams = new String[] { String.valueOf(nUserID) };
		MessageContainer qu = new MessageContainer();
		qu.setStrParameters(strParams);
		qu.setStrCommand(CommandProtocol.CMD_QUERY);
		qu.strToken = UserLoginInfoManager.strToken;
		MessageContainer ret = RequestHandler.sendRequest(qu);
		if (ret == null) {
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);
		} else {
			return ret;
			/*if (ret.getStrParameters()[0].equals(CommandProtocol.SUCCESS)) {// 失败信息放在strParams[0],成功后余额放在strParams[1]返回
				MessageContainer rett = new MessageContainer();
				rett.setStrCommand(CommandProtocol.SUCCESS);
				rett.setStrParameters(ret.strParameters);
				return rett;
			} else {
				switch (ret.getStrParameters()[0]) {
				case CommandProtocol.NONEXIST_TRANID:
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);// 不存在该交易ID
				case CommandProtocol.ERROR:
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ERROR);// 身份验证错误
				default:
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);// 未知错误
				}
			}*/
		}

	}

	/**
	 * 使用所给交易信息进行转账，并返回信息
	 * 
	 * @param
	 * 
	 * @return 成功返回用户余额信息，失败返回错误信息
	 */
	public static MessageContainer Tran(TransactionInfoContainer tran) { // nTransactionID代表传出ID
		MessageContainer tranout = new MessageContainer();
		tranout.setStrCommand(CommandProtocol.CMD_TRAN);
		/*String[] strParams = new String[] { String.valueOf(tran.nTransactionID), String.valueOf(tran.lOutID),
				String.valueOf(tran.fAmount) };*/
		String[] strParams = new String[] { String.valueOf(tran.fAmount), tran.strName, tran.strMemo };
		tranout.strToken = UserLoginInfoManager.strToken;
		tranout.setStrParameters(strParams);
		MessageContainer ret = RequestHandler.sendRequest(tranout);
		if (ret == null) {
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);
		}
		return ret;
		/*if (ret.getStrCommand().equals(CommandProtocol.SUCCESS)) {
			return ret;
		} else {
			switch (ret.getStrParameters()[0]) {
			case CommandProtocol.NONEXIST_TRANID:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);// 传入账号不存在
			case CommandProtocol.NONEXIST_OUTID:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_OUTID);// 传出账号号不存在
			case CommandProtocol.ARITH_ERROR:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ARITH_ERROR);// 转账后有一方账户余额小于0
			case CommandProtocol.ERROR:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ERROR);// 身份验证错误
			default:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);// 未知错误，用unknown_cmd替代
			}
		}*/
	}

	public static MessageContainer View(int nTransaction) { // nTransaction在这里表示被查询ID
		MessageContainer vie = new MessageContainer();
		vie.setStrCommand(CommandProtocol.CMD_VIEW);
		vie.strToken = UserLoginInfoManager.strToken;
		String[] tem = new String[] { String.valueOf(nTransaction) };
		vie.setStrParameters(tem);
		MessageContainer ret = RequestHandler.sendRequest(vie);
		if (ret == null) {
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);// 连接失败
		}
		return ret;
		/*if (ret.getStrParameters()[0].equals(CommandProtocol.SUCCESS)) {
			return ret;
		} else {
			switch (ret.strParameters[0]) {
			case CommandProtocol.NONEXIST_TRANID:// 查询记录的ID不存在
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.NONEXIST_TRANID);
			case CommandProtocol.ERROR:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ERROR);// 身份验证错误
			default:
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.UNKNOWN_CMD);// 未知错误
			}
		}*/
	}
	
	public static MessageContainer Charge(int uid, float amount) {
		MessageContainer vie = new MessageContainer();
		vie.setStrCommand(CommandProtocol.CMD_CHARGE);
		vie.strToken = UserLoginInfoManager.strToken;
		String[] tem = new String[] { String.valueOf(uid), String.valueOf(amount) };
		vie.setStrParameters(tem);
		MessageContainer ret = RequestHandler.sendRequest(vie);
		if (ret == null) {
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);// 连接失败
		}
		return ret;
	}
}
