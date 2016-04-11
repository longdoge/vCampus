/**
 * ��Ʒģ��_�������
 */
package edu.seu.vCampus.client.biz.module.vStore;

import java.util.Vector;

import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.*;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.biz.util.UserLoginInfoManager;

public class goodManager {
	
	/**
	 * ��ѯ����ĳ�̵����Ա����Ʒ
	 * @param uId
	 * 		�̵����ԱID
	 * @return
	 * 		�õ�������Ʒ ��Vector�洢
	 */
	public  Vector<vGood> scangoodswithuid(vGood Good){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANGOODSWITHUID;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = Good;
        req.setEntityParameters(term);
        MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vGood> goodvector = new Vector<vGood>();
		for(int i = 0; i < ret.entityParameters.length;i++)
			goodvector.add((vGood)ret.entityParameters[i]);
		
		return goodvector;
	}
	public  Vector<vGood> scangoodswithgid(vGood Good){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANGOODSWITHGID;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = Good;
        req.setEntityParameters(term);
        MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vGood> goodvector = new Vector<vGood>();
		for(int i = 0; i < ret.entityParameters.length;i++)
			goodvector.add((vGood)ret.entityParameters[i]);
		
		return goodvector;
	} 
	
	/**
	 * ��ѯ������Ʒ
	 * @return
	 */
	public  Vector<vGood> scangoods(){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANGOODS;
		req.strToken = UserLoginInfoManager.getStrToken();
        //req.strParameters[0] = Long.toString(uId_m);
        MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vGood> Good = new Vector<vGood>();
		for(int i = 0; i < ret.entityParameters.length;++i)
			Good.add((vGood)ret.entityParameters[i]);
		return Good;
	}
	
	public Vector<vGood> scangoodswithname(vGood Good){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANGOODSWITHNAME;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = Good;
        req.setEntityParameters(term);
        
        MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vGood> goodvector = new Vector<vGood>();
		for(int i = 0; i < ret.entityParameters.length;++i)
			goodvector.add((vGood)ret.entityParameters[i]);
		return goodvector;
	}
	
	public Vector<vGood> scangoodswithtype(vGood Good){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_SCANGOODSWITHTYPE;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = Good;
        req.setEntityParameters(term);
        
        MessageContainer ret = RequestHandler.sendRequest(req);
		Vector<vGood> goodvector = new Vector<vGood>();
		for(int i = 0; i < ret.entityParameters.length;++i)
			goodvector.add((vGood)ret.entityParameters[i]);
		return goodvector;
	}
	/**
	 * �����Ʒ
	 * @param Good
	 * @return
	 */
	public String addgood(vGood Good){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_ADDGOOD;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = Good;
        req.setEntityParameters(term);
        MessageContainer ret = RequestHandler.sendRequest(req);
        return ret.strCommand;
	}
	
	/**
	 * ɾ����Ʒ
	 * @param Good
	 * @return
	 */
	public String deletegood(vGood Good){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_DELETEGOOD;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = Good;
        req.setEntityParameters(term);
        MessageContainer ret = RequestHandler.sendRequest(req);
        return ret.strCommand;
	}
	/**
	 * ��server���ݸ�����Ʒ������ ͬʱ������Ҫ���µ���ƷID�Ͷ���
	 * @param Good
	 * @return
	 */
	public String updategood(vGood Good){
		MessageContainer req = new MessageContainer();
		req.strCommand = CommandProtocol.CMD_UPDATEGOOD;
		req.strToken = UserLoginInfoManager.getStrToken();
		EntityModel[] term = new EntityModel[1];
		term[0] = Good;
        req.setEntityParameters(term);
        MessageContainer ret = RequestHandler.sendRequest(req);
        return ret.strCommand;
	}
	
	
}