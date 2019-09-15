package wxgh.sys.service.weixin.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.pub.Archive;

/**
 * @author hhl
 * @create 2017-08-02
 **/
@Service
@Transactional(readOnly = true)
public class ArchiveService {

    @Transactional
    public void addArchive(Archive archive){
        SQL sql = new SQL.SqlBuilder().field("name,userid,status,img_list")
                .value(":name,:userid,:status,:imgList")
                .insert("archive").build();
        pubDao.executeBean(sql.sql(),archive);
    }

    @Autowired
    private PubDao pubDao;
}
