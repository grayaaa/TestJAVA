package spring.test1;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet set, int index) throws SQLException {
		Person person = new Person(set.getInt("id"), set.getString("name"), set.getInt("age"), set.getString("sex"));
		return person;
	}

}