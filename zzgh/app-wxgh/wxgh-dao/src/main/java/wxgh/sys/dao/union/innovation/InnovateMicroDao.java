package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.union.innovation.work.MicroInfo;
import wxgh.entity.union.innovation.InnovateMicro;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.param.union.innovation.QueryInnovateMicro;

import java.util.List;

/**
 * Created by ✔ on 2016/11/10.
 */
@Repository
public class InnovateMicroDao extends MyBatisDao<InnovateMicro> {

    
    public InnovateMicro getOne(QueryInnovateMicro queryInnovateMicro) {
        return selectOne("getOne", queryInnovateMicro);
    }

    
    public List<InnovateMicro> getList(QueryInnovateMicro queryInnovateRace) {
        return selectList("getList", queryInnovateRace);
    }

    
    public Integer getCount(QueryInnovateMicro queryInnovateRace) {
        return selectOne("getCount", queryInnovateRace);
    }

    
    public Integer updateMicro(InnovateMicro innovateMicro) {
        this.getSqlSession().update("updateMicro", innovateMicro);
        int pid = innovateMicro.getPid();
        return pid;
    }

    
    public List<MicroInfo> getInfos(InnovateApplyQuery query) {
        return selectList("xlkai_getInfos", query);
    }
}
