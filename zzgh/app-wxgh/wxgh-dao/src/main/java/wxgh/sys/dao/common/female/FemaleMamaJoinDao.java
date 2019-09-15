package wxgh.sys.dao.common.female;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.female.FemaleMamaJoin;
import wxgh.param.common.female.QueryFemaleMamaJoin;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:38
 *----------------------------------------------------------
 */
@Repository
public class FemaleMamaJoinDao extends MyBatisDao<FemaleMamaJoin> {

    public List<FemaleMamaJoin> getData(FemaleMamaJoin femaleMamaJoin) {
        return selectList("getData",femaleMamaJoin );
    }

    public List<FemaleMamaJoin> getDataWX(QueryFemaleMamaJoin queryFemaleMamaJoin) {
        return selectList("getDataWX", queryFemaleMamaJoin);
    }
    
}

