package wxgh.sys.dao.party.admin;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.party.PartyGroup;

import java.util.List;

/**
 * Created by WIN on 2016/8/24.
 */
@Repository
public class PartyGroupDao extends MyBatisDao<PartyGroup> {

    public List<PartyGroup> getGroups(PartyGroup partyGroup) {
        return selectList("getGroups", partyGroup);
    }

    public void update(PartyGroup group) {
        execute("xlkai_updateGroup", group);
    }
}
