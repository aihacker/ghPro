package pub.dao;

import javax.sql.DataSource;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version Jun 24, 2009
 */
public interface DataSourceAware {

	void setDataSource(DataSource dataSource);

}
