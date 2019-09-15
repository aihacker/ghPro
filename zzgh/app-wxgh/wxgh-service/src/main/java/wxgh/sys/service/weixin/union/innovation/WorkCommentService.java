package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.entity.party.beauty.WorkComment;
import wxgh.sys.dao.union.innovation.WorkCommentDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class WorkCommentService {

    @Autowired
    private WorkCommentDao workCommentDao;

    @Autowired
    private PubDao pubDao;

    @Transactional
    public Integer saveWorkComment(WorkComment workComment) {
        workCommentDao.save(workComment);
        return workComment.getId();
    }

    
    public Integer getCount(Integer id) {
        return workCommentDao.getCount(id);
    }

    
    public List<WorkComment> getList(Integer id) {
        return workCommentDao.getList(id);
    }

    /**
     * 获取最新三条评论记录
     * @param id
     * @return
     */
    public List<WorkComment> getListLimit3(Integer id){

        SQL sql = new SQL.SqlBuilder()
                .table("work_comment wc")
                .field("wc.*,u.name AS username,u.avatar AS avatar")
                .join("work w", "w.id = wc.work_id")
                .join("user u", "u.userid = wc.user_id")
                .where("wc.work_id = ?")
                .order("wc.id", Order.Type.DESC)
                .limit("3")
                .select()
                .build();

        return pubDao.queryList(WorkComment.class, sql.sql(), id );
    }

}
