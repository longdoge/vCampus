/**
 * 处理模块与服务器交互数据的类
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.client.biz.util;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import edu.seu.vCampus.SharedComponents.util.MessageEncryptor;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;

public class RequestHandler {
	// 服务器端IP地址
	private static String serverIP = "127.0.0.1";
	// 服务器端端口号
	private static int port = 55555;

	public static MessageContainer sendRequest(MessageContainer messageToServer) {
		if (messageToServer == null)
			return null;
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		MessageContainer messageFromServer = null;
		try {
			// 建立连接
			socket = new Socket(serverIP, port);
			// 初始化流
			os = socket.getOutputStream();
			is = socket.getInputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			messageToServer = new MessageContainer(MessageEncryptor.EncryptMessage(messageToServer)); //加密数据
			oos.writeObject(messageToServer);
			oos.flush(); // 缓冲流
			ObjectInputStream oin = new ObjectInputStream(is);
			try {
				// 将接收到的数据映射到MessageContainer
				messageFromServer = (MessageContainer) oin.readObject();
				messageFromServer = MessageEncryptor.DecryptMessage(messageFromServer.binaryParameters); // 解密数据
				oin.close();
				oos.close();
			} catch (Exception e) {
				messageFromServer = null;
			}
		} catch (Exception e) {
			System.out.println("无法连接至服务器！");
			messageFromServer = null;
		} finally {
			try {
				// 关闭流和连接
				is.close();
				os.close();
				socket.close();
				// System.out.println("连接已关闭");
			} catch (Exception e2) {
			}
		}
		return messageFromServer;
	}

	/**
	 * @return the serverIP
	 */
	public static String getServerIP() {
		return serverIP;
	}

	/**
	 * @param serverIP
	 *            the serverIP to set
	 */
	public static void setServerIP(String serverIP) {
		RequestHandler.serverIP = serverIP;
	}

	/**
	 * @return the port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public static void setPort(int port) {
		RequestHandler.port = port;
	}
}
