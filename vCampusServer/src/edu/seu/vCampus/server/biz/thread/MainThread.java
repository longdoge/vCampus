/**
 * ���������̹߳�����
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.server.biz.thread;

import java.net.ServerSocket;
import java.net.Socket;

import edu.seu.vCampus.server.dao.DatabaseManager;

public class MainThread extends Thread {
    ServerSocket serverSocket = null;
    Socket socket = null;
	private static int port = 55555; // �����˿ں�

    /**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public static void setPort(int port) {
		MainThread.port = port;
	}

	public MainThread() {
        this.start();
    }

    public void close() {
        try {
            // �ر�����
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
            DatabaseManager.closeDatabase();
            System.out.println("vCampus Server ��ֹͣ");
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // �������ݿ�
            try {
                DatabaseManager.connectDatabase();
            } catch (Exception se) {
            	se.printStackTrace();
                System.out.println("���ݿ�����ʧ�ܣ�");
                return;
            }
            // ��������
            serverSocket = new ServerSocket(port);
            // �˳�ʱ�Զ��ر�
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    close();
                }
            });
            System.out.println("vCampus Server ������");
            while (true) {
                // �������
                socket = serverSocket.accept();
                // �����߳�
                new RequestHandler(socket);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
