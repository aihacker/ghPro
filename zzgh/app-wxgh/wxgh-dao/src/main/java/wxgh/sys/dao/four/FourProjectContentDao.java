package wxgh.sys.dao.four;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.four.FourProjectContentData;
import wxgh.entity.four.FourProjectContent;
import wxgh.param.four.ProjectContentParam;

import java.util.List;

/**
 * Created by WIN on 2016/8/29.
 */
@Repository
public class FourProjectContentDao extends MyBatisDao<FourProjectContent>{

    public void addFPC(FourProjectContentData fourProjectContentData) {
        getSqlSession().insert("liuhe.sys.entity.FourProjectContent.addProjectContent", fourProjectContentData);
    }

    public List<FourProjectContent> getContents(ProjectContentParam query) {
        return selectList("xlkai_getContents", query);
    }

    public Integer updateContent(FourProjectContent content) {
        return execute("xlkai_updateContent", content);
    }

    public FourProjectContent getOne(ProjectContentParam projectContentQuery) {
        return selectOne("xlkai_getContents", projectContentQuery);
    }

}
