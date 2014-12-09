package date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qmgeng on 2014/9/11.
 */
public class JavaDateTest {

    //毫秒转换为日期
    public static void main(String[] args) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        long tmp = 1410350474000l;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(tmp);
        System.out.println(tmp + " = " + formatter.format(calendar.getTime()));
// 日期转换为毫秒 两个日期想减得到天数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String now = "20140830";
//得到毫秒数
        long timeStart = sdf.parse(now).getTime();
//两个日期想减得到天数
        long dayCount = (timeStart - tmp) / (24 * 3600 * 1000) + 1;
        System.out.println(dayCount);


        //取得系统当前时间
        Calendar cal = Calendar.getInstance();
//取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("当前月第一天：" + cal.getTime());
//日期减一,取得上月最后一天时间对象
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
//输出上月最后一天日期
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("上个月最后一天：" + cal.getTime());


        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("上个月第一天：" + cal.getTime());

        System.out.println("###############################");

        int x =5;
        for (int i = x-1; i > -1; i--) {
            Calendar cal2 = Calendar.getInstance();

            cal2.add(Calendar.MONTH,-i);
            cal2.set(Calendar.DAY_OF_MONTH, 1);
            cal2.add(Calendar.DAY_OF_MONTH, -1);
            System.out.println(cal2.getTime());
        }

        System.out.println("###############################");
        for (int i = 2*(x-1)+1; i > x-1; i--) {
            Calendar cal2 = Calendar.getInstance();

            cal2.add(Calendar.MONTH,-i);
            cal2.set(Calendar.DAY_OF_MONTH, 1);
            cal2.add(Calendar.DAY_OF_MONTH, -1);
            System.out.println(cal2.getTime());
        }
        System.out.println("###############################");

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        Date dayft = new Date();
        String res[] = new String[5];
        int tt = 0;
        dayft = sp.parse("2014-11-04");
        for (int i = 5 - 1; i > -1; i--) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(dayft);
            cal2.add(Calendar.MONTH, -i);
            cal2.set(Calendar.DAY_OF_MONTH, 1);
            cal2.add(Calendar.DAY_OF_MONTH, -1);
            System.out.println(cal2.getTime());
            res[tt] = sp.format(cal2.getTime());
            tt++;
        }
        System.out.println(res[0]+"_"+res[res.length-1]);

    }
}
