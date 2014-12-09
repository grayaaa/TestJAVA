package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDate {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		String tmp = "2013-10-17 10:38:22";
		Date nowT = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println(nowT);
		System.out.println(nowT.getDay());
		System.out.println(sdf.format(nowT));

		Date tmpT = sdf.parse(tmp);

		System.out.println(tmpT);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar Cal = Calendar.getInstance();
		Cal.setTime(nowT);

        System.out.println("@@"+Cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("@@"+Cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("@@"+Cal.get(Calendar.MONTH));


		Cal.add(Calendar.MINUTE, -5);
		System.out.println("当前时间减5分钟:" + format.format(Cal.getTime()));

		System.out.println("///////////////////////////////////////");
		String s1 = "2013-11-17 10:38:22";
		String s2 = "2013-12-17 10:38:22";

		Date d1 = sdf.parse(s1);
		Date d2 = sdf.parse(s2);
		Long ll;
		if (d1.after(d2)) {
			ll = d1.getTime() - d2.getTime();
		} else {
			ll = d2.getTime() - d1.getTime();
		}

		Long tm = d1.getTime() + ll / 30;

		System.out.println(format.format(new Date(tm)));

	}
}
