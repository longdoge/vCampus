/**
 * 用户信息的容器类
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

public class UserInfoContainer extends EntityModel {
    private static final long serialVersionUID = 1L;
    public int nUserID = 0;
    public int[] nGroupIDs = null;
    public String strUsername = null;
    public String strName = null;
    public int uBalance;
    public String shopname;
    
    public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public int getuBalance() {
		return uBalance;
	}
	public void setuBalance(int uBalance) {
		this.uBalance = uBalance;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	/**
     * @return the nUserID
     */
    public int getnUserID() {
        return nUserID;
    }
    /**
     * @param nUserID the nUserID to set
     */
    public void setnUserID(int nUserID) {
        this.nUserID = nUserID;
    }
    /**
     * @return the nGroupIDs
     */
    public int[] getnGroupIDs() {
        return nGroupIDs;
    }
    /**
     * @param nGroupIDs the nGroupIDs to set
     */
    public void setnGroupIDs(int[] nGroupIDs) {
        this.nGroupIDs = nGroupIDs;
    }
    /**
     * @return the strUsername
     */
    public String getStrUsername() {
        return strUsername;
    }
    /**
     * @param strUsername the strUsername to set
     */
    public void setStrUsername(String strUsername) {
        this.strUsername = strUsername;
    }
}
