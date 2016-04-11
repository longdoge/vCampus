package edu.seu.vCampus.SharedComponents.vo;

public class vGrade extends EntityModel {
    private static final long serialVersionUID = 1L;
    public int grId;
    public int uId;
    public int cId;
    public String cName;
    public String cType;
    public String cTime;
    public float grRaw;
    public String tName;
    public float credit;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public int getGrId() {
        return grId;
    }

    public void setGrId(int grId) {
        this.grId = grId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public float getGrRaw() {
        return grRaw;
    }

    public void setGrRaw(float grRaw) {
        this.grRaw = grRaw;
    }

}
