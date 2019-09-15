package wxgh.entity.four;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
@Entity
@Table(name = "t_four_details")
public class FourDetails implements Serializable {

    private Integer id;
    private Integer fpId;
    private Integer deptId;
    private Integer fpcId;
    private String brand;
    private String modelName;
    private Integer numb;
    private String remark;
    private String buyTime;
    private String condit;
    private String userId;
    private Integer fproId;
    private Date time;
    private String deptName;
    private String fpName;
    private String fpcName;
    private Integer status;
    private String condStr; //资产所属
    private String deptStr; //部门名称
    private String unit; //数量单位
    private Date planUpdate;
    private Integer usefulLife;
    private Integer repairCount;
    private Integer isExport;   // 是否为导入的数据
    private String imgIds;

    private String marketName;//营销中心
    private String panUpdateStr;

    private String fadPicture;//各个营销中心所对应四小建设项目的展示图

    private String priceSource; //资金来源
    private Double price; //单价

    /**
     * @Author: ✔
     * @Description: plus
     * @Date: 15:23, 2016/11/16
     */

    private String imgs;
    private Integer mid;
    private Integer collecting;

    private String path;
    private String thumb;

    private Integer countAll;

    private List<String> results;

    private List<String> thumbs;

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public Integer getIsExport() {
        return isExport;
    }

    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }

    private Integer conditGoodCount;
    private Integer conditCanCount;
    private Integer conditChangeCount;
    private Integer conditUnknown;

    public Integer getConditUnknown() {
        return conditUnknown;
    }

    public void setConditUnknown(Integer conditUnknown) {
        this.conditUnknown = conditUnknown;
    }

    public Integer getConditGoodCount() {
        return conditGoodCount;
    }

    public void setConditGoodCount(Integer conditGoodCount) {
        this.conditGoodCount = conditGoodCount;
    }

    public Integer getConditCanCount() {
        return conditCanCount;
    }

    public void setConditCanCount(Integer conditCanCount) {
        this.conditCanCount = conditCanCount;
    }

    public Integer getConditChangeCount() {
        return conditChangeCount;
    }

    public void setConditChangeCount(Integer conditChangeCount) {
        this.conditChangeCount = conditChangeCount;
    }

    public Integer getCollecting() {
        return collecting;
    }

    public void setCollecting(Integer collecting) {
        this.collecting = collecting;
    }

    @Column(name = "plan_update")
    public Date getPlanUpdate() {
        return planUpdate;
    }

    @Column(name = "useful_life")
    public Integer getUsefulLife() {
        return usefulLife;
    }

    @Column(name = "repair_count")
    public Integer getRepairCount() {
        return repairCount;
    }

    public String getFadPicture() {
        return fadPicture;
    }

    public void setFadPicture(String fadPicture) {
        this.fadPicture = fadPicture;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Column(name = "mid")
    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    @Column(name = "imgs")
    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "fp_id")
    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    @Column(name = "fpc_id")
    public Integer getFpcId() {
        return fpcId;
    }

    public void setFpcId(Integer fpcId) {
        this.fpcId = fpcId;
    }

    @Column(name = "fpro_id")
    public Integer getFproId() {
        return fproId;
    }

    public void setFproId(Integer fproId) {
        this.fproId = fproId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name = "dept_id")
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "model_name")
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Column(name = "numb")
    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "buy_time")
    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    @Column(name = "condit")
    public String getCondit() {
        return condit;
    }

    public void setCondit(String condit) {
        this.condit = condit;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "cond_str")
    public String getCondStr() {
        return condStr;
    }

    @Column(name = "dept_str")
    public String getDeptStr() {
        return deptStr;
    }

    public void setDeptStr(String deptStr) {
        this.deptStr = deptStr;
    }

    public void setCondStr(String condStr) {
        this.condStr = condStr;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "price_source")
    public String getPriceSource() {
        return priceSource;
    }

    public void setPriceSource(String priceSource) {
        this.priceSource = priceSource;
    }

    public void setPlanUpdate(Date planUpdate) {
        this.planUpdate = planUpdate;
    }

    public void setUsefulLife(Integer usefulLife) {
        this.usefulLife = usefulLife;
    }

    public void setRepairCount(Integer repairCount) {
        this.repairCount = repairCount;
    }

    public String getPanUpdateStr() {
        return panUpdateStr;
    }

    public void setPanUpdateStr(String panUpdateStr) {
        this.panUpdateStr = panUpdateStr;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    @Column
    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }
}
