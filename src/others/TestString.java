package others;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class TestString {

	public static String splitString(String source, String from, String to) {

		String strResult = "";
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("\\").append(from).append("(.*?)\\").append(to);
		Pattern p = Pattern.compile(sBuffer.toString());//
		// 正则表达式，取//和/之间的字符串，不包括=和|
		Matcher m = p.matcher(source);
		while (m.find()) {
			strResult = m.group(1);// m.group(1)不包括这两个字符

		}
		return strResult;

	}

	public static void main(String[] args) {
		String mysql = "jdbc:mysql://localhost:3309/metastore_db?createDatabaseIfNotExist=true";
		System.out.println(StringUtils.substringBetween(mysql, "//", "/"));

		String oracle = "jdbc:oracle:thin:@127.0.0.1:5090:oracle_db";

		System.out.println(StringUtils.substringAfter(oracle, "@"));
		System.out.println(StringUtils.lastIndexOf(oracle, ":"));

	}

}
