package edu.seu.vCampus.SharedComponents.vo;

public class UserOrderInfoContainer extends EntityModel {
	private static final long serialVersionUID = 1L;

	public vGood getGood() {
		return Good;
	}

	public void setGood(vGood good) {
		Good = good;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public vGood Good;
	public long num;

}
