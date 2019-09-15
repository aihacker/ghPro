package wxgh.sys.service.weixin.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;

/**
 * Created by Administrator on 2017/7/13.
 */
@Service
@Transactional(readOnly = true)
public class ConverUserService {

    @Transactional
    public void del(String userid, String chatid) {
        String sql = "delete from t_wx_conver_user where userid=? and conver_id=?";
        pubDao.execute(sql, userid, chatid);
    }

    @Autowired
    private PubDao pubDao;

}
