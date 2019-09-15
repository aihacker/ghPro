package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.union.innovation.work.WorkInfo;
import wxgh.entity.union.innovation.InnovateShop;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.param.union.innovation.QueryInnovateShop;

import java.util.List;

@Repository
public class InnovateShopDao extends MyBatisDao<InnovateShop> {

    
    public InnovateShop getOne(QueryInnovateShop queryInnovateShop) {
        return selectOne("getOne", queryInnovateShop);
    }

    
    public List<InnovateShop> getList(QueryInnovateShop queryInnovateShop) {
        return selectList("getList", queryInnovateShop);
    }

    
    public Integer getCount(QueryInnovateShop queryInnovateShop) {
        return selectOne("getCount", queryInnovateShop);
    }

    
    public Integer updateShop(QueryInnovateShop queryInnovateShop) {
        this.getSqlSession().update("updateShop", queryInnovateShop);
        int id = queryInnovateShop.getId();
        return id;
    }

    
    public List<WorkInfo> getInfos(InnovateApplyQuery query) {
        return selectList("xlkai_getInfos", query);
    }
}
