package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

public class FileUtils {
	private static final Log LOG = LogFactory.getLog(FileUtils.class);

	/**
	 * 从XML文件中读取参数
	 * 
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	public static List<Map<String, String>> getParamListFromeXml(String xmlPath) throws DocumentException {
		Preconditions.checkNotNull(xmlPath);

		List<Map<String, String>> listResult = Lists.newArrayList();

		// 打开xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		// 获取根节点
		Element root = document.getRootElement();
		if (!"configuration".equals(root.getName()))
			LOG.fatal("bad conf file: top-level element not <configuration>");

		// 获取子节点并遍历
		List nodes = root.elements("property");
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();

			// 获取子节点并取值
			List elements = elm.elements();
			Map<String, String> proMap = Maps.newHashMap();
			for (Object object : elements) {
				Element child = (Element) object;
				if ("name".equals(child.getName())) {
					proMap.put("name", child.getTextTrim());
				}
				if ("defaultValue".equals(child.getName())) {
					proMap.put("defaultValue", child.getTextTrim());
				}
				if ("description".equals(child.getName())) {
					proMap.put("description", child.getText());
				}
				if ("source".equals(child.getName())) {
					proMap.put("source", child.getTextTrim());
				}
				if ("type".equals(child.getName())) {
					proMap.put("type", child.getTextTrim());
				}
				if ("value".equals(child.getName())) {
					proMap.put("value", child.getTextTrim());
				} else {
					proMap.put("value", "");
				}
			}

			listResult.add(proMap);
		}

		return listResult;

	}

	/**
	 * 修改XML文件 （若参数名为空，则写入新的属性element ；若参数存在，则更新参数值）
	 * 
	 * @param xmlPath
	 * @param name
	 * @param value
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void modifyParamInXml(String xmlPath, String name, String value) throws DocumentException,
			IOException {

		// 声明写XML的对象
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		// format.setNewlines(true);
		// format.setTrimText(false);
		// format.setPadText(true);
		format.setEncoding("UTF-8");// 设置XML文件的编码格式

		format.setSuppressDeclaration(true);
		format.setIndent(true); // 设置是否缩进
		format.setIndent("   "); // 以空格方式实现缩进
		format.setNewlines(true); // 设置是否换行

		// 打开xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		// 获取根节点
		Element root = document.getRootElement();
		if (!"configuration".equals(root.getName()))
			LOG.fatal("bad conf file: top-level element not <configuration>");

		// 获取子节点并遍历
		List<Element> nodes = root.elements("property");

		// 如果属性已存在，则覆盖属性值
		boolean flag = true;
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			if (name.equals(elm.element("name").getText())) {
				elm.element("value").setText(value);
				flag = false;
				break;
			}
		}

		if (flag) {
			Element pro = root.addElement("property");
			pro.addElement("name").setText(name);
			pro.addElement("value").setText(value);
		}

		writer = new XMLWriter(new FileWriter(new File(xmlPath)), format);
		writer.setEscapeText(false);
		writer.write(document);
		writer.close();

	}

	/**
	 * 从XML文件中删除所选属性及其对应父element
	 * 
	 * @param xmlPath
	 * @param tagName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void deleteParamInXml(String xmlPath, String tagName) throws DocumentException, IOException {

		// 声明写XML的对象
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setIndentSize(0);
		format.setNewlines(false);
		format.setTrimText(false);
		format.setPadText(true);
		format.setEncoding("UTF-8");// 设置XML文件的编码格式

		// 打开xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		// 获取根节点
		Element root = document.getRootElement();
		if (!"configuration".equals(root.getName()))
			LOG.fatal("bad conf file: top-level element not <configuration>");

		// 获取子节点并遍历
		List nodes = root.elements("property");
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			Element eleName = elm.element("name");
			if (tagName.equals(eleName.getText())) {
				root.remove(elm);
			}

		}

		writer = new XMLWriter(new FileWriter(xmlPath), format);
		writer.setEscapeText(false);
		writer.write(document);
		writer.close();

	}

	/**
	 * 读取cfg文件
	 * 
	 * @param xmlPath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<Map<String, String>> loadProperties(String xmlPath) throws FileNotFoundException, IOException {
		Preconditions.checkNotNull(xmlPath);

		List<Map<String, String>> listResult = Lists.newArrayList();

		// 读入cfg
		Properties prop = new Properties();
		prop.load(new FileInputStream(new File(xmlPath)));

		// 遍历读取属性
		Set keyValue = prop.keySet();
		for (Iterator it = keyValue.iterator(); it.hasNext();) {
			Map<String, String> mapResult = Maps.newHashMap();
			String key = (String) it.next();
			mapResult.put("name", key);
			mapResult.put("defaultValue", prop.getProperty(key));
			mapResult.put("type", "advance");
			mapResult.put("value", "");
			mapResult.put("source", "zoo.cfg");
			// mapResult.put(key, prop.getProperty(key));
			listResult.add(mapResult);
		}
		return listResult;

	}

	/**
	 * 写入cfg文件（若参数为空，则写入；若参数存在，则更新）
	 * 
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		// 重写Properties防止中文乱码
		HadoopProperties prop = new HadoopProperties();
		// Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 强制要求为属性的键和值使用字符串。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
		}
	}

	/**
	 * 从cfg文件中删除参数
	 * 
	 * @param filePath
	 * @param parameterName
	 */
	public static void deleteProperties(String filePath, String parameterName) {

		HadoopProperties prop = new HadoopProperties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			// 删除参数
			prop.remove(parameterName);
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
		}

	}

	/**
	 * 按行读取文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List<String> loadFile(String fileName) throws IOException {
		Preconditions.checkNotNull(fileName);

		// 调用google guava包中的Files类
		List<String> slaves = Files.readLines(new File("src/slaves"), Charset.forName("utf-8"));

		List<String> result = Lists.newArrayList();
		for (String string : slaves) {
			if (!Strings.isNullOrEmpty(string)) {
				result.add(string.trim());
			}
		}
		return result;
	}

	/**
	 * 按行写入文件
	 * 
	 * @param fileName
	 * @param it
	 * @throws IOException
	 */
	public static void writeFile(String fileName, Iterable<String> it) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		bw.write("");

		for (String string : it) {
			if (Strings.isNullOrEmpty(string)) {
				continue;
			}
			bw.write(string.trim());
			bw.newLine();
		}

		bw.flush(); // 将数据更新至文件
		bw.close();
	}

	/**
	 * 按行添加文件
	 * 
	 * @param fileName
	 * @param it
	 * @throws IOException
	 */
	public static void writeRowsInFile(String fileName, List<String> it) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));

		for (String string : it) {
			if (Strings.isNullOrEmpty(string)) {
				continue;
			}
			bw.write(string.trim());
			bw.newLine();
		}

		bw.flush(); // 将数据更新至文件
		bw.close();
	}

	/**
	 * 备份文件
	 * 
	 * @param fileName
	 */
	public static void backupFile(String fileName) {

	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void CopyFile(File source, File target) throws Exception {
		FileInputStream fis = new FileInputStream(source);
		FileOutputStream fos = new FileOutputStream(target);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}

	public static void modifyParamInYarnXml(String xmlPath) throws DocumentException, IOException {

		// 声明写XML的对象
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		// format.setNewlines(true);
		// format.setTrimText(false);
		// format.setPadText(true);
		format.setEncoding("UTF-8");// 设置XML文件的编码格式

		format.setSuppressDeclaration(true);
		format.setIndent(true); // 设置是否缩进
		format.setIndent("   "); // 以空格方式实现缩进
		format.setNewlines(true); // 设置是否换行

		// 打开xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		// 获取根节点
		Element root = document.getRootElement();
		if (!"configuration".equals(root.getName()))
			LOG.fatal("bad conf file: top-level element not <configuration>");

		// 获取子节点并遍历
		List<Element> nodes = root.elements("property");

		// 如果属性已存在，则覆盖属性值
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			elm.addElement("source").setText("yarn-mapred-site.xml");
			elm.addElement("type").setText("Advance");
			if (elm.element("value") == null) {
				elm.addElement("value").setText("");
			}
		}

		writer = new XMLWriter(new FileWriter(new File(xmlPath)), format);
		writer.setEscapeText(false);
		writer.write(document);
		writer.close();

	}

	/**
	 * 获取参数指标，返回map：key=value
	 * 
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String, String> getParamMapFromXml(String xmlPath) throws DocumentException {
		Preconditions.checkNotNull(xmlPath);

		Map<String, String> mapResult = Maps.newHashMap();

		// 打开xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));

		// 获取根节点
		Element root = document.getRootElement();
		if (!"configuration".equals(root.getName())) {
			LOG.fatal("bad conf file: top-level element not <configuration>");
		}

		// 获取子节点并遍历
		List nodes = root.elements("property");
		if (nodes == null || nodes.size() == 0) {
			return mapResult;
		}
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();

			// 获取子节点并取值
			List elements = elm.elements();
			Map<String, String> proMap = Maps.newHashMap();
			for (Object object : elements) {
				Element child = (Element) object;
				proMap.put(child.getName(), child.getTextTrim());
			}
			mapResult.put(proMap.get("name"), proMap.get("value"));

		}

		return mapResult;

	}

	/**
	 * @param args
	 * @throws DocumentException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws DocumentException, FileNotFoundException, IOException {
		// writeProperties("src/hive-env.sh", "export HIVE_PID_DIR",
		// "/maped/local");
		// writeProperties("src/hive-env.sh", "export HADOOP_HOME",
		// "/maped/local");
		// writeProperties("src/hive-env.sh", "export HADOOP_CONF_DIR",
		// "/maped/local");

		// modifyParamInYarnXml("src/yarn-mapred-default.xml");
		testLiu("src/liu2.xml");

	}

	public static void testLiu(String xmlPath) throws DocumentException, IOException {

		// 声明写XML的对象
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		// format.setNewlines(true);
		// format.setTrimText(false);
		// format.setPadText(true);
		format.setEncoding("UTF-8");// 设置XML文件的编码格式

		format.setSuppressDeclaration(true);
		format.setIndent(true); // 设置是否缩进
		format.setIndent("   "); // 以空格方式实现缩进
		format.setNewlines(true); // 设置是否换行

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("root");

		for (long i = 0; i < 2001; i++) {
			String ss1 = String.valueOf(13891990000L + i) + ",00001";
			String ss2 = String.valueOf(13891990000L + i) + ",00001,14";

			Element oper1 = root.addElement("oper");
			oper1.addElement("order").setText("1");
			oper1.addElement("type").setText("1");
			Element chgNode1 = oper1.addElement("chgNode");
			chgNode1.addElement("chgName").setText("IDValue,SPBizCode");
			chgNode1.addElement("chgValue").setText(ss1);

			Element oper2 = root.addElement("Oper");
			oper2.addElement("order").setText("2");
			oper2.addElement("type").setText("1");
			Element chgNode2 = oper2.addElement("chgNode");
			chgNode2.addElement("chgName").setText("IDValue,SPBizCode,OprCode");
			chgNode2.addElement("chgValue").setText(ss2);
		}

		// Element root = document.getRootElement();
		// if (!"configuration".equals(root.getName()))
		// LOG.fatal("bad conf file: top-level element not <configuration>");
		//
		// // 获取子节点并遍历
		// List<Element> nodes = root.elements("property");
		//
		// // 如果属性已存在，则覆盖属性值
		// for (Iterator it = nodes.iterator(); it.hasNext();) {
		// Element elm = (Element) it.next();
		// elm.addElement("source").setText("yarn-mapred-site.xml");
		// elm.addElement("type").setText("Advance");
		// if (elm.element("value") == null) {
		// elm.addElement("value").setText("");
		// }
		// }

		writer = new XMLWriter(new FileWriter(new File(xmlPath)), format);
		writer.setEscapeText(false);
		writer.write(document);
		writer.close();

	}
}
