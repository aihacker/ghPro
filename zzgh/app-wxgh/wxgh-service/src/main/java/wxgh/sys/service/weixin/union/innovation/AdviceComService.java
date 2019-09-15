package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.entity.union.innovation.AdviceComm;
import wxgh.entity.union.innovation.AdviceZan;
import wxgh.sys.dao.union.innovation.AdviceComDao;
import wxgh.sys.dao.union.innovation.AdviceZanDao;

import java.util.Date;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/30
 */
@Service
@Transactional(readOnly = true)
public class AdviceComService {

    
    @Transactional
    public void addCom(AdviceComm adviceComm) {
        adviceComDao.save(adviceComm);
    }

    
    public List<AdviceComm> getComs(Integer adviceId) {
        String sql = "select c.*,u.avatar,u.name as username from t_innovate_advice_comm c " +
                "join t_user u on u.userid=c.userid " +
                "where c.advice_id=? and c.status=? order by c.add_time DESC";
        return pubDao.queryList(AdviceComm.class, sql, adviceId, 1);
    }

    /**
     * 表增加content, image_ids 后的业务逻辑
     * @param adviceId
     * @return
     */
    public List<AdviceComm> getComments(Integer adviceId){
        SQL sql = new SQL.SqlBuilder()
                .table("t_innovate_advice_comm c")
                .join("t_user u", "u.userid = c.userid")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, c.image_ids)) as img")
                .field("u.avatar,u.name as username")
                .field("c.id, c.cnt, c.advice_id, c.userid, c.add_time, c.zan_numb, c.status")
                .where("c.advice_id=? and c.status=?")
                .order("c.add_time", Order.Type.DESC).select().build();
        return pubDao.queryList(AdviceComm.class, sql.sql(), adviceId, 1);
    }

    
    @Transactional
    public Integer updateZan(Integer id, String userid, Integer adviceId) {

        AdviceZan adviceZan = new AdviceZan();
        adviceZan.setUserid(userid);
        adviceZan.setAddTime(new Date());
        adviceZan.setAdviceId(adviceId);
        adviceZan.setType(2);
        adviceZan.setCommId(id);

        adviceZanDao.save(adviceZan);

        String sql = "update t_innovate_advice_comm set zan_numb=zan_numb+1 where id=?";

        return pubDao.execute(sql, id);
    }

    
    public AdviceComm get(Integer id) {
        return adviceComDao.get(id);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private AdviceComDao adviceComDao;

    @Autowired
    private AdviceZanDao adviceZanDao;
}
