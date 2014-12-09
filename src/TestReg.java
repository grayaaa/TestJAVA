import java.util.regex.Pattern;


public class TestReg {

    public static void main(String[] args) {
        String regFilter = "yanyiuser(((?!0{4})\\d{4})|([1-4]\\d{4})|(50000))@163.com";
        String regValue = "yanyiuser0000@163.com";
        Boolean flagFilter = true;
        try {
            flagFilter = Pattern.matches(regFilter, regValue);
        } catch (Exception e) {
            System.out.println("test");
        }

        System.out.println(flagFilter);
    }

}
