package wxgh.sys.service.weixin.party.sug;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.party.sug.PartySug;
import wxgh.param.party.sug.SugQuery;
import wxgh.sys.dao.party.sug.PartySugDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */
@Service
@Transactional(readOnly = true)
public class PartySugService {

    @Transactional
    public Integer save(PartySug partySug) {
        partySug.setAddTime(new Date());
        return (Integer) partySugDao.save(partySug);
    }

    public PartySug getSug(Integer id) {
        String sql = "select s.*, u.`name` as username, u.mobile, u.avatar, d.name as deptname\n" +
                "from t_party_sug s\n" +
                "join t_user u on s.userid = u.userid\n" +
                "left join t_dept d on u.deptid = d.deptid where s.id=?";
        return generalDao.query(PartySug.class, sql, id);
    }

    public List<PartySug> getSugs(SugQuery query) {
        String sql = "select s.*, GROUP_CONCAT(u.`name` SEPARATOR ',') as username from t_party_sug s\n" +
                "join t_user u on FIND_IN_SET(u.userid,s.users) where s.userid=?\n" +
                "GROUP BY s.id order by s.add_time DESC limit ?,?";
        return generalDao.queryList(PartySug.class, sql, query.getUserid(), query.getPagestart(), query.getRowsPerPage());
    }

    public Integer getCount(SugQuery query) {
        String sql = "select count(*) from t_party_sug where userid=?";
        Integer count = generalDao.query(Integer.class, sql, query.getUserid());
        return count == null ? 0 : count;
    }

    @Autowired
    private PartySugDao partySugDao;

    @Autowired
    private PubDao generalDao;

}
