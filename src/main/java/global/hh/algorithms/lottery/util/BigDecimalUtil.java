package global.hh.algorithms.lottery.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

	public static BigDecimal add(Number... numbers) {
		int length = numbers.length;
		switch (length) {
		case 0:
			return new BigDecimal("0");
		case 1:
			return new BigDecimal(numbers[0].toString());
		default:
			BigDecimal sum = new BigDecimal(numbers[0].toString());
			for (int i = 1; i < length; i++) {
				sum = sum.add(new BigDecimal(numbers[i].toString()));
			}
			return sum;
		}
	}
	
	public static BigDecimal add(String... strs) {
		int length = strs.length;
		switch (length) {
		case 0:
			return new BigDecimal("0");
		case 1:
			return new BigDecimal(strs[0]);
		default:
			BigDecimal sum = new BigDecimal(strs[0]);
			for (int i = 1; i < length; i++) {
				sum = sum.add(new BigDecimal(strs[i]));
			}
			return sum;
		}
	}

	public static BigDecimal subtract(Number one, Number two) {
		return new BigDecimal(one.toString()).subtract(new BigDecimal(two.toString()));
	}

	public static BigDecimal multiply(Number... numbers) {
		int length = numbers.length;
		switch (length) {
		case 0:
			return new BigDecimal("0");
		case 1:
			return new BigDecimal(numbers[0].toString());
		default:
			BigDecimal sum = new BigDecimal(numbers[0].toString());
			for (int i = 1; i < length; i++) {
				sum = sum.multiply(new BigDecimal(numbers[i].toString()));
			}
			return sum;
		}
	}
	public static BigDecimal multiply(String... strs) {
		int length = strs.length;
		switch (length) {
		case 0:
			return new BigDecimal("0");
		case 1:
			return new BigDecimal(strs[0]);
		default:
			BigDecimal sum = new BigDecimal(strs[0]);
			for (int i = 1; i < length; i++) {
				sum = sum.multiply(new BigDecimal(strs[i]));
			}
			return sum;
		}
	}

	public static BigDecimal divide(Number one, Number two) {
		return new BigDecimal(one.toString()).divide(new BigDecimal(two.toString()));
	}
}
