package others;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TestOracleJdbc {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection db_conn = DriverManager.getConnection("jdbc:oracle:thin:@10.0.31.166:1521:xsm", "xdata123",
				"xdata123");

		PreparedStatement db_pstmt = db_conn
				.prepareStatement("select * from pdb_service_config where key = 'max_con' and section = 'LServer'");
		ResultSet rs = db_pstmt.executeQuery();

		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		Map<String, Object> returnMap = new HashMap<String, Object>(columnCount);
		if (rs.next()) {
			String note = rs.getString("note");
			String ss = new String(note.getBytes("gbk"), "UTF-8");
			System.out.println(ss);

			// CharacterSet dbCharset =
			// CharacterSet.make(CharacterSet.ZHS16GBK_CHARSET);
			// oracle.sql.CHAR out_value = new oracle.sql.CHAR(note, dbCharset);
			// System.out.println(out_value);

			for (int i = 1; i <= columnCount; i++) {
				Object v = rs.getObject(i);
				if (v instanceof String) {
					String xx = (String) v;
					String tmp = new String(xx.getBytes("GBK"), "UTF-8");
					System.out.println(tmp);
				}

				returnMap.put(md.getColumnLabel(i).toLowerCase(), v);
			}
		}

		System.out.println(returnMap.toString());

	}

}
