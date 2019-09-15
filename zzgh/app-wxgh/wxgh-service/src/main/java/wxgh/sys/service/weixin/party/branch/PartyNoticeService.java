package wxgh.sys.service.weixin.party.branch;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.entity.party.party.PartyNotice;
import wxgh.param.party.notice.NoticeParam;

import java.util.Date;
import java.util.List;

/**
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-16  09:44
 * --------------------------------------------------------- *
 */
@Service
@Transactional(readOnly = true)
public class PartyNoticeService {

    @Autowired
    private PubDao pubDao;

    @Transactional
    public Integer add(PartyNotice partyNotice) {
        if (partyNotice.getAddTime() == null)
            partyNotice.setAddTime(new Date());
        String sql = new SQL.SqlBuilder()
                .table("party_notice")
                .field("title, content, type, userid, branch_id, add_time,start_time,end_time,address,remark")
                .value(":title, :content, :type, :userid, :branchId, :addTime,:startTime,:endTime,:address,:remark")
                .insert()
                .build()
                .sql();
        return pubDao.insertAndGetKey(sql, partyNotice);
    }

    public List<PartyNotice> list(NoticeParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_notice pn")
                .field("pn.id, pn.title, pn.type, pn.content, pn.add_time as addTime, pn.userid, u.name as username")
                .field("d.name as branchName")
                .join("party_dept d", "d.id = pn.branch_id", Join.Type.LEFT)
                .join("user u", "u.userid = pn.userid", Join.Type.LEFT)
                .where("pn.branch_id = :branchId or pn.branch_id = 0")
                .order("pn.add_time", Order.Type.DESC);
        return pubDao.queryPage(sql, param, PartyNotice.class);
    }

    public PartyNotice get(Integer id) {
        String sql = new SQL.SqlBuilder()
                .table("party_notice pn")
                .field("pn.*, u.name as username")
                .field("d.name as branchName")
                .join("party_dept d", "d.id = pn.branch_id", Join.Type.LEFT)
                .join("user u", "u.userid = pn.userid", Join.Type.LEFT)
                .where("pn.id = ?")
                .limit("1")
                .select()
                .build()
                .sql();
        return pubDao.query(PartyNotice.class, sql, id);
    }

    public Integer findPartyIdByUid(String userid) {
        String sql = new SQL.SqlBuilder()
                .table("t_party_dept_user")
                .field("party_id")
                .where("userid = ?")
                .limit("1")
                .select()
                .build()
                .sql();
        return pubDao.query(Integer.class, sql, userid);
    }

    public Integer findPartyIdByGroupId(Integer groupId) {
        String sql = new SQL.SqlBuilder()
                .table("t_party_dept d")
                .field("d.id")
                .join("t_chat_group g", "g.group_id = d.group_id")
                .where("g.id = ?")
                .limit("1")
                .select()
                .build()
                .sql();
        return pubDao.query(Integer.class, sql, groupId);
    }

}
