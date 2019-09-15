package wxgh.sys.dao.common.disease;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.disease.ApplyAccessory;
import wxgh.param.common.disease.AccessoryQuery;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Repository
public class AccessoryDao extends MyBatisDao<ApplyAccessory> {

    public List<ApplyAccessory> getFiles(AccessoryQuery query) {
        return selectList("xlkai_getFile", query);
    }

    public ApplyAccessory getFile(AccessoryQuery query) {
        return selectOne("xlkai_getFile", query);
    }

    public Integer addFiles(List<ApplyAccessory> accessories) {
        return execute("xlkai_addFiles", accessories);
    }

    public Integer updateWH(AccessoryQuery query) {
        return selectOne("updateWH",query);
    }
}
