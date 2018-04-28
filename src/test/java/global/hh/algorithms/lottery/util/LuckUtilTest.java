package global.hh.algorithms.lottery.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import global.hh.algorithms.lottery.Luck;

public class LuckUtilTest {

	@Test
	public void choose() {
		Map<Prize, Integer> countPrize = new HashMap<>();
		List<Prize> ps = Arrays.asList(new Prize("菠萝", "0.1"), new Prize("芒果", "-2"), new Prize("香蕉", "0.2"),
				new Prize("苹果", "0.0"), new Prize("草莓", "0.5"));
		int times = 1000;
		Prize p;
		for (int i = 0; i < times; i++) {
			p = LuckUtil.choose(ps);
			if (null != countPrize.get(p)) {
				countPrize.put(p, countPrize.get(p) + 1);
			} else {
				countPrize.put(p, 1);
			}
		}
		countPrize.forEach((k, v) -> {
			System.out.println((null == k ? "不中" : k.getName()) + " : " + v);
		});
	}

	static class Prize implements Luck {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5709773408639033589L;
		private String name;
		private String chance;

		public Prize(String name, String chance) {
			super();
			this.name = name;
			this.chance = chance;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getChance() {
			return chance;
		}

		public void setChance(String chance) {
			this.chance = chance;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((chance == null) ? 0 : chance.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Prize other = (Prize) obj;
			if (chance == null) {
				if (other.chance != null)
					return false;
			} else if (!chance.equals(other.chance))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}
}
