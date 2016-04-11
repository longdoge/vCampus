/*
   * 购物车管理
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
	 * 			向购物车添加商品
	 * @param num
	 * 			商品数量
	 */
	public void add(vGood Good, long num){
		UserOrderInfoContainer UserOrderInfo = new UserOrderInfoContainer();
		UserOrderInfo.setGood(Good);
		UserOrderInfo.setNum(num);
		shoppingCar.add(UserOrderInfo);
	}
	
	/**
	 * 清空购物车
	 */
	public void clear(){
		shoppingCar.removeAllElements();
	}
	
	/**
	 * 
	 * @return 购物清单
	 */
	public UserOrderInfoContainer[] scanShoppingCar(){
		return shoppingCar.toArray(new UserOrderInfoContainer[0]);
	}
}
