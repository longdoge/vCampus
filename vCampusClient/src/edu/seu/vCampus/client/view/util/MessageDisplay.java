/**
 * �Ի���������
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.view.util;

import javax.swing.JOptionPane;

public class MessageDisplay {
    /**
     * չʾ��Ϣ�ͶԻ���
     * 
     * @param strInfo
     *          ��Ϣ����
     */
    public static void showInfo(String strInfo) {
        JOptionPane.showMessageDialog(null, strInfo, "��Ϣ��ʾ", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * չʾ������ʾ�ͶԻ���
     * 
     * @param strInfo
     *          ������ʾ����
     */
    public static void showError(String strInfo) {
        JOptionPane.showMessageDialog(null, strInfo, "������ʾ", JOptionPane.ERROR_MESSAGE);
    }
}
