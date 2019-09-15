package wxgh.sys.service.weixin.party.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.party.di.notice.NoticeInfo;
import wxgh.data.party.di.notice.NoticeList;
import wxgh.param.party.di.notice.ListParam;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
@Transactional(readOnly = true)
public class NoticeService {

    public List<NoticeList> listNotice(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("di_notice n")
                .field("n.id, n.title, n.content, n.add_time")
                .sys_file("n.image")
                .order("n.add_time", Order.Type.DESC);
        if (param.getGroupId() != null) {
            if (param.getGroupId() == 0) {
                sql.where("n.group_id = '0'");
            } else {
                sql.join("chat_group g", "g.group_id = n.group_id")
                        .where("g.id = :groupId");
            }
        }
        return pubDao.queryPage(sql, param, NoticeList.class);
    }

    public NoticeInfo getNoticeInfo(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("di_notice n")
                .field("n.id, n.title, n.content, n.add_time, n.author, n.link")
                .sys_file("n.image")
                .where("n.id = ?")
                .build();
        return pubDao.query(NoticeInfo.class, sql.sql(), id);
    }

    @Autowired
    private PubDao pubDao;

}
