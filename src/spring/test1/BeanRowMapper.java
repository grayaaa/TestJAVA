package spring.test1;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import json.GSONUtil;

import org.springframework.jdbc.core.RowMapper;

public class BeanRowMapper implements RowMapper {
	private Object beanClass;

	public BeanRowMapper(Class beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public Object mapRow(ResultSet set, int index) throws SQLException {
		Map map = (Map) dataConvert(set, index);

		Object result = GSONUtil.fromJson(map, beanClass.getClass());
		return result;
	}

	private Object dataConvert(ResultSet rs, int rowNum) {
		HashMap map = new HashMap();

		ResultSetMetaData rsmd = null;
		int rawCount = 0;
		try {
			// 如果rsmd为空
			if (null == rsmd) {
				rsmd = rs.getMetaData();
				rawCount = rsmd.getColumnCount();
			}
			for (int i = 0; i < rawCount; i++) {
				String columnName = rsmd.getCatalogName((i + 1)).toLowerCase();// 得到列名称
																				// 并转换小写
				int sqlType = rsmd.getColumnType(i + 1);
				Object sqlView = rs.getObject(columnName);

				switch (sqlType) {
				case Types.CHAR:
					map.put(columnName, sqlView.toString().trim());
					break;
				case Types.NUMERIC:
					if (null == sqlView) {
						sqlView = "";
					}
					map.put(columnName, sqlView.toString());
					break;
				default:
					map.put(columnName, sqlView != null ? sqlView.toString() : "");
					break;
				}
			}
		} catch (SQLException sqlE) {
			// ...
		} catch (Exception e) {
			// ...
		}
		return map;
	}
}