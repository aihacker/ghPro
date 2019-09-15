package wxgh.sys.service.weixin.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.pub.SysVal;

/**
 * Created by Administrator on 2017/8/1.
 */
@Service
@Transactional(readOnly = true)
public class SysValService {

    public SysVal getVal(String name) {
        String sql = "select * from t_sys_val where name = ?";
        return pubDao.query(SysVal.class, sql, name);
    }

    @Transactional
    public void addVal(String name, String val) {
        String sql = "insert into t_sys_val(name, val, add_time) values(:name, :val, :addTime)";
        SysVal sysVal = new SysVal();
        sysVal.setName(name);
        sysVal.setVal(val);
        sysVal.setAddTime(System.currentTimeMillis());
        pubDao.executeBean(sql, sysVal);
    }


    @Autowired
    private PubDao pubDao;
}
