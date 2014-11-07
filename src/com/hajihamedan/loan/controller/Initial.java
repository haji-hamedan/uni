package com.hajihamedan.loan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hajihamedan.loan.model.DataHandler;

public class Initial {

	public void index(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DataHandler dh = DataHandler.getInstance();

		String query_loans = "CREATE TABLE \"LOANS\" "
				+ "(	\"LOANID\" NUMBER(12,0) NOT NULL ENABLE,"
				+ "\"TITLE\" VARCHAR2(255), " + "\"AMOUNT\" NUMBER(21,0), "
				+ "\"INTERESTRATE\" NUMBER(3,0), "
				+ "\"PAYMENTCOUNT\" NUMBER(5,0), "
				+ "\"PAYMENTFREQUENCY\" NUMBER(1,0), "
				+ "\"FIRSTPAYMENTDATE\" NUMBER(13,0), "
				+ "\"CREATEDATE\" NUMBER(13,0), " + "\"USERID\" NUMBER(12,0), "
				+ " CONSTRAINT \"LOANS_PK\" PRIMARY KEY (\"LOANID\") ENABLE"
				+ ") ;";

		String query_payments = "CREATE TABLE  \"PAYMENTS\" "
				+ "(	\"PAYMENTID\" NUMBER(12,0) NOT NULL ENABLE, "
				+ "\"LOANID\" NUMBER(12, 0) NOT NULL, "
				+ "\"AMOUNT\" NUMBER(21,0), "
				+ "\"PAYDATE\" NUMBER(13,0), "
				+ "\"CREATEDATE\" NUMBER(13,0), "
				+ "\"USERID\" NUMBER(12,0), "
				+ "CONSTRAINT \"PAYMENTS_PK\" PRIMARY KEY (\"PAYMENTID\") ENABLE"
				+ ") ;";

		String query_users = "CREATE TABLE \"USERS\" "
				+ "(	\"USERID\" NUMBER(12,0) NOT NULL ENABLE,"
				+ "\"USERNAME\" VARCHAR2(255), " + "\"EMAIL\" VARCHAR2(255), "
				+ "\"PASSWORD\" VARCHAR2(255), "
				+ "\"FIRSTNAME\" VARCHAR2(255), "
				+ "\"LASTNAME\" VARCHAR2(255), " + "\"MOBILE\" VARCHAR2(255), "
				+ " CONSTRAINT \"USERS_PK\" PRIMARY KEY (\"USERID\") ENABLE"
				+ ") ;";

		dh.query(query_loans);
		dh.query(query_payments);
		dh.query(query_users);

	}

}
