package test;

import org.apache.commons.lang3.StringUtils;

public class TestString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] ss = StringUtils.split("a,,b,c ,d , ", ",");
		for (int i = 0; i < ss.length; i++) {
			System.out.println("$" + ss[i] + "$");
		}

	}

}
