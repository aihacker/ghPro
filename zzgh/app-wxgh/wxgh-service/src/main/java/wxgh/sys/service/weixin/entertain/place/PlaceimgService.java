package wxgh.sys.service.weixin.entertain.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.entertain.place.PlaceImg;
import wxgh.param.entertain.place.PlaceImgParam;
import wxgh.sys.dao.entertain.place.PlaceimgDao;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/15
 */
@Service
@Transactional(readOnly = true)
public class PlaceimgService{

    @Transactional
    public Integer addImg(PlaceImg placeImg) {
        //placeimgDao.save(placeImg);
        String sql = new SQL.SqlBuilder()
                .table("place_img")
                .field("img_path,add_time,status,place_id")
                .value(":imgPath,:addTime,:status,:placeId")
                .insert()
                .build().sql();
        return generalDao.insertAndGetKey(sql,placeImg);
    }

    public Integer addImgs(List<PlaceImg> placeImgs) {
        return placeimgDao.addImgs(placeImgs);
    }

    public List<String> getImgs(Integer placeId) {
        String sql = "select img_path from t_place_img where place_id=? and status=1 order by is_cover DESC";
        return generalDao.queryList(String.class, sql, placeId);
    }

    public List<PlaceImg> getData(PlaceImgParam placeImgQuery) {
        return placeimgDao.getData(placeImgQuery);
    }

    @Transactional
    public void del(PlaceImg placeImg) {
        this.placeimgDao.delete(placeImg);
    }

    @Autowired
    private PlaceimgDao placeimgDao;

    @Autowired
    private PubDao generalDao;
}
