/**
 * 令牌管理类
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.server.biz.util;

import java.util.HashMap;
import java.util.Random;

import edu.seu.vCampus.SharedComponents.vo.UserInfoContainer;

public class TokenHandler {
    private static HashMap<String, UserInfoContainer> tokenPool = new HashMap<String, UserInfoContainer>();

    /**
     * 为用户信息分配令牌
     * 
     * @param userInfo
     *            用户信息
     *
     * @return 分配的令牌，32位长度
     */
    public static String addToPool(UserInfoContainer userInfo) {
        String strRndToken = getRandomString(32);
        tokenPool.put(strRndToken, userInfo);
        return strRndToken;
    }

    /**
     * 撤销令牌
     * 
     * @param strToken
     *            欲撤销的令牌
     */
    public static void revokeToken(String strToken) {
        tokenPool.remove(strToken);
    }

    /**
     * 由令牌获取用户信息
     * 
     * @param strToken
     *            欲查询的令牌
     *
     * @return 若令牌存在，返回用户信息；否则，返回null
     */
    public static UserInfoContainer checkToken(String strToken) {
        if (strToken == null)
            return null;
        return tokenPool.get(strToken);
    }

    /**
     * 生成随机字符串
     * 
     * @param length
     *            随机字符串长度
     *
     * @return 生成的随机字符串
     */
    private static String getRandomString(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
