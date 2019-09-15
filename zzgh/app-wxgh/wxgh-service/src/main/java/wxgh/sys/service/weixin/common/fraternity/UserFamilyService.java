package wxgh.sys.service.weixin.common.fraternity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.UserFamily;
import wxgh.param.common.fraternity.FamilyQuery;
import wxgh.sys.dao.common.fraternity.UserFamilyDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:18
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class UserFamilyService {

    @Autowired
    private UserFamilyDao userFamilyDao;

    @Transactional
    public Integer addFamilys(List<UserFamily> families) {
        return userFamilyDao.addFamilys(families);
    }

    public List<UserFamily> getFamilys(FamilyQuery query) {
        return userFamilyDao.getFamilys(query);
    }

    public UserFamily getFamily(FamilyQuery query) {
        return userFamilyDao.getFamily(query);
    }

    @Transactional
    public void insertOrUpdate(List<UserFamily> families) {
        if (families != null && families.size() > 0) {
            for (UserFamily family : families) {
                UserFamily hasFamily = userFamilyDao.getFamily(new FamilyQuery(family.getUserId(), family.getRelation()));
                if (hasFamily != null) {
                    family.setId(hasFamily.getId());
                }
                userFamilyDao.save(family);
            }
        }
    }

    public List<UserFamily> getUFs(String id) {
        return userFamilyDao.getUFs(id);
    }
    
}

