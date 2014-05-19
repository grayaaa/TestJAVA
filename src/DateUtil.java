/**
 *
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author 工作
 *
 */
public class DateUtil {

	/**
	 * 程序通用时间格式字符串
	 */
	public static final String TIME_FORMAT = "yyyyMMddHHmmss";

	/**
	 * 转换指定的Date变量到String类型<br>
	 * 使用时间格式串：yyyyMMddHHmmss
	 *
	 * @param date
	 *            需要转换的日期
	 * @return
	 */
	public static String date2String(Date date) {
		try {
			/*
			 * SimpleDateFormat存在同步问题。如果使用全局变量或类变量，则在多线程情况下需要进行同步。所以这里使用临时变量
			 */
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 使用给定的模式串转换指定的Date变量到String类型
	 *
	 * @param date
	 *            所要转换的日期变量
	 * @param pattern
	 *            转换需要的模式串
	 * @return 转换之后的字符串
	 */
	public static String date2String(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 转换指定的字符串到Date类型<br>
	 * 使用时间格式串：yyyyMMddHHmmss
	 *
	 * @param str
	 *            日期的字符串
	 * @return 转换后的日期，如果转换失败，则返回null
	 */
	public static Date string2Date(String str) {
		try {
			/*
			 * SimpleDateFormat存在同步问题。如果使用全局变量或类变量，则在多线程情况下需要进行同步。所以这里使用临时变量
			 */
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 使用给定的模式串转换指定的字符串到Date类型
	 *
	 * @param str
	 *            日期的字符串
	 * @param pattern
	 *            日期的格式串
	 * @return 转换后的日期，如果转换失败，则返回null
	 */
	public static Date string2Date(String str, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取某一指定整点时间的字符串，按照给定日期格式串返回 比如当前时间是2010-5-6
	 * 17:13:10,如果calendarFiled为Calendar.HOUR_OF_DAY，interval为-1时
	 * 返回的字符串为2010-5-6 16:00:00
	 *
	 * @param pattern
	 *            日期格式串,为null则使用默认字符串格式TIME_FORMAT
	 * @param calendarFiled
	 *            日期filed，目前只允许有月Calendar.MONTH，天Calendar.DAY_OF_MONTH,
	 *            小时Calendar.HOUR_OF_DAY
	 * @param interval
	 *            与当前时间间隔
	 * @return 转换后的日期字符串，如果转换失败，返回null
	 */
	public static String getNatureTime(String pattern, int calendarFiled,
			int interval) {

		String pat;
		if (pattern == null) {
			pat = TIME_FORMAT;
		} else {
			pat = pattern;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pat);
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			int filedVal = c.get(calendarFiled);
			filedVal += interval;
			c.set(calendarFiled, filedVal);
			// 精确到某一月,将天设为当月第一天、小时、分、秒归零
			if (calendarFiled == Calendar.MONTH) {
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				// 精确到某一天，将小时、分、秒归零
			} else if (calendarFiled == Calendar.DAY_OF_MONTH) {
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				// 精确到某一小时，将分、秒归零
			} else if (calendarFiled == Calendar.HOUR_OF_DAY) {
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
			} else {

			}

			date = c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (date != null) {
			return sdf.format(date);
		}

		return null;
	}

}
