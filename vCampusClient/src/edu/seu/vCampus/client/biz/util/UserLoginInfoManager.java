/**
 * 客户端用于暂存用户登录信息的类
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.biz.util;

import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;

public class UserLoginInfoManager {
    public static String strToken = null;
    public static UserInfoContainer userInfo = null;

    /**
     * @return the userInfo
     */
    public static UserInfoContainer getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public static void setUserInfo(UserInfoContainer userInfo) {
        UserLoginInfoManager.userInfo = userInfo;
    }

    /**
     * @return the strToken
     */
    public static String getStrToken() {
        return strToken;
    }

    /**
     * @param strToken
     *            the strToken to set
     */
    public static void setStrToken(String strToken) {
        UserLoginInfoManager.strToken = strToken;
    }

    /**
     * @return the nUserID
     */
    public static int getnUserID() {
        return userInfo.nUserID;
    }

    /**
     * @param nUserID
     *            the nUserID to set
     */
    public static void setnUserID(int nUserID) {
        userInfo.nUserID = nUserID;
    }

    /**
     * @return the nGroupIDs
     */
    public static int[] getnGroupIDs() {
        return userInfo.nGroupIDs;
    }

    /**
     * @param nGroupIDs
     *            the nGroupIDs to set
     */
    public static void setnGroupIDs(int[] nGroupIDs) {
        userInfo.nGroupIDs = nGroupIDs;
    }

    /**
     * @return the strUsername
     */
    public static String getStrUsername() {
        return userInfo.strUsername;
    }

    /**
     * @param strUsername
     *            the strUsername to set
     */
    public static void setStrUsername(String strUsername) {
        userInfo.strUsername = strUsername;
    }
}
