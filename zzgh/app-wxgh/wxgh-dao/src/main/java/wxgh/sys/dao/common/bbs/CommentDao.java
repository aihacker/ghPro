package wxgh.sys.dao.common.bbs;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.Comment;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-26 14:21
 *----------------------------------------------------------
 */
@Repository
public class CommentDao extends MyBatisDao<Comment> {

    public void addComm(Comment comment) {
        this.save(comment);
    }
    
}

