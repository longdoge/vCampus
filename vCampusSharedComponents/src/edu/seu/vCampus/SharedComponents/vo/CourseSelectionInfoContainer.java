/**
 * Ñ¡¿ÎÐÅÏ¢ÈÝÆ÷
 * @author Shangfu Duan
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

public class CourseSelectionInfoContainer extends EntityModel {
	private static final long serialVersionUID = 1L;
	public int nCsID, nTeacherID, nStudentID, nType;
	public String strDate;

	/**
	 * @return the nCsID
	 */
	public int getnCsID() {
		return nCsID;
	}

	/**
	 * @param nCsID
	 *            the nCsID to set
	 */
	public void setnCsID(int nCsID) {
		this.nCsID = nCsID;
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
	 * @return the nStudentID
	 */
	public int getnStudentID() {
		return nStudentID;
	}

	/**
	 * @param nStudentID
	 *            the nStudentID to set
	 */
	public void setnStudentID(int nStudentID) {
		this.nStudentID = nStudentID;
	}

	/**
	 * @return the nType
	 */
	public int getnType() {
		return nType;
	}

	/**
	 * @param nType
	 *            the nType to set
	 */
	public void setnType(int nType) {
		this.nType = nType;
	}

	/**
	 * @return the strDate
	 */
	public String getStrDate() {
		return strDate;
	}

	/**
	 * @param strDate
	 *            the strDate to set
	 */
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
}
