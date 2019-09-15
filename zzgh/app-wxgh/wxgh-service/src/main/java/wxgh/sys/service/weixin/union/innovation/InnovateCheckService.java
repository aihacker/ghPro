package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.innovation.DetailInfo;
import wxgh.data.union.innovation.ListInfo;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.entity.union.innovation.InnovateMicro;
import wxgh.entity.union.innovation.InnovateRace;
import wxgh.entity.union.innovation.InnovateShop;
import wxgh.entity.union.innovation.WorkResult;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class InnovateCheckService  {

    @Autowired
    private PubDao pubDao;


    
    public List<ListInfo> listItemByApplyType(Integer applyType, List<Integer> deptIds) {
        List<ListInfo> list;

        String and_replace_string = "";
        String in = "";
        if (deptIds != null && deptIds.size() > 0) {
            for (Integer id : deptIds) {
                in += id + ",";
            }
            in = in.substring(0, in.length() - 1);
        }

        String sql1 = "select a.id,a.apply_type as applyType,a.add_time as time,\n" +
                "(select u.name from t_user u where u.userid=a.userid) as applicant,\n" +
                "r.race_name as itemName,r.race_type as itemType\n" +
                "from t_innovate_apply a join t_innovate_race r on a.id=r.apply_id\n" +
                "where a.apply_step=1 and a.apply_type=? and a.status=0 AND_REPLACE_STRING order by a.add_time desc;";

        String sql2 = "select a.id,a.apply_type as applyType,a.add_time as time,\n" +
                "(select u.name from t_user u where u.userid=a.userid) as applicant,\n" +
                "s.item_name as itemName,s.shop_type as itemType\n" +
                "from t_innovate_apply a join t_innovate_shop s on a.id=s.apply_id\n" +
                "where a.apply_step=1 and a.apply_type=? and a.status=0 AND_REPLACE_STRING order by a.add_time desc;";

        String sql3 = "select a.id,a.apply_type as applyType,a.add_time as time,\n" +
                "(select u.name from t_user u where u.userid=a.userid) as applicant,\n" +
                "m.name as itemName,m.type as itemType\n" +
                "from t_innovate_apply a join t_innovate_micro m on a.id=m.pid\n" +
                "where a.apply_step=1 and a.apply_type=? and a.status=0 AND_REPLACE_STRING order by a.add_time desc;";

        String sql4 = "select a.id,a.apply_type as applyType,a.add_time as time,\n" +
                "(select u.name from t_user u where u.userid=a.userid) as applicant,\n" +
                "ad.title as itemName,ad.type as itemType\n" +
                "from t_innovate_apply a join t_innovate_advice ad on a.id=ad.pid\n" +
                "where a.apply_step=1 and a.apply_type=? and a.status=0 AND_REPLACE_STRING order by a.add_time desc;";

        if (applyType == 1) {
            if (deptIds != null && deptIds.size() > 0) {
                and_replace_string = " and r.dept_id not in (" + in + ") ";
            }
            sql1 = sql1.replace("AND_REPLACE_STRING", and_replace_string);
            list = pubDao.queryList(ListInfo.class, sql1, applyType);
        } else if (applyType == 2) {
            if (deptIds != null && deptIds.size() > 0) {
                and_replace_string = " and (select d.deptid from t_user d where d.userid=a.userid) not in (" + in + ")";
            }
            sql2 = sql2.replace("AND_REPLACE_STRING", and_replace_string);
            list = pubDao.queryList(ListInfo.class, sql2, applyType);
        } else if (applyType == 3) {
            if (deptIds != null && deptIds.size() > 0) {
                and_replace_string = " and m.deptid not in (" + in + ") ";
            }
            sql3 = sql3.replace("AND_REPLACE_STRING", and_replace_string);
            list = pubDao.queryList(ListInfo.class, sql3, applyType);
        } else {
            if (deptIds != null && deptIds.size() > 0) {
                and_replace_string = " and ad.deptid not in (" + in + ") ";
            }
            sql4 = sql4.replace("AND_REPLACE_STRING", and_replace_string);
            list = pubDao.queryList(ListInfo.class, sql4, applyType);
        }
        return list;
    }

    
    public DetailInfo getDetailInfo(Integer type, Integer id) {
        String sql = "select id,apply_type as applyType,status,\n" +
                "add_time as time,userid as applicant \n" +
                "from t_innovate_apply where id=?;";
        DetailInfo detailInfo = pubDao.query(DetailInfo.class, sql, id);

        String subSql = "";
        String workSql = "select * from t_work_result where work_id =? and work_type=?";
        if (type == 1) {
            subSql = "select * from t_innovate_race where apply_id=? order by create_time desc limit 1;";
            InnovateRace innovateRace = pubDao.query(InnovateRace.class, subSql, id);
            WorkResult workResult = pubDao.query(WorkResult.class, workSql, innovateRace.getId(), type);
            if (workResult == null) {
                workResult = new WorkResult();
                workResult.setContent("error");
            }
            detailInfo.setWorkResult(workResult);
            detailInfo.setInnovateRace(innovateRace);
        }
        if (type == 2) {
            subSql = "select * from t_innovate_shop where apply_id=? order by create_time desc limit 1;";
            InnovateShop innovateShop = pubDao.query(InnovateShop.class, subSql, id);
            WorkResult workResult = pubDao.query(WorkResult.class, workSql, innovateShop.getId(), type);
            if (workResult == null) {
                workResult = new WorkResult();
                workResult.setContent("error");
            }
            detailInfo.setWorkResult(workResult);
            detailInfo.setInnovateShop(innovateShop);
        }
        if (type == 3) {
            subSql = "select *,cate_1 as cate1,cate_2 as cate2 from t_innovate_micro where pid=? order by add_time desc limit 1;";
            InnovateMicro innovateMicro = pubDao.query(InnovateMicro.class, subSql, id);
            WorkResult workResult = pubDao.query(WorkResult.class, workSql, id, type);
            if (workResult == null) {
                workResult = new WorkResult();
                workResult.setContent("error");
            }
            detailInfo.setWorkResult(workResult);
            detailInfo.setInnovateMicro(innovateMicro);
        }
        if (type == 4) {
            subSql = "select * from t_innovate_advice where pid=? order by add_time desc limit 1;";
            InnovateAdvice innovateAdvice = pubDao.query(InnovateAdvice.class, subSql, id);
            detailInfo.setInnovateAdvice(innovateAdvice);
        }
        return detailInfo;
    }

    
    public String getUserDeptById(Integer deptId) {
        String sql = "select deptname from t_dept where deptid=?";
        return pubDao.query(String.class, sql, deptId);
    }

    
    @Transactional
    public Integer setApplyStatus(InnovateApply info) {
        SeUser user = UserSession.getUser();
        Integer status = info.getStatus();
        String auditIdea = info.getAuditIdea();
        String adminUserId = user.getUserid();
        Date auditTime = new Date();
        String updateSql = "update t_innovate_apply set status=?,audit_idea=?,admin_userid=?,audit_time=?" +
                " where id=?;";
        pubDao.execute(updateSql, status, auditIdea, adminUserId, auditTime, info.getId());
        String sql = "select * from t_innovate_apply where id=?;";
        InnovateApply apply = pubDao.query(InnovateApply.class, sql, info.getId());
        if (apply.getApplyType() == 4) {
            String sql1 = "select * from t_innovate_advice where pid=" + info.getId();
            InnovateAdvice advice = pubDao.query(InnovateAdvice.class, sql1);
            if (advice != null) {

            }
        }
        return apply.getStatus();
    }

    
    public String getLeaderByUserId(String userId) {
        String sql = "select name from t_user where userid=?";
        return pubDao.query(String.class, sql, userId);
    }
}
