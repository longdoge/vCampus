/**
 * ��Ʒģ��-��������
 */
package edu.seu.vCampus.client.biz.module.vStore;

import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.*;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class orderManager {

	/**
	 * ��ѯ���ж���
	 * 
	 * @return
	 */
	public Vector<vOrder> scanorderwithout() {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANORDERBWITHOUT;
		req.strToken = UserLoginInfoManager.getStrToken();
		MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vOrder> order = new Vector<vOrder>();
		for (int i = 0; i < ret.entityParameters.length; i++)
			order.add((vOrder) ret.entityParameters[i]);

		return order;
	}

	/**
	 * ��ѯĳ�����������ж���
	 * 
	 * @param uId_b
	 *            �û�ID
	 * @return
	 */
	public Vector<vOrder> scanorderwithuidb(int uId_b) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANORDERMWITHUIDB;
		req.strToken = UserLoginInfoManager.getStrToken();
		String[] term_string = new String[2];
		term_string[0] = Long.toString(uId_b);
		term_string[1] = "0";
		req.setStrParameters(term_string);
		MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vOrder> order = new Vector<vOrder>();
		for (int i = 0; i < ret.entityParameters.length; i++)
			order.add((vOrder) ret.entityParameters[i]);

		return order;
	}

	/**
	 * ��ѯĳ�̵����Ա�������ж���
	 * 
	 * @param uId_m
	 *            �̵�����ID
	 * @return
	 */
	public Vector<vOrder> scanorderwithuidm(int uId_m) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANORDERMWITHUIDM;
		req.strToken = UserLoginInfoManager.getStrToken();
		String[] term_string = new String[2];
		term_string[0] = "0";
		term_string[1] = Long.toString(uId_m);
		req.setStrParameters(term_string);
		MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vOrder> order = new Vector<vOrder>();
		for (int i = 0; i < ret.entityParameters.length; i++)
			order.add((vOrder) ret.entityParameters[i]);

		return order;
	}

	/**
	 * ��Ӷ���
	 * 
	 * @param order
	 * @return
	 */
	public String addorder(vOrder order) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_ADDORDER;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = order;
		req.setEntityParameters(term);
		MessageContainer ret = RequestHandler.sendRequest(req);
		return ret.strCommand;
	}

	/**
	 * ɾ������
	 * 
	 * @param order
	 * @return
	 */
	public String deleteorder(vOrder order) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_DELETEORDER;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = order;
		req.setEntityParameters(term);
		MessageContainer ret = RequestHandler.sendRequest(req);
		return ret.strCommand;
	}

	/**
	 * ���¶���
	 * 
	 * @param order
	 * @return
	 */
	public String updateorder(vOrder order) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_UPDATEORDER;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = order;
		req.setEntityParameters(term);
		MessageContainer ret = RequestHandler.sendRequest(req);
		return ret.strCommand;
	}

	/**
	 * 
	 * @param OrderInfo[]
	 *            ���ɶ����Ļ����嵥
	 * @param uId_b
	 *            ��������������
	 */
	public static void createOrder(UserOrderInfoContainer OrderInfo[], long uId_b, long uId_m) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_CREATEORDER;
		req.strToken = UserLoginInfoManager.getStrToken();
		req.strParameters[0] = Long.toString(uId_b);
		req.strParameters[1] = Long.toString(uId_m);
		for (int i = 0; i < OrderInfo.length; i++)
			req.entityParameters[i] = OrderInfo[i];
	}

	/**
	 * 
	 * @param OrderInfo[]
	 *            ���ɶ����Ļ����嵥
	 * @param uId_b
	 *            ��������������
	 */
	public static MessageContainer createOrder(vOrder orderInfo) {
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_ADDORDER;
		req.strToken = UserLoginInfoManager.getStrToken();
		req.entityParameters = new vOrder[] { orderInfo };
		return RequestHandler.sendRequest(req);
	}
}