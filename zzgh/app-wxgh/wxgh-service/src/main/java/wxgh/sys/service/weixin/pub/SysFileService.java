package wxgh.sys.service.weixin.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.pub.SysFile;
import wxgh.sys.dao.pub.SysFileDao;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/14.
 */
@Service
@Transactional(readOnly = true)
public class SysFileService {

    @Transactional
    public void addFile(SysFile file) {
        file.setAddTime(new Date());
        sysFileDao.save(file);
    }

    @Transactional
    public void delete(String fileId) {
        String sql = "delete from t_sys_file where file_id in(" + fileId + ")";
        pubDao.execute(sql);
    }

    public SysFile getFilePath(String fileId) {
        String sql = "select * from t_sys_file where file_id = ?";
        return pubDao.query(SysFile.class, sql, fileId);
    }

    public SysFile getFile(String md5) {
        String sql = "select * from t_sys_file where md5 = ? limit 1";
        return pubDao.query(SysFile.class, sql, md5);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private SysFileDao sysFileDao;

}
