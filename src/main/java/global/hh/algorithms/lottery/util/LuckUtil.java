package global.hh.algorithms.lottery.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
	/**
	 * 小数点最多位数，因为随机数用int生成，不能超过范围
	 */
	private final static int MAX_DECIMAL_LENGTH = 9;

	public static <T extends Luck> T choose(List<T> lucks) {
		if (null == lucks || lucks.isEmpty()) {
			return null;
		}
		//过滤非法概率
		Tuple2<Boolean, List<T>> desc = doFilter(lucks);
		if (null == desc) {
			return null;
		}
		//如果有概率>=1的，直接返回
		if (desc.getT1()) {
			return desc.getT2().get(0);
		}
		int maxLength = maxDecimalLength(desc.getT2());
		//分母
		int length = (int) Math.pow(10, maxLength);
		//分段
		List<Tuple2<T, Integer>> list = luckToRange(desc.getT2(), length);
		//随机数
		int num = ThreadLocalRandom.current().nextInt(1, length + 1);
		//抽
		return list.stream().filter(item -> num <= item.getT2()).map(Tuple2::getT1).findFirst().orElse(null);
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
		int maxLength = lucks.stream().map(i -> i.getChance().split("\\.")[1]).mapToInt(String::length).max().orElse(1);
		if (maxLength > MAX_DECIMAL_LENGTH) {
			throw new MaxDecimalLengthException("The length of the decimal  are more than " + MAX_DECIMAL_LENGTH);
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
	public static <T extends Luck> List<Tuple2<T, Integer>> luckToRange(List<T> lucks, Integer den) {
		List<Tuple2<T, Integer>> list = new ArrayList<>();
		int max;
		for (int i = 0; i < lucks.size(); i++) {
			if (i == 0) {
				list.add(Tuples.of(lucks.get(i),
						BigDecimalUtil.multiply(lucks.get(i).getChance(), den.toString()).intValue()));
			} else {
				max = list.get(i - 1).getT2()
						+ BigDecimalUtil.multiply(lucks.get(i).getChance(), den.toString()).intValue();
				if (max > den) {
					list.add(Tuples.of(lucks.get(i), den));
					break;
				}
				list.add(Tuples.of(lucks.get(i), max));
			}
		}
		return list;

	}
}
