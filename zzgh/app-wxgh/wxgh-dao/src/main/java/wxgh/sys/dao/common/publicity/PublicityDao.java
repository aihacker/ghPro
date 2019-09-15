package wxgh.sys.dao.common.publicity;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.common.publicity.PublicityData;
import wxgh.entity.common.publicity.Publicity;
import wxgh.param.common.publicity.QueryPublicity;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-03 10:03
 *----------------------------------------------------------
 */
@Repository
public class PublicityDao extends MyBatisDao<Publicity> {

    
    public List<Publicity> getPublicitys(QueryPublicity queryPublicity) {
        return getSqlSession().selectList("getPublicitys", queryPublicity);
    }

    
    public void delete(Integer id) {
        getSqlSession().delete("del", id);
    }

    
    public void update(PublicityData lawData) {
        getSqlSession().update("updatePublicity", lawData);
    }

    
    public Integer addPublicity(PublicityData publicityData) {
        return getSqlSession().insert("addPublicity", publicityData);
    }

    
    public List<Publicity> queryPublicity(QueryPublicity queryLaw) {
        return  getSqlSession().selectList("queryPublicity", queryLaw);
    }

    
    public Integer getCount(QueryPublicity queryPublicity) {
        return selectOne("getCount", queryPublicity);
    }

    
    public Integer setTop(PublicityData publicityData) {
        return selectOne("setTop", publicityData);
    }

    
    public List<Publicity> applyListRefresh(QueryPublicity query) {
        return selectList("applyListRefresh", query);
    }

    
    public List<Publicity> applyListMore(QueryPublicity query) {
        return selectList("applyListMore", query);
    }

    
    public Publicity getPublicity(QueryPublicity queryPublicity) {
        return selectOne("getPublicitys", queryPublicity);
    }

}


