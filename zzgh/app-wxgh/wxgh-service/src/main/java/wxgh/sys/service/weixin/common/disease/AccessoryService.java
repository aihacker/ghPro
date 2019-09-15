package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.ApplyAccessory;
import wxgh.param.common.disease.AccessoryQuery;
import wxgh.sys.dao.common.disease.AccessoryDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class AccessoryService  {

    @Autowired
    private AccessoryDao accessoryDao;

    public List<ApplyAccessory> getFiles(AccessoryQuery query) {
        return accessoryDao.getFiles(query);
    }

    public ApplyAccessory getFile(AccessoryQuery query) {
        return accessoryDao.getFile(query);
    }

    @Transactional
    public Integer addFiles(List<ApplyAccessory> accessories) {
        return accessoryDao.addFiles(accessories);
    }
}
