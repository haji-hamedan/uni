package com.hajihamedan.loan.model;

import java.util.Vector;

public class Loan extends Domain {

	private int loanId;
	private String title;
	private long amount;
	private byte interestRate;
	private short paymentCount;
	private byte paymentFrequency;
	private long firstPaymentDate;
	private long createDate;
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

	@Override
	public Loan loadById(int id) {
		Loan loan = new Loan();

		return loan;
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
	public long getFirstPaymentDate() {
		return firstPaymentDate;
	}

	/**
	 * @param today
	 *          the firstPaymentDate to set
	 */
	public void setFirstPaymentDate(long today) {
		this.firstPaymentDate = today;
	}

	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}

	/**
	 * @param today
	 *          the createDate to set
	 */
	public void setCreateDate(long today) {
		this.createDate = today;
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

	public Vector<Domain> getPayments() throws Exception {
		PaymentRepo paymentRepo = new PaymentRepo();

		String condition = "loanId = " + this.getLoanId();
		Vector<Domain> payments = paymentRepo.loadByCondition(condition);

		return payments;
	}
}
