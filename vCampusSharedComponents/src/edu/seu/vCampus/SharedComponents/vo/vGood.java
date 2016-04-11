package edu.seu.vCampus.SharedComponents.vo;

public class vGood extends EntityModel {
	private static final long serialVersionUID = 1L;

	public int gId;
	public String gName;
	public float gPrice;
	public int gStock;
	public int uId;
	public String gIntro;
	public int gType;
	public String shopName;

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the gType
	 */
	public int getgType() {
		return gType;
	}

	/**
	 * @param gType
	 *            the gType to set
	 */
	public void setgType(int gType) {
		this.gType = gType;
	}

	/**
	 * @return the gId
	 */
	public int getgId() {
		return gId;
	}

	/**
	 * @param gId
	 *            the gId to set
	 */
	public void setgId(int gId) {
		this.gId = gId;
	}

	/**
	 * @return the gName
	 */
	public String getgName() {
		return gName;
	}

	/**
	 * @param gName
	 *            the gName to set
	 */
	public void setgName(String gName) {
		this.gName = gName;
	}

	/**
	 * @return the gPrice
	 */
	public float getgPrice() {
		return gPrice;
	}

	/**
	 * @param gPrice
	 *            the gPrice to set
	 */
	public void setgPrice(float gPrice) {
		this.gPrice = gPrice;
	}

	/**
	 * @return the gStock
	 */
	public int getgStock() {
		return gStock;
	}

	/**
	 * @param gStock
	 *            the gStock to set
	 */
	public void setgStock(int gStock) {
		this.gStock = gStock;
	}

	/**
	 * @return the uId
	 */
	public int getuId() {
		return uId;
	}

	/**
	 * @param uId
	 *            the uId to set
	 */
	public void setuId(int uId) {
		this.uId = uId;
	}

	/**
	 * @return the gIntro
	 */
	public String getgIntro() {
		return gIntro;
	}

	/**
	 * @param gIntro
	 *            the gIntro to set
	 */
	public void setgIntro(String gIntro) {
		this.gIntro = gIntro;
	}

}