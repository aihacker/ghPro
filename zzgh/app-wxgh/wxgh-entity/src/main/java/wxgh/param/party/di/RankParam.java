package wxgh.param.party.di;


import pub.dao.page.Page;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-11-23  15:59
 * --------------------------------------------------------- *
 */
public class RankParam extends Page {

    // 群组id
    private Integer id;
    private Integer status;
    private Integer scoreType;
    private Integer scoreGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    public Integer getScoreGroup() {
        return scoreGroup;
    }

    public void setScoreGroup(Integer scoreGroup) {
        this.scoreGroup = scoreGroup;
    }
}
