/*
   * ���ﳵ����
   *
   */

package edu.seu.vCampus.client.biz.module.vStore;

import edu.seu.vCampus.SharedComponents.vo.*;

import java.util.Vector;

public class staticShoppingCar {
	public static Vector<UserOrderInfoContainer> shoppingCar = new Vector<UserOrderInfoContainer>();
	
	/**
	 * 
	 * @param Good
	 * 			���ﳵ�����Ʒ
	 * @param num
	 * 			��Ʒ����
	 */
	public void add(vGood Good, long num){
		UserOrderInfoContainer UserOrderInfo = new UserOrderInfoContainer();
		UserOrderInfo.setGood(Good);
		UserOrderInfo.setNum(num);
		shoppingCar.add(UserOrderInfo);
	}
	
	/**
	 * ��չ��ﳵ
	 */
	public void clear(){
		shoppingCar.removeAllElements();
	}
	
	/**
	 * 
	 * @return �����嵥
	 */
	public UserOrderInfoContainer[] scanShoppingCar(){
		return shoppingCar.toArray(new UserOrderInfoContainer[0]);
	}
}
