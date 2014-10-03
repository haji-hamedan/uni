package com.hajihamedan.loan.model;

public class PaymentFrequency {
	public static final byte YEARLY_PAYMENT = 1;
	public static final byte MONTHLY_PAYMENT = 2;

	public static String getPaymentName(byte paymentFrequency) {
		switch (paymentFrequency) {
		case 1:
			return "سالیانه";
		case 2:
			return "ماهیانه";
		}

		return null;
	}

}
