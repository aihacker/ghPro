package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.wxadmin.union.AdviceInfo;
import wxgh.entity.pub.User;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.param.union.innovation.QueryApply;
import wxgh.sys.dao.union.innovation.InnovateApplyDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ✔ on 2016/11/10.
 */
@Service
@Transactional(readOnly = true)
public class InnovateApplyService {

    
    @Transactional
    public Integer add(User user, Integer type, Float price) {
        InnovateApply innovateApply = new InnovateApply();
        innovateApply.setApplyStep(1);
        innovateApply.setStatus(0);
        innovateApply.setUserid(user.getUserid());
        innovateApply.setAddTime(new Date());
        innovateApply.setApplyType(type);
        innovateApply.setDeptid(user.getDeptid());
//        innovateApply.setDeptid(user.getDepartment());
        innovateApply.setApplyPrice(price);

        innovateApplyDao.save(innovateApply);

        return innovateApply.getId();
    }

    
    @Transactional
    public Integer updateApply(InnovateApply innovateApply) {
        return innovateApplyDao.updateApply(innovateApply);
    }

    
    public List<InnovateApply> getApplys(QueryApply queryApply) {
        return innovateApplyDao.getApplys(queryApply);
    }

    
    public Integer countApply(QueryApply queryApply) {
        Integer count = innovateApplyDao.countApply(queryApply);
        return count == null ? 0 : count;
    }

    
    public InnovateApply get(Serializable id) {
        return innovateApplyDao.get(id);
    }

    
    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_innovate_apply set status=? where id=?";
        pubDao.execute(sql, status, id);
    }

    public AdviceInfo getInfo(Integer adviceId){
        SQL sql = new SQL.SqlBuilder()
                .table("t_innovate_apply")
                .field("userid, apply_type as applyType")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(AdviceInfo.class, sql.sql(), adviceId);
    }

    public InnovateApply getUseridAndStatus(Integer adviceId){
        SQL sql = new SQL.SqlBuilder()
                .table("t_innovate_apply")
                .field("userid,status")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(InnovateApply.class, sql.sql(), adviceId);
    }

    @Transactional
    public void updateStatusByAdviceId(Integer adviceId, Integer status) {
        String sql = "UPDATE t_innovate_apply a \n" +
                "join t_innovate_advice ia on a.id = ia.pid\n" +
                "set a.`status` = ? where ia.id = ?";
        pubDao.execute(sql, status, adviceId);
    }

    @Autowired
    private InnovateApplyDao innovateApplyDao;

    @Autowired
    private PubDao pubDao;
}
