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
		 * ��ȡ��ƷID
		 * 
		 * @return
		 * ��ƷID
		 */
		public String getId() {
			return Id;
		}
		
		/**
		 * ������ƷID
		 * 
		 * @param id
		 * ��ƷID
		 */
		public void setId(String id) {
			Id = id;
		}
		
		/**
		 * ��ȡ��Ʒ����
		 * 
		 * @return
		 * ��Ʒ����
		 */
		public String getName() {
			return Name;
		}
		
		/**
		 * ������Ʒ����
		 * 
		 * @param name
		 * ��Ʒ����
		 */
		public void setName(String name) {
			Name = name;
		}
		
		/**
		 * ��ȡ��Ʒ�۸�
		 * 
		 * @return
		 * ��Ʒ�۸�
		 */
		public String getPrice() {
			return Price;
		}

		/**
		 * ������Ʒ�۸�
		 * 
		 * @param price
		 * ��Ʒ�۸�
		 */
		public void setPrice(String price) {
			Price = price;
		}
		
		/**
		 * ��ȡ��Ʒ����
		 * 
		 * @return
		 * ��Ʒ����
		 */
		public String getCount() {
			return Count;
		}

		/**
		 * ������Ʒ����
		 * 
		 * @param count
		 * ��Ʒ����
		 */
		public void setCount(String count) {
			Count = count;
		}

		/**
		 * ��ȡ��Ʒ����
		 * 
		 * @return
		 * ��Ʒ����
		 */
		public String getType() {
			return Type;
		}

		/**
		 * ������Ʒ����
		 * 
		 * @param type
		 * ��Ʒ����
		 */
		public void setType(String type) {
			Type = type;
		}

}
