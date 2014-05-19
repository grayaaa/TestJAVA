import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPro {

	public String getPara() {

		Properties prop = new Properties();

		String str = this.getClass().getResource("ParaStor_en.properties").getFile();

		System.out.println(str);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(str);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
		return prop.getProperty("jnl_tsrecord_enable");

	}

	public static void main(String[] args) {
		ReadPro pro = new ReadPro();
		System.out.println(pro.getPara());
	}

	// -----------------------------------
	// ip.property内容如下：
	// ip:192.168.0.1

}