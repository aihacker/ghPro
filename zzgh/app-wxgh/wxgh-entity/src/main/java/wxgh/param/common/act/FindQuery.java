package wxgh.param.common.act;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/6/30.
 * <p>
 * Date： 2016/6/30
 */
public class FindQuery extends ActListQuery implements Serializable {

    private String findTitle; //活动标题
    private String findType; //活动类型
    private String findTime; //活动时间
    private String findUser; //组织者

    private boolean findAll; //查找全部
    private String name; //搜索关键字

    public String getFindTitle() {
        return findTitle;
    }

    public void setFindTitle(String findTitle) {
        this.findTitle = findTitle;
    }

    public String getFindType() {
        return findType;
    }

    public void setFindType(String findType) {
        this.findType = findType;
    }

    public String getFindTime() {
        return findTime;
    }

    public void setFindTime(String findTime) {
        this.findTime = findTime;
    }

    public String getFindUser() {
        return findUser;
    }

    public void setFindUser(String findUser) {
        this.findUser = findUser;
    }

    public boolean isFindAll() {
        return findAll;
    }

    public void setFindAll(boolean findAll) {
        this.findAll = findAll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
