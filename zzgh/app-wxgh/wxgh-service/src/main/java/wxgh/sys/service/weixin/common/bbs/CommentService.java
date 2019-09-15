package wxgh.sys.service.weixin.common.bbs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.entity.common.Comment;
import wxgh.entity.common.Zan;
import wxgh.param.common.bbs.CommParam;
import wxgh.sys.dao.common.bbs.CommentDao;

import java.util.Date;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-26 10:56
 * ----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class CommentService {

    @Autowired
    private PubDao pubDao;

    @Autowired
    private CommentDao commentDao;

    public Integer countComm(CommParam query) {
        SQL.SqlBuilder builder = new SQL.SqlBuilder()
                .table("t_comment cm")
                .count();
        if (query.getArticleId() != null)
            builder.where("cm.atl_id = " + query.getArticleId());
        if (query.getIsdel() != null)
            builder.where("cm.isdel = " + query.getIsdel());
        if (query.getStatus() != null)
            builder.where("cm.status = " + query.getStatus());
        return pubDao.queryInt(builder.build().sql());
    }

    /**
     * 获取评论
     *
     * @param query
     * @return
     */
    public List<Comment> listComm(CommParam query) {

        SQL.SqlBuilder builder = new SQL.SqlBuilder()
                .table("t_comment cm")
                .field("d.name")
                .field("cm.*")
                .field("u.avatar as headimg, u.name as userName")
                .field("(select id from t_zan where zan_id=cm.com_id and userid='" + query.getUserid() + "' and zan_type=3) as isZan")
                .join("t_user u", "cm.user_id = u.userid")
                .join("t_dept d", "u.deptid = d.deptid", Join.Type.LEFT)
                .order("cm.zan_num", Order.Type.DESC)
                .order("create_time", Order.Type.DESC);

        if (query.getArticleId() != null)
            builder.where("cm.atl_id = " + query.getArticleId());
        if (query.getIsdel() != null)
            builder.where("cm.isdel = " + query.getIsdel());
        if (query.getStatus() != null)
            builder.where("cm.status = " + query.getStatus());

        return pubDao.queryPage(builder, query, Comment.class);
    }

    /**
     * 获取评论总数
     *
     * @param id
     * @return
     */
    public Integer getCommNum(Integer id) {
        String sql = "select zan_num from t_comment where com_id=?";
        Integer num = pubDao.query(Integer.class, sql, id);
        return num == null ? 0 : num;
    }

    /**
     * 更新评论总数
     *
     * @param id
     * @param userid
     * @return
     */
    @Transactional
    public Integer updateCommNum(Integer id, String userid) {

        Integer type = 1;
        String sql = "select id from t_zan where zan_id=? and userid=? and zan_type=?";
        Integer zanId = pubDao.query(Integer.class, sql, id, userid, Zan.TYPE_ARTICLE_COMM);
        if (zanId == null) {
            sql = "update t_comment set zan_num = zan_num+1 where com_id=?";
        } else {
            //删除zan记录
            sql = "delete from t_zan where id=?";
            pubDao.execute(sql, zanId);

            //更新文章赞数量
            sql = "update t_comment set zan_num = zan_num-1 where com_id=?";
            type = 0;
        }
        pubDao.execute(sql, id);

        return type;

    }

    @Transactional
    public void addComm(Comment comment) {
        comment.setCreateTime(new Date());
        comment.setIsdel(1);
        if (comment.getType() == null) {
            comment.setType(Comment.TYPE_BBS);
        }
        comment.setStatus(1);
        comment.setZanNum(0);
        commentDao.addComm(comment);

        if (comment.getType() == Comment.TYPE_BBS) {
            //更新评论数量
            String sql = "update t_article set comm_num=comm_num+1 where atl_id=?";
            pubDao.execute(sql, comment.getAtlId());
        } else if (comment.getType().equals(Comment.TYPE_ACT_RESULT)) {
            String sql = "update t_act_result set comm_numb = comm_numb+1 where id=?";
            pubDao.execute(sql, comment.getAtlId());
        }
    }

}

