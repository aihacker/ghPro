package wxgh.entity.union.innovation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_innovate_shop")
public class InnovateShop implements Serializable {
    private Integer id;
    private Integer shopType;
    private String teamName;
    private String itemName;
    private String teamLeader;//userId
    private Integer applyId;
    private String address;
    private Integer type;
    private Integer status;
    private Date createTime;

    private String shopTypeName;

    public String getShopTypeName() {
        return shopTypeName;
    }

    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
    }

    /**
     *@Author:  ✔
     *@Description:  plus
     *@Date:  18:20, 2016/11/15
     */
    private String auditIdea;
    private Date auditTime;
    private String content;
    private Integer applyStatus;
    private String teamLeaderName;
    private String txt;
    private List<String> imgList;
    private String deptName;
    private String userName;
    private Integer wid;

    public Integer getWid() {

        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTeamLeaderName() {
        return teamLeaderName;
    }

    public void setTeamLeaderName(String teamLeaderName) {
        this.teamLeaderName = teamLeaderName;
    }

    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "shop_type")
    public Integer getShopType() {
        return shopType;
    }

    @Column(name = "team_name")
    public String getTeamName() {
        return teamName;
    }

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    @Column(name = "team_leader")
    public String getTeamLeader() {
        return teamLeader;
    }

    @Column(name = "apply_id")
    public Integer getApplyId() {
        return applyId;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
