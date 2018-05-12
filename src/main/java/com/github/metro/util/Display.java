package com.github.metro.util;

import java.math.BigDecimal;

public class Display {
	 public static String number(double number) {
		return new BigDecimal(number)
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
