package pub.dao.query;

import java.io.Serializable;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-9-3
 */
public class QuerySettings implements Serializable {

	private static final long serialVersionUID = -74433747975640550L;

	protected QueryResultType resultType = QueryResultType.MAP;

	public QueryResultType getResultType() {
		return resultType;
	}
	public void setResultType(QueryResultType resultType) {
		this.resultType = resultType;
	}
}
