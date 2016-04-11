package edu.seu.vCampus.SharedComponents.vo;

public class vOrder extends EntityModel {
	private static final long serialVersionUID = 1L;

	public int oId;
	public String oTime;
	public int uId_b;
	public int uId_m;
	public String oLists;
	public float oAmount;
	public int oStatus;
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

	public int getoId() {
		return oId;
	}

	public void setoId(int oId) {
		this.oId = oId;
	}

	public String getoTime() {
		return oTime;
	}

	public void setoTime(String oTime) {
		this.oTime = oTime;
	}

	public int getuId_b() {
		return uId_b;
	}

	public void setuId_b(int uId_b) {
		this.uId_b = uId_b;
	}

	public int getuId_m() {
		return uId_m;
	}

	public void setuId_m(int uId_m) {
		this.uId_m = uId_m;
	}

	public String getoLists() {
		return oLists;
	}

	public void setoLists(String oLists) {
		this.oLists = oLists;
	}

	public float getoAmount() {
		return oAmount;
	}

	public void setoAmount(float oAmount) {
		this.oAmount = oAmount;
	}

	public int getoStatus() {
		return oStatus;
	}

	public void setoStatus(int oStatus) {
		this.oStatus = oStatus;
	}

}