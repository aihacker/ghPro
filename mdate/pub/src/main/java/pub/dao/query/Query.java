package pub.dao.query;

import pub.dao.sql.model.Columns;
import pub.types.Executable;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-9-3
 */
public interface Query<T> extends Executable {

	Query<T> select(String select);
	Query<T> outerSelect(String outerSelect);
	Query<T> innerSelect(String innerSelect);
	Query<T> from(String from);
	Query<T> where(Object where);
	Query<T> distinct(boolean distinct);
	Query<T> orderBy(String orderBy);
	Query<T> groupBy(String groupBy);
	Query<T> put(String paramName, Object paramValue);
	Query<T> setSql(String sql);
	QueryResult<T> getResult();
	QuerySettings getSettings();
	void applySettings(QuerySettings settings);

    Query<T> select(Columns columns);

	void setQuietLog();

}
