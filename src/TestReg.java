import java.util.regex.Pattern;


public class TestReg {

    public static void main(String[] args) {
        //String regFilter = "yanyiuser(((?!0{4})\\d{4})|([1-4]\\d{4})|(50000))@163.com";
        //String regValue = "yanyiuser0000@163.com";
        String regFilter = "(?!.*bulletin|.*roomId)^.*newsbobo.*";
        String regFilter2 = "^http://www.bobo.com/special/sp/newsbobo.html(?:.(?!(roomId|bulletin)))*$";
        String regFilter3 = "(?!.*bulletin|.*roomId)^http://www.bobo.com/special/sp/newsbobo.html.*";

        String regValue1 = "http://www.bobo.com/special/sp/newsbobo.html?viewid=home&NTES&bulletin&logined";
        String regValue2 = "http://www.bobo.com/special/sp/newsbobo.html?viewid=home&NTES&&roomId&logined";
        String regValue3 = "http://www.bobo.com/special/sp/newsbobo.html?viewid=home&NTES&logined";
        Boolean flagFilter = true;
        try {
            flagFilter = Pattern.matches(regFilter2, regValue3);
        } catch (Exception e) {
            System.out.println("test");
        }

        System.out.println(flagFilter);
    }

}
