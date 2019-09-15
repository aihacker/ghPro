package wxgh.data.four.excel;


import java.util.Date;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-04  09:44
 * --------------------------------------------------------- *
 */
public class FourDetailExcelData {

    private Integer id;
    private Integer mid;
    private Integer fpId;
    private Integer deptId;
    private Integer fpcId;
    private Integer fproId;
    private String userId;
    private String brand;
    private String modelName;
    private Integer numb;
    private String remark;
    private Date buyTime;
    private String condit;
    private Date time;
    private String companyName; // 分公司名称
    private String deptName;   // （营销中心或者项目部）
    private String parentDeptName;  // 上级部门名称
    private String projectName; // 四小项目名称
    private String projectContent; // 设施项目内容
    private String priceSource; // 资金来源
    private Double price;  // 单价
    private String images;  // 图片
    private String fpName;
    private String fpcName;
    private Integer status;
    private String condStr; //资产所属
    private String deptStr; //部门名称
    private String unit; //数量单位
    private Date  planUpdate;  // Date
    private Integer usefulLife;
    private Integer repairCount;
    private String repairString;
    private String path;
    private String thumb;
    private String imgIds;
    private String machineLocation;//设备安放地址

    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    private Integer isExport;

    public Integer getIsExport() {
        return isExport;
    }

    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentDeptName() {
        return parentDeptName;
    }

    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRepairString() {
        return repairString;
    }

    public void setRepairString(String repairString) {
        this.repairString = repairString;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public String getPriceSource() {
        return priceSource;
    }

    public void setPriceSource(String priceSource) {
        this.priceSource = priceSource;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getFpcId() {
        return fpcId;
    }

    public void setFpcId(Integer fpcId) {
        this.fpcId = fpcId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getCondit() {
        return condit;
    }

    public void setCondit(String condit) {
        this.condit = condit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getFproId() {
        return fproId;
    }

    public void setFproId(Integer fproId) {
        this.fproId = fproId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getFpName() {
        return fpName;
    }

    public void setFpName(String fpName) {
        this.fpName = fpName;
    }

    public String getFpcName() {
        return fpcName;
    }

    public void setFpcName(String fpcName) {
        this.fpcName = fpcName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCondStr() {
        return condStr;
    }

    public void setCondStr(String condStr) {
        this.condStr = condStr;
    }

    public String getDeptStr() {
        return deptStr;
    }

    public void setDeptStr(String deptStr) {
        this.deptStr = deptStr;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getPlanUpdate() {
        return planUpdate;
    }

    public void setPlanUpdate(Date planUpdate) {
        this.planUpdate = planUpdate;
    }

    public Integer getUsefulLife() {
        return usefulLife;
    }

    public void setUsefulLife(Integer usefulLife) {
        this.usefulLife = usefulLife;
    }

    public Integer getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(Integer repairCount) {
        this.repairCount = repairCount;
    }

    public String getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(String machineLocation) {
        this.machineLocation = machineLocation;
    }
}
