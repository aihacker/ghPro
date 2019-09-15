package wxgh.sys.dao.common.fraternity;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.UserFamily;
import wxgh.param.common.fraternity.FamilyQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:19
 *----------------------------------------------------------
 */
@Repository
public class UserFamilyDao extends MyBatisDao<UserFamily>{

    public Integer addFamilys(List<UserFamily> families) {
        return execute("xlkai_addFamilys", families);
    }

    public List<UserFamily> getUFs(String id) {
        return selectList("getUFs", id);
    }

    public List<UserFamily> getFamilys(FamilyQuery query) {
        return selectList("xlkai_getFamily", query);
    }

    public UserFamily getFamily(FamilyQuery query) {
        return selectOne("xlkai_getFamily", query);
    }
    
}

