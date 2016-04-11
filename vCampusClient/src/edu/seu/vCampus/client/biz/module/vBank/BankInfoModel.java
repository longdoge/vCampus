package edu.seu.vCampus.client.biz.module.vBank;

import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.TransactionInfoContainer;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class BankInfoModel {
	/**
	 * ʹ����������ID��ѯ����������Ϣ
	 * 
	 * @param ����ID
	 * 
	 * @return �ɹ������û������Ϣ��ʧ�ܷ��ش�����Ϣ
	 */
	public static MessageContainer Query(int nUserID) { // nTransaction��ʾ�û�ID
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
			/*if (ret.getStrParameters()[0].equals(CommandProtocol.SUCCESS)) {// ʧ����Ϣ����strParams[0],�ɹ���������strParams[1]����
				MessageContainer rett = new MessageContainer();
				rett.setStrCommand(CommandProtocol.SUCCESS);
				rett.setStrParameters(ret.strParameters);
				return rett;
			} else {
				switch (ret.getStrParameters()[0]) {
				case CommandProtocol.NONEXIST_TRANID:
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);// �����ڸý���ID
				case CommandProtocol.ERROR:
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ERROR);// �����֤����
				default:
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);// δ֪����
				}
			}*/
		}

	}

	/**
	 * ʹ������������Ϣ����ת�ˣ���������Ϣ
	 * 
	 * @param
	 * 
	 * @return �ɹ������û������Ϣ��ʧ�ܷ��ش�����Ϣ
	 */
	public static MessageContainer Tran(TransactionInfoContainer tran) { // nTransactionID������ID
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
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);// �����˺Ų�����
			case CommandProtocol.NONEXIST_OUTID:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_OUTID);// �����˺źŲ�����
			case CommandProtocol.ARITH_ERROR:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ARITH_ERROR);// ת�˺���һ���˻����С��0
			case CommandProtocol.ERROR:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ERROR);// �����֤����
			default:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);// δ֪������unknown_cmd���
			}
		}*/
	}

	public static MessageContainer View(int nTransaction) { // nTransaction�������ʾ����ѯID
		MessageContainer vie = new MessageContainer();
		vie.setStrCommand(CommandProtocol.CMD_VIEW);
		vie.strToken = UserLoginInfoManager.strToken;
		String[] tem = new String[] { String.valueOf(nTransaction) };
		vie.setStrParameters(tem);
		MessageContainer ret = RequestHandler.sendRequest(vie);
		if (ret == null) {
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);// ����ʧ��
		}
		return ret;
		/*if (ret.getStrParameters()[0].equals(CommandProtocol.SUCCESS)) {
			return ret;
		} else {
			switch (ret.strParameters[0]) {
			case CommandProtocol.NONEXIST_TRANID:// ��ѯ��¼��ID������
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.NONEXIST_TRANID);
			case CommandProtocol.ERROR:
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ERROR);// �����֤����
			default:
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.UNKNOWN_CMD);// δ֪����
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
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.FAIL_CONN);// ����ʧ��
		}
		return ret;
	}
}
