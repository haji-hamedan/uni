package com.hajihamedan.loan.helper;

public class NumberDelimiter {

	public static String addDelimiter(int number) {
		String numberString = String.valueOf(number);

		String numberWithDelimiter = NumberDelimiter.build(numberString);
		return numberWithDelimiter;
	}

	public static String addDelimiter(long number) {
		String numberString = String.valueOf(number);

		String numberWithDelimiter = NumberDelimiter.build(numberString);
		return numberWithDelimiter;
	}

	public static String addDelimiter(byte number) {
		String numberString = String.valueOf(number);

		String numberWithDelimiter = NumberDelimiter.build(numberString);
		return numberWithDelimiter;
	}

	private static String build(String number) {
		String numberWithDelimiter = "";
		int numberLength = number.length();

		if (numberLength > 3) {
			int mod = numberLength % 3;
			int i;

			for (i = 0; i < mod; i++) {
				numberWithDelimiter += number.charAt(i);
			}

			for (i = mod; i < numberLength; i++) {
				if (i != 0 && (i - mod) % 3 == 0) {
					numberWithDelimiter += ",";
				}
				numberWithDelimiter += number.charAt(i);
			}
		} else {
			numberWithDelimiter = number;
		}

		return numberWithDelimiter;

	}
}
