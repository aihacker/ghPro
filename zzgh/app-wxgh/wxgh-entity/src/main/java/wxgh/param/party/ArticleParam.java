package wxgh.param.party;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/23.
 */
public class ArticleParam extends Page {

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
