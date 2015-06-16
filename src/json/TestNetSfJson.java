package json;

import net.sf.json.JSONObject;

/**
 * Created by qmgeng on 2015/5/13.
 */
public class TestNetSfJson {
    public static void main(String[] args) {
        JSONObject objecthbase = new JSONObject();
        objecthbase.put("tablename", "aaa");
        objecthbase.put("columnfamily", "bbb");

        System.out.println(objecthbase.toString());

    }
}
