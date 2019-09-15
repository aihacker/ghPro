package wxgh.sys.service.weixin.union.group.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.union.group.pay.ListData;
import wxgh.param.union.group.pay.RecordListParam;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@Service
@Transactional(readOnly = true)
public class RecordService {

    public List<ListData> listRecord(RecordListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("group_pay_record r")
                .field("r.id, r.money, r.add_time")
                .join("group g", "r.group_id = g.group_id")
                .order("r.add_time", Order.Type.DESC);
        if (param.getGroupId() != null) {
            sql.where("g.id = :groupId");
        }
        if (param.getUserid() != null) {
            sql.where("r.userid = :userid");
        }

        return pubDao.queryPage(sql, param, ListData.class);
    }

    @Autowired
    private PubDao pubDao;

}
