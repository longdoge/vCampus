/**
 * 对话框生成类
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.view.util;

import javax.swing.JOptionPane;

public class MessageDisplay {
    /**
     * 展示消息型对话框
     * 
     * @param strInfo
     *          消息内容
     */
    public static void showInfo(String strInfo) {
        JOptionPane.showMessageDialog(null, strInfo, "消息提示", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 展示错误提示型对话框
     * 
     * @param strInfo
     *          错误提示内容
     */
    public static void showError(String strInfo) {
        JOptionPane.showMessageDialog(null, strInfo, "错误提示", JOptionPane.ERROR_MESSAGE);
    }
}
