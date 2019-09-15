package wxgh.sys.dao.common.act;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.ActApply;
import wxgh.param.common.act.ActApplyQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:06
 *----------------------------------------------------------
 */
@Repository
public class ActApplyDao extends MyBatisDao<ActApply>  {


    public List<ActApply> getApplys(ActApplyQuery query) {
        return selectList("xlkai_getApply", query);
    }

    
    public ActApply getApply(ActApplyQuery query) {
        return selectOne("xlkai_getApply", query);
    }

    
    public Integer updateApply(ActApply actApply) {
        return execute("xlkai_editApply", actApply);
    }

    
    public Integer del(Integer id) {
        return getSqlSession().delete("del", id);
    }

    
    public Integer getCount(ActApplyQuery actApplyQuery) {
        return selectOne("getCount", actApplyQuery);
    }

    
    public List<ActApply> applyListRefresh(ActApplyQuery query) {
        return selectList("applyListRefresh", query);
    }

    
    public List<ActApply> applyListMore(ActApplyQuery query) {
        return selectList("applyListMore", query);
    }
}

