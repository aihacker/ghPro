package wxgh.sys.service.wxadmin.act;

import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.app.consts.Status;
import wxgh.app.sys.api.ActSchedule;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.wxadmin.act.ActInfo;
import wxgh.data.wxadmin.act.ActList;
import wxgh.entity.entertain.act.Act;
import wxgh.param.entertain.act.ActParam;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Service
@Transactional(readOnly = true)
public class ActService {

    public ActInfo getAct(Integer id) {
        String sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.id, a.name, a.info, a.start_time, a.end_time, a.address, a.has_score, a.status, a.userid, a.add_time")
                .field("u.name as username")
                .join("user u", "u.userid = a.userid")
                .where("a.id = ?")
                .build().sql();
        return pubDao.query(ActInfo.class, sql, id);
    }

    public List<ActList> listAct(ActParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.id, a.userid, a.name, a.info, a.add_time")
                .field("u.name as username, u.avatar")
                .join("user u", "a.userid = u.userid")
                .where("a.status = :status")
                .where("a.act_type = :actType")
                .order("a.add_time", Order.Type.DESC);
        return pubDao.queryPage(sql, param, ActList.class);
    }

    @Transactional
    public void delAct(Integer id) {
        String sql = "delete from t_act where id = ?";
        pubDao.execute(sql, id);
    }

    @Transactional
    public void apply(Integer id, Integer status) {
        Act act = pubDao.query(Act.class, "select * from t_act where id = ?", id);
        if (act != null) {
            String sql = "update t_act set status = ? where id = ?";
            pubDao.execute(sql, status, id);

            //审核通过，推送活动
            if (Status.NORMAL.getStatus().equals(status)) {
                weixinPush.act(act.getActId(), null, true, Agent.ACT.getAgentId());

                actSchedule.act(act);
            }
        }
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private ActSchedule actSchedule;
}
