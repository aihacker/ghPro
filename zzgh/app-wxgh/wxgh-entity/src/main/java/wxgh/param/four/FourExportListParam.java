package wxgh.param.four;


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
 * @Date 2017-09-25  09:13
 * --------------------------------------------------------- *
 */
public class FourExportListParam extends Page {

    private Integer deptid;
    private Integer fpId;
    private Integer fpcId;
    private Integer mid;
    // 图片字段是否为空
    private Integer img;

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public Integer getFpcId() {
        return fpcId;
    }

    public void setFpcId(Integer fpcId) {
        this.fpcId = fpcId;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}
