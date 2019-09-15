package wxgh.sys.dao.entertain.nanhai.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceSite;
import wxgh.entity.entertain.place.PlaceSite;
import wxgh.param.entertain.place.PlaceSiteParam;

import java.util.List;

/**
 * Created by XDLK on 2016/9/5.
 * <p>
 * Date： 2016/9/5
 */
@Repository
public class NanHaiPlaceSiteDao extends MyBatisDao<NanHaiPlaceSite>{


    public List<NanHaiPlaceSite> getData(PlaceSiteParam placeSiteQuery) {
        return selectList("getData", placeSiteQuery);
    }

    public long updateSite(NanHaiPlaceSite site) {
        return execute("xlkai_updateSite", site);
    }
}
