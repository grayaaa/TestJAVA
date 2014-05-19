package spring.test1;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 业务bean
 * 
 * @author Hongten
 * 
 */
@Repository("personService")
public class PersonServiceBean implements PersonService {

	/**
	 * 数据源
	 */
	private DataSource dataSource;
	/**
	 * spring提供的jdbc操作辅助类
	 */
	private JdbcTemplate jdbcTemplate;

	// 设置数据源
	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void save(Person person) {
		jdbcTemplate.update("insert into person(id,name,age,sex)values(?,?,?,?)",
				new Object[] { person.getId(), person.getName(), person.getAge(), person.getSex() },
				new int[] { java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.INTEGER,
						java.sql.Types.VARCHAR });
	}

	public void update(Person person) {
		jdbcTemplate.update("update person set name=?,age=?,sex=? where id=?",
				new Object[] { person.getName(), person.getAge(), person.getSex(), person.getId() },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.INTEGER, java.sql.Types.VARCHAR,
						java.sql.Types.INTEGER });

	}

	public Person getPerson(Integer id) {
		Person person = (Person) jdbcTemplate.queryForObject("select * from person where id=?", new Object[] { id },
				new int[] { java.sql.Types.INTEGER }, new PersonRowMapper());
		return person;

	}

	@SuppressWarnings("unchecked")
	public List<Person> getPerson() {
		// List<Person> list = jdbcTemplate.query("select * from person", new
		// PersonRowMapper());
		List<Person> list = jdbcTemplate.query("select * from person", new BeanPropertyRowMapper(Person.class));

		return list;

	}

	public void delete(Integer id) {
		jdbcTemplate.update("delete from person where id = ?", new Object[] { id },
				new int[] { java.sql.Types.INTEGER });

	}
}