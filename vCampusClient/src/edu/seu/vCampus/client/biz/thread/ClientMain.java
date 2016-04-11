/**
 * vCampus Client �����
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.biz.thread;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.client.biz.util.RequestHandler;
import edu.seu.vCampus.client.controller.LoginController;

public class ClientMain {
    public static void main(String[] args) {
    	// ��ȡ�����ļ�
    	Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream (new FileInputStream("vCampusClient.properties"));  
            pps.load(in);
            String strServerAddr = pps.getProperty("ServerAddr");
            if (strServerAddr != null && strServerAddr.length() > 0) {
            	RequestHandler.setServerIP(strServerAddr);
            	System.out.println("Server Addr = " + strServerAddr);
            }
            String strServerPort = pps.getProperty("ServerPort");
            if (strServerPort != null && strServerPort.length() > 0 && StringUtils.isOnlyDigit(strServerPort)) {
            	RequestHandler.setPort(Integer.valueOf(strServerPort));
            	System.out.println("Server Port = " + strServerPort);
            }
        } catch (Exception e) {
        }
        // ������ֱ�ӽ����¼����
        new LoginController();
    }
}
