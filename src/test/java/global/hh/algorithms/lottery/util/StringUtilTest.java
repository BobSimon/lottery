package global.hh.algorithms.lottery.util;

import org.junit.Test;

public class StringUtilTest {
	
	@Test
	public void is0And1() {
		assert StringUtil.is0And1("0.2") == true;
		assert StringUtil.is0And1("0.20") == true;
		assert StringUtil.is0And1("0.020") == true;
		assert StringUtil.is0And1("0.0") == false;
		assert StringUtil.is0And1("1.0") == false;
		assert StringUtil.is0And1("1.020") == false;
		assert StringUtil.is0And1("1.020") == false;
		assert StringUtil.is0And1("-0.2") == false;
	}

}
