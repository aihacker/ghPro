package wxgh.sys.service.weixin.union.group.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.union.group.act.RecordList;
import wxgh.data.union.group.act.RecordTotal;
import wxgh.param.union.group.act.ListParam;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@Service
@Transactional(readOnly = true)
public class RecordService {

    /**
     * 获取用户在协会内的总积分和总会费
     *
     * @param groupId
     * @param userid
     * @return
     */
    public RecordTotal getTotal(Integer groupId, String userid) {
        SQL sql = new SQL.SqlBuilder()
                .table("group_user u")
                .field("score, money")
                .join("group g", "u.group_id = g.group_id")
                .where("u.userid = ? and g.id = ?")
                .select().build();
        return pubDao.query(RecordTotal.class, sql.sql(), userid, groupId);
    }

    /**
     * 获取用户参与活动获得积分和花费会费记录
     *
     * @param param
     * @return
     */
    public List<RecordList> listRecord(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("group_act_record r")
                .field("r.*, a.name as actName")
                .join("act a", "a.act_id = r.act_id")
                .order("r.add_time", Order.Type.DESC)
                .where("r.userid = :userid");
        if (param.getGroupId() != null) {
            sql.join("group g", "r.group_id = g.group_id")
                    .where("g.id = :groupId");
        }
        return pubDao.queryPage(sql, param, RecordList.class);
    }

    @Autowired
    private PubDao pubDao;
}
