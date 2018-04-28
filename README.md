请参考global.hh.algorithms.lottery.util.LuckUtilTest.choose()

公司的一些活动之类的，经常有抽奖，这里介绍一种比较简单的思路。

假设有这些奖品和他们的概率分别为

一等奖 iphone x           0.1

二等奖荣耀10              0.15

三等奖1000元京东卡        0.5

看看，我们怎么设置。

首先，找出分母，这里是100，思路如下：

所有概率中小数位的最长位数为N，则分母为10的N次方,

这里是0.15，是2，然后10的2次方==100

其次，在分母中根据概率分段，这里是

一等奖 iphone x           1-10

二等奖荣耀10              11-25

三等奖1000元京东卡        26-75

思路如下：

循环奖品，如果是第一项，则从1开始到概率*分母结束，其它的，则从前一项的最后数+1开始

具体为：

开始1，      结束0.1*100 = 10,所以[1,10]

开始10+1,  结束10+0.15*100 = 25,所以[11,25]

开始25+1,  结束25+0.5*100 = 75,所以[26,75]

再次，生成1到分母的随机数，看随机数落在什么范围，则代表中了什么奖，简单吧。

 

下面看看具体的编码过程，首先是奖品，而最重要的就是概率，这里我们建一个类

public interface Luck extends Serializable{
	/**
	 * 概率
	 * 
	 * <=0 永远不会中
	 * 
	 * >=1肯定中
	 * 
	 * @return
	 */
	String getChance();
}
 然后，我们找出分母，而分母是10的N次方，N是概率中小数位的最长位数，所以先找最长位数，代码如下：

	public static <T extends Luck> int maxDecimalLength(List<T> lucks) {
		int maxLength = 1;
		for (Luck luck : lucks) {
			String s = luck.getChance().split("\\.")[1];
			int sl = s.length();
			if (sl > maxLength) {
				maxLength = sl;
			}
		}
		return maxLength;
	}
 ，找到了N，那分母则为int length = (int) Math.pow(10, maxLength);

有了分母，下面就要进行分段，每个奖品都有一个开始数字和结束数字，所以给个简单的类吧。

public class Tuple2<T1,T2> {
	final T1 t1;
	final T2 t2;
	public Tuple2(T1 t1, T2 t2) {
		super();
		this.t1 = t1;
		this.t2 = t2;
	}
	public T1 getT1() {
		return t1;
	}
	public T2 getT2() {
		return t2;
	}
}
 t1代表开始数字，t2代表结束数字，所以分段代码如下：

/**
	 * 
	 * 计算出每项的数值范围
	 * 
	 * @param lucks
	 * 			奖项
	 * @param den
	 *            分母
	 * @return
	 */
	public static <T extends Luck> List<Tuple2<Integer, Integer>> luckToRange(List<T> lucks, Integer den) {
		List<Tuple2<Integer, Integer>> list = new ArrayList<>();
		for (int i = 0; i < lucks.size(); i++) {
			if (i == 0) {
				list.add(new Tuple2(1, BigDecimalUtil.multiply(lucks.get(i).getChance(), den.toString()).intValue()));
			} else {
				list.add(new Tuple2(list.get(i - 1).getT2() + 1, list.get(i - 1).getT2()
						+ BigDecimalUtil.multiply(lucks.get(i).getChance(), den.toString()).intValue()));
			}
		}
		return list;
	}
 分好段，就可以生成随机数了。int num = new Random().nextInt(length) + 1; （length为分母，这个例子是100），最后，再判断随机数落在什么区间。

		for (int j = 0; j < list.size(); j++) {
			if (num <= list.get(j).getT2()) {
				return desc.getT2().get(j);
			}
		}
 当然，如果都没落在区间，则return null；唉，又是什么奖都没中，一如年会时的我们。

 

 
