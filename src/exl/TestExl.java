package exl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.google.common.io.Files;

public class TestExl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// read();
		write();
	}

	public static void read() {

		try {
			Workbook book = Workbook.getWorkbook(new File("src/SDH_Conf.xls"));
			System.out.println(book.getNumberOfSheets());
			System.out.println("----------------------");

			Sheet[] sheets = book.getSheets();
			for (Sheet sheet : sheets) {
				System.out.println(sheet.getName());
			}

			System.out.println("----------------------");
			Sheet rackSheet = book.getSheet("机柜");
			int columnum1 = rackSheet.getColumns();// 得到列数
			int rownum1 = rackSheet.getRows();// 得到行数
			System.out.println(columnum1 + "*" + rownum1);

			System.out.println("----------------------");
			// 获得hdfs-federation工作表对象
			Sheet sheet = book.getSheet("hdfs-federation");

			int columnum = sheet.getColumns();// 得到列数
			int rownum = sheet.getRows();// 得到行数
			System.out.println(columnum + "*" + rownum);
			for (int i = 1; i < rownum; i++)// 循环进行读写
			{
				if (sheet.getCell(0, i).getContents() != "" && sheet.getCell(0, i).getContents() != null) {
					List<String> listHadoop = new LinkedList<String>();

					for (int j = 0; j < columnum; j++) {

						Cell cellx = sheet.getCell(j, i);
						String result = cellx.getContents();

						listHadoop.add(result);
						System.out.print(result);
						System.out.print("\t");
					}
					System.out.println();
					System.out.println("###" + listHadoop.get(5));
				}

			}

			System.out.println("----------------------");
			Cell cell1 = sheet.getCell("G2");
			String result = cell1.getContents();
			String[] results = full2HalfChange(result).split("[,|\\n]");
			for (String string : results) {
				System.out.println(string.trim());
				System.out.println("###");
			}
			System.out.println("----------------------");

			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void write() {
		WritableWorkbook book = null;
		try {
			try {
				Files.copy(new File("src/SDH_Conf.xls.template"), new File("src/SDH_Conf.xls"));

				Workbook wb = Workbook.getWorkbook(new File("src/SDH_Conf.xls"));

				// 打开一个文件的副本，并且指定数据写回到原文件
				book = Workbook.createWorkbook(new File("src/SDH_Conf.xls"), wb);
				if (book.getNumberOfSheets() == 9) {
					// 生成名为“第十一页”的工作表，参数11表示这是第十一页
					// WritableSheet sheet = book.createSheet("TEST", 9);
					WritableSheet sheet = book.getSheet("机柜");
					WritableSheet serverSheet = book.getSheet("服务器");
					WritableSheet hadoopSheet = book.getSheet("hadoop");
					WritableSheet simpleSheet = book.getSheet("hdfs-simple");
					WritableSheet federationSheet = book.getSheet("hdfs-federation");
					WritableSheet zkSheet = book.getSheet("zookeeper");
					WritableSheet mapredSheet = book.getSheet("mapreduce");
					WritableSheet hbaseSheet = book.getSheet("hbase");
					WritableSheet hiveSheet = book.getSheet("hive");

					for (int i = 0; i < 200; i++) {
						Label label0 = new Label(i, 1, "");
						Label label1 = new Label(i, 2, " ");
						Label label2 = new Label(i, 3, " test2 ");
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
					}

					// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
					// 以及单元格内容为test
					// Label label = new Label(0, 0, " test ");
					//
					// // 将定义好的单元格添加到工作表中
					// sheet.addCell(label);
					//
					// /*
					// * 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义
					// 单元格位置是第二列，第一行，值为789.123
					// */
					// jxl.write.Number number = new jxl.write.Number(1, 0,
					// 555.12541);
					// sheet.addCell(number);
					//
					// sheet.addCell(new Label(3, 0, "2563.25698"));
					// sheet.addCell(new Label(2, 0, " 第二页的测试数据 "));

					// 写入数据并关闭文件
					// book.write();
				} else {
					System.out.println(book.getNumberOfSheets());
				}

				// book.close();
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				// 写入数据并关闭文件
				if (book != null) {
					book.write();
					book.close();
				}

			}

		} catch (WriteException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 全角转半角的 转换函数
	public static final String full2HalfChange(String QJstr) throws UnsupportedEncodingException {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			// 全角空格转换成半角空格
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}
			b = Tstr.getBytes("unicode");
			// 得到 unicode 字节数据
			if (b[2] == -1) {
				// 全角
				b[3] = (byte) (b[3] + 32);
				b[2] = 0;
				outStrBuf.append(new String(b, "unicode"));
			} else {
				outStrBuf.append(Tstr);
			}
		} // end for.
		return outStrBuf.toString();
	}

	// 半角转全角
	public static final String half2Fullchange(String QJstr) throws UnsupportedEncodingException {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			if (Tstr.equals(" ")) {
				// 半角空格
				outStrBuf.append(Tstr);
				continue;
			}
			b = Tstr.getBytes("unicode");
			if (b[2] == 0) {
				// 半角
				b[3] = (byte) (b[3] - 32);
				b[2] = -1;
				outStrBuf.append(new String(b, "unicode"));
			} else {
				outStrBuf.append(Tstr);
			}
		}
		return outStrBuf.toString();
	}
}
