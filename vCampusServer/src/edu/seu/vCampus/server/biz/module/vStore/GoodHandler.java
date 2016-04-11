package edu.seu.vCampus.server.biz.module.vStore;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.EntityModel;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.UserActionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.vGood;
import edu.seu.vCampus.server.biz.util.TokenHandler;
import edu.seu.vCampus.server.dao.DatabaseManager;
import edu.seu.vCampus.SharedComponents.dao.CommandProtocol;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;
import edu.seu.vCampus.SharedComponents.vo.UserActionInfoContainer;
import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;

public class GoodHandler {
	private String strCommand = "", strToken = "";
	private int uId = 0;
	public vGood Good = null;

	/**
	 * 
	 * 无参构造函数 根据client传来信息构造服务类GoodHandler对象
	 * 
	 * @param msg
	 */
	public GoodHandler(MessageContainer msg) {
		strToken = msg.strToken;
		strCommand = msg.strCommand;
		if (msg.entityParameters != null && msg.entityParameters.length >= 1) {
			Good = (vGood) msg.entityParameters[0];
		}
		if (msg.strParameters != null && msg.strParameters.length >= 1)
			uId = Integer.parseInt(msg.strParameters[0]);

	}

	/**
	 * Handler函数 根据不同命令产生不同的返回消息
	 * 
	 * @return
	 */
	public MessageContainer Handle() {
		MessageContainer ret = new MessageContainer();
		int bool = 0;
		switch (strCommand) {
		case CommandProtocol.CMD_SCANGOODS:
			Vector<vGood> vg = scangoods();
			EntityModel[] ve = new EntityModel[vg.size()];
			for (int i = 0; i < vg.size(); ++i)
				ve[i] = ((EntityModel) vg.get(i));
			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve;
			return ret;
		case CommandProtocol.CMD_SCANGOODSWITHUID:
			Vector<vGood> vg2 = scangoodswithuid(Good.uId);
			EntityModel[] ve2 = new EntityModel[vg2.size()];
			for (int i = 0; i < vg2.size(); ++i)
				ve2[i] = ((EntityModel) vg2.get(i));

			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve2;
			return ret;
		case CommandProtocol.CMD_SCANGOODSWITHNAME:
			Vector<vGood> vg3 = scangoodswithname(Good.getgName());
			EntityModel[] ve3 = new EntityModel[vg3.size()];
			for (int i = 0; i < vg3.size(); ++i)
				ve3[i] = ((EntityModel) vg3.get(i));

			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve3;
			return ret;
		case CommandProtocol.CMD_SCANGOODSWITHTYPE:
			Vector<vGood> vg4= scangoodswithtype(Good.getgType());
			EntityModel[] ve4 = new EntityModel[vg4.size()];
			for (int i = 0; i < vg4.size(); ++i)
				ve4[i] = ((EntityModel) vg4.get(i));

			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve4;
			return ret;
		case CommandProtocol.CMD_SCANGOODSWITHGID:
			Vector<vGood> vg5= scangoodswithgid(Good.getgId());
			EntityModel[] ve5 = new EntityModel[vg5.size()];
			for (int i = 0; i < vg5.size(); ++i)
				ve5[i] = ((EntityModel) vg5.get(i));

			ret.strCommand = CommandProtocol.SUCCESS;
			ret.entityParameters = (EntityModel[]) ve5;
			return ret;
		case CommandProtocol.CMD_ADDGOOD:
			bool = addgood(Good);
			if (bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;
		case CommandProtocol.CMD_DELETEGOOD:
			bool = deletegood(Good.getgId());
			if (bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;
		case CommandProtocol.CMD_UPDATEGOOD:
			bool = updategood(Good);
			if (bool == 1)
				ret.strCommand = CommandProtocol.SUCCESS;
			else
				ret.strCommand = CommandProtocol.ERROR;
			return ret;

		}
		return new MessageContainer(CommandProtocol.ERROR, CommandProtocol.UNKNOWN_CMD);
	}

	/**
	 * 
	 * @param uId
	 *            根据商品所属商店管理员ID查询商品
	 * @return 返回改管理员ID下所有商品
	 */
	public Vector<vGood> scangoodswithuid(int uId) {
		Vector<vGood> goodvector = new Vector<vGood>();

		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblGoods WHERE uId=" + uId);
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGood Good = new vGood();
					Good.setgId(Integer.valueOf(ret.get(i).get("gId")));
					Good.setgName(ret.get(i).get("gName"));
					Good.setgIntro(ret.get(i).get("gIntro"));
					Good.setuId(Integer.valueOf(ret.get(i).get("uId")));
					Good.setgStock(Integer.valueOf(ret.get(i).get("gStock")));
					Good.setgPrice(Float.valueOf(ret.get(i).get("gPrice")));
					Good.setgType(Integer.valueOf(ret.get(i).get("gType")));
					Good.setShopName(getShopName(Good.uId));
					goodvector.add(Good);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodvector;
	}
	
	/**
	 * 
	 * @param uId
	 *            根据商品所属商店管理员ID查询商品
	 * @return 返回改管理员ID下所有商品
	 */
	public static Vector<vGood> scangoodswithgid(int gId) {
		Vector<vGood> goodvector = new Vector<vGood>();

		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblGoods WHERE gId=" + gId);
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGood Good = new vGood();
					Good.setgId(Integer.valueOf(ret.get(i).get("gId")));
					Good.setgName(ret.get(i).get("gName"));
					Good.setgIntro(ret.get(i).get("gIntro"));
					Good.setuId(Integer.valueOf(ret.get(i).get("uId")));
					Good.setgStock(Integer.valueOf(ret.get(i).get("gStock")));
					Good.setgPrice(Float.valueOf(ret.get(i).get("gPrice")));
					Good.setgType(Integer.valueOf(ret.get(i).get("gType")));
					Good.setShopName(getShopName(Good.uId));
					goodvector.add(Good);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodvector;
	}

	/**
	 * 返回所有商品
	 * 
	 * @return
	 */
	public Vector<vGood> scangoods() {
		Vector<vGood> goodvector = new Vector<vGood>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT * FROM tblGoods");
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGood Good = new vGood();
					Good.setgId(Integer.valueOf(ret.get(i).get("gId")));
					Good.setgName(ret.get(i).get("gName"));
					Good.setgIntro(ret.get(i).get("gIntro"));
					Good.setuId(Integer.valueOf(ret.get(i).get("uId")));
					Good.setgStock(Integer.valueOf(ret.get(i).get("gStock")));
					Good.setgPrice(Float.valueOf(ret.get(i).get("gPrice")));
					Good.setgType(Integer.valueOf(ret.get(i).get("gType")));
					Good.setShopName(getShopName(Good.uId));
					goodvector.add(Good);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodvector;
	}

	public Vector<vGood> scangoodswithname(String str) {
		Vector<vGood> goodvector = new Vector<vGood>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblGoods WHERE gName LIKE '%" + str + "%'");
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGood Good = new vGood();
					Good.setgId(Integer.valueOf(ret.get(i).get("gId")));
					Good.setgName(ret.get(i).get("gName"));
					Good.setgIntro(ret.get(i).get("gIntro"));
					Good.setuId(Integer.valueOf(ret.get(i).get("uId")));
					Good.setgStock(Integer.valueOf(ret.get(i).get("gStock")));
					Good.setgPrice(Float.valueOf(ret.get(i).get("gPrice")));
					Good.setgType(Integer.valueOf(ret.get(i).get("gType")));
					Good.setShopName(getShopName(Good.uId));
					goodvector.add(Good);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodvector;
	}
	
	public Vector<vGood> scangoodswithtype(int type) {
		Vector<vGood> goodvector = new Vector<vGood>();
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager
					.queryWithResult("SELECT * FROM tblGoods WHERE gType LIKE '%" + type + "%'");
			if (ret.size() > 0) {
				System.out.println(ret);
				for (int i = 0; i < ret.size(); ++i) {
					vGood Good = new vGood();
					Good.setgId(Integer.valueOf(ret.get(i).get("gId")));
					Good.setgName(ret.get(i).get("gName"));
					Good.setgIntro(ret.get(i).get("gIntro"));
					Good.setuId(Integer.valueOf(ret.get(i).get("uId")));
					Good.setgStock(Integer.valueOf(ret.get(i).get("gStock")));
					Good.setgPrice(Float.valueOf(ret.get(i).get("gPrice")));
					Good.setgType(Integer.valueOf(ret.get(i).get("gType")));
					Good.setShopName(getShopName(Good.uId));
					goodvector.add(Good);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodvector;
	}
	
	/**
	 * 添加一个商品到数据库
	 * 
	 * @return
	 */
	public int addgood(vGood Good) {

		int bool = 0;
		try {
			DatabaseManager.queryWithoutResult("INSERT INTO tblGoods (gName,gPrice,gStock,gIntro,uId,gType) VALUES ('"
					+ Good.gName + "'," + Good.gPrice + "," + Good.gStock + ",'" + Good.gIntro + "'," + Good.uId + ","
					+ Good.gType + ")");
			bool = 1;
			System.out.println("add finished");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bool;
	}

	/**
	 * 根据商品ID删除商品
	 * 
	 * @return
	 */
	public int deletegood(int gId) {
		int bool = 0;
		try {
			DatabaseManager.queryWithoutResult("DELETE * FROM tblGoods WHERE gId=" + gId);
			bool = 1;
		} catch (Exception e) {
			e.printStackTrace();
			bool = 0;
		}

		return bool;
	}

	/**
	 * 根据商品对象更新商品 由商品gId唯一确定商品
	 * 
	 * @param Good
	 *            商品对象
	 * @return
	 * 
	 */
	public static int updategood(vGood Good) {
		int bool = 0;
		try {
			DatabaseManager.queryWithoutResult("UPDATE tblGoods SET gName='" + Good.gName + "',gPrice=" + Good.gPrice + ",gStock=" + Good.gStock + ",gIntro='" + Good.gIntro + "',gType=" + Good.gType + " WHERE gId = " + Good.gId);
			bool = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
	
	public static String getShopName(int nUserId) {
		try {
			Vector<HashMap<String, String>> ret = DatabaseManager.queryWithResult("SELECT shopName FROM tblUser WHERE uId=" + nUserId);
			if (ret.size() == 0)
				return null;
			return ret.get(0).get("shopName");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}