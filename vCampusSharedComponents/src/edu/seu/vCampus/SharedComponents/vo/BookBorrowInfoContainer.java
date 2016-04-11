/**
 * 图书借阅行为信息容器
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

public class BookBorrowInfoContainer extends EntityModel {
    private static final long serialVersionUID = 1L;
    public int nBorId, nUserId, nBookId;
    public String strBorrowDate, strReturnDate;

    /**
     * @return the nBorId
     */
    public int getnBorId() {
        return nBorId;
    }

    /**
     * @param nBorId
     *            the nBorId to set
     */
    public void setnBorId(int nBorId) {
        this.nBorId = nBorId;
    }

    /**
     * @return the nUserId
     */
    public int getnUserId() {
        return nUserId;
    }

    /**
     * @param nUserId
     *            the nUserId to set
     */
    public void setnUserId(int nUserId) {
        this.nUserId = nUserId;
    }

    /**
     * @return the nBookId
     */
    public int getnBookId() {
        return nBookId;
    }

    /**
     * @param nBookId
     *            the nBookId to set
     */
    public void setnBookId(int nBookId) {
        this.nBookId = nBookId;
    }

    /**
     * @return the strBorrowDate
     */
    public String getStrBorrowDate() {
        return strBorrowDate;
    }

    /**
     * @param strBorrowDate
     *            the strBorrowDate to set
     */
    public void setStrBorrowDate(String strBorrowDate) {
        this.strBorrowDate = strBorrowDate;
    }

    /**
     * @return the strReturnDate
     */
    public String getStrReturnDate() {
        return strReturnDate;
    }

    /**
     * @param strReturnDate
     *            the strReturnDate to set
     */
    public void setStrReturnDate(String strReturnDate) {
        this.strReturnDate = strReturnDate;
    }
}
