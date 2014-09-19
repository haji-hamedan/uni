package com.hajihamedan.loan.model;

public class PaymentRepo extends Repo {

	public PaymentRepo() {
		this.className = "Payment";
		this.idProperty = "paymentId";
		this.tableName = "payments";
	}

}
