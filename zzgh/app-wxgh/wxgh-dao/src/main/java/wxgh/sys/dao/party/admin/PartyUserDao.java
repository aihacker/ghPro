package wxgh.sys.dao.party.admin;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.party.PartyUser;
import wxgh.param.party.PartyParam;
import wxgh.param.user.UserQuery;

import java.util.List;

/**
 * Created by WIN on 2016/8/24.
 */
@Repository
public class PartyUserDao extends MyBatisDao<PartyUser> {
    public List<PartyUser> getGroups(PartyUser partyUser) {
        return selectList("getGroups",partyUser);
    }

    public List<PartyUser> getNations(PartyUser partyUser) {
        return selectList("getNations",partyUser);
    }

    public List<PartyUser> getList(PartyParam query) {
        return selectList("getUserList", query);
    }

    public PartyUser getOne(PartyParam query) {
        return selectOne("getOne",query);
    }

    public Integer getCount(PartyParam query) {
        return selectOne("getCount", query);
    }

    public Integer updatePartyUser(PartyUser partyUser) {
        return execute("update_party_user",partyUser);
    }

    public Integer delPartyUser(Integer id) {
        return getSqlSession().delete("delete_party_user",id);
    }

    public void addUser(PartyUser partyUser) {
        int addUser = getSqlSession().insert("addUser", partyUser);
        System.out.println(addUser);
    }

    public List<PartyUser> queryUserList(UserQuery userQuery) {
        return selectList("queryUserList", userQuery);
    }
}
