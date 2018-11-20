package pub.dao.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-5-1
 */
public class ArrayRowMapper implements RowMapper<Object[]> {

	private int columnCount = -1;

	@Override
	public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (columnCount == -1) {
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
		}
		Object[] row = new Object[columnCount];
		for (int n = 0; n < columnCount; n++) {
			Object colValue = JdbcUtils.getResultSetValue(rs, n + 1);
			row[n] = colValue;
		}
		return row;
	}

}
