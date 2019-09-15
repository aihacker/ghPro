package wxgh.sys.service.weixin.common.disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.union.SendInfo;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.param.common.disease.ApplyQuery;
import wxgh.sys.dao.common.disease.DiseaseDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class DiseaseService {

    @Autowired
    private DiseaseDao diseaseDao;
    @Autowired
    private PubDao pubDao;

    public List<DiseaseApply> getApplys(ApplyQuery query) {
        return diseaseDao.getApplys(query);
    }

    public DiseaseApply getApply(ApplyQuery query) {
        return diseaseDao.getApply(query);
    }

    @Transactional
    public Integer updateApply(DiseaseApply apply) {
        return diseaseDao.updateApply(apply);
    }

    public String getUserid(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("t_disease_apply")
                .field("userid")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }

    @Transactional
    public Integer addApply(DiseaseApply apply) {
        diseaseDao.save(apply);
        return apply.getId();
    }

    @Transactional
    public Integer del(Integer id) {
        return diseaseDao.del(id);
    }

    public List<DiseaseApply> applyListRefresh(ApplyQuery query) {
        return diseaseDao.applyListRefresh(query);
    }

    public List<DiseaseApply> applyListMore(ApplyQuery query) {
        return diseaseDao.applyListMore(query);
    }

    public List<DiseaseApply> getApplysNotInTargetDeptId(ApplyQuery query, List<Integer> deptIds) {
        String in = "";
        if (deptIds != null) {
            for (Integer id : deptIds) {
                in += id + ",";
            }
            in = in.substring(0, in.length() - 1);
        }

        String and = "";
        if (query.getDiseaseOldestId() != null) {
            and = " and d.id<" + query.getDiseaseOldestId() + " ";
        }
        String sql = "select d.*,u.deptid \n" +
                "from t_disease_apply d join t_user u on u.userid=d.userid \n" +
                "where u.deptid not in (" + in + ") \n" + and +
                "and d.status=? and d.step=? \n" +
                "order by d.id desc limit 0,15;";
        return pubDao.queryList(DiseaseApply.class, sql, query.getStatus(), query.getStep());
    }

    public SendInfo getSendInfoById(Integer id) {
        String sql = "select '互助资金申请' as sendType,\n" +
                "                s.id as itemId,s.cate_id as type,\n" +
                "                u.name as username,u.userid as userId,\n" +
                "                d.name as deptName\n" +
                "                from t_disease_apply s join t_user u on u.userid=s.userid\n" +
                "                join t_dept d on u.deptid=d.deptid \n" +
                "                where s.id=?;";
        SendInfo sendInfo = pubDao.query(SendInfo.class, sql, id);
        if ("jb".equals(sendInfo.getType())) {
            sendInfo.setType("getJB");
        } else if ("jy".equals(sendInfo.getType())) {
            sendInfo.setType("getJY");
        } else if ("qs".equals(sendInfo.getType())) {
            sendInfo.setType("getQS");
        } else if ("zc".equals(sendInfo.getType())) {
            sendInfo.setType("getZC");
        } else if ("zh".equals(sendInfo.getType())) {
            sendInfo.setType("getZH");
        } else if ("zx".equals(sendInfo.getType())) {
            sendInfo.setType("getZX");
        } else if ("pk".equals(sendInfo.getType())) {
            sendInfo.setType("getPK");
        }
        return sendInfo;
    }
}
