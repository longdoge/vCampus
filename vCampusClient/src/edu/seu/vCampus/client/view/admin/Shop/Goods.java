package edu.seu.vCampus.client.view.admin.Shop;

import java.io.Serializable;

public class Goods {
	
	 
		private static final long serialVersionUID = 1L;
		String Id;	
		String Name;
		String Price;
		String Count;
		String Type;
		
		public Goods() {
			
		}
		
		/**
		 * 获取商品ID
		 * 
		 * @return
		 * 商品ID
		 */
		public String getId() {
			return Id;
		}
		
		/**
		 * 设置商品ID
		 * 
		 * @param id
		 * 商品ID
		 */
		public void setId(String id) {
			Id = id;
		}
		
		/**
		 * 获取商品名称
		 * 
		 * @return
		 * 商品名称
		 */
		public String getName() {
			return Name;
		}
		
		/**
		 * 设置商品名称
		 * 
		 * @param name
		 * 商品名称
		 */
		public void setName(String name) {
			Name = name;
		}
		
		/**
		 * 获取商品价格
		 * 
		 * @return
		 * 商品价格
		 */
		public String getPrice() {
			return Price;
		}

		/**
		 * 设置商品价格
		 * 
		 * @param price
		 * 商品价格
		 */
		public void setPrice(String price) {
			Price = price;
		}
		
		/**
		 * 获取商品数量
		 * 
		 * @return
		 * 商品数量
		 */
		public String getCount() {
			return Count;
		}

		/**
		 * 设置商品数量
		 * 
		 * @param count
		 * 商品数量
		 */
		public void setCount(String count) {
			Count = count;
		}

		/**
		 * 获取商品种类
		 * 
		 * @return
		 * 商品种类
		 */
		public String getType() {
			return Type;
		}

		/**
		 * 设置商品种类
		 * 
		 * @param type
		 * 商品种类
		 */
		public void setType(String type) {
			Type = type;
		}

}
