package wxgh.sys.service.weixin.common.fraternity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.FAShenheData;
import wxgh.data.union.SendInfo;
import wxgh.entity.common.fraternity.FraternityApply;
import wxgh.param.common.fraternity.ApplyParam;
import wxgh.sys.dao.common.fraternity.FraternityDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:00
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class FraternityService {

    @Autowired
    private FraternityDao fraternityDao;
    @Autowired
    private PubDao pubDao;

    @Transactional
    public Integer insertOrUpdate(FraternityApply apply) {
        return fraternityDao.insertOrUpdate(apply);
    }

    @Transactional
    public Integer updateApply(FraternityApply apply) {
        return fraternityDao.updateApply(apply);
    }

    public List<FraternityApply> getApplys(ApplyParam query) {
        return fraternityDao.getApplys(query);
    }

    @Transactional
    public FraternityApply getApply(ApplyParam query) {
        return fraternityDao.getApply(query);
    }

    @Transactional
    public Integer del(Integer id) {
        return fraternityDao.del(id);
    }

    @Transactional
    public Integer apply(FAShenheData faShenheData) {
        return fraternityDao.apply(faShenheData);
    }

    public String getUserid(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("t_fraternity_apply")
                .field("userid")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }

    public List<FraternityApply> applyListRefresh(ApplyParam query) {
        return fraternityDao.applyListRefresh(query);
    }

    public List<FraternityApply> applyListMore(ApplyParam query) {
        return fraternityDao.applyListMore(query);
    }

    public List<FraternityApply> getApplysNotInTargetDeptId(ApplyParam query, List<Integer> ids) {
        String in = "";
        if (ids != null) {
            for (Integer id : ids) {
                in += id + ",";
            }
            if(in.length() > 0)
            in = in.substring(0, in.length() - 1);
        }

        String and = "";
        if (query.getFraternityOldestId() != null) {
            and = " and fa.id<" + query.getFraternityOldestId() + " ";
        }
        String sql = "SELECT fa.*, wd.name AS deptName, u.avatar AS avatar\n" +
                "        FROM t_fraternity_apply fa\n" +
                "        JOIN t_dept wd ON wd.deptid = fa.dept_id\n" +
                "        JOIN t_user u ON  u.userid = fa.user_id\n" +
                "where fa.dept_id NOT IN (" + in + ") \n" +
                "AND fa.status = ? \n" + and +
                "ORDER BY fa.id DESC limit 0, 15";
        return pubDao.queryList(FraternityApply.class, sql, query.getStatus());
    }

    public SendInfo getSendInfoByUserId(String userId) {
        String sql = "select '互助会入会申请' as sendType,f.id as itemId,\n" +
                "u.name as username,d.name as deptName,u.userid as userId\n" +
                "from t_fraternity_apply f join t_user u on u.userid=f.user_id\n" +
                "join t_dept d on u.deptid=d.deptid\n" +
                "where f.user_id=? order by f.apply_time desc limit 1;";
        return pubDao.query(SendInfo.class, sql, userId);
    }
    
}

