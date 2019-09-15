package wxgh.sys.dao.four;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.FpShenheData;
import wxgh.data.four.FoursmallData;
import wxgh.entity.four.FourSmall;
import wxgh.param.four.FourSmallParam;

import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
@Repository
public class FourSmallDao extends MyBatisDao<FourSmall>{


    public List<FourSmall> getSmalls(FourSmallParam query) {
        return selectList("xlkai_getSmall", query);
    }

    public FourSmall getSmall(FourSmallParam query) {
        return selectOne("xlkai_getSmall", query);
    }

    public List<FourSmall> getFourSmallList() {
        return getSqlSession().selectList("liuhe.sys.entity.FourSmall.getFourSmallList");
    }

    public void shenhe(FpShenheData fpShenheData) {
        getSqlSession().update("liuhe.sys.entity.FourSmall.shenhe", fpShenheData);
    }

    public void delete(Integer id) {
        getSqlSession().delete("liuhe.sys.entity.FourSmall.del", id);
    }

    public void update(FoursmallData foursmallData) {
        getSqlSession().update("liuhe.sys.entity.FourSmall.updateFourSmall", foursmallData);
    }
}
