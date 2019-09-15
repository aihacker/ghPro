package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.union.innovation.work.RaceInfo;
import wxgh.entity.union.innovation.InnovateRace;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.param.union.innovation.QueryInnovateRace;

import java.util.List;

@Repository
public class InnovateRaceDao extends MyBatisDao<InnovateRace> {

    
    public InnovateRace getOne(QueryInnovateRace queryInnovateRace) {
        return selectOne("getOne", queryInnovateRace);
    }

    
    public List<InnovateRace> getList(QueryInnovateRace queryInnovateRace) {
        return selectList("getList", queryInnovateRace);
    }

    
    public Integer getCount(QueryInnovateRace queryInnovateRace) {
        return selectOne("getCount", queryInnovateRace);
    }

    
    public Integer updateRace(InnovateRace innovateRace) {
        this.getSqlSession().update("updateRace", innovateRace);
        int id = innovateRace.getId();
        return id;
    }

    
    public List<RaceInfo> getInfos(InnovateApplyQuery query) {
        return selectList("xlkai_getInfos", query);
    }
}
