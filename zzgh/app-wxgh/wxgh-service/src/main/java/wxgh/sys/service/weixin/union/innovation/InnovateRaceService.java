package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.union.SendInfo;
import wxgh.data.union.innovation.IdName;
import wxgh.data.union.innovation.work.RaceInfo;
import wxgh.data.union.innovation.work.UserInfo;
import wxgh.entity.union.innovation.InnovateRace;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.entity.union.innovation.WorkUser;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.param.union.innovation.QueryInnovateRace;
import wxgh.sys.dao.union.innovation.InnovateRaceDao;
import wxgh.sys.dao.union.innovation.WorkResultDao;
import wxgh.sys.dao.union.innovation.WorkUserDao;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class InnovateRaceService {

    
    public List<IdName> listType() {
        List<IdName> idNames = new ArrayList<>();
        IdName idName = new IdName();
        idName.setId(InnovateRace.TYPE_LABOUR);
        idName.setName("劳动竞赛");
        idNames.add(idName);
        idName = new IdName();
        idName.setId(InnovateRace.TYPE_SKILL);
        idName.setName("技能竞赛");
        idNames.add(idName);
        return idNames;
    }


    
    public String getUserDept(Integer deptId) {
        String sql = "select deptname from t_dept where deptid=?";
        return pubDao.query(String.class, sql, deptId);
    }

    
    @Transactional
    public Integer save(InnovateRace innovateRace) {
        innovateRaceDao.save(innovateRace);
        return innovateRace.getId();
    }

    
    @Transactional
    public Integer saveRaceMember(WorkUser workUser) {
        workUserDao.save(workUser);
        return workUser.getId();
    }

    
    @Transactional
    public Integer saveWorkResult(WorkResult workResult) {
        workResultDao.save(workResult);
        return workResult.getId();
    }

    
    @Transactional
    public void setInnovateApplyStep(Integer applyId, Integer step) {
        String sql = "update t_innovate_apply set apply_step=? " +
                "where id=?";
        pubDao.execute(sql, step, applyId);
    }

    
    public Integer checkSetStepSuccess(Integer applyId) {
        Integer success = 0;
        String sql = "select apply_step from t_innovate_apply where id=?";
        Integer step = pubDao.query(Integer.class, sql, applyId);
        if (step == 1) {
            success = 1;
        }
        return success;
    }

    
    public InnovateRace getOne(QueryInnovateRace queryInnovateRace) {
        return innovateRaceDao.getOne(queryInnovateRace);
    }

    
    public SendInfo getSendInfoById(Integer applyId) {
        String sql = "select '岗位创新申请' as sendType,\n" +
                "                s.id as itemId,\n" +
                "                u.name as username,u.userid as userId,\n" +
                //"                d.name as deptName\n" +
                "                from t_innovate_apply s join t_user u on u.userid=s.userid\n" +
               // "                join t_dept d on u.deptid=d.deptid \n" +
                "                where s.id=?;";
        return pubDao.query(SendInfo.class, sql, applyId);
    }

    
    public List<RaceInfo> getInfos(InnovateApplyQuery query) {
        List<RaceInfo> infos = innovateRaceDao.getInfos(query);
        if (infos != null && infos.size() > 0) {
            String sql = "select u.name,u.mobile,u.avatar,wu.type_name as userType\n" +
                    "from t_work_user wu\n" +
                    "join t_user u on wu.userid = u.userid\n" +
                    //"join t_dept d on u.deptid = d.deptid\n" +  ,d.name,d.deptid
                    "where wu.work_type=? and wu.work_id=?";
            for (int i = 0; i < infos.size(); i++) {
                RaceInfo tmp = infos.get(i);
                List<UserInfo> userInfos = pubDao.queryList(UserInfo.class, sql, tmp.getWorkType(), tmp.getId());
                infos.get(i).setUserInfos(userInfos);
            }
        }
        return infos;
    }

    @Autowired
    private PubDao pubDao;
    @Autowired
    private InnovateRaceDao innovateRaceDao;
    @Autowired
    private WorkUserDao workUserDao;
    @Autowired
    private WorkResultDao workResultDao;
}
