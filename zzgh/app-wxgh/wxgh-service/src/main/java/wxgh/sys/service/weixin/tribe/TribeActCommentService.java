package wxgh.sys.service.weixin.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.tribe.TribeActComment;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-07
 **/
@Service
public class TribeActCommentService {
    public List<TribeActComment> getData(TribeActComment tribeActComment){
        String sql = "select * from t_tribe_act_comment where act_id = ?";
        return pubDao.queryList(TribeActComment.class,sql,tribeActComment.getActId());
    }

    public void add(TribeActComment tribeActComment){
        SQL sql = new SQL.SqlBuilder().field("act_id,userid,add_time,content,img")
                .value(":actId,:userid,:addTime,:content,:img").insert("tribe_act_comment").build();
        pubDao.executeBean(sql.sql(),tribeActComment);
    }

    public void addManage(TribeActComment tribeActComment){
        SQL sql = new SQL.SqlBuilder().field("act_id,userid,add_time,content,img")
                .value(":actId,:userid,:addTime,:content,:img").insert("manage_act_comment").build();
        pubDao.executeBean(sql.sql(),tribeActComment);
    }

    @Autowired
    private PubDao pubDao;
}
