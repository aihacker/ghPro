package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.union.innovation.WorkZan;
import wxgh.sys.dao.union.innovation.WorkZanDao;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class WorkZanService {

    @Autowired
    private WorkZanDao workZanDao;

    @Autowired
    private PubDao pubDao;

    public Boolean checkZan(String userid, Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("work_zan")
                .field("user_id")
                .where("user_id = ? and work_id = ?")
                .limit("1")
                .select()
                .build();
        String uid = pubDao.query(String.class, sql.sql(), userid, id);
        if(uid == null)
            return false;
        else
            return true;
    }
    
    @Transactional
    public Integer saveWorkZan(WorkZan workZan) {
        workZanDao.save(workZan);
        return workZan.getId();
    }

    
    public Integer getCount(Integer id) {
        return workZanDao.getCount(id);
    }

    
    @Transactional
    public Integer delZan(WorkZan workZan) {
        return workZanDao.delZan(workZan);
    }

    
    public Integer getOne(WorkZan workZan) {
        return workZanDao.getOne(workZan);
    }


}
