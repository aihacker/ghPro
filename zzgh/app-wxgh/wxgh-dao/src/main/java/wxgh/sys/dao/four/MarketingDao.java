package wxgh.sys.dao.four;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.four.Marketing;
import wxgh.param.four.QueryMarketingParam;

import java.util.List;

/**
 * Created by ✔ on 2016/11/16.
 */
@Repository
public class MarketingDao extends MyBatisDao<Marketing>{

    public List<Marketing> getList(QueryMarketingParam queryMarketing) {
        return selectList("getList", queryMarketing);
    }

    public Marketing getOne(QueryMarketingParam queryMarketing) {
        return selectOne("getList", queryMarketing);
    }
}
