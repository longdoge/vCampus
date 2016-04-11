/**
 * 字符串工具类
 * @author wanggp（http://wanggp.iteye.com/blog/796776）, Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.util;

public class StringUtils {
    // 过滤通过页面表单提交的字符
    private static String[][] FilterChars = { { "\"", "" }, { "'", "" }, { "\n", "<br>" } };
    // 过滤通过javascript脚本处理并提交的字符
    private static String[][] FilterScriptChars = { { "\n", "\'+\'\\n\'+\'" }, { "\r", " " },
            { "\\", "\'+\'\\\\\'+\'" }, { "\'", "\'+\'\\\'\'+\'" } };

    /**
     * 用特殊的字符连接字符串
     * 
     * @param strings
     *            要连接的字符串数组
     * @param spilit_sign
     *            连接字符
     * @return 连接字符串
     */
    public static String stringConnect(String[] strings, String spilit_sign) {
        String str = "";
        for (int i = 0; i < strings.length; i++) {
            str += strings[i] + spilit_sign;
        }
        return str;
    }

    /**
     * 过滤字符串里的的特殊字符
     * 
     * @param str
     *            要过滤的字符串
     * @return 过滤后的字符串
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
     * 过滤脚本中的特殊字符（包括回车符(\n)和换行符(\r)）
     * 
     * @param str
     *            要进行过滤的字符串
     * @return 过滤后的字符串 2004-12-21 闫
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
     * 分割字符串
     * 
     * @param str
     *            要分割的字符串
     * @param spilit_sign
     *            字符串的分割标志
     * @return 分割后得到的字符串数组
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
     * 判断一个字符串是否仅包含数字
     * 
     * @param str
     *            要判断的字符串
     * @return 判断结果
     */
    public static boolean isOnlyDigit(String str) {
        return str.matches("^[0-9]+$");
    }
    
    /**
     * String数组->int数组
     * 
     * @param str
     *            String数组
     * @return int数组
     */
    public static int[] strArrToIntArr(String[] str) {
        int[] ret = new int[str.length];
        for (int i = 0; i < str.length; ++i)
            ret[i] = Integer.valueOf(str[i]);
        return ret;
    }
    
    /**
     * 判断元素是否在数组内
     * 
     * @param arr
     *            数组
     * @param target
     *            元素
     * 
     * @return 判断结果
     */
    public static boolean inArray(int[] arr, int target) {
        for (int i = 0; i < arr.length; ++i)
            if (arr[i] == target)
                return true;
        return false;
    }
    
    /**
     * 判断字符串是否为空（自动判断null）
     * 
     * @param str
     *            字符串
     * 
     * @return 判断结果
     */
    public static boolean isStrEmptySafe(String str) {
        if (str == null || str.length() == 0)
            return true;
        return false;
    }
}
