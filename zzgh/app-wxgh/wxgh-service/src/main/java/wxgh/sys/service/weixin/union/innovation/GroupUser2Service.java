package wxgh.sys.service.weixin.union.innovation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.union.innovation.GroupUserData;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-03 17:03
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class GroupUser2Service {

    @Autowired
    private PubDao pubDao;

    public Integer getCount(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("group_user gu")
                .join("user u", "u.userid = gu.userid")
                .where("gu.id = ?")
                .where("gu.status = 1")
                .count();
        return pubDao.queryInt(sql.build().sql(), id);
    }

    public GroupUserData getUser(String userid, String groupId) {
        SQL sql = new SQL.SqlBuilder()
                .field("gu.status, gu.type, gu.join_time as joinTime, u.name as username, gu.score, gu.money")
                .join("user u", "u.userid = gu.userid")
                .where("gu.userid = ? and gu.group_id = ?")
                .table("group_user gu")
                .limit("1")
                .build();
        return pubDao.query(GroupUserData.class, sql.sql(), userid, groupId);
    }

}

