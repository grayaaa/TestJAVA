package json;

import net.sf.json.JSONObject;

public class TestJson {
	public static void main(String[] args) {

		String tmp = "{'Hive' : {'DBNode' : '','MetaStore' : '','HiveServer2' : ''},'test' : ''}";
		JSONObject jsonObject = JSONObject.fromObject(tmp);
		System.out.println(jsonObject.toString());
	}
}
