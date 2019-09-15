package wxgh.param.four;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
public class ProjectContentParam implements Serializable {

    private Integer projectId;

    /**
     *@Author:  ✔
     *@Description:  plus
     *@Date:  14:44, 2016/11/18
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectContentParam() {
    }

    public ProjectContentParam(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
