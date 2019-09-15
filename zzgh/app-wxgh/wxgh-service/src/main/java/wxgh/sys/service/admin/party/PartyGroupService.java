package wxgh.sys.service.admin.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.wxadmin.party.UserGroup;
import wxgh.entity.party.party.PartyGroup;
import wxgh.sys.dao.party.admin.PartyGroupDao;

import java.util.List;

/**
 * Created by cby on 2017/8/15.
 */
@Service
@Transactional(readOnly = true)
public class PartyGroupService {

    @Autowired
    private PartyGroupDao partyGroupDao;

    @Autowired
    private PubDao generalDao;

    public List<PartyGroup> getGroups(PartyGroup partyGroup) {
        return partyGroupDao.getGroups(partyGroup);
    }

    public PartyGroup isExistGroup(String groupName) {
        String sql = "select * from t_party_group where name=? limit 1";
        return generalDao.query(PartyGroup.class, sql, groupName);
    }

    public List<String> getPartys() {
        String sql = "select name from t_party_group";
        return generalDao.queryList(String.class, sql);
    }

    public List<UserGroup> listGroups() {
        String sql = "select id, name, 1 as type from t_party_group";
        return generalDao.queryList(UserGroup.class, sql);
    }

    public List<PartyGroup> listMyGroups(String userid) {
        String sql = "select g.* from t_party_group g\n" +
                "join t_party_user u on g.id = u.groupid\n" +
                "where u.userid = ?";
        return generalDao.queryList(PartyGroup.class, sql, userid);
    }

    public PartyGroup get(Integer id) {
        return partyGroupDao.get(id);
    }

    @Transactional
    public void update(PartyGroup group) {
        partyGroupDao.update(group);
    }
}
