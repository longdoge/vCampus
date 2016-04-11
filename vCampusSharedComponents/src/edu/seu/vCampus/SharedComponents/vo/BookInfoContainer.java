/**
 * Õº È–≈œ¢»›∆˜
 * @author Wenyu
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

public class BookInfoContainer extends EntityModel {
    private static final long serialVersionUID = 1L;
    public int nBookId, nAmount, nNum;

    /**
     * @return the nNum
     */
    public int getnNum() {
        return nNum;
    }

    /**
     * @param nNum
     *            the nNum to set
     */
    public void setnNum(int nNum) {
        this.nNum = nNum;
    }

    public String strName, strAuthor, strPress, strISBN;

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
     * @return the nAmount
     */
    public int getnAmount() {
        return nAmount;
    }

    /**
     * @param nAmount
     *            the nAmount to set
     */
    public void setnAmount(int nAmount) {
        this.nAmount = nAmount;
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
     * @return the strAuthor
     */
    public String getStrAuthor() {
        return strAuthor;
    }

    /**
     * @param strAuthor
     *            the strAuthor to set
     */
    public void setStrAuthor(String strAuthor) {
        this.strAuthor = strAuthor;
    }

    /**
     * @return the strPress
     */
    public String getStrPress() {
        return strPress;
    }

    /**
     * @param strPress
     *            the strPress to set
     */
    public void setStrPress(String strPress) {
        this.strPress = strPress;
    }

    /**
     * @return the strISBN
     */
    public String getStrISBN() {
        return strISBN;
    }

    /**
     * @param strISBN
     *            the strISBN to set
     */
    public void setStrISBN(String strISBN) {
        this.strISBN = strISBN;
    }
}
