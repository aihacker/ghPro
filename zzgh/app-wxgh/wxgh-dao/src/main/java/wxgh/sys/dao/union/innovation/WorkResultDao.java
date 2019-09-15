package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.param.union.innovation.work.WorkResultQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDLK on 2016/8/29.
 * <p>
 * Date： 2016/8/29
 */
@Repository
public class WorkResultDao extends MyBatisDao<WorkResult> {

    
    public List<WorkResult> getResults(WorkResultQuery query) {
        return selectList("xlkai_getResult", query);
    }

    
    public WorkResult getResult(WorkResultQuery query) {
        return selectOne("xlkai_getResult", query);
    }

    
    public List<WorkResult> resultList(WorkResultQuery workResultQuery) {

        Integer workType = workResultQuery.getWorkType();
        List<WorkResult> workResultList = new ArrayList<WorkResult>();
        switch (workType) {
            case 1:
                workResultList = selectList("getResult1", workResultQuery);
                break;
            case 2:
                workResultList = selectList("getResult2", workResultQuery);
                break;
            case 3:
                workResultList = selectList("getResult3", workResultQuery);
                break;
            default:
                break;
        }
        return workResultList;
    }

    
    public WorkResult result(WorkResultQuery workResultQuery) {
        Integer workType = workResultQuery.getWorkType();
        WorkResult workResult = new WorkResult();
        switch (workType) {
            case 1:
                workResult = selectOne("getOne1", workResultQuery);
                break;
            case 2:
                workResult = selectOne("getOne2", workResultQuery);
                break;
            case 3:
                workResult = selectOne("getOne3", workResultQuery);
                break;
            default:
                break;
        }
        return workResult;
    }

}
