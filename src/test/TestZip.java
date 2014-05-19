package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class TestZip {

	public static void main(String[] args) throws IOException {
		long starTime = 0;
		long endTime = 0;

		System.out.println("开始压缩...");
		starTime = System.currentTimeMillis();
		ZIP("c:/test", "c:/hadoop.zip");
		endTime = System.currentTimeMillis();
		System.out.println("压缩完毕！花费时间: " + (endTime - starTime) + " 毫秒！");

		System.out.println("开始解压...");
		starTime = System.currentTimeMillis();
		UnZIP("c:/hadoop.zip", "c:/");
		endTime = System.currentTimeMillis();
		System.out.println("解压完毕！花费时间: " + (endTime - starTime) + " 毫秒！");

	}

	public static void ZIP(String source, String zipFileName)
			throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new File(zipFileName));

		// 设置压缩的时候文件名编码为gb2312
		zos.setEncoding("gb2312");

		File f = new File(source);

		if (f.isDirectory()) {
			// 如果直接压缩文件夹
			ZIPDIR(source, zos, f.getName() + "/");// 此处使用/来表示目录，如果使用\\来表示目录的话，会导致压缩后的文件目录组织形式在解压缩的时候不能正确识别。
		} else {
			// 如果直接压缩文件
			ZIPDIR(f.getPath(), zos, new File(f.getParent()).getName() + "/");
			ZIPFile(f.getPath(), zos, new File(f.getParent()).getName() + "/"
					+ f.getName());
		}

		zos.closeEntry();
		zos.close();
	}

	public static void ZIPFile(String sourceFileName, ZipOutputStream zos,
			String tager) throws IOException {
		ZipEntry ze = new ZipEntry(tager);
		zos.putNextEntry(ze);

		// 读取要压缩文件并将其添加到压缩文件中
		FileInputStream fis = new FileInputStream(new File(sourceFileName));
		byte[] bf = new byte[2048];
		int location = 0;
		while ((location = fis.read(bf)) != -1) {
			zos.write(bf, 0, location);
		}
		fis.close();
	}

	public static void ZIPDIR(String sourceDir, ZipOutputStream zos,
			String tager) throws IOException {
		ZipEntry ze = new ZipEntry(tager);
		zos.putNextEntry(ze);
		// 提取要压缩的文件夹中的所有文件
		File f = new File(sourceDir);
		File[] flist = f.listFiles();
		if (flist != null) {
			// 如果该文件夹下有文件则提取所有的文件进行压缩
			for (File fsub : flist) {
				if (fsub.isDirectory()) {
					// 如果是目录则进行目录压缩
					ZIPDIR(fsub.getPath(), zos, tager + fsub.getName() + "/");
				} else {
					// 如果是文件，则进行文件压缩
					ZIPFile(fsub.getPath(), zos, tager + fsub.getName());
				}
			}
		}
	}

	public static void UnZIP(String sourceFileName, String desDir)
			throws IOException {
		// 创建压缩文件对象, 注意指定格式与压缩时同样的格式
		ZipFile zf = new ZipFile(new File(sourceFileName), "gb2312");

		// 获取压缩文件中的文件枚举
		Enumeration<ZipEntry> en = zf.getEntries();
		int length = 0;
		byte[] b = new byte[2048];

		// 提取压缩文件夹中的所有压缩实例对象
		while (en.hasMoreElements()) {
			ZipEntry ze = en.nextElement();
			// 创建解压缩后的文件实例对象
			File f = new File(desDir + ze.getName());
			// 如果当前压缩文件中的实例对象是文件夹就在解压缩后的文件夹中创建该文件夹
			if (ze.isDirectory()) {
				f.mkdirs();
			} else {
				// 如果当前解压缩文件的父级文件夹没有创建的话，则创建好父级文件夹
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}

				// 将当前文件的内容写入解压后的文件夹中。
				OutputStream outputStream = new FileOutputStream(f);
				InputStream inputStream = zf.getInputStream(ze);
				while ((length = inputStream.read(b)) > 0)
					outputStream.write(b, 0, length);

				inputStream.close();
				outputStream.close();
			}
		}
		zf.close();
	}

}
