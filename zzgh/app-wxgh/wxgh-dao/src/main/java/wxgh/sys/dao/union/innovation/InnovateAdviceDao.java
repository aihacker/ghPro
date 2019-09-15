package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.union.innovation.work.AdviceInfo;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.param.union.innovation.QueryInnovateAdvice;

import java.util.List;

/**
 * Created by ✔ on 2016/11/10.
 */
@Repository
public class InnovateAdviceDao extends MyBatisDao<InnovateAdvice> {

    
    public List<InnovateAdvice> getList(QueryInnovateAdvice queryInnovateAdvice) {
        return selectList("getList", queryInnovateAdvice);
    }

    
    public InnovateAdvice getOne(QueryInnovateAdvice queryInnovateAdvice) {
        return selectOne("getOne", queryInnovateAdvice);
    }

    
    public List<InnovateAdvice> getList2(QueryInnovateAdvice queryInnovateAdvice) {
        return selectList("getList2", queryInnovateAdvice);
    }

    
    public Integer getCount(QueryInnovateAdvice queryInnovateAdvice) {
        return selectOne("getCount", queryInnovateAdvice);
    }

    
    public Integer updateIntegerAdvice(QueryInnovateAdvice queryInnovateAdvice) {
        return selectOne("updateIntegerAdvice", queryInnovateAdvice);
    }

    
    public List<AdviceInfo> getInfos(InnovateApplyQuery query) {
        return selectList("xlkai_getInfos", query);
    }
}
