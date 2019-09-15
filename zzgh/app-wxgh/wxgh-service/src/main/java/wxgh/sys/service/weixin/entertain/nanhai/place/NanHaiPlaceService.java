package wxgh.sys.service.weixin.entertain.nanhai.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.utils.StrUtils;
import wxgh.data.entertain.place.ListData;
import wxgh.data.entertain.place.yuyue.YuyueList;
import wxgh.data.pub.NameValue;
import wxgh.entity.entertain.nanhai.place.NanHaiPlace;
import wxgh.entity.entertain.place.Place;
import wxgh.param.entertain.place.PlaceParam;
import wxgh.sys.dao.entertain.nanhai.place.NanHaiPlaceDao;
import wxgh.sys.dao.entertain.place.PlaceDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/19.
 * <p>
 * Date： 2016/9/19
 */
@Service
@Transactional(readOnly = true)
public class NanHaiPlaceService {

    public List<YuyueList> getAllPlace(Integer placeId, Integer timeStatus) {
        String sql = "select p.title as placeName,\n" +
                "s.`name` as siteName,\n" +
                "c.`name` as cateName,\n" +
                "CONCAT(t.start_time,'~',t.end_time) as time, t.`week`, t.`status`, t.id\n" +
                "from t_nanhai_place p\n" +
                "join t_nanhai_place_site s on p.id = s.place_id\n" +
                "join t_nanhai_place_cate c on c.id = s.cate_id\n" +
                "join t_nanhai_place_time t on s.id = t.site_id\n" +
                "where p.id = ?";
        if (timeStatus != null) {
            if (timeStatus == -1) {
                sql += " and t.status != ?";
                return generalDao.queryList(YuyueList.class, sql, placeId, 1);
            }
            sql += " and t.status = ?";
            return generalDao.queryList(YuyueList.class, sql, placeId, timeStatus);
        }
        return generalDao.queryList(YuyueList.class, sql, placeId);
    }

    public List<wxgh.data.entertain.place.YuyueList> getAllPlaces(Integer placeId, Integer timeStatus) {

        // 获取单双周情况
        boolean isSingle = com.libs.common.data.DateUtils.isSingleWeek();

        String sql = "select p.title as placeName,\n" +
                "s.`name` as siteName,\n" +
                "c.`name` as cateName,\n" +
                "CONCAT(t.start_time,'~',t.end_time) as time, t.`week`, t.id, " +
                (isSingle ? " t.`d_status` as status " : " t.`s_status` as status ") +
                "from t_nanhai_place p\n" +
                "join t_nanhai_place_site s on p.id = s.place_id\n" +
                "join t_nanhai_place_cate c on c.id = s.cate_id\n" +
                "join t_nanhai_place_time t on s.id = t.site_id\n" +
                "where p.id = ?";
        if (timeStatus != null) {

            if (timeStatus == -1) {
                sql += isSingle ? " and t.d_status != ?" : " and t.s_status != ?";
                return generalDao.queryList(wxgh.data.entertain.place.YuyueList.class, sql, placeId, 1);
            }
            sql += isSingle ? " and t.d_status = ?" : " and t.s_status = ?";
            return generalDao.queryList(wxgh.data.entertain.place.YuyueList.class, sql, placeId, timeStatus);
        }
        return generalDao.queryList(wxgh.data.entertain.place.YuyueList.class, sql, placeId);
    }

    public NanHaiPlace getPlace(PlaceParam query) {
        return placeDao.getPlace(query);
    }

    public List<NanHaiPlace> getPlaces(PlaceParam query) {
        return placeDao.getPlaces(query);
    }

    public List<NanHaiPlace> getSchePlaces(PlaceParam query) {
        return placeDao.getSchePlaces(query);
    }

    public NanHaiPlace getSchePlace(PlaceParam query) {
        return placeDao.getSchePlace(query);
    }

    public Integer countPlaces(PlaceParam query) {
        String sql = "select count(*) from t_nanhai_place where find_in_set(?, type_int) and place_type=? and is_ad=? and status=?";
        Integer count = generalDao.query(Integer.class, sql, query.getTypeInt(), query.getPlaceType(), query.getIsAd(), query.getStatus());
        return count == null ? 0 : count;
    }

    public List<ListData> getIndexList(PlaceParam query) {
        String sql = "select id,title,type_int as cate,price,geohash,lat,lng from t_nanhai_place where find_in_set(?, type_int)" +
                " and place_type=? and is_ad=? and status=? LIMIT ?, ?";
        List<ListData> places = generalDao.queryList(ListData.class, sql,
                query.getTypeInt(), query.getPlaceType(), query.getIsAd(), query.getStatus(),
                query.getPagestart(), query.getRowsPerPage());

        //获取封面图片
        if (places != null && places.size() > 0) {
            sql = "select img_path from t_nanhai_place_img where place_id=? and is_cover=? limit 1";
            String sql1 = "select name from t_nanhai_place_cate where find_in_set(id, ?)>0 and status=1";
            for (int i = 0; i < places.size(); i++) {
                ListData tmpPlace = places.get(i);
                String imgPath = generalDao.query(String.class, sql, tmpPlace.getId(), 1);
                if (StrUtils.empty(imgPath)) {
                    imgPath = generalDao.query(String.class, sql, tmpPlace.getId(), 0);
                }
                places.get(i).setImgPath(imgPath);

                if (!StrUtils.empty(tmpPlace.getCate())) {
                    List<String> cates = generalDao.queryList(String.class, sql1, tmpPlace.getCate());
                    String cate = "";
                    for (String t : cates) {
                        cate += (t + ", ");
                    }
                    if (cate != "")
                        cate = cate.trim();
                    cate = cate.substring(0, cate.length() - 1);
                    places.get(i).setCate(cate);
                }
            }
        }
        return places;
    }

    public NanHaiPlace getAdPlace(Integer placeType, Integer cateId) {
        String sql = "select id,title,address,geohash,lat,lng,price,is_ad " +
                "from t_nanhai_place where place_type=? and find_in_set(?, type_int) and status=1 and is_ad=? limit 1";
        NanHaiPlace place = generalDao.query(NanHaiPlace.class, sql, placeType, cateId, 1);
        if (place == null) {
            place = generalDao.query(NanHaiPlace.class, sql, placeType, cateId, 0);
        }
        if (place != null) {
            sql = "select img_path from t_nanhai_place_img where place_id=? and is_cover=? limit 1";

            String imgPath = generalDao.query(String.class, sql, place.getId(), 1);
            if (StrUtils.empty(imgPath)) {
                imgPath = generalDao.query(String.class, sql, place.getId(), 0);
            }
            place.setCoverImgPath(imgPath);
        }
        return place;
    }

    public Integer getCount(PlaceParam query) {
        return placeDao.getCount(query);
    }

    @Transactional
    public Integer delPlace(Integer id) {
        placeDao.delPlace(id);
        return -1;
    }

    @Transactional
    public void delete(String id) {
        String[] ids = id.split(",");
        placeDao.delete(ids);
    }

    @Transactional
    public Integer updatePlace(NanHaiPlace place) {
        return placeDao.updatePlace(place);
    }

    public List<NameValue> listCate(Integer placeId) {
        String sql = "select pc.id as value, pc.`name` from t_nanhai_place_cate pc where FIND_IN_SET(id, (select type_int from t_nanhai_place where id = ?))";
        return generalDao.queryList(NameValue.class, sql, placeId);
    }

    @Autowired
    private PubDao generalDao;

    @Autowired(required = true)
    private NanHaiPlaceDao placeDao;
}
