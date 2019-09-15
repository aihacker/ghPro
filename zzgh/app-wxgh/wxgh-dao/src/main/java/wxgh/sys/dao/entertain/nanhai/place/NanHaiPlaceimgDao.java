package wxgh.sys.dao.entertain.nanhai.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceImg;
import wxgh.entity.entertain.place.PlaceImg;
import wxgh.param.entertain.place.PlaceImgParam;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/15
 */
@Repository
public class NanHaiPlaceimgDao extends MyBatisDao<NanHaiPlaceImg>{


    public Integer addImgs(List<NanHaiPlaceImg> placeImgs) {
        return execute("xlkai_addImgs", placeImgs);
    }

    public List<NanHaiPlaceImg> getData(PlaceImgParam placeImgQuery) {
        return selectList("getData", placeImgQuery);
    }
}
