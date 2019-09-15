package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.utils.StrUtils;
import wxgh.data.union.SendInfo;
import wxgh.data.union.innovation.work.MicroInfo;
import wxgh.data.union.innovation.work.UserInfo;
import wxgh.entity.union.innovation.InnovateMicro;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.param.union.innovation.QueryInnovateMicro;
import wxgh.sys.dao.union.innovation.InnovateMicroDao;

import java.util.List;

/**
 * Created by ✔ on 2016/11/10.
 */
@Service
@Transactional(readOnly = true)
public class InnovateMicroService {

    
    @Transactional
    public Integer addOne(InnovateMicro innovateMicro) {
        if (StrUtils.empty(innovateMicro.getCate2())) {
            String sql = "select type from t_innovate_advice where id=?";
            Integer type = pubDao.query(Integer.class, sql, innovateMicro.getAdviceId());
            innovateMicro.setCate2(InnovateMicro.getCate(type));
        }

        innovateMicroDao.save(innovateMicro);

        //更新合理化建议申请状态
        String sql = "update t_innovate_apply a \n" +
                "join t_innovate_advice ad on a.id = ad.pid\n" +
                "set a.status = ? where ad.id = ?";
        pubDao.execute(sql, 3, innovateMicro.getAdviceId());

        return innovateMicro.getId();
    }

    
    public InnovateMicro getOne(QueryInnovateMicro queryInnovateMicro) {
        return innovateMicroDao.getOne(queryInnovateMicro);
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

    
    public List<MicroInfo> getInfos(InnovateApplyQuery query) {
        List<MicroInfo> infos = innovateMicroDao.getInfos(query);
        if (infos != null && infos.size() > 0) {
            String sql = "select u.name,u.mobile,u.avatar,d.name,d.deptid,wu.type_name as userType\n" +
                    "from t_work_user wu\n" +
                    "join t_user u on wu.userid = u.userid\n" +
                    "join t_dept d on u.deptid = d.deptid\n" +
                    "where wu.work_type=? and wu.work_id=?";
            for (int i = 0; i < infos.size(); i++) {
                MicroInfo tmp = infos.get(i);
                List<UserInfo> userInfos = pubDao.queryList(UserInfo.class, sql, tmp.getWorkType(), tmp.getId());
                infos.get(i).setUserInfos(userInfos);
            }
        }
        return infos;
    }

    @Autowired
    private InnovateMicroDao innovateMicroDao;
    @Autowired
    private PubDao pubDao;
}
