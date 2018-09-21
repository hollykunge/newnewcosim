package com.casic.cloud.pub.util;

public class PasswordUtil {
	public static final int Int_WORD = 1;
	public static final int STRING_WORD = 2;
	public static final int MIX_WORD = 3;

	public static String getPassWord(int style, int length) {
		if (style == Int_WORD) {
			return getIntRandom(length);
		} else if (style == STRING_WORD) {
			return getCharRandom(length);
		} else if (style == MIX_WORD) {
			return getMixRandom(length);
		}
		return getMixRandom(length);
	}

	private static String getIntRandom(int length) {
		int[] array = new int[length];
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
			array[i] = (int) (Math.random() * 10);
			str.append(array[i]);
		}
		return str.toString();
	}

	private static String getCharRandom(int length) {
		int[] array = new int[length];
		char[] chars = new char[length];
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
			while (true) {
				array[i] = (int) (Math.random() * 1000);
				if ((array[i] > 64 && array[i] < 91)
						|| (array[i] > 96 && array[i] < 123))
					break;
			}
			chars[i] = (char) array[i];
			str.append(chars[i]);
		}
		return str.toString();
	}

	private static String getMixRandom(int length) {
		int[] array = new int[length];
		char[] chars = new char[length];
		StringBuilder str = new StringBuilder();
		int temp = 0;
		for (int i = 0; i < length; i++) {
			while (true) {
				temp = (int) (Math.random() * 1000);
				if (temp < 128)
					break;
			}
			array[i] = temp;
			chars[i] = (char) array[i];
			str.append(chars[i]);
		}
		return str.toString();
	}
}