package wxgh.sys.dao.party.match;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.match.SheyingMatch;

import java.util.List;

/**
 * Created by XDLK on 2016/6/20.
 * <p>
 * Date： 2016/6/20
 */

@Repository
public class SheyingMatchDao extends MyBatisDao<SheyingMatch>  {

    public List<SheyingMatch> getData(SheyingMatch sheyingMatch) {
        return selectList("getData", sheyingMatch);
    }

}
