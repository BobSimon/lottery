package global.hh.algorithms.lottery.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import global.hh.algorithms.lottery.Luck;
import global.hh.algorithms.lottery.exception.MaxDecimalLengthException;
import global.hh.algorithms.lottery.util.tuple.Tuple2;
import global.hh.algorithms.lottery.util.tuple.Tuples;

/**
 * Description:
 *
 * 抽奖算法
 *
 * @author Moss Zeng
 * @date 2018年3月13日下午4:45:18
 */
public class LuckUtil {
	private final static Random r = new Random();

	/**
	 * 小数点最多位数，因为随机数用int生成，不能超过范围
	 */
	private final static int MAX_DECIMAL_LENGTH = 9;

	public static <T extends Luck> T choose(List<T> lucks) {
		if (null == lucks || lucks.isEmpty()) {
			return null;
		}
		Tuple2<Boolean, List<T>> desc = doFilter(lucks);
		if (null == desc) {
			return null;
		}
		if (desc.getT1()) {
			return desc.getT2().get(0);
		}
		int maxLength = maxDecimalLength(desc.getT2());
		BigDecimal den = BigDecimal.TEN.pow(maxLength);
		List<Tuple2<Integer, Integer>> list = luckToRange(desc.getT2(), den);
		int length = den.intValue();
		int num = r.nextInt(length) + 1;
		for (int j = 0; j < list.size(); j++) {
			if (num <= list.get(j).getT2()) {
				return desc.getT2().get(j);
			}
		}
		return null;
	}

	/**
	 * 
	 * <=0的，永远不中，直接过滤 >=1的，肯定中，直接返回，不用抽
	 * 
	 * @param lucks
	 * @return
	 */
	public static <T extends Luck> Tuple2<Boolean, List<T>> doFilter(List<T> lucks) {
		List<T> copyLucks = SerializationListUtils.deepCopy(lucks);
		List<T> r = new ArrayList<>();
		Iterator<T> it = copyLucks.iterator();
		while (it.hasNext()) {
			T t = it.next();
			// 如果不是（0,1）之间的数
			if (!StringUtil.is0And1(t.getChance())) {
				double c = Double.parseDouble(t.getChance());
				if (c <= 0) {
					continue;
				} else {
					return Tuples.of(true, Arrays.asList(t));
				}
			}
			r.add(t);
		}
		if (!r.isEmpty()) {
			return Tuples.of(false, r);
		}
		return null;
	}

	/**
	 * 算出分母 计算小数点的最长位数 为方便计算，分子分母并没有除以最大公约数
	 * 
	 * @param lucks
	 * @return
	 */
	public static <T extends Luck> int maxDecimalLength(List<T> lucks) {
		int maxLength = 1;
		for (Luck luck : lucks) {
			String s = luck.getChance().split("\\.")[1];
			int sl = s.length();
			if (sl > MAX_DECIMAL_LENGTH) {
				throw new MaxDecimalLengthException("The length of the decimal  are more than " + MAX_DECIMAL_LENGTH);
			}
			if (sl > maxLength) {
				maxLength = sl;
			}
		}
		return maxLength;
	}

	/**
	 * 
	 * 计算出每项的数值范围
	 * 
	 * @param lucks
	 * 
	 * @param den
	 *            分母
	 * @return
	 */
	public static <T extends Luck> List<Tuple2<Integer, Integer>> luckToRange(List<T> lucks, BigDecimal den) {
		List<Tuple2<Integer, Integer>> list = new ArrayList<>();
		for (int i = 0; i < lucks.size(); i++) {
			if (i == 0) {
				list.add(new Tuple2<Integer, Integer>(1,
						new BigDecimal(lucks.get(i).getChance()).multiply(den).intValue()));
			} else {
				list.add(new Tuple2<Integer, Integer>(list.get(i - 1).getT2() + 1,
						list.get(i - 1).getT2() + new BigDecimal(lucks.get(i).getChance()).multiply(den).intValue()));
			}
		}
		return list;
	}
}
