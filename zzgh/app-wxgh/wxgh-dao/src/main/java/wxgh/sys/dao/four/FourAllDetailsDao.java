package wxgh.sys.dao.four;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.four.FourAllDetails;
import wxgh.param.four.FourAllDetailsParam;

import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
@Repository
public class FourAllDetailsDao extends MyBatisDao<FourAllDetails>{


    public FourAllDetails getAllDetails(FourAllDetailsParam query) {
        return selectOne("get_market_four_details",query);
    }

    public List<FourAllDetails> getList(FourAllDetailsParam query) {
        return selectList("get_market_four_details",query);
    }

}
