package wxgh.sys.service.admin.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.entertain.place.User;
import wxgh.entity.party.party.PartyUser;
import wxgh.param.party.PartyParam;
import wxgh.param.user.UserQuery;
import wxgh.sys.dao.party.admin.PartyUserDao;

import java.util.List;

/**
 * Created by cby on 2017/8/15.
 */
@Service
@Transactional(readOnly = true)
public class PartyUserService {

    @Autowired
    private PartyUserDao partyUserDao;

    @Autowired
    private PubDao generalDao;

    public List<PartyUser> getGroups(PartyUser partyUser) {
        return partyUserDao.getGroups(partyUser);
    }

    public List<PartyUser> getNations(PartyUser partyUser) {
        return partyUserDao.getNations(partyUser);
    }

    public List<PartyUser> getList(PartyParam query) {
        return partyUserDao.getList(query);
    }

    public Integer getCount(PartyParam query) {
        return partyUserDao.getCount(query);
    }

    public PartyUser getOne(PartyParam query) {
        return partyUserDao.getOne(query);
    }

    @Transactional
    public Integer deletePartyUser(Integer id) {
        partyUserDao.delPartyUser(id);
        return -1;
    }

    @Transactional
    public Integer updatePartyUser(PartyUser partyUser) {
        return partyUserDao.updatePartyUser(partyUser);
    }

    @Transactional
    public Integer addUsers(List<PartyUser> users) {
        String sql = "select id from t_party_user where userid=?";
        int count = 0;
        for (int i = 0; i < users.size(); i++) {
            PartyUser user = users.get(i);
            if (user.getResults() == null || user.getResults().size() <= 0) {
                Integer partyUserId = generalDao.query(Integer.class, sql, user.getUserId());

                if (partyUserId == null) {
                    partyUserDao.save(user);
                    count++;
                } else {
                    users.get(i).getResults().add("用户已存在");
                }
            }
        }
        return count;
    }

    @Transactional
    public void addUser(PartyUser partyUser) {
        partyUserDao.addUser(partyUser);
    }

    public PartyUser login(String mobile) {
        String sql = "SELECT pu.*, pg.name AS groupName, u.mobile AS mobile , u.name AS username\n" +
                "FROM t_party_user pu\n" +
                "JOIN t_party_group pg ON pu.`groupid` = pg.`id`\n" +
                "JOIN t_user u ON pu.`userid` = u.`userid` where u.mobile=? limit 1";
        return generalDao.query(PartyUser.class, sql, mobile);
    }

    public boolean hasAddUser(String userid, Integer groupId) {
        String sql = "select id from t_party_user where userid=? and groupid=? limit 1";
        Integer id = generalDao.query(Integer.class, sql, userid, groupId);
        return id != null;
    }

    public List<PartyUser> queryUserList(UserQuery userQuery) {
        return partyUserDao.queryUserList(userQuery);
    }

    public List<User> searchUser(String key) {
        String sql = "select u.name,u.mobile,u.deptid,u.userid,u.avatar, wd.name as deptname from t_user u join t_dept wd on wd.deptid = u.deptid where u.name like ? or u.mobile like ? or u.userid like ? group by u.userid limit 8";
        return generalDao.queryList(User.class, sql, "%" + key + "%", key + "%", "%" + key + "%");
    }

}
