package wxgh.param.party.notice;


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
 * @Date 2017-10-16  10:40
 * --------------------------------------------------------- *
 */
public class NoticeParam extends Page{

    private Integer branchId;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}
