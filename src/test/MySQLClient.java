package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Connection connection = null;

		Statement st = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.setProperty("javax.net.ssl.keyStore", "/mysql/keystore");
			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			System.setProperty("javax.net.ssl.trustStore", "/mysql/truststore");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");
			connection = DriverManager.getConnection("jdbc:mysql://10.0.31.170"
					+ ":3309/gv_local?verifyServerCertificate=false&useSSL=true", "root", "root123");
			// String url="jdbc:mysql://10.0.31.170:3309/gv_local";
			String sql = "show tables";

			// Properties info = new Properties();
			// info.setProperty("user", "root");
			// info.setProperty("password", "root123");
			// info.setProperty("useSSL", "true");
			// info.setProperty("serverSslCert", "classpath:server.crt");
			// connection = DriverManager.getConnection(url, info);
			//
			// info.setProperty("javax.net.ssl.keyStore",
			// "/CERTIFICATES/mysql4/cacert.pem");
			// info.setProperty("javax.net.ssl.keyStorePassword",
			// "/CERTIFICATES/mysql4/client-cert.pem");
			// info.setProperty("javax.net.ssl.trustStore",
			// "/CERTIFICATES/mysql4/client-key.pem");

			// System.out.println(sql);

			st = (Statement) connection.createStatement();

			ResultSet result = st.executeQuery(sql);

			while (result.next()) {

				ResultSetMetaData resultMetaData = result.getMetaData();

				StringBuffer columnBuffer = new StringBuffer();

				int columnCount = resultMetaData.getColumnCount();

				if (columnCount > 0) {

					columnBuffer.delete(0, columnBuffer.length());

					for (int i = 1; i <= columnCount; i++) {
						if (columnBuffer.length() > 0) {
							columnBuffer.append("|");
						}

						columnBuffer.append(result.getString(i));

					}
				}

				System.out.println(columnBuffer.toString());
			}

			connection.close();
		} catch (Exception e) {
			System.err.println("database connect failured-->" + e.getMessage());
		} finally {

			try {

				if (st != null) {
					st.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

			}

		}

	}
}
