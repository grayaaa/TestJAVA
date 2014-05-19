package hadoop.accesslog;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class AccessLogMapper extends MapReduceBase implements
        Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);

    private Text url = new Text();

    static String POST = "\"POST ";

    static String GET = "\"GET ";

    static String END = " HTTP";

    public void map(LongWritable key, Text value,
            OutputCollector<Text, IntWritable> output, Reporter reporter)
            throws IOException {
        String line = value.toString();
        String url1 = getUrl(line);

        url.set(url1);
        output.collect(url, one);
    }

    private static String getUrl(String a) {
        // int len = POST.length();
        int begin = a.indexOf(POST);
        int get = a.indexOf(GET);
        if (get > -1) {
            begin = get;
            // len = GET.length();
        }

        int end = a.indexOf(END);

        if (begin < 0 || end < 0) {
            System.out.println(a);
            return a;
        }

        String url = a.substring(begin + 1, end);

        if (url.indexOf("?") > 0) {
            return url.substring(0, url.indexOf("?"));
        }

        return url;
    }

    public static void main(String[] args) {
        String a = "10.2.112.34 - - [06/Mar/2012:18:05:41 +0800] \"GET /mine?originUrl= HTTP/1.0\" 302 -";
        String b = "10.2.112.34 - - [06/Mar/2012:15:02:42 +0800] \"POST /user/login?originUrl=http%3A%2F%2Fwww.jiexi.com%2Fhome HTTP/1.0\" 200 25";

        String c = "61.135.255.86 - - [02/Aug/2012:11:34:50 +0800] \"GET /mb/script/task/setHeight.js GETP/1.1\" 304 - \"http://buzz097.intra.t.163.com:20080/54138911361\" \"Mozilla/5.0 (Windows NT 5.1;rv:9.0.1) Gecko/20100101 Firefox/9.0.1\"";

        System.out.println(getUrl(a));
        System.out.println(getUrl(b));
        System.out.println(getUrl(c));

        String s = " /user/register?originUrl=http%3A%2F%2Fwww.jiexi.com%2Fhome";

        System.out.println(s.substring(0, s.indexOf("?")));
    }

}
