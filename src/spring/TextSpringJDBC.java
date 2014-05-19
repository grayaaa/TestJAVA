package spring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class TextSpringJDBC {
	public static void main(String[] args) {

		int rowCount = getJDBCTemplate().queryForInt("SELECT COUNT(*) FROM v$session WHERE username IS NOT NULL");
		System.out.println("USER_count=" + rowCount);

		// int rowCount =
		// getJDBCTemplate().queryForInt("select count(*) from user");
		// System.out.println("USER_count=" + rowCount);
		//
		// User user =
		// getJDBCTemplate().queryForObject("select * from user where id = ?",
		// new Object[] { 100001 },
		// new RowMapper<User>() {
		// public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// User user = new User();
		// user.setId(rs.getInt("id"));
		// user.setName(rs.getString("name"));
		// user.setAge(rs.getInt("age"));
		// user.setSex(rs.getString("sex"));
		// return user;
		// }
		// });
		// System.out.println(user.toString());
	}

	public static JdbcTemplate getJDBCTemplate() {
		// DriverManagerDataSource ds = new
		// DriverManagerDataSource("com.mysql.jdbc.Driver",
		// "jdbc:mysql://10.0.50.84:3309/test", "root", "root123");
		DriverManagerDataSource ds = new DriverManagerDataSource("oracle.jdbc.driver.OracleDriver",
				"jdbc:oracle:thin:@10.0.31.166:1521:xquerydb", "xdata123", "xdata123");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.0.31.166:1521:xquerydb", "xdata123",
					"xdata123");
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT COUNT(*) USER_count FROM v$session WHERE username IS NOT NULL");
			while (rs.next()) {
				System.out.println(rs.getInt("USER_count"));
			}
		} catch (Exception e) {
			System.out.println("db exception!");
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(ds);

		return jdbcTemplate;
	}
}
