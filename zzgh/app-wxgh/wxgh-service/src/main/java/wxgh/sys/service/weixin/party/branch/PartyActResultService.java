package wxgh.sys.service.weixin.party.branch;


import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.common.FileData;
import wxgh.data.common.FileInfo;
import wxgh.data.party.branch.result.PartyResultInfo;
import wxgh.data.pub.NameValue;
import wxgh.data.union.group.act.result.ResultList;
import wxgh.entity.common.Comment;
import wxgh.entity.common.Zan;
import wxgh.entity.party.party.PartyActResult;
import wxgh.entity.pub.SysFile;
import wxgh.param.common.bbs.CommParam;
import wxgh.param.entertain.act.SignParam;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2018-01-18  15:08
 * --------------------------------------------------------- *
 */
@Service
@Transactional(readOnly = true)
public class PartyActResultService {

    public List<Comment> listComm(CommParam query) {

        SQL.SqlBuilder builder = new SQL.SqlBuilder()
                .table("t_comment cm")
                .field("cm.*")
                .field("u.avatar as headimg, u.name as userName")
                .field("(select id from t_zan where zan_id=cm.com_id and userid='" + query.getUserid() + "' and zan_type="+ Zan.TYPE_PARTY_ACT_RESULT_COMM+") as isZan")
                .join("t_user u", "cm.user_id = u.userid")
                .order("cm.zan_num")
                .order("cm.create_time", Order.Type.DESC);

        if (query.getArticleId() != null)
            builder.where("cm.atl_id = " + query.getArticleId());
        if (query.getIsdel() != null)
            builder.where("cm.isdel = " + query.getIsdel());
        if (query.getStatus() != null)
            builder.where("cm.status = " + query.getStatus());
        if (query.getType() != null)
            builder.where("cm.type = " + query.getType());

        return pubDao.queryPage(builder, query, Comment.class);
    }

    public Integer getNumb(Integer id, String colum) {
        String sql = "select " + colum + " from t_party_act_result where id=?";
        return pubDao.queryInt(sql, id);
    }

    public Integer isZan(Integer id, String userid) {
        String sql = "select id from t_zan where zan_id=? and userid=? and zan_type=?";
        Integer zanId = pubDao.query(Integer.class, sql, id, userid, Zan.TYPE_PARTY_ACT_RESULT);
        return zanId;
    }

    @Transactional
    public void updateSeeNum(Integer id) {
        String sql = "update t_party_act_result set see_numb = see_numb+1 where id=?";
        pubDao.execute(sql, id);
    }

    @Transactional
    public Integer updateZanNum(Integer id, String userid) {
        Integer type = 1;
        Integer zanId = isZan(id, userid);
        String sql;
        if (zanId == null) {
            sql = "update t_party_act_result set zan_numb = zan_numb+1 where id=?";
        } else {
            //删除zan记录
            sql = "delete from t_zan where id=?";
            pubDao.execute(sql, zanId);

            //更新文章赞数量
            sql = "update t_party_act_result set zan_numb = zan_numb-1 where id=?";
            type = 0;
        }
        pubDao.execute(sql, id);
        return type;
    }

    public List<NameValue> listAct(Integer groupId) {
        SQL sql = new SQL.SqlBuilder()
                .table("party_act a")
                .field("a.name, a.id as value")
                .join("chat_group g", "g.group_id = a.groupid")
                .where("g.id = ?")
                .build();
        return pubDao.queryList(NameValue.class, sql.sql(), groupId);
    }

    @Transactional
    public Integer addResult(PartyActResult result) {
        result.setUserid(UserSession.getUserid());
        result.setAddTime(new Date());
        result.setStatus(Status.NORMAL.getStatus());

        if (!TypeUtils.empty(result.getImgs())) {
            List<String> imgs = new ArrayList<>();
            String[] imgIds = result.getImgs().split(",");
            for (String img : imgIds) {
                fileApi.wxDownload(img, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        imgs.add(file.getFileId());
                    }
                });
            }
            result.setImgs(TypeUtils.listToStr(imgs));
        }

        String sql = "insert into t_party_act_result(title, info, imgs, file_ids, act_id, status, add_time, userid, group_id) " +
                "select :title, :info, :imgs, :fileIds, :actId, :status, :addTime, :userid, group_id from t_chat_group where id = :groupId";
        return pubDao.insertAndGetKey(sql, result);
    }

    public PartyResultInfo getResult(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("party_act_result r")
                .field("r.*, a.id as activityId")
                .field("u.name as username, u.avatar")
                .field("(select IFNULL(1, 0) from t_zan where zan_id = r.id and zan_type = ? and userid = ? limit 1) as zanIs")
                .join("user u", "u.userid = r.userid")
                .join("party_act a", "a.id = r.act_id")
                .where("r.id = ?")
                .build();
        PartyResultInfo info = pubDao.query(PartyResultInfo.class, sql.sql(), Zan.TYPE_PARTY_ACT_RESULT, UserSession.getUserid(), id);
        if (info != null && !TypeUtils.empty(info.getImgs())) {
            String seSql = "select file_path as path, thumb_path as thumb from t_sys_file where find_in_set(file_id, ?)";
            List<FileData> files = pubDao.queryList(FileData.class, seSql, info.getImgs());
            info.setImgFiles(files);
            info.setImgs(null);
        }

        //文件列表
        if (!TypeUtils.empty(info.getFileIds())) {
            String fileSql = "select file_path as path, thumb_path as thumb, filename from t_sys_file where find_in_set(file_id, ?)";
            info.setFileList(pubDao.queryList(FileInfo.class, fileSql, info.getFileIds()));
            info.setFileIds(null);
        }

        return info;
    }

    public List<ResultList> listResult(SignParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_act_result r")
                .field("r.id, r.title, r.info, r.userid, r.add_time")
                .field("u.avatar, u.name as username")
                .join("user u", "r.userid = u.userid")
                .order("r.add_time", Order.Type.DESC);
        if (param.getActId() != null) {
            sql.join("party_act a", "a.id = r.act_id")
                    .where("a.id = :actId");
        }
        return pubDao.queryPage(sql, param, ResultList.class);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private FileApi fileApi;

}
