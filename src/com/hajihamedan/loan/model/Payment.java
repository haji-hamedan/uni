package com.hajihamedan.loan.model;

public class Payment extends Domain {
	private int paymentId;
	private int loanId;
	private long amount;
	private long payDate;
	private long createDate;
	private int userId;

	protected String[] dbProps = { "paymentId", "loanId", "amount", "payDate",
			"createDate", "userId" };

	public Payment() {
		this.repoName = "PaymentRepo";
	}

	@Override
	public int getIdProperty() {
		return this.getPaymentId();
	}

	@Override
	public void setIdProperty(int id) {
		this.setPaymentId(id);
	}

	@Override
	public String[] getDbProps() {
		return this.dbProps;
	}

	@Override
	public Object loadById(int id) {
		Payment payment = new Payment();
		return payment;
	}

	/**
	 * @return the paymentId
	 */
	public int getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId
	 *          the paymentId to set
	 */
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the loanId
	 */
	public int getLoanId() {
		return loanId;
	}

	/**
	 * @param loanId
	 *          the loanId to set
	 */
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	/**
	 * @return the amount
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *          the amount to set
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}

	/**
	 * @return the date
	 */
	public long getPayDate() {
		return payDate;
	}

	/**
	 * @param date
	 *          the date to set
	 */
	public void setPayDate(long payDate) {
		this.payDate = payDate;
	}

	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *          the createDate to set
	 */
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *          the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
