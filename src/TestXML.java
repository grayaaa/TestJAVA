import java.io.File;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestXML {

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// String xml =
	// "<Chart palette='3' numberSuffix='/s'  lowerLimit='0' upperLimit='500' gaugeStartAngle='240' gaugeEndAngle='-60' gaugeInnerRadius='60%' gaugeFillMix='{light-10},{light-30},{light-20},{dark-5},{color},{light-30},{light-20},{dark-10}' gaugeFillRatio='' toolTipBgColor='333333' toolTipBorderColor='333333' decimals='1'><colorRange><color minValue='0' maxValue='100'/><color minValue='100' maxValue='250'/><color minValue='250' maxValue='400'/><color minValue='400' maxValue='500'/></colorRange><dials><dial id='Dial1' value='260.9' baseWidth='6' topWidth='1' editMode='1' showValue='1' rearExtension='10' valueY='270'/></dials><styles><definition><style type='font' name='valueFont' borderColor='FFFFFF' bold='1' size='13'/></definition><application><apply toObject='value' styles='valueFont'/></application></styles></Chart>";
	//
	// System.out.println(Test.getAngularChartXMLData(xml, "30000"));
	// }

	public static String getAngularChartXMLData(String chartXML, String value) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(chartXML);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Element ec = (Element) root.selectSingleNode("/Chart/dials/dial");
		Attribute att = ec.attribute("value");
		att.setText(value);

		String str = doc.asXML();
		return str;

	}

	public static void main(String[] args) {

		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File("src/hdfs-site.xml"));

			Element root = document.getRootElement();
			Element ec = (Element) document
					.selectSingleNode("//configuration[name='dfs.permissions.superusergroup']/value");

			System.out.println(ec.getText());

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Document doc = DocumentHelper.createDocument();
		// doc.setXMLEncoding("utf-8");
		//
		// Element graph = doc.addElement("graph");// 根节点
		// graph.addAttribute("xaxisname", "");// 西侧标题
		// graph.addAttribute("yaxisname", "");// 南侧标题
		// graph.addAttribute("hovercapbg", "DEDEBE");// 鼠标停留显示标签背景色
		// graph.addAttribute("hovercapborder", "889E6D");// 鼠标停留显示标签边框色
		// graph.addAttribute("yAxisMaxValue", "");// 坐标y轴最大数值
		// graph.addAttribute("numdivlines", "");// y轴数值间隔数(分割线数)
		// graph.addAttribute("divLineColor", "CCCCCC");// 分割线颜色
		// graph.addAttribute("divLineAlpha", "80");// 分割线透明度
		// graph.addAttribute("decimalPrecision", "0");// 线数位数
		// graph.addAttribute("showAlternateHGridColor", "1");//
		// graph.addAttribute("AlternateHGridAlpha", "30");//
		// graph.addAttribute("AlternateHGridColor", "CCCCCC");//
		// graph.addAttribute("caption", "正向有功");// 标题
		// graph.addAttribute("subcaption", "隔湖变[万kWh]");// 副标题
		//
		// Element categories = graph.addElement("categories");
		// categories.addAttribute("font", "宋体");// 字体
		// categories.addAttribute("fontSize", "12");// 字号
		// categories.addAttribute("fontColor", "000000");// 字体颜色
		//
		// Element category = categories.addElement("category");
		// category.addAttribute("name", "开关");// 显示柱状图种类
		// category.addAttribute("hoverText", "开关1");// 鼠标停留显示文字
		//
		// Element category2 = categories.addElement("category");
		// category2.addAttribute("name", "母联");// 显示柱状图种类
		//
		// Element category3 = categories.addElement("category");
		// category3.addAttribute("name", "线端");// 显示柱状图种类
		//
		// Element category4 = categories.addElement("category");
		// category4.addAttribute("name", "绕组");// 显示柱状图种类
		//
		// Element dataset = graph.addElement("dataset");
		// dataset.addAttribute("seriesname", "1");// 连接名称
		// dataset.addAttribute("color", "FDC12E");// 柱颜色
		//
		// Element set = dataset.addElement("set");
		// set.addAttribute("value", "100");// 数值
		// Element set2 = dataset.addElement("set");
		// set2.addAttribute("value", "100");// 数值
		// Element set3 = dataset.addElement("set");
		// set3.addAttribute("value", "100");// 数值
		// Element set4 = dataset.addElement("set");
		// set4.addAttribute("value", "100");// 数值
		//
		// Element dataset2 = graph.addElement("dataset");
		// dataset2.addAttribute("seriesname", "2");// 连接名称
		// dataset2.addAttribute("color", "FDC12E");// 柱颜色
		//
		// Element set21 = dataset2.addElement("set");
		// set21.addAttribute("value", "100");// 数值
		// Element set22 = dataset2.addElement("set");
		// set22.addAttribute("value", "150");// 数值
		// Element set23 = dataset2.addElement("set");
		// set23.addAttribute("value", "100");// 数值
		// Element set24 = dataset2.addElement("set");
		// set24.addAttribute("value", "100");// 数值
		//
		// StringWriter sw = new StringWriter();
		// XMLWriter xw = new XMLWriter(sw);
		// try {
		// xw.write(doc);
		// xw.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// System.out.println(sw.toString());
	}

}
