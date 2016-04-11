/**
 * ¿Î³ÌÐÅÏ¢ÈÝÆ÷
 * @author Shangfu Duan
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

public class CourseInfoContainer extends EntityModel {
	private static final long serialVersionUID = 1L;
	public String strName, strCourseDate, strBeginDate, strEndDate, strIntro;
	public String[] strStuNo;
	public int nCourseID, nSemester, nTeacherID, nStuNum, nMaxStuNum;
	public float fCredit;
	public boolean cDeleted;

	/**
	 * @return the cDeleted
	 */
	public boolean iscDeleted() {
		return cDeleted;
	}

	/**
	 * @param cDeleted the cDeleted to set
	 */
	public void setcDeleted(boolean cDeleted) {
		this.cDeleted = cDeleted;
	}

	/**
	 * @return the strName
	 */
	public String getStrName() {
		return strName;
	}

	/**
	 * @param strName
	 *            the strName to set
	 */
	public void setStrName(String strName) {
		this.strName = strName;
	}

	/**
	 * @return the strCourseDate
	 */
	public String getStrCourseDate() {
		return strCourseDate;
	}

	/**
	 * @param strCourseDate
	 *            the strCourseDate to set
	 */
	public void setStrCourseDate(String strCourseDate) {
		this.strCourseDate = strCourseDate;
	}

	/**
	 * @return the strBeginDate
	 */
	public String getStrBeginDate() {
		return strBeginDate;
	}

	/**
	 * @param strBeginDate
	 *            the strBeginDate to set
	 */
	public void setStrBeginDate(String strBeginDate) {
		this.strBeginDate = strBeginDate;
	}

	/**
	 * @return the strEndDate
	 */
	public String getStrEndDate() {
		return strEndDate;
	}

	/**
	 * @param strEndDate
	 *            the strEndDate to set
	 */
	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}

	/**
	 * @return the strIntro
	 */
	public String getStrIntro() {
		return strIntro;
	}

	/**
	 * @param strIntro
	 *            the strIntro to set
	 */
	public void setStrIntro(String strIntro) {
		this.strIntro = strIntro;
	}

	/**
	 * @return the strStuNo
	 */
	public String[] getStrStuNo() {
		return strStuNo;
	}

	/**
	 * @param strStuNo
	 *            the strStuNo to set
	 */
	public void setStrStuNo(String[] strStuNo) {
		this.strStuNo = strStuNo;
	}

	/**
	 * @return the nCourseID
	 */
	public int getnCourseID() {
		return nCourseID;
	}

	/**
	 * @param nCourseID
	 *            the nCourseID to set
	 */
	public void setnCourseID(int nCourseID) {
		this.nCourseID = nCourseID;
	}

	/**
	 * @return the nSemester
	 */
	public int getnSemester() {
		return nSemester;
	}

	/**
	 * @param nSemester
	 *            the nSemester to set
	 */
	public void setnSemester(int nSemester) {
		this.nSemester = nSemester;
	}

	/**
	 * @return the nTeacherID
	 */
	public int getnTeacherID() {
		return nTeacherID;
	}

	/**
	 * @param nTeacherID
	 *            the nTeacherID to set
	 */
	public void setnTeacherID(int nTeacherID) {
		this.nTeacherID = nTeacherID;
	}

	/**
	 * @return the nStuNum
	 */
	public int getnStuNum() {
		return nStuNum;
	}

	/**
	 * @param nStuNum
	 *            the nStuNum to set
	 */
	public void setnStuNum(int nStuNum) {
		this.nStuNum = nStuNum;
	}

	/**
	 * @return the nMaxStuNum
	 */
	public int getnMaxStuNum() {
		return nMaxStuNum;
	}

	/**
	 * @param nMaxStuNum
	 *            the nMaxStuNum to set
	 */
	public void setnMaxStuNum(int nMaxStuNum) {
		this.nMaxStuNum = nMaxStuNum;
	}

	/**
	 * @return the fCredit
	 */
	public float getfCredit() {
		return fCredit;
	}

	/**
	 * @param fCredit
	 *            the fCredit to set
	 */
	public void setfCredit(float fCredit) {
		this.fCredit = fCredit;
	}
}
