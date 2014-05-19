import java.util.regex.Pattern;


public class TestReg {

    public static void main(String[] args) {
        String regFilter = ".*qqzone((\\d{2})|100)?.*";
        String regValue = "qqzone100";
        Boolean flagFilter = true;
        try {
            flagFilter = Pattern.matches(regFilter, regValue);
        } catch (Exception e) {
            System.out.println("test");
        }

        System.out.println(flagFilter);
    }

}
