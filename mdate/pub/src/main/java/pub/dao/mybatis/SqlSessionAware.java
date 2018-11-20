package pub.dao.mybatis;

import org.apache.ibatis.session.SqlSession;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-15
 */
public interface SqlSessionAware {

    void setSqlSession(SqlSession sqlSession);

}
