/**
 * ����ģ����������������ݵ���
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
	// ��������IP��ַ
	private static String serverIP = "127.0.0.1";
	// �������˶˿ں�
	private static int port = 55555;

	public static MessageContainer sendRequest(MessageContainer messageToServer) {
		if (messageToServer == null)
			return null;
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		MessageContainer messageFromServer = null;
		try {
			// ��������
			socket = new Socket(serverIP, port);
			// ��ʼ����
			os = socket.getOutputStream();
			is = socket.getInputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			messageToServer = new MessageContainer(MessageEncryptor.EncryptMessage(messageToServer)); //��������
			oos.writeObject(messageToServer);
			oos.flush(); // ������
			ObjectInputStream oin = new ObjectInputStream(is);
			try {
				// �����յ�������ӳ�䵽MessageContainer
				messageFromServer = (MessageContainer) oin.readObject();
				messageFromServer = MessageEncryptor.DecryptMessage(messageFromServer.binaryParameters); // ��������
				oin.close();
				oos.close();
			} catch (Exception e) {
				messageFromServer = null;
			}
		} catch (Exception e) {
			System.out.println("�޷���������������");
			messageFromServer = null;
		} finally {
			try {
				// �ر���������
				is.close();
				os.close();
				socket.close();
				// System.out.println("�����ѹر�");
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
