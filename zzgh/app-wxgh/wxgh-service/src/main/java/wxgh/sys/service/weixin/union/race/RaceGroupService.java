package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.utils.TypeUtils;
import wxgh.entity.union.race.RaceGroup;
import wxgh.entity.union.race.RaceGroupDetail;
import wxgh.param.union.race.arrange.group.GroupInfo;
import wxgh.sys.dao.union.race.RaceGroupDao;
import wxgh.sys.dao.union.race.RaceGroupDetailDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/20.
 */
@Service
@Transactional(readOnly = true)
public class RaceGroupService {

    @Transactional
    public void save(RaceGroup group) {
        raceGroupDao.save(group);
    }

    @Transactional
    public void saveMap(List<GroupInfo> infos, Integer raceId) {

        //删除原来记录
        String sql = "delete g.*,d.* from t_race_group g, t_race_group_detail d where g.id = d.group_id AND g.race_id = ?";
        generalDao.execute(sql, raceId);

        for (GroupInfo info : infos) {
            RaceGroup group = info.getGroup();
            raceGroupDao.save(group);

            List<RaceGroupDetail> details = info.getDetails();
            for (RaceGroupDetail detail : details) {
                detail.setGroupId(group.getId());
                raceGroupDetailDao.save(detail);
            }
        }
    }

    public List<RaceGroup> getGroups(Integer raceId) {
        String sql = "select * from t_race_group where race_id = ?";
        return generalDao.queryList(RaceGroup.class, sql, raceId);
    }

    public List<GroupInfo> getGroupInfo(Integer raceId) {
        List<GroupInfo> groupInfos = new ArrayList<>();

        List<RaceGroup> groups = getGroups(raceId);
        if (!TypeUtils.empty(groups)) {
            String ids = "";
            for (RaceGroup g : groups) {
                ids += (g.getId() + ",");
            }
            if (!"".equals(ids)) {
                ids = ids.substring(0, ids.length() - 1);
            }

            Map<Integer, List<RaceGroupDetail>> map = getGroupDetails(ids);
            if (map != null) {
                for (RaceGroup g : groups) {
                    GroupInfo info = new GroupInfo();
                    info.setGroup(g);

                    info.setDetails(map.get(g.getId()));
                    groupInfos.add(info);
                }
            }
        }
        return groupInfos;
    }

    public Map<Integer, List<RaceGroupDetail>> getGroupDetails(String groupIds) {
        String sql = "select * from t_race_group_detail where group_id in (" + groupIds + ")";
        List<RaceGroupDetail> details = generalDao.queryList(RaceGroupDetail.class, sql);
        if (!TypeUtils.empty(details)) {
            Map<Integer, List<RaceGroupDetail>> map = new HashMap<>();
            for (RaceGroupDetail d : details) {
                List<RaceGroupDetail> tmp = map.get(d.getGroupId());
                if (tmp == null) {
                    tmp = new ArrayList<>();
                    tmp.add(d);
                    map.put(d.getGroupId(), tmp);
                } else {
                    map.get(d.getGroupId()).add(d);
                }
            }
            return map;
        }
        return null;
    }

    @Autowired
    private RaceGroupDao raceGroupDao;

    @Autowired
    private RaceGroupDetailDao raceGroupDetailDao;

    @Autowired
    private PubDao generalDao;

}
