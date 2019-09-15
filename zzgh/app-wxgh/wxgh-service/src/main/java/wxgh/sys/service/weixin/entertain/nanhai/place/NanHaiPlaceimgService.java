package wxgh.sys.service.weixin.entertain.nanhai.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceImg;
import wxgh.entity.entertain.place.PlaceImg;
import wxgh.param.entertain.place.PlaceImgParam;
import wxgh.sys.dao.entertain.nanhai.place.NanHaiPlaceimgDao;
import wxgh.sys.dao.entertain.place.PlaceimgDao;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/15
 */
@Service
@Transactional(readOnly = true)
public class NanHaiPlaceimgService {

    @Transactional
    public Integer addImg(NanHaiPlaceImg placeImg) {
        //placeimgDao.save(placeImg);
        String sql = new SQL.SqlBuilder()
                .table("nanhai_place_img")
                .field("img_path,add_time,status,place_id")
                .value(":imgPath,:addTime,:status,:placeId")
                .insert()
                .build().sql();
        return generalDao.insertAndGetKey(sql,placeImg);
    }

    public Integer addImgs(List<NanHaiPlaceImg> placeImgs) {
        return placeimgDao.addImgs(placeImgs);
    }

    public List<String> getImgs(Integer placeId) {
        String sql = "select img_path from t_nanhai_place_img where place_id=? and status=1 order by is_cover DESC";
        return generalDao.queryList(String.class, sql, placeId);
    }

    public List<NanHaiPlaceImg> getData(PlaceImgParam placeImgQuery) {
        return placeimgDao.getData(placeImgQuery);
    }

    @Transactional
    public void del(NanHaiPlaceImg placeImg) {
        this.placeimgDao.delete(placeImg);
    }

    @Autowired
    private NanHaiPlaceimgDao placeimgDao;

    @Autowired
    private PubDao generalDao;
}
