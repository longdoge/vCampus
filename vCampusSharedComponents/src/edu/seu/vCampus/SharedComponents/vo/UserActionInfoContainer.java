/**
 * 用户行为信息容器
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

public class UserActionInfoContainer extends EntityModel {
    private static final long serialVersionUID = 1L;
    public String strUsername = null;
    public String strPassword = null;
    public int nUserID = 0;
    
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
    /**
     * @return the strPassword
     */
    public String getStrPassword() {
        return strPassword;
    }
    /**
     * @param strPassword the strPassword to set
     */
    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
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
}
