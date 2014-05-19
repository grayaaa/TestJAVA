package exl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;

public class TestExl2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Properties properties_cn = new Properties();
			OutputStream outputStream_cn = new FileOutputStream("src/ParaStor_zh_cn.properties");

			Properties properties_en = new Properties();
			OutputStream outputStream_en = new FileOutputStream("src/ParaStor_en.properties");

			Workbook book = Workbook.getWorkbook(new File("src/ParaStor.xls"));
			Sheet[] sheets = book.getSheets();
			System.out.println(sheets.length);
			for (Sheet sheet : sheets) {
				int columnum = sheet.getColumns();// 得到列数
				int rownum = sheet.getRows();// 得到行数
				System.out.println(columnum + "*" + rownum);
				for (int i = 1; i < rownum; i++)// 循环进行读写
				{
					if (StringUtils.isNotEmpty(sheet.getCell(0, i).getContents())) {
						properties_cn.setProperty(sheet.getCell(0, i).getContents(), sheet.getCell(1, i).getContents());
						properties_en.setProperty(sheet.getCell(0, i).getContents(), sheet.getCell(0, i).getContents());
					}

				}
			}
			// Sheet oPara = book.getSheet("oPara");
			// Sheet oStor = book.getSheet("oStor");

			properties_cn.store(outputStream_cn, "author: shixing_11@sina.com");
			outputStream_cn.close();

			properties_en.store(outputStream_en, "author: shixing_11@sina.com");
			outputStream_en.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 写资源文件，含中文
	public static void writePropertiesFile(String filename) {
		Properties properties = new Properties();
		try {
			OutputStream outputStream = new FileOutputStream(filename);
			properties.setProperty("username", "myname");
			properties.setProperty("password", "mypassword");
			properties.setProperty("chinese", "中文");
			properties.store(outputStream, "author: shixing_11@sina.com");
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
