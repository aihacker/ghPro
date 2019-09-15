package wxgh.sys.service.admin.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.group.GroupUser;

/**
 * @author hhl
 * @create 2017-09-18
 **/
@Service
public class GroupService {

    public void setGroupUser(Integer id){
        String sql = new SQL.SqlBuilder()
                .table("group g")
                .field("g.userid,g.add_time,g.group_id")
                .where("g.id = ?")
                .select().build().sql();
        Group group = pubDao.query(Group.class,sql,id);
        if(group.getUserid() != null){
            GroupUser groupUser = new GroupUser();
            groupUser.setUserid(group.getUserid());
            groupUser.setJoinTime(group.getAddTime());
            groupUser.setType(1);
            groupUser.setStatus(1);
            groupUser.setGroupId(group.getGroupId());
            groupUser.setMoney((float)0);
            groupUser.setScore(0);
            String sql2 = new SQL.SqlBuilder()
                    .table("group_user")
                    .field("userid,join_time,type,status,group_id,score,money")
                    .value(":userid,:joinTime,:type,:status,:groupId,:score,:money")
                    .insert().build().sql();
            pubDao.executeBean(sql2,groupUser);
        }
    }

    @Autowired
    private PubDao pubDao;
}
