package jackson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	private static ObjectMapper mapper;

	/**
	 * 
	 * @param createNew
	 *            是否创建一个新的Mapper
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
		if (createNew) {
			return new ObjectMapper();
		} else if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	/**
	 * 从json中读取tagPath处的值 tagPath用 :分隔
	 * 
	 * @param json
	 * @param tagPath
	 * @return
	 * @throws Exception
	 */
	public static List<String> readValueFromJson(String json, String tagPath) throws Exception {
		// 返回值
		List<String> value = new ArrayList<String>();
		if (isEmpty(json) || (isEmpty(tagPath))) {
			return value;
		}
		ObjectMapper mapper = getMapperInstance(false);
		String[] path = tagPath.split(":");
		JsonNode node = mapper.readTree(json);
		getJsonValue(node, path, value, 1);
		return value;
	}

	public static void getJsonValue(JsonNode node, String[] path, List<String> values, int nextIndex) {
		if (isEmpty(node)) {
			return;
		}
		// 是路径的最后就直接取值
		if (nextIndex == path.length) {
			if (node.isArray()) {
				for (int i = 0; i < node.size(); i++) {
					JsonNode child = node.get(i).get(path[nextIndex - 1]);
					if (isEmpty(child)) {
						continue;
					}
					values.add(child.toString());
				}
			} else {
				JsonNode child = node.get(path[nextIndex - 1]);
				if (!isEmpty(child)) {
					values.add(child.asText());
				}
			}
			return;
		}
		// 判断是Node下是集合还是一个节点
		node = node.get(path[nextIndex - 1]);
		if (node.isArray()) {
			for (int i = 0; i < node.size(); i++) {
				getJsonValue(node.get(i), path, values, nextIndex + 1);
			}
		} else {
			getJsonValue(node, path, values, nextIndex + 1);
		}
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		boolean result = true;
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			result = (obj.toString().trim().length() == 0) || obj.toString().trim().equals("null");
		} else if (obj instanceof Collection) {
			result = ((Collection) obj).size() == 0;
		} else {
			result = ((obj == null) || (obj.toString().trim().length() < 1)) ? true : false;
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		// 准备数据
		List<Person> pers = new ArrayList<Person>();
		List<Person> childs = new ArrayList<Person>();
		Person p = new Person("张三", 46);
		childs.add(new Person("小张三1", 20));
		childs.add(new Person("小张三2", 17));
		p.setChilds(childs);
		pers.add(p);
		p = new Person("李四", 29);
		childs = new ArrayList<Person>();
		childs.add(new Person("小李四1", 20));
		p.setChilds(childs);
		pers.add(p);
		p = new Person("王二麻子", 23);
		pers.add(p);
		TestVO vo = new TestVO("一个容器而已", pers);

		// 实体转JSON字符串
		// ObjectMapper mapper = new ObjectMapper();
		String voJson = getMapperInstance(false).writeValueAsString(vo);
		System.out.println(voJson);

		JsonNode node = mapper.readTree(voJson);// JsonNode和XML里面的Node很像
		node = node.get("pers");
		System.out.println("node是不是个集合：" + node.isArray());
		for (int i = 0; i < node.size(); i++) {
			JsonNode childNode = node.get(i);
			System.out.println("readValueFromJson>>>" + childNode.get("name").toString());// 获取name
		}

		// 把所有的孩子找出来
		Object[] obj = readValueFromJson(voJson, "pers:childs:name").toArray();
		System.out.println(Arrays.toString(obj));
	}
}
