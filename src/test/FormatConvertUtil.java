package test;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class FormatConvertUtil {

	/**
	 * properties结构转换成Map结构
	 * 
	 * @param props
	 * @return Map
	 */
	public static Map<String, String> propertiesToMap(Properties props) {
		Preconditions.checkState(props == null || props.isEmpty(), " input param:props is null!");

		Map<String, String> m = new HashMap<String, String>();

		Set set = props.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			m.put(key, props.getProperty(key));
		}

		return m;
	}

	/**
	 * 将list转换成string ,以分隔符号分隔
	 * 
	 * @param listSource
	 * @param stringSeparator
	 * @return a string Separated by stringSeparator
	 */
	public static String listToString(List listSource, String strSeparator) {
		Preconditions.checkNotNull(listSource, "input param:listSource is null!");
		Preconditions.checkNotNull(strSeparator, "input param:stringSeparator is null!");

		return Joiner.on(strSeparator).join(listSource);

	}

	/**
	 * 将string转换成 list,string以分隔符号分隔
	 * 
	 * @param stringSource
	 * @param stringSeparator
	 * @return List<String>
	 */
	public static List<String> stringToList(String strSource, String strSeparator) {
		Preconditions.checkNotNull(strSource, "input param:strSource is null!");
		Preconditions.checkNotNull(strSeparator, "input param:strSeparator is null!");

		List<String> listStr = Lists.newArrayList();
		Iterable<String> iterable = Splitter.on(strSeparator).trimResults().omitEmptyStrings().split(strSource);
		for (String stmp : iterable) {
			listStr.add(stmp);
		}

		return listStr;

	}

	/**
	 * Dictionary类向Map类转换的函数<br>
	 * 此处的Map使用HashMap
	 * 
	 * @param dic
	 *            Dictionary类对象
	 * @return Map类型对象
	 */
	public static Map dictionaryToMap(Dictionary dic) {
		Preconditions.checkNotNull(dic, "input param:dic is null!");

		Map m = new HashMap();
		Enumeration iter = dic.keys();
		while (iter.hasMoreElements()) {
			Object key = iter.nextElement();
			m.put(key, dic.get(key));
		}
		return m;
	}

	/**
	 * Map类向Dictionary类转换的函数<br>
	 * 此处的Dictionary使用Hashtable
	 * 
	 * @param m
	 *            Map类对象
	 * @return Dictionary类对象
	 */
	public static Dictionary mapToDictionary(Map m) {
		Preconditions.checkNotNull(m, "input param:m is null!");

		Dictionary dic = new Hashtable();
		for (Object key : m.keySet()) {
			dic.put(key, m.get(key));
		}
		return dic;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String tmp1 = "a,b,,,,,c,d , e , ";
		System.out.println(stringToList(tmp1, ","));

		List<String> listStr = Lists.newArrayList();
		listStr.add("aa");
		listStr.add("bb");
		listStr.add("cc ");
		listStr.add("");
		listStr.add(" dd .");
		System.out.println(listToString(listStr, ","));

	}
}
