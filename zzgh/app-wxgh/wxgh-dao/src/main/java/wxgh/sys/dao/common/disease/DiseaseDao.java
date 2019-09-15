package wxgh.sys.dao.common.disease;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.param.common.disease.ApplyQuery;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Repository
public class DiseaseDao extends MyBatisDao<DiseaseApply> {

    public List<DiseaseApply> getApplys(ApplyQuery query) {
        return selectList("xlkai_getApply", query);
    }

    public DiseaseApply getApply(ApplyQuery query) {
        return selectOne("xlkai_getApply", query);
    }

    public Integer updateApply(DiseaseApply apply) {
        return execute("xlkai_updateApply", apply);
    }

    public Integer del(Integer id) {
        return getSqlSession().delete("del", id);
    }

    public Integer getCount(ApplyQuery applyQuery) {
        return selectOne("getCount", applyQuery);
    }

    public List<DiseaseApply> applyListRefresh(ApplyQuery query) {
        return selectList("applyListRefresh", query);
    }

    public List<DiseaseApply> applyListMore(ApplyQuery query) {
        return selectList("applyListMore",query);
    }
}
