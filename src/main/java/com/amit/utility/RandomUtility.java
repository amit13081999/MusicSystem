package com.amit.utility;

import java.security.SecureRandom;

public class RandomUtility {
	private static final String ALPHA_NUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final SecureRandom RND = new SecureRandom();

	public static String getUniqueName() {
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++)
			sb.append(ALPHA_NUMERIC.charAt(RND.nextInt(ALPHA_NUMERIC.length())));
		return sb.toString();
	}
}
