/**
 * �������ͻ���֮�����Ϣ������
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

import java.io.Serializable;

public class MessageContainer implements Serializable {
    private static final long serialVersionUID = 1L;
    public String strCommand = null, strToken = null;
    public String[] strParameters = null;
    public byte[] binaryParameters = null;
    public EntityModel[] entityParameters = null;
    
    /**
     * @return the entityParameters
     */
    public EntityModel[] getEntityParameters() {
        return entityParameters;
    }

    /**
     * @param entityParameters the entityParameters to set
     */
    public void setEntityParameters(EntityModel[] entityParameters) {
        this.entityParameters = entityParameters;
    }

    /**
     * @return the strCommand
     */
    public String getStrCommand() {
        return strCommand;
    }

    /**
     * @param strCommand the strCommand to set
     */
    public void setStrCommand(String strCommand) {
        this.strCommand = strCommand;
    }

    /**
     * @return the strToken
     */
    public String getStrToken() {
        return strToken;
    }

    /**
     * @param strToken the strToken to set
     */
    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    /**
     * @return the strParameters
     */
    public String[] getStrParameters() {
        return strParameters;
    }

    /**
     * @param strParameters the strParameters to set
     */
    public void setStrParameters(String[] strParameters) {
        this.strParameters = strParameters;
    }

    /**
     * @return the binaryParameters
     */
    public byte[] getBinaryParameters() {
        return binaryParameters;
    }

    /**
     * @param binaryParameters the binaryParameters to set
     */
    public void setBinaryParameters(byte[] binaryParameters) {
        this.binaryParameters = binaryParameters;
    }

    /**
     * �չ��캯��
     */
    public MessageContainer() {

    }

    /**
     * ���캯��������һ��������command��һ����������Ϣ
     * 
     * @param strMsgCmd
     *            command��Ϣ
     * @param strMsgParam
     *            ������Ϣ
     */
    public MessageContainer(String strMsgCmd, String strMsgParam) {
        strCommand = strMsgCmd;
        strParameters = new String[] { strMsgParam };
    }
    
    public MessageContainer(byte[] bParam) {
        binaryParameters = bParam;
    }
}
