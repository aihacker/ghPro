package wxgh.sys.service.admin.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.party.di.DiPlan;
import wxgh.data.pub.NameValue;
import wxgh.entity.chat.ChatGroup;
import wxgh.param.admin.di.plan.PlanParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
@Transactional(readOnly = true)
public class PlanService {

    public List<NameValue> listGroup() {
        SQL sql = new SQL.SqlBuilder()
                .table("chat_group")
                .field("id as 'value', name")
                .where("type = ?")
                .build();
        return pubDao.queryList(NameValue.class, sql.sql(), ChatGroup.TYPE_DI);
    }

    public List<DiPlan> listPlan(PlanParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("p.*")
                .table("di_plan p");
        if (param.getGroupId() != null) {
            sql.join("chat_group g", "p.group_id = g.group_id")
                    .where("g.id = :groupId");
        }
        return pubDao.queryPage(sql, param, DiPlan.class);
    }

    @Transactional
    public void addPlan(DiPlan plan) {
        if (plan.getId() == null) {
            plan.setAddTime(new Date());

            String addSql = "insert into t_di_plan(content, start_time, end_time, add_time, group_id)" +
                    " select :content, :startTime, :endTime, :addTime, group_id " +
                    "from t_chat_group where id = :groupId";

            pubDao.executeBean(addSql, plan);
        } else {
            SQL.SqlBuilder sql = new SQL.SqlBuilder();
            if (plan.getContent() != null) {
                sql.set("content = :content");
            }
            if (plan.getStartTime() != null) {
                sql.set("start_time = :startTime");
            }
            if (plan.getEndTime() != null) {
                sql.set("end_time = :endTime");
            }
            sql.where("id = :id")
                    .update("di_plan");
            System.out.println(plan.getId());
            pubDao.executeBean(sql.build().sql(), plan);
        }
    }

    @Transactional
    public void delPlan(String id) {
        pubDao.execute(SQL.deleteByIds("di_plan", id));
    }

    @Autowired
    private PubDao pubDao;

}
