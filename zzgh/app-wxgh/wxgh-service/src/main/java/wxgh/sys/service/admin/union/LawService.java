package wxgh.sys.service.admin.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.law.Law;
import wxgh.param.union.law.LawParam;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-16
 **/
@Service
public class LawService {

    public List<Law> lawList(LawParam param) {
        String sql = "select %s from t_law";
        String selSql = String.format(sql, "*");
        if (param.getPageIs()) {
            String countSql = String.format(sql, "count(*)");
            Integer count = pubDao.queryParamInt(countSql, param);
            param.setTotalCount(count);

            selSql += " order by sort_id ASC";
            selSql += " limit :pagestart, :rowsPerPage";
        }
        return pubDao.queryList(selSql, param, Law.class);
    }

    @Autowired
    private PubDao pubDao;
}
