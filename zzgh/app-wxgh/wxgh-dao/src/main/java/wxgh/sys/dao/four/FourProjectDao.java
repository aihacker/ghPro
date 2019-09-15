package wxgh.sys.dao.four;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.four.FourProject;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Repository
public class FourProjectDao extends MyBatisDao<FourProject>{


    public List<FourProject> getProjects(FourProject project) {
        return selectList("xlkai_getProject", project);
    }

    public FourProject getOne(FourProject fourProject) {
        return selectOne("xlkai_getProject", fourProject);
    }

}
