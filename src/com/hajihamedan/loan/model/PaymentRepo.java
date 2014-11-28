package com.hajihamedan.loan.model;

import java.util.Vector;

public class PaymentRepo extends Repo {

	public PaymentRepo() {
		this.className = "Payment";
		this.idProperty = "paymentId";
		this.tableName = "payments";
	}

	public Vector loadByLoanId(int loanId) throws Exception {
		String condition = "loanId = " + loanId;
		Vector<Domain> payments = this.loadByCondition(condition, this.idProperty, "ASC");
		return payments;
	}

	public void deleteByLoanId(int loanId) throws Exception {
		String condition = " loanId = " + loanId;
		this.deleteByCondition(condition);
	}
}
