package com.hajihamedan.loan.model;

public class LoanRepo extends Repo {

	public LoanRepo() {
		this.className = "Loan";
		this.idProperty = "loanId";
		this.tableName = "loans";
	}

}
