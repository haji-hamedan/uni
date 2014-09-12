package com.hajihamedan.loan.model;

public class Loan extends Domain {

	private int loanId;
	private String title;
	private long amount;
	private byte interestRate;
	private short paymentCount;
	private byte paymentFrequency;
	private String firstPaymentDate;
	private String createDate;
	private int userId;

	public static final int YEARLY_PAYMENT = 1;
	public static final int MONTHLY_PAYMENT = 2;

	protected String[] dbProps = { "loanId", "title", "amount", "interestRate",
			"paymentCount", "paymentFrequency", "firstPaymentDate", "createDate",
			"userId" };

	public Loan() {
		this.repoName = "LoanRepo";

	}

	@Override
	public int getIdProperty() {
		return this.getLoanId();
	}

	@Override
	public void setIdProperty(int id) {
		this.setLoanId(id);
	}

	@Override
	public String[] getDbProps() {
		return this.dbProps;
	}

	/**
	 * @return the loanId
	 */
	public int getLoanId() {
		return loanId;
	}

	/**
	 * @param loanId
	 */
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *          the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the interest rate
	 */
	public byte getInterestRate() {
		return interestRate;
	}

	/**
	 * @param rate
	 *          the rate to set
	 */
	public void setInterestRate(byte interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the paymentCount
	 */
	public short getPaymentCount() {
		return paymentCount;
	}

	/**
	 * @param paymentCount
	 *          the paymentCount to set
	 */
	public void setPaymentCount(short paymentCount) {
		this.paymentCount = paymentCount;
	}

	/**
	 * @return the paymentFrequency
	 */
	public byte getPaymentFrequency() {
		return paymentFrequency;
	}

	/**
	 * @param paymentFrequency
	 *          the paymentFrequency to set
	 */
	public void setPaymentFrequency(byte paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	/**
	 * @return the firstPaymentDate
	 */
	public String getFirstPaymentDate() {
		return firstPaymentDate;
	}

	/**
	 * @param firstPaymentDate
	 *          the firstPaymentDate to set
	 */
	public void setFirstPaymentDate(String firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *          the createDate to set
	 */
	public void setCreateDate(String createDate) {
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
