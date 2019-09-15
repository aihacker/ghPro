package wxgh.sys.dao.entertain.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.place.PlaceSite;
import wxgh.param.entertain.place.PlaceSiteParam;

import java.util.List;

/**
 * Created by XDLK on 2016/9/5.
 * <p>
 * Date： 2016/9/5
 */
@Repository
public class PlaceSiteDao extends MyBatisDao<PlaceSite>{


    public List<PlaceSite> getData(PlaceSiteParam placeSiteQuery) {
        return selectList("getData", placeSiteQuery);
    }

    public long updateSite(PlaceSite site) {
        return execute("xlkai_updateSite", site);
    }
}
