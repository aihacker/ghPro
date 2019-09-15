package wxgh.sys.service.weixin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.four.FourProjectContent;
import wxgh.param.four.ProjectContentParam;
import wxgh.sys.dao.four.FourProjectContentDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class ProjectContentService{

    @Autowired
    private FourProjectContentDao fourProjectContentDao;

    @Autowired
    private PubDao generalDao;

    public List<FourProjectContent> getContents(ProjectContentParam query) {
        return fourProjectContentDao.getContents(query);
    }

    @Transactional
    public void insertOrUpdateCnt(FourProjectContent projectContent) {

        String sql = "select id from t_four_project_content where fp_id=? and name=? order by fp_id limit 1";

        Integer id = generalDao.query(Integer.class, sql, projectContent.getFpId(), projectContent.getName());

        if (id == null) {
            fourProjectContentDao.save(projectContent);
        } else {
            projectContent.setId(id);
        }

    }

    public List<String> groupContent() {
        String sql = "select name from t_four_project_content group by name";
        return generalDao.queryList(String.class, sql);
    }

    public Integer updateContent(FourProjectContent content) {
        return fourProjectContentDao.updateContent(content);
    }

    public FourProjectContent getOne(ProjectContentParam projectContentQuery) {
        return fourProjectContentDao.getOne(projectContentQuery);
    }
}
