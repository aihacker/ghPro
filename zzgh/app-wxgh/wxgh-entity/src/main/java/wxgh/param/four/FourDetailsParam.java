package wxgh.param.four;

import pub.dao.page.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
public class FourDetailsParam extends Page implements Serializable {

    private String updateYear;
    private Integer deptId;

    private List<Integer> depts;

    private Integer id;

    private Integer thedeptid;

    //limit
    private boolean isLimit = false;
    private Integer startPage = 0;
    private Integer totalPage = 5;

    //plus
    private Integer start;
    private Integer num;

    private String deptStr; //部门名称

    private Integer mid;
    private String fpcName;
    private String fpName;

    public String getFpName() {
        return fpName;
    }

    public void setFpName(String fpName) {
        this.fpName = fpName;
    }

    private String search;

    private Integer fpID;//四小项目ID
    private Integer status;

    /**
     * @Author: ✔
     * @Description:
     * @Date: 17:05, 2016/11/24
     */
    private Integer addId;

    private Integer type;

    private Integer foshanDeptid;

    private String typeName;

    private String brand;

    private String condit;

    private String condStr;

    private List<Integer> idList;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCondStr() {
        return condStr;
    }

    public void setCondStr(String condStr) {
        this.condStr = condStr;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getFoshanDeptid() {
        return foshanDeptid;
    }

    public void setFoshanDeptid(Integer foshanDeptid) {
        this.foshanDeptid = foshanDeptid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAddId() {
        return addId;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }

    public Integer getFpID() {
        return fpID;
    }

    public void setFpID(Integer fpID) {
        this.fpID = fpID;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFpcName() {
        return fpcName;
    }

    public void setFpcName(String fpcName) {
        this.fpcName = fpcName;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getDeptStr() {
        return deptStr;
    }

    public void setDeptStr(String deptStr) {
        this.deptStr = deptStr;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public FourDetailsParam(List<Integer> depts) {
        this.depts = depts;
    }

    public FourDetailsParam() {
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public List<Integer> getDepts() {
        return depts;
    }

    public void setDepts(List<Integer> depts) {
        this.depts = depts;
    }

    public boolean isLimit() {
        return isLimit;
    }

    public void setIsLimit(boolean isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getThedeptid() {
        return thedeptid;
    }

    public void setThedeptid(Integer thedeptid) {
        this.thedeptid = thedeptid;
    }

    public String getCondit() {
        return condit;
    }

    public void setCondit(String condit) {
        this.condit = condit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateYear() {
        return updateYear;
    }

    public void setUpdateYear(String updateYear) {
        this.updateYear = updateYear;
    }

    @Override
    public String toString() {
        return "FourDetailsParam{" +
                "updateYear='" + updateYear + '\'' +
                ", deptId=" + deptId +
                ", depts=" + depts +
                ", id=" + id +
                ", thedeptid=" + thedeptid +
                ", isLimit=" + isLimit +
                ", startPage=" + startPage +
                ", totalPage=" + totalPage +
                ", start=" + start +
                ", num=" + num +
                ", deptStr='" + deptStr + '\'' +
                ", mid=" + mid +
                ", fpcName='" + fpcName + '\'' +
                ", fpName='" + fpName + '\'' +
                ", search='" + search + '\'' +
                ", fpID=" + fpID +
                ", status=" + status +
                ", addId=" + addId +
                ", type=" + type +
                ", foshanDeptid=" + foshanDeptid +
                ", typeName='" + typeName + '\'' +
                ", brand='" + brand + '\'' +
                ", condit='" + condit + '\'' +
                ", condStr='" + condStr + '\'' +
                ", idList=" + idList +
                '}';
    }
}
