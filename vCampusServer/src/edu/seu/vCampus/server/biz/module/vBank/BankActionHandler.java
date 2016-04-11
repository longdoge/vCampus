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
		nlInID = msg.strParameters[0];// �����ò�ѯ���ͽ��׼�¼���ܣ���nlInID��Ϊ�û�
										// ID��������ת�˹��ܣ���nlInID��Ϊ���˵��û�ID
		if (msg.strParameters.length == 2) {
			nlOutID = msg.strParameters[0];// ֧���˻���ID
			fAmount = msg.strParameters[1];// �������
		}
		if (msg.strParameters.length > 2) {
			// nlOutID = msg.strParameters[1];// ֧���˻���ID
			// fAmount = msg.strParameters[2];// �������
			fAmount = msg.strParameters[0];
			strUName = msg.strParameters[1];
			strMemo = msg.strParameters[2];
		}
	}

	/**
	 * �ж��û���ݡ����û����󣨲�ѯ��ת�ˡ���ѯ���׼�¼���ַ�����ͬ�ķ���
	 * 
	 * @return ���ͻ��˵���Ϣ
	 */
	public MessageContainer handle() {
		if (TokenHandler.checkToken(strToken) == null) {
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
		}
		switch (strCommand) {
		case CommandProtocol.CMD_QUERY:
			return Query(Integer.valueOf(nlInID));// ��ѯ���
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
			return Tran(transaction); // ת�˹���
		case CommandProtocol.CMD_VIEW:
			return View(Integer.valueOf(nlInID));// ��ѯ���׼�¼����
		case CommandProtocol.CMD_CHARGE:
			if (!StringUtils.inArray(TokenHandler.checkToken(strToken).nGroupIDs, CommandProtocol.ROLE_FINANCIAL_ADMIN)) // Ȩ�޼��
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
	 * ��ѯ���
	 * 
	 * @return ���ͻ��˵���Ϣ
	 */
	public static MessageContainer Query(int nUserId) {
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager// ���
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
	 * ����ת�˲������û����û�֮�䣩
	 * 
	 * @return ���ͻ��˵���Ϣ
	 */
	public static MessageContainer Tran(TransactionInfoContainer trans) {
		try {
			Vector<HashMap<String, String>> ret1 = DatabaseManager// ��tblUser�в�ѯ����ID�Ƿ����
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + Integer.valueOf((int) trans.lInID));
			Vector<HashMap<String, String>> ret2 = DatabaseManager// ��tblUser�в�ѯ֧��ID�Ƿ����
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + Integer.valueOf((int) trans.lOutID));
			if (ret1.size() == 0) {
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);
			}
			if (ret2.size() == 0) {
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_OUTID);
			}
			if (ret1.size() > 0 && ret2.size() > 0) {// ���˺Ŷ����ڣ���ȡ�ö��ߵ����
				float balanceOfout = Float.valueOf(ret2.get(0).get("uBalance"));
				float balanceOfin = Float.valueOf(ret1.get(0).get("uBalance"));
				balanceOfout = balanceOfout - trans.fAmount;// ����֧���˻������
				balanceOfin = balanceOfin + trans.fAmount;// ���������˻������
				if (balanceOfout < 0 || balanceOfin < 0) {
					return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ARITH_ERROR);// ת�˺����С��0
				}
				DatabaseManager.queryWithoutResult(
						"INSERT INTO tblTransaction (uId_o,uId_i,tAmount,tBalanceOut,tBalanceIn,tMemo,tOpr) VALUES(" + trans.lOutID
								+ "," + trans.lInID + "," + trans.fAmount + "," + balanceOfout + "," + balanceOfin + ",'" + trans.strMemo + "'," + trans.lOperation
								+ ")");
				// �ѽ������ݲ������//tblTransaction�������޸�,ɾ��tBalance�ֶΣ����˵�����������ΪtBalaceOut��tBalanceIn���ֶ�
				DatabaseManager.queryWithoutResult("UPDATE tblUser SET uBalance=" + balanceOfout + " WHERE uId="
						+ trans.lOutID);// ����tblUser�е��û����
				DatabaseManager.queryWithoutResult("UPDATE tblUser SET uBalance=" + balanceOfin + " WHERE uId="
						+ trans.lInID);// ����tblUser�е��û����
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.CMD_TRAN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���в�ѯ���׼�¼����
	 * 
	 * @return ���ͻ��˵���Ϣ
	 */
	public MessageContainer View(int nID) {
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager// �����û�ID�Ƿ����
					.queryWithResult("SELECT * FROM tblUser WHERE uId=" + nID);
			if (ret.size() == 0) {
				return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.NONEXIST_TRANID);
			}
			Vector<HashMap<String, String>> ret1 = DatabaseManager.queryWithResult("SELECT * FROM tblTransaction WHERE uId_o=" + nID); // �����û��˻���Ϊ����˻��Ľ��׼�¼
			Vector<HashMap<String, String>> ret2 = DatabaseManager.queryWithResult("SELECT * FROM tblTransaction WHERE uId_i=" + nID); // �����û��˻���Ϊ�����˻��Ľ��׼�¼
			if (ret1.size() == 0 && ret2.size() == 0) {// ���޼�¼�������޼�¼��Ϣ
				return new MessageContainer(CommandProtocol.SUCCESS, CommandProtocol.NODATA);
			}
			TransactionInfoContainer[] tran_array = new TransactionInfoContainer[ret1.size() + ret2.size()];
			MessageContainer retmsg = new MessageContainer();
			for (int i = 0; i < ret1.size(); ++i) {// ����¼�����������
				tran_array[i] = new TransactionInfoContainer();
				tran_array[i].fAmount = Float.valueOf(ret1.get(i).get("tAmount"));
				tran_array[i].lInID = Integer.valueOf(ret1.get(i).get("uId_o"));
				tran_array[i].lOutID = Integer.valueOf(ret1.get(i).get("uId_i"));
				tran_array[i].strMemo = "֧����" + ret1.get(i).get("tMemo");
				// tran_array[i].strMemo = "֧��";// ����Ϊ����˻��Ľ��׼�¼�ı�ע��Ϊ֧��
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
				tran_array[i + ret1.size()].strMemo = "���룺" + ret2.get(i).get("tMemo");
				// tran_array[i + ret1.size()].strMemo = "����";// ����Ϊ�����˻��Ľ��׼�¼�ı�ָ��Ϊ����
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
			retmsg.setEntityParameters(ent);// ����¼��������뷵�ض���retmsg��Entity������
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
							+ "," + uid + "," + amount + "," + 0 + "," + (Float.valueOf(ret.get(0).get("uBalance")) + amount) + ",'�˹���ֵ',2)"); // �ѽ������ݲ������
			DatabaseManager.queryWithoutResult("UPDATE tblUser SET uBalance=uBalance+" + amount + " WHERE uId=" + uid); // ����tblUser�е��û����
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
}