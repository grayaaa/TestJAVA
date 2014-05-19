package test;

import java.util.Date;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

public class TestService {
	public static void main(String[] hey) throws Exception {
		DynaProperty[] pro = new DynaProperty[] {// 准备一个属性，动态定义类依靠这些属性来决定类的构成。
		new DynaProperty("title", String.class),
				new DynaProperty("content", String.class),
				new DynaProperty("createdTime", Date.class),
				new DynaProperty("id", Integer.class), };

		DynaClass articleClass = new BasicDynaClass("Article", null, pro);// 定义了一个类

		DynaBean article = articleClass.newInstance();// 声明了一个ArticleClass的对象

		article.set("title", "this is a test");// 对该对象进行操作
		article.set("content", "oh my god");
		article.set("createdTime", new Date());
		article.set("id", new Integer(1));

		System.out.println(article.get("title"));
		System.out.println(article.get("content"));
		System.out.println(article.get("createdTime"));
		System.out.println(article.get("id"));
	}
}
