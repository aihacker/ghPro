package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.union.SendInfo;
import wxgh.data.union.innovation.AdviceList;
import wxgh.data.union.innovation.work.AdviceInfo;
import wxgh.entity.union.innovation.AdviceZan;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.param.union.innovation.AdviceParam;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.param.union.innovation.QueryInnovateAdvice;
import wxgh.sys.dao.union.innovation.AdviceZanDao;
import wxgh.sys.dao.union.innovation.InnovateAdviceDao;

import java.util.Date;
import java.util.List;

/**
 * Created by ✔ on 2016/11/10.
 */
@Service
@Transactional(readOnly = true)
public class InnovateAdviceService {

    
    @Transactional
    public Integer addOne(InnovateAdvice innovateAdvice) {
        innovateAdviceDao.save(innovateAdvice);
        return innovateAdvice.getId();
    }

    
    public List<InnovateAdvice> getList(QueryInnovateAdvice queryInnovateAdvice) {
        return innovateAdviceDao.getList(queryInnovateAdvice);
    }

    public List<InnovateAdvice> getList2(QueryInnovateAdvice queryInnovateAdvice) {
        return innovateAdviceDao.getList2(queryInnovateAdvice);
    }
    
    public InnovateAdvice getOne(QueryInnovateAdvice queryInnovateAdvice) {
        return innovateAdviceDao.getOne(queryInnovateAdvice);
    }

    
    public SendInfo getSendInfoById(Integer applyId) {
        String sql = "select '岗位创新申请' as sendType,\n" +
                "                s.id as itemId,\n" +
                "                u.name as username,u.userid as userId,\n" +
                "                d.name as deptName\n" +
                "                from t_innovate_apply s join t_user u on u.userid=s.userid\n" +
                "                join t_dept d on u.deptid=d.deptid \n" +
                "                where s.id=?;";
        return pubDao.query(SendInfo.class, sql, applyId);
    }

    
    public InnovateAdvice getAdvice(Integer pid) {
        String sql = "select * from t_innovate_advice where pid=? limit 1";
        return pubDao.query(InnovateAdvice.class, sql, pid);
    }

    
    public InnovateAdvice get(Integer id) {
        InnovateAdvice advice = innovateAdviceDao.get(id);
        if (advice != null) {
            String sql = "select u.avatar,u.name as username,a.status from t_innovate_apply a join t_user u on a.userid=u.userid where a.id=? limit 1";

            InnovateAdvice tmp = pubDao.query(InnovateAdvice.class, sql, advice.getPid());

            advice.setAvatar(tmp.getAvatar());
            advice.setUsername(tmp.getUsername());
            advice.setStatus(tmp.getStatus());
        }
        return advice;
    }

    
    @Transactional
    public Integer updateSeeNumb(Integer id) {
        String sql = "update t_innovate_advice set see_numb=see_numb+1 where id=?";
        return pubDao.execute(sql, id);
    }

    
    @Transactional
    public Integer updateZanNumb(Integer id, String userid) {
        AdviceZan actResultZan = new AdviceZan();
        actResultZan.setAdviceId(id);
        actResultZan.setType(1);
        actResultZan.setUserid(userid);
        actResultZan.setAddTime(new Date());

        adviceZanDao.save(actResultZan);

        String sql = "update t_innovate_advice set zan_numb=zan_numb+1 where id=?";
        return pubDao.execute(sql, id);
    }

    
    public List<AdviceInfo> getInfos(InnovateApplyQuery query) {
        return innovateAdviceDao.getInfos(query);
    }

    
    public Integer getApplyId(Integer id) {
        String sql = "select pid from t_innovate_advice where id=?";
        return pubDao.query(Integer.class, sql, id);
    }

    
    public List<AdviceList> getAdminAdvice(AdviceParam param) {
        String sql = "select a.id as applyId, a.add_time, a.`status`, a.userid, u.name as username, d.name,\n" +
                "ad.title, ad.type as adviceType, ad.id as adviceId\n" +
                "from t_innovate_apply a\n" +
                "join t_user u on u.userid = a.userid\n" +
                "left join t_dept d on u.deptid = d.deptid\n" +
                "join t_innovate_advice ad on a.id = ad.pid\n" +
                "where a.apply_type = ? and a.status=?";
        if (param.getNodeptid() != null) {
            sql += " and a.deptid != " + param.getNodeptid();
        }
        sql += " ORDER BY a.add_time DESC LIMIT ?,?";
        return pubDao.queryList(AdviceList.class, sql, InnovateApply.TYPE_ADVICE,
                param.getStatus(), param.getPagestart(), param.getRowsPerPage());
    }

    
    public Integer countAdminAdvice(AdviceParam param) {
        String sql = "select count(*)\n" +
                "from t_innovate_apply a\n" +
                "join t_user u on u.userid = a.userid\n" +
                "left join t_dept d on u.deptid = d.deptid\n" +
                "join t_innovate_advice ad on a.id = ad.pid\n" +
                "where a.apply_type = ? and a.status=?";
        if (param.getNodeptid() != null) {
            sql += " and a.deptid != " + param.getNodeptid();
        }
        Integer count = pubDao.query(Integer.class, sql, InnovateApply.TYPE_ADVICE, param.getStatus());
        return count == null ? 0 : count;
    }

    @Autowired
    private InnovateAdviceDao innovateAdviceDao;

    @Autowired
    private PubDao pubDao;

    @Autowired
    private AdviceZanDao adviceZanDao;

    @Autowired
    private InnovateApplyService innovateApplyService;
}
