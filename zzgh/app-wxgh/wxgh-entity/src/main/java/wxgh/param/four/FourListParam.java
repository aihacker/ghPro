package wxgh.param.four;


import pub.bean.Page;

/**
 * @Author xlkai
 * @Version 2016/12/9
 */
public class FourListParam extends Page {

    private Integer deptid; //部门id
    private Integer mid;  //营销中心id

    private Integer fpid; //四小项目ID
    private String fpcName; //四小项目内容
    private String brand;
    private String condit; //设备情况
    private String belong; //资产所属
    private String searchKey;//搜索关键字

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getFpid() {
        return fpid;
    }

    public void setFpid(Integer fpid) {
        this.fpid = fpid;
    }

    public String getFpcName() {
        return fpcName;
    }

    public void setFpcName(String fpcName) {
        this.fpcName = fpcName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCondit() {
        return condit;
    }

    public void setCondit(String condit) {
        this.condit = condit;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
