package wxgh.sys.dao.common.female;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.female.FemaleMama;
import wxgh.param.common.female.QueryFemaleMama;

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
public class FemaleMamaDao extends MyBatisDao<FemaleMama> {

    public List<FemaleMama> getData(QueryFemaleMama queryFemaleMama) {
        return selectList("getDataWX",queryFemaleMama );
    }
    
}

