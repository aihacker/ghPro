package wxgh.sys.dao.entertain.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.place.PlaceImg;
import wxgh.param.entertain.place.PlaceImgParam;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/15
 */
@Repository
public class PlaceimgDao extends MyBatisDao<PlaceImg>{


    public Integer addImgs(List<PlaceImg> placeImgs) {
        return execute("xlkai_addImgs", placeImgs);
    }

    public List<PlaceImg> getData(PlaceImgParam placeImgQuery) {
        return selectList("getData", placeImgQuery);
    }
}
