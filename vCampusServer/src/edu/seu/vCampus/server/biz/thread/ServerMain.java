/**
 * vCampus Server �����
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.server.biz.thread;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import edu.seu.vCampus.SharedComponents.util.StringUtils;
import edu.seu.vCampus.server.dao.DatabaseManager;
import edu.seu.vCampus.server.view.ServerFrame;

public class ServerMain {
    public static void main(String[] args) {
    	// ��ȡ�����ļ�
    	Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream (new FileInputStream("vCampusServer.properties"));  
            pps.load(in);
            String strDbPath = pps.getProperty("DbPath");
            if (strDbPath != null && strDbPath.length() > 0) {
            	DatabaseManager.setStrDbPath(strDbPath);
            	System.out.println("DbPath = " + strDbPath);
            }
            String strServerPort = pps.getProperty("ServerPort");
            if (strServerPort != null && strServerPort.length() > 0 && StringUtils.isOnlyDigit(strServerPort)) {
            	MainThread.setPort(Integer.valueOf(strServerPort));
            	System.out.println("Server Port = " + strServerPort);
            }
        } catch (Exception e) {
        }
        // ������ֱ�ӽ�����������ƽ���
        new ServerFrame();
    }
}
