public class TestString {

    /**
     * @param args
     */
    // public static void main(String[] args) {
    // String ss=
    // "nodetype:oApp#nodeid:1#ctlip:10.0.0.1#status:ok\nnodetype:oApp#nodeid:2#ctlip:10.0.0.2#status:ok";
    // String[] temp = ss.split("\\p{javaWhitespace}+");
    // for (int i = 0; i < temp.length; i++) {
    // System.out.println(temp[i]);
    // int x = temp[i].indexOf("status:");
    // System.out.println(temp[i].substring(x+7).trim());
    // }
    //
    // }

    public static void main(String[] args) {

        // int i = 1;
        // Integer i1 = 2;
        // double d = 1.8;
        // long l = 76;
        // boolean b = false;
        // int[] a = new int[3];
        // System.out.println(getType(a[i]));
        // System.out.println(getType(i));
        // System.out.println(getType(i1));
        // System.out.println(getType(d));
        // System.out.println(getType(l));
        // System.out.println(getType(b));

        String ss = "nodename:node166#message:sh: /opt/xdata/sysconf/Time_Job/Operate_crontab/crontabjob/cron_bak: No such file or directory ";

        System.out.println(ss.substring(ss.indexOf(":") + 1,
                ss.indexOf("#message:")));

        System.out.println(ss.substring(ss.indexOf("#message:") + 9,
                ss.length()));

        String ss2 = "jdbc:mysql://10.0.31.639:3309/test?createDatabaseIfNotExist=true";
        String ss3 = "jdbc:oracle:thin:@10.0.31.166:1521:test";
        String ss4 = "jdbc:postgresql://10.0.31.166:1521/test";
        System.out.println(ss2.split(":")[1]);
        System.out.println(ss2.split("//")[1].split(":")[0]);
        System.out.println(ss3.split(":")[1]);
        System.out.println(ss4.split(":")[1]);

    }

    public static String getType(Object o) {
        return o.getClass().toString();
    }

    public static String getType(int o) {
        return "int";
    }

    public static String getType(byte o) {
        return "byte";
    }

    public static String getType(char o) {
        return "char";
    }

    public static String getType(double o) {
        return "double";
    }

    public static String getType(float o) {
        return "float";
    }

    public static String getType(long o) {
        return "long";
    }

    public static String getType(boolean o) {
        return "boolean";
    }

    public static String getType(short o) {
        return "short";
    }

}
