package wxgh.sys.dao.common.law;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.common.law.LawData;
import wxgh.entity.common.law.Law;
import wxgh.param.common.law.QueryLaw;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 14:34
 *----------------------------------------------------------
 */
@Repository
public class LawDao extends MyBatisDao<Law> {

    
    public List<Law> getLaws(QueryLaw queryLaw) {
        return getSqlSession().selectList("getLaws", queryLaw);
    }

    
    public void delete(Integer id) {
        getSqlSession().delete("del", id);
    }

    
    public void update(LawData lawData) {
        getSqlSession().update("updateLaw", lawData);
    }

    
    public void addLaw(LawData lawData) {
        getSqlSession().insert("addLaw", lawData);
    }

    
    public List<Law> queryLaw(QueryLaw queryLaw) {
        return getSqlSession().selectList("queryLaw", queryLaw);
    }

    
    public List<Law> getFieldLaws() {
        return selectList("xlkai_getLaw");
    }

    
    public Law getLaw(Integer id) {
        return selectOne("getLaw",id);
    }

    
    public Integer getCount(QueryLaw queryLaw) {
        return selectOne("getCount", queryLaw);
    }

    
    public Integer updateSortId(LawData lawData) {
        return getSqlSession().update("updateSortId",lawData);
    }
    
}

