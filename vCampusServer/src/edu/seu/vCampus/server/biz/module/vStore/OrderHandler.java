package edu.seu.vCampus.server.biz.module.vStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.server.biz.module.vBank.BankActionHandler;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.TransactionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.SharedComponents.vo.vOrder;
import edu.seu.vCampus.server.dao.DatabaseManager;

public class OrderHandler {
    private String strCommand = "", strToken = "";
    private int uId_b = 0, uId_m = 0;
    public vOrder order = null;

    /**
     * 
     * @param ��client��������Ϣ�����������OrderHandler����
     */
    public OrderHandler(MessageContainer msg) {
        strToken = msg.strToken;
        strCommand = msg.strCommand;
        if (msg.strParameters != null && msg.strParameters.length >= 2) {
            uId_b = Integer.parseInt(msg.strParameters[0]);
            uId_m = Integer.parseInt(msg.strParameters[1]);
        }
        if (msg.entityParameters != null && msg.entityParameters.length >= 1) {
            order = (vOrder) msg.entityParameters[0];
        }
    }

    /**
     * 
     * �޲κ��� ��������Ĳ�ͬ �����ز�ͬ����Ϣ����
     * 
     * @return ��Ϣ���� �������ͬ������ͬ
     */
    public MessageContainer Handle() {
    	if (TokenHandler.checkToken(strToken) == null)
			return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.LOGGED_IN);
    	int uid = TokenHandler.checkToken(strToken).nUserID;
        MessageContainer ret = new MessageContainer();
        int bool = 0;
        switch (strCommand) {
        case CommandProtocol.CMD_SCANORDERBWITHOUT:
            Vector<vOrder> vo = scanorderwithout();
            EntityModel[] ve = new EntityModel[vo.size()];
            for (int i = 0; i < vo.size(); ++i)
                ve[i] = (EntityModel) vo.get(i);
            ret.strCommand = CommandProtocol.SUCCESS;
            ret.entityParameters = (EntityModel[]) ve;
            return ret;
        case CommandProtocol.CMD_SCANORDERMWITHUIDB:
            Vector<vOrder> vo2 = scanorderwithuidb(uId_b);
            EntityModel[] ve2 = new EntityModel[vo2.size()];
            for (int i = 0; i < vo2.size(); ++i)
                ve2[i] = (EntityModel) vo2.get(i);
            ret.strCommand = CommandProtocol.SUCCESS;
            ret.entityParameters = (EntityModel[]) ve2;
            return ret;
        case CommandProtocol.CMD_SCANORDERMWITHUIDM:
            Vector<vOrder> vo3 = scanorderwithuidm(uId_m);
            EntityModel[] ve3 = new EntityModel[vo3.size()];
            for (int i = 0; i < vo3.size(); ++i)
                ve3[i] = (EntityModel) vo3.get(i);
            ret.strCommand = CommandProtocol.SUCCESS;
            ret.entityParameters = (EntityModel[]) ve3;
            return ret;
        case CommandProtocol.CMD_ADDORDER:
        	vGood g = GoodHandler.scangoodswithgid(order.uId_m).get(0);
        	float balance = Float.valueOf(BankActionHandler.Query(uid).strParameters[0]);
        	order.oAmount = (int)order.oAmount;
        	if (order.oAmount <= 0)
        		return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ARITH_ERROR);
        	if (balance < order.oAmount * g.gPrice)
        		return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.BALANCE_NOT_ENOUGH);
        	if (order.oAmount > g.gStock)
        		return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.STOCK_NOT_ENOUGH);
        	g.gStock -= order.oAmount;
        	order.oLists = g.gName + " x" + order.oAmount;
        	order.oAmount = order.oAmount * g.gPrice;
        	order.uId_b = uid;
        	order.uId_m = g.uId;
            bool = addorder(order);
            if (bool == 1) {
            	TransactionInfoContainer tran = new TransactionInfoContainer();
            	tran.lInID = order.uId_m;
            	tran.lOutID = order.uId_b;
            	tran.fAmount = order.oAmount;
            	tran.strMemo = order.oLists;
            	BankActionHandler.Tran(tran);
            	GoodHandler.updategood(g);
                ret.strCommand = CommandProtocol.SUCCESS;
                return ret;
            } else
            	return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.ARITH_ERROR);
        case CommandProtocol.CMD_DELETEORDER:
            bool = deleteorder(uId_m);
            if (bool == 1)
                ret.strCommand = CommandProtocol.SUCCESS;
            else
                ret.strCommand = CommandProtocol.ERROR;
            return ret;
        case CommandProtocol.CMD_UPDATEORDER:
            bool = updateorder(order);
            if (bool == 1)
                ret.strCommand = CommandProtocol.SUCCESS;
            else
                ret.strCommand = CommandProtocol.ERROR;
            return ret;
        }
        return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);

    }

    /**
     * �������еĶ���
     * 
     * @return
     */
    public Vector<vOrder> scanorderwithout() {
        Vector<vOrder> ordervector = new Vector<vOrder>();
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblOrder");
            if (ret.size() > 0) {
                System.out.println(ret);
                vOrder order = new vOrder();
                for (int i = 0; i < ret.size(); ++i) {
                	order.setoId(Integer.valueOf(ret.get(i).get("oID")));
                    order.setoAmount(Float.valueOf(ret.get(i).get("oAmount")));
                    order.setoStatus(Integer.valueOf(ret.get(i).get("oStatus")));
                    order.setuId_b(Integer.valueOf(ret.get(i).get("uID_b")));
                    order.setuId_m(Integer.valueOf(ret.get(i).get("uID_m")));
                    order.setoTime(ret.get(i).get("oTime"));
                    order.setoLists(ret.get(i).get("oLists"));
                    order.setShopName(GoodHandler.getShopName(order.uId_m));
                    ordervector.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordervector;
    }

    /**
     * 
     * @param uId_b
     *            �����ߵ�ID
     * @return ���ظ������������ѵĶ���
     */
    public Vector<vOrder> scanorderwithuidb(int uId_b) {
        Vector<vOrder> ordervector = new Vector<vOrder>();
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblOrder WHERE uID_b = " + uId_b);
            if (ret.size() > 0) {
                System.out.println(ret);
                for (int i = 0; i < ret.size(); ++i) {
                	vOrder order = new vOrder();
                	order.setoId(Integer.valueOf(ret.get(i).get("oID")));
                    order.setoAmount(Float.valueOf(ret.get(i).get("oAmount")));
                    order.setoStatus(Integer.valueOf(ret.get(i).get("oStatus")));
                    order.setuId_b(Integer.valueOf(ret.get(i).get("uID_b")));
                    order.setuId_m(Integer.valueOf(ret.get(i).get("uID_m")));
                    order.setoTime(ret.get(i).get("oTime"));
                    order.setoLists(ret.get(i).get("oLists"));
                    order.setShopName(GoodHandler.getShopName(order.uId_m));
                    ordervector.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordervector;
    }

    /**
     * 
     * @param uId_m
     *            �̵����ԱID
     * @return ���ظ��̵��������ж���
     */
    public Vector<vOrder> scanorderwithuidm(int uId_m) {
        Vector<vOrder> ordervector = new Vector<vOrder>();
        try {
            Vector<HashMap<String, String>> ret = DatabaseManager
                    .queryWithResult("SELECT * FROM tblOrder WHERE uID_m = " + uId_m);
            if (ret.size() > 0) {
                System.out.println(ret);
                vOrder order = new vOrder();
                for (int i = 0; i < ret.size(); ++i) {
                	order.setoId(Integer.valueOf(ret.get(i).get("oID")));
                    order.setoAmount(Float.valueOf(ret.get(i).get("oAmount")));
                    order.setoStatus(Integer.valueOf(ret.get(i).get("oStatus")));
                    order.setuId_b(Integer.valueOf(ret.get(i).get("uID_b")));
                    order.setuId_m(Integer.valueOf(ret.get(i).get("uID_m")));
                    order.setoTime(ret.get(i).get("oTime"));
                    order.setoLists(ret.get(i).get("oLists"));
                    order.setShopName(GoodHandler.getShopName(order.uId_m));
                    ordervector.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordervector;
    }

    /**
     * 
     * @param order
     *            ��Ҫ��ӵĶ�������
     * @return ����SQL���ɹ�����1 ���򷵻�0
     */
    public int addorder(vOrder order) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int bool = 0;
        try {
            DatabaseManager.queryWithoutResult("INSERT INTO tblOrder (oTime,uID_b,uID_m,oLists,oAmount,oStatus) VALUES "
                    + "('" + sdf.format(new Date()) + "'," + order.uId_b + "," + order.uId_m + ",'" + order.oLists + "'," + order.oAmount
                    + "," + order.oStatus + ")");
            bool = 1;
            System.out.println("add finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }

    /**
     * 
     * @param uId_m
     *            ��Ҫɾ���Ķ��������̵����ԱID
     * @return ����SQL���ɹ�����1 ���򷵻�0
     */
    public int deleteorder(int uId_m) {
        int bool = 0;
        try {
            DatabaseManager.queryWithoutResult("DELETE * FROM tblOrder WHERE uID_m=" + uId_m);
            bool = 1;
        } catch (Exception e) {
            e.printStackTrace();
            bool = 0;
        }

        return bool;
    }

    /**
     * 
     * @param order
     *            ��Ҫ���µĶ������� ������IDΨһʶ��
     * @return ����SQL���ɹ�����1 ���򷵻�0
     */
    public int updateorder(vOrder order) {
        int bool = 0;
        try {
            DatabaseManager.queryWithoutResult("UPDATE tblOrder SET oTime ='" + order.oTime + "', " + "oAmount = "
                    + order.oAmount + " WHERE oID = " + order.oId);
            bool = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }
}