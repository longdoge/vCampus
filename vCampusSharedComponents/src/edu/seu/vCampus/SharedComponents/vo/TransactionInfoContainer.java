/**
 * 交易信息容器
 * @author Yi Yang
 * @version 1.0
 */

package edu.seu.vCampus.SharedComponents.vo;

public class TransactionInfoContainer extends EntityModel {
	private static final long serialVersionUID = 1L;
	public int nTransactionID;
	public int lOutID;
	public int lInID;
	public float fBalanceout;
	public float fBalancein;
	public int lOperation;
	public float fAmount;
	public String strMemo;
	public String strName;
	public String strDate;

	/**
	 * @return the strDate
	 */
	public String getStrDate() {
		return strDate;
	}

	/**
	 * @param strDate the strDate to set
	 */
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	/**
	 * @return the strMemo
	 */
	public String getStrMemo() {
		return strMemo;
	}

	/**
	 * @param strMemo
	 *            the strMemo to set
	 */
	public void setStrMemo(String strMemo) {
		this.strMemo = strMemo;
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
	 * @return the nTransactionID
	 */
	public int getnTransactionID() {
		return nTransactionID;
	}

	/**
	 * @param nTransactionID
	 *            the nTransactionID to set
	 */
	public void setnTransactionID(int nTransactionID) {
		this.nTransactionID = nTransactionID;
	}

	/**
	 * @return the lOutID
	 */
	public int getlOutID() {
		return lOutID;
	}

	/**
	 * @param lOutID
	 *            the lOutID to set
	 */
	public void setlOutID(int lOutID) {
		this.lOutID = lOutID;
	}

	/**
	 * @return the lInID
	 */
	public int getlInID() {
		return lInID;
	}

	/**
	 * @param lInID
	 *            the lInID to set
	 */
	public void setlInID(int lInID) {
		this.lInID = lInID;
	}

	/**
	 * @return the fBalanceout
	 */
	public float getfBalanceout() {
		return fBalanceout;
	}

	/**
	 * @param fBalanceout
	 *            the fBalanceout to set
	 */
	public void setfBalanceout(float fBalanceout) {
		this.fBalanceout = fBalanceout;
	}

	/**
	 * @return the fBalancein
	 */
	public float getfBalancein() {
		return fBalancein;
	}

	/**
	 * @param fBalancein
	 *            the fBalancein to set
	 */
	public void setfBalancein(float fBalancein) {
		this.fBalancein = fBalancein;
	}

	/**
	 * @return the lOperation
	 */
	public int getlOperation() {
		return lOperation;
	}

	/**
	 * @param lOperation
	 *            the lOperation to set
	 */
	public void setlOperation(int lOperation) {
		this.lOperation = lOperation;
	}

	/**
	 * @return the fAmount
	 */
	public float getfAmount() {
		return fAmount;
	}

	/**
	 * @param fAmount
	 *            the fAmount to set
	 */
	public void setfAmount(float fAmount) {
		this.fAmount = fAmount;
	}

}
