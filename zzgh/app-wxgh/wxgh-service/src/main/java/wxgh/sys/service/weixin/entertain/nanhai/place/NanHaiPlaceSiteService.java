package wxgh.sys.service.weixin.entertain.nanhai.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.entertain.place.SelectSite;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceSite;
import wxgh.entity.entertain.place.PlaceSite;
import wxgh.param.entertain.place.PlaceSiteParam;
import wxgh.sys.dao.entertain.nanhai.place.NanHaiPlaceSiteDao;
import wxgh.sys.dao.entertain.place.PlaceSiteDao;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/1/12
 */
@Service
@Transactional(readOnly = true)
public class NanHaiPlaceSiteService {

    public List<NanHaiPlaceSite> getSites(Integer placeId, Integer cateId) {
        String sql = "select * from t_nanhai_place_site where place_id=?";
        if (cateId != null) {
            sql += " and cate_id=? order by name";
            return generalDao.queryList(NanHaiPlaceSite.class, sql, placeId, cateId);
        }
        return generalDao.queryList(NanHaiPlaceSite.class, sql, placeId);
    }

    @Transactional
    public int updateSiteNumb(Integer id) {
        String sql = "update t_nanhai_place_site set numb=numb+1 where id = ?";
        return generalDao.execute(sql, id);
    }

    public List<NanHaiPlaceSite> getData(PlaceSiteParam placeSiteQuery) {
        return placeSiteDao.getData(placeSiteQuery);
    }

    @Transactional
    public void save(NanHaiPlaceSite placeSite) {
        this.placeSiteDao.save(placeSite);
    }

    @Transactional
    public void del(Integer id) {
        placeSiteDao.delete(id);
    }

    public Integer getCount(Integer placeId) {
        String sql = "select sum(numb) from t_nanhai_place_site where place_id = ?";
        Integer count = generalDao.query(Integer.class, sql, placeId);
        return count == null ? 0 : count;
    }

    public List<SelectSite> getSelectSite(Integer placeId, Integer cateId) {
            String sql = "select id, name,price,score from t_nanhai_place_site where place_id = ? and cate_id = ? ORDER BY name asc";
        return generalDao.queryList(SelectSite.class, sql, placeId, cateId);
    }

    @Transactional
    public void updateSite(NanHaiPlaceSite site) {
        placeSiteDao.updateSite(site);
    }

    @Autowired
    private PubDao generalDao;

    @Autowired
    private NanHaiPlaceSiteDao placeSiteDao;
}
