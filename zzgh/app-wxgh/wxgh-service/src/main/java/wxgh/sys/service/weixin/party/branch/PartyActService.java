package wxgh.sys.service.weixin.party.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.party.branch.PartyActInfo;
import wxgh.data.party.branch.PartyActList;
import wxgh.data.party.branch.PartyJoinList;
import wxgh.entity.party.party.PartyAct;
import wxgh.entity.party.party.PartyActJoin;
import wxgh.entity.pub.SysFile;
import wxgh.param.party.branch.ActListParam;
import wxgh.param.party.tribe.JoinParam;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service
@Transactional(readOnly = true)
public class PartyActService {

    @Transactional
    public void addJoin(PartyActJoin join) {
        SQL seSql = new SQL.SqlBuilder()
                .table("party_act_join")
                .field("status, id")
                .where("userid = ? and act_id = ?")
                .build();
        PartyActJoin actJoin = pubDao.query(PartyActJoin.class, seSql.sql(), join.getUserid(), join.getActId());
        if (actJoin == null) {
            join.setAddTime(new Date());
            pubDao.executeBean(JdbcSQL.save(PartyActJoin.class), join);
        } else if (actJoin != null && !join.getStatus().equals(actJoin.getStatus())) {
            String sql = "update t_party_act_join set status = ?, add_time = ? where id = ?";
            pubDao.execute(sql, join.getStatus(), new Date(), actJoin.getId());
        }
        // todo 签到后增加党员积分
        String sql = "INSERT INTO t_party_score(userid, score, info, by_act_id) VALUES(?, 1, '【参加活动】', ?)";
        pubDao.execute(sql, join.getUserid(), join.getActId());
    }

    public List<PartyJoinList> listJoin(JoinParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_act_join j")
                .field("j.add_time, j.status")
                .simple_user("j.userid")
                .where("j.act_id = :actId");
        if (param.getStatus() != null) {
            sql.where("j.status = :status");
        }
        return pubDao.queryPage(sql, param, PartyJoinList.class);
    }

    public List<PartyActList> listAct(ActListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_act a")
                .field("a.id, a.name, a.start_time, a.end_time, a.address, a.userid")
                .field("u.name as username, u.avatar")
                .sys_file("a.img_url")
                .join("user u", "u.userid = a.userid")
                .order("a.start_time", Order.Type.DESC);
        if (param.getGroupId() != null) {
            sql.join("chat_group g", "g.group_id = a.groupid")
                    .where("g.id = :groupId");
        }
        return pubDao.queryPage(sql, param, PartyActList.class);
    }

    @Transactional
    public Integer addAct(PartyAct partyAct) {
        if (partyAct.getImgUrl() != null) {
            fileApi.wxDownload(partyAct.getImgUrl(), new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    partyAct.setImgUrl(file.getFileId());
                }
            });
        }

        partyAct.setAddTime(new Date());
        partyAct.setScope(0);
        partyAct.setUserid(UserSession.getUserid());
        String sql = "insert into t_party_act(name, type_name, mobile, start_time, end_time, address, lat, lng, join_type, info, remark, userid, add_time, img_url, scope, party_act, groupid,type_meet,file_ids) " +
                "select :name, :typeName, :mobile, :startTime, :endTime, :address, :lat, :lng, :joinType, :info, :remark, :userid, :addTime, :imgUrl, :scope, :partyAct, group_id,:typeMeet,:fileIds " +
                "from t_chat_group where id = :groupid";
        return pubDao.insertAndGetKey(sql, partyAct);
    }

    public PartyActInfo actInfo(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("party_act a")
                .field("a.*")
                .sys_file("a.img_url")
                .field("u.name as username")
                .field("(select file_path from t_sys_file where file_id = a.file_ids) as filepath")
                .field("(select filename from t_sys_file where file_id = a.file_ids) as filename")
                .field("(select status from t_party_act_join where act_id = a.id and userid = ?) as joinStatus")
                .field("(select count(*) from t_party_act_join where act_id = a.id) as joinCount")
                .join("user u", "u.userid = a.userid")
                .where("a.id = ?")
                .build();
        PartyActInfo actInfo = pubDao.query(PartyActInfo.class, sql.sql(),
                UserSession.getUserid(), id);
        if (actInfo != null) {
            String msql = "select u.avatar from t_party_act_join j join t_user u on u.userid = j.userid where act_id = ? limit 4";
            List<String> avatars = pubDao.queryList(String.class, msql, actInfo.getId());
            actInfo.setAvatars(avatars);

        }
        return actInfo;
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private FileApi fileApi;
}
