package conf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import test.FileUtils;

public class Test {
	private static final Log LOG = LogFactory.getLog(FileUtils.class);

	public static void modifyParamInXml(String xmlPath) throws DocumentException, IOException {

		// 声明写XML的对象
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置XML文件的编码格式
		format.setEncoding("UTF-8");

		format.setSuppressDeclaration(true);
		// 设置是否缩进
		format.setIndent(true);
		// 以空格方式实现缩进
		format.setIndent("   ");
		// 设置是否换行
		format.setNewlines(true);

		// 打开xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		// 获取根节点
		Element root = document.getRootElement();
		if (!root.getName().equals("configuration")) {
			LOG.fatal("bad conf file: top-level element not <configuration>");
		}

		// 获取子节点并遍历
		List<Element> nodes = root.elements("property");

		// 如果属性已存在，则覆盖属性值
		boolean flag = true;
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			String defaultValue = elm.element("value").getText();
			elm.remove(elm.element("value"));
			elm.addElement("defaultValue").setText(defaultValue);
			elm.addElement("type").setText("高级");
			elm.addElement("source").setText("mapred-site.xml");

		}

		writer = new XMLWriter(new FileWriter(new File(xmlPath)), format);
		writer.setEscapeText(false);
		writer.write(document);
		writer.close();

	}

	public static void modifyParamInXml2(String xmlPath) throws DocumentException, IOException {

		// 声明写XML的对象
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置XML文件的编码格式
		format.setEncoding("UTF-8");

		format.setSuppressDeclaration(true);
		// 设置是否缩进
		format.setIndent(true);
		// 以空格方式实现缩进
		format.setIndent("   ");
		// 设置是否换行
		format.setNewlines(true);

		// 打开xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		// 获取根节点
		Element root = document.getRootElement();
		if (!root.getName().equals("configuration")) {
			LOG.fatal("bad conf file: top-level element not <configuration>");
		}

		// 获取子节点并遍历
		List<Element> nodes = root.elements("property");

		// 如果属性已存在，则覆盖属性值
		boolean flag = true;
		// for (Iterator it = nodes.iterator(); it.hasNext();) {
		// Element elm = (Element) it.next();
		// String defaultValue = elm.element("value").getText();
		// elm.remove(elm.element("value"));
		// elm.addElement("defaultValue").setText(defaultValue);
		// elm.addElement("type").setText("高级");
		// elm.addElement("source").setText("mapred-site.xml");
		//
		// }

		writer = new XMLWriter(new FileWriter(new File(xmlPath)), format);
		writer.setEscapeText(false);
		writer.write(document);
		writer.close();

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Map<String, String> mapTestConf =
		// FileUtils.getParamMapFromXml("src/conf/mapred-default-test.xml");
		// Map<String, String> mapConf =
		// FileUtils.getParamMapFromXml("src/conf/mapred-default.xml");
		// for (String name : mapTestConf.keySet()) {
		// if (mapConf.containsKey(name)) {
		// continue;
		// }
		// System.out.println(name);
		// }

		modifyParamInXml2("src/conf/mapred-default.xml");
		modifyParamInXml2("src/conf/core-default.xml");
		modifyParamInXml2("src/conf/hdfs-default.xml");
		modifyParamInXml2("src/conf/hive-default.xml");
		modifyParamInXml2("src/conf/yarn-default.xml");
		modifyParamInXml2("src/conf/hbase-default.xml");
		modifyParamInXml2("src/conf/zookeeper-default.xml");
	}
}
