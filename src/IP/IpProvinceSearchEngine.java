package IP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class IpProvinceSearchEngine extends RangeSearchEngine<Long, String> {
    public static final String SPLITER = "\\|";
    //private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param dataFile
     * @return void 返回类型
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @Title: load
     * @Description: 载入Ip库文件
     * @author Administrator
     */
    public void load(String dataFile) throws FileNotFoundException, IOException {
        TreeMap<Long, RangeRow<Long, String>> map = new TreeMap<Long, RangeRow<Long, String>>();
        BufferedReader file = new BufferedReader(new FileReader(dataFile));
        try {
            //
            String content = null;
            while ((content = file.readLine()) != null) {
                String[] parts = content.split(SPLITER);
                if (parts.length < 3) {
                    //logger.warn("parse line failed, " + content);
                    continue;
                }
                //
                try {
                    IpProvinceRow row = new IpProvinceRow();
                    row.setBegin(Long.valueOf(parts[0]));
                    row.setEnd(Long.valueOf(parts[1]));
                    row.setValue(parts[2]);
                    //
                    if (parts.length > 3) {
                        row.setCity(parts[3]);
                    }
                    //
                    map.put(row.getBegin(), row);
                } catch (Exception e) {
                    //logger.warn("parse row failed:" + content);
                }
            }
            //
            setCache(map);
        } finally {
            try {
                file.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * @param @param  ip
     * @param @return 设定文件
     * @return String 返回类型
     * @Title: getProvince
     * @Description: 获取省份信息
     * @author Administrator
     */
    public String getProvince(String ip) {
        //
        long intIp = convertIpToLong(ip);
        //
        return getValue(intIp);
    }

    /**
     * @param ip
     * @return long
     * @Title: convertIpToLong
     * @Description: 转换Ip为256进制整数
     * @author Kolor
     */
    public static long convertIpToLong(String ip) {
        String[] checkIp = ip.split("\\.", 4);
        long intIp = 0;

        for (int i = 3, j = 0; i >= 0 && j <= 3; i--, j++) {
            intIp += Integer.parseInt(checkIp[j]) * Math.pow(256, i);
        }
        return intIp;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String file = "D:\\IdeaProjects\\testJava\\src\\IP\\ip.data";
        IpProvinceSearchEngine engine = new IpProvinceSearchEngine();
        //
        long beginTime = System.currentTimeMillis();
        engine.load(file);
        long endTime = System.currentTimeMillis();
        System.out.println("load cost " + (endTime - beginTime));
        //
        long beginTime2 = System.currentTimeMillis();
        System.out.println(engine.getProvince("202.101.15.61"));
        long endTime2 = System.currentTimeMillis();
        System.out.println("get cost " + (endTime2 - beginTime2));

    }
}