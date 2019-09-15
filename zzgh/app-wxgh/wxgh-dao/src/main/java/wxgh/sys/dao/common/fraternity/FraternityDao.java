package wxgh.sys.dao.common.fraternity;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.FAShenheData;
import wxgh.entity.common.fraternity.FraternityApply;
import wxgh.param.common.fraternity.ApplyParam;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:02
 *----------------------------------------------------------
 */
@Repository
public class FraternityDao extends MyBatisDao<FraternityApply> {

    
    public Integer insertOrUpdate(FraternityApply apply) {
        return execute("xlkai_insertOrUpdate", apply);
    }

    
    public Integer updateApply(FraternityApply apply) {
        return execute("xlkai_updateApply", apply);
    }

    
    public List<FraternityApply> getApplys(ApplyParam query) {
        return selectList("xlkai_getApply", query);
    }

    
    public List<FraternityApply> getFAs(Integer id) {
        return selectList("getFAs");
    }

    
    public FraternityApply getApply(ApplyParam query) {
        return selectOne("xlkai_getApply", query);
    }

    
    public Integer del(Integer id) {
        return getSqlSession().delete("del",id);
    }

    
    public Integer apply(FAShenheData faShenheData) {
        return getSqlSession().update("apply", faShenheData);
    }

    
    public Integer getCount(ApplyParam applyQuery) {
        return selectOne("getCount", applyQuery);
    }

    
    public List<FraternityApply> applyListRefresh(ApplyParam query) {
        return selectList("applyListRefresh", query);
    }

    
    public List<FraternityApply> applyListMore(ApplyParam query) {
        return selectList("applyListMore",query);
    }
    
}

