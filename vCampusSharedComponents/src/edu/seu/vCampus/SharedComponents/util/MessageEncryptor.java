/**
 * MessageContainer压缩加密工具类
 * @author The JASYPT Team (http://www.jasypt.org/), Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.jasypt.util.binary.BasicBinaryEncryptor;

import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.InflaterInputStream;
import edu.seu.vCampus.SharedComponents.vo.MessageContainer;

public class MessageEncryptor {
	private static final String strKey = "JKObzkAVXcJSfPPDprNMJBJi65CaUgaf"; // 预共享密钥

	public static byte[] EncryptMessage(MessageContainer msg) {
		byte[] bMsg = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(msg);
			bMsg = bos.toByteArray(); // 消息类转为二进制数据
			bMsg = jzlib(bMsg); // 压缩数据
		} catch (IOException e) {
			// ignore close exception
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		BasicBinaryEncryptor binaryEncryptor = new BasicBinaryEncryptor();
		binaryEncryptor.setPassword(strKey);
		return binaryEncryptor.encrypt(bMsg); //加密数据
	}

	public static MessageContainer DecryptMessage(byte[] msg) {
		MessageContainer retMsg = null;
		BasicBinaryEncryptor binaryEncryptor = new BasicBinaryEncryptor();
		binaryEncryptor.setPassword(strKey);
		byte[] bMsg = binaryEncryptor.decrypt(msg); // 解密数据
		bMsg = unjzlib(bMsg); // 解压数据
		ByteArrayInputStream bis = new ByteArrayInputStream(bMsg);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			retMsg = (MessageContainer) in.readObject(); // 二进制数据重新生成消息类
		} catch (ClassNotFoundException e) {
			// ignore close exception
		} catch (IOException e) {
			// ignore close exception
		} finally {
			try {
				bis.close();
			} catch (IOException ex) {
				// ignore close exception
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		return retMsg;
	}

	/**
	 * 压缩数据
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] jzlib(byte[] object) {
		byte[] data = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DeflaterOutputStream zOut = new DeflaterOutputStream(out);
			DataOutputStream objOut = new DataOutputStream(zOut);
			objOut.write(object);
			objOut.flush();
			zOut.close();
			data = out.toByteArray();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 解压被压缩的数据
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] unjzlib(byte[] object) {
		byte[] data = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(object);
			InflaterInputStream zIn = new InflaterInputStream(in);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = zIn.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			data = baos.toByteArray();
			baos.flush();
			baos.close();
			zIn.close();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
