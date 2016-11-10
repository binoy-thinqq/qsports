package com.thinqq.qsports.client.helper;

/**
 * Write static methods inside this. Use as helper class
 * 
 */
public class ClientUtil {

	public static boolean isEmpty(final String value) {
		return (value == null || "".equals(value.trim()));
	}
	
	/**
	 * Reference commons StringUtils.java
	 * @param str
	 * @return
	 */

	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

}
