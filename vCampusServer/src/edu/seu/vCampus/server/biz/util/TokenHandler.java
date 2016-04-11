/**
 * ���ƹ�����
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
     * Ϊ�û���Ϣ��������
     * 
     * @param userInfo
     *            �û���Ϣ
     *
     * @return ��������ƣ�32λ����
     */
    public static String addToPool(UserInfoContainer userInfo) {
        String strRndToken = getRandomString(32);
        tokenPool.put(strRndToken, userInfo);
        return strRndToken;
    }

    /**
     * ��������
     * 
     * @param strToken
     *            ������������
     */
    public static void revokeToken(String strToken) {
        tokenPool.remove(strToken);
    }

    /**
     * �����ƻ�ȡ�û���Ϣ
     * 
     * @param strToken
     *            ����ѯ������
     *
     * @return �����ƴ��ڣ������û���Ϣ�����򣬷���null
     */
    public static UserInfoContainer checkToken(String strToken) {
        if (strToken == null)
            return null;
        return tokenPool.get(strToken);
    }

    /**
     * ��������ַ���
     * 
     * @param length
     *            ����ַ�������
     *
     * @return ���ɵ�����ַ���
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
