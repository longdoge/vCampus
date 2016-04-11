/**
 * 服务器主线程管理类
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
	private static int port = 55555; // 监听端口号

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
            // 关闭连接
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
            DatabaseManager.closeDatabase();
            System.out.println("vCampus Server 已停止");
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // 连接数据库
            try {
                DatabaseManager.connectDatabase();
            } catch (Exception se) {
            	se.printStackTrace();
                System.out.println("数据库连接失败！");
                return;
            }
            // 建立连接
            serverSocket = new ServerSocket(port);
            // 退出时自动关闭
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    close();
                }
            });
            System.out.println("vCampus Server 已启动");
            while (true) {
                // 获得连接
                socket = serverSocket.accept();
                // 启动线程
                new RequestHandler(socket);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
