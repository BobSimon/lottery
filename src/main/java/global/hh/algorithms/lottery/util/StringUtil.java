package global.hh.algorithms.lottery.util;

import org.apache.commons.lang3.StringUtils;

public abstract class StringUtil extends StringUtils{
	
	/**
	 * (0,1)之间的数
	 * @return
	 */
	public static boolean is0And1(String str) {
		if (isEmpty(str)) {
            return false;
        }
		return str.matches("^0\\.\\d*[1-9]+\\d*");
	}
	
}
