package test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

public class DynamicBean {

	private Object object = null;

	private BeanMap beanMap = null;

	public DynamicBean() {
		super();
	}

	public DynamicBean(Map propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
	}

	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	public Object getValue(String property) {
		return beanMap.get(property);
	}

	public Object getObject() {
		return this.object;
	}

	private Object generateBean(Map propertyMap) {
		BeanGenerator generator = new BeanGenerator();
		Set keySet = propertyMap.keySet();
		for (Iterator i = keySet.iterator(); i.hasNext();) {
			String key = (String) i.next();
			generator.addProperty(key, (Class) propertyMap.get(key));
		}
		return generator.create();
	}

	public static void main(String[] args) throws ClassNotFoundException {

		// 设置类成员属性
		Map<String, Object> propertyMap = new HashMap<String, Object>();

		propertyMap.put("id", Class.forName("java.lang.Integer"));

		propertyMap.put("name", Class.forName("java.lang.String"));

		propertyMap.put("address", Class.forName("java.lang.String"));

		// 生成动态 Bean
		DynamicBean bean = new DynamicBean(propertyMap);

		// 给 Bean 设置值
		bean.setValue("id", new Integer(123));

		bean.setValue("name", "454");

		bean.setValue("address", "789");

		// 从 Bean 中获取值，当然了获得值的类型是 Object

		System.out.println("  >> id      = " + bean.getValue("id"));

		System.out.println("  >> name    = " + bean.getValue("name"));

		System.out.println("  >> address = " + bean.getValue("address"));

		// 获得bean的实体
		Object object = bean.getObject();

		// 通过反射查看所有方法名
		Class clazz = object.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i].getName());
		}

		// 设置类成员属性
		HashMap paramMap = new HashMap();

		propertyMap.put("type", Class.forName("java.lang.String"));

		propertyMap.put("key", Class.forName("java.lang.String"));

		propertyMap.put("value", Class.forName("java.lang.String"));

		// 生成动态 Bean
		DynamicBean paramInfo = new DynamicBean(paramMap);

	}
}