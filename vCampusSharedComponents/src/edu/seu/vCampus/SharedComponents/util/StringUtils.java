/**
 * �ַ���������
 * @author wanggp��http://wanggp.iteye.com/blog/796776��, Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.util;

public class StringUtils {
    // ����ͨ��ҳ����ύ���ַ�
    private static String[][] FilterChars = { { "\"", "" }, { "'", "" }, { "\n", "<br>" } };
    // ����ͨ��javascript�ű������ύ���ַ�
    private static String[][] FilterScriptChars = { { "\n", "\'+\'\\n\'+\'" }, { "\r", " " },
            { "\\", "\'+\'\\\\\'+\'" }, { "\'", "\'+\'\\\'\'+\'" } };

    /**
     * ��������ַ������ַ���
     * 
     * @param strings
     *            Ҫ���ӵ��ַ�������
     * @param spilit_sign
     *            �����ַ�
     * @return �����ַ���
     */
    public static String stringConnect(String[] strings, String spilit_sign) {
        String str = "";
        for (int i = 0; i < strings.length; i++) {
            str += strings[i] + spilit_sign;
        }
        return str;
    }

    /**
     * �����ַ�����ĵ������ַ�
     * 
     * @param str
     *            Ҫ���˵��ַ���
     * @return ���˺���ַ���
     */
    public static String stringFilter(String str) {
        if (str == null)
            return "";
        String[] str_arr = stringSpilit(str, "");
        for (int i = 0; i < str_arr.length; i++) {
            for (int j = 0; j < FilterChars.length; j++) {
                if (FilterChars[j][0].equals(str_arr[i]))
                    str_arr[i] = FilterChars[j][1];
            }
        }
        return (stringConnect(str_arr, "")).trim();
    }

    /**
     * ���˽ű��е������ַ��������س���(\n)�ͻ��з�(\r)��
     * 
     * @param str
     *            Ҫ���й��˵��ַ���
     * @return ���˺���ַ��� 2004-12-21 ��
     */
    public static String stringFilterScriptChar(String str) {
        if (str == null)
            return "";
        String[] str_arr = stringSpilit(str, "");
        for (int i = 0; i < str_arr.length; i++) {
            for (int j = 0; j < FilterScriptChars.length; j++) {
                if (FilterScriptChars[j][0].equals(str_arr[i]))
                    str_arr[i] = FilterScriptChars[j][1];
            }
        }
        return (stringConnect(str_arr, "")).trim();
    }

    /**
     * �ָ��ַ���
     * 
     * @param str
     *            Ҫ�ָ���ַ���
     * @param spilit_sign
     *            �ַ����ķָ��־
     * @return �ָ��õ����ַ�������
     */
    public static String[] stringSpilit(String str, String spilit_sign) {
        String[] spilit_string = str.split(spilit_sign);
        if (spilit_string[0].equals("")) {
            String[] new_string = new String[spilit_string.length - 1];
            for (int i = 1; i < spilit_string.length; i++)
                new_string[i - 1] = spilit_string[i];
            return new_string;
        } else
            return spilit_string;
    }
    
    /**
     * �ж�һ���ַ����Ƿ����������
     * 
     * @param str
     *            Ҫ�жϵ��ַ���
     * @return �жϽ��
     */
    public static boolean isOnlyDigit(String str) {
        return str.matches("^[0-9]+$");
    }
    
    /**
     * String����->int����
     * 
     * @param str
     *            String����
     * @return int����
     */
    public static int[] strArrToIntArr(String[] str) {
        int[] ret = new int[str.length];
        for (int i = 0; i < str.length; ++i)
            ret[i] = Integer.valueOf(str[i]);
        return ret;
    }
    
    /**
     * �ж�Ԫ���Ƿ���������
     * 
     * @param arr
     *            ����
     * @param target
     *            Ԫ��
     * 
     * @return �жϽ��
     */
    public static boolean inArray(int[] arr, int target) {
        for (int i = 0; i < arr.length; ++i)
            if (arr[i] == target)
                return true;
        return false;
    }
    
    /**
     * �ж��ַ����Ƿ�Ϊ�գ��Զ��ж�null��
     * 
     * @param str
     *            �ַ���
     * 
     * @return �жϽ��
     */
    public static boolean isStrEmptySafe(String str) {
        if (str == null || str.length() == 0)
            return true;
        return false;
    }
}
