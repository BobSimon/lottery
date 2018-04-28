package global.hh.algorithms.lottery;

import java.io.Serializable;

/**
 * Description:
 *
 * 抽奖概率
 *
 * @author Moss Zeng
 * @date 2018年3月26日上午9:36:39
 */
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
