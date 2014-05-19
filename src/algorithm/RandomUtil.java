package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	/**
	 * 使用系统时间和随机数产生一个与时间相关的一个随机数<br>
	 * 当前采用策略，当前时间（毫秒级）去掉头两位+4为随机数<br>
	 * 保证ID在长度15<br>
	 * 由于开发机的CPU获取的系统时间精度为16毫秒，所以不能够保证每毫秒的四位随机，仅能够保证每16毫秒的四位随机
	 * 
	 * @return 产生的随机数
	 */
	public static long generateRandomWithTime() {
		// 获取当前时间
		// 获取到的时间13位，单位毫秒
		long time = System.currentTimeMillis();

		// 获取一个随机数字
		Random r = new Random();
		long v = r.nextInt(9999);

		// 组合两个值，生成一个新的与时间有关随机值
		// 组合方式为字符串形式加和
		long n = Long.valueOf(String.valueOf(time).substring(2)) * 10000 + v;

		return n;
	}

	/**
	 * @param digit
	 *            随机数的位数
	 * @return 返回数字字符串
	 */
	public static String generateStrRandom(int digit) throws Exception {

		if (digit <= 0) {
			throw new Exception("Random number digit is negative!");
		}
		String strRand = "";
		Random r = new Random();
		for (int j = 0; j < digit; j++) {
			for (int i = 0; i < 1; i++) { // 生成1位随机数
				strRand = strRand + r.nextInt(10);
			}
		}
		return strRand;
	}

	public static void main(String[] args) {
		List<Long> ll = new ArrayList<Long>();
		long index = 0;
		while (true) {
			long l = RandomUtil.generateRandomWithTime();
			System.out.println(l + ":" + index++);
			if (ll.contains(l)) {
				break;
			} else {
				ll.add(l);
			}
		}
		try {
			System.out.println("random str is : " + RandomUtil.generateStrRandom(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}