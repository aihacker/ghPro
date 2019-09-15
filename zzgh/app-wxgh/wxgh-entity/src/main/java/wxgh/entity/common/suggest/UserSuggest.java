package wxgh.entity.common.suggest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-27 17:04
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_user_suggest")
public class UserSuggest implements Serializable {

    public static final Integer STATUS_SH = 0; //审核中
    public static final Integer STATUS_PASS = 1; //通过
    public static final Integer STATUS_REJECT = 2; //拒绝

    public static final Integer NOT_FROM_INNOVATE = 0;//非来自合理化建议
    public static final Integer IS_FROM_INNOVATE = 1;//来自合理化建议

    private Integer id;
    //標題
    private String title;
    //內容
    private String content;
    //用戶id
    private String userid;
    //投訴部門對象
    private String deptid;
    //時間
    private Date createTime;

    //審核狀態
    private Integer status;
    //類型
    private Integer type;
    //评论数
    private Integer commNum;

    private Integer cateId;
    private Integer seeNum; //查看数量
    private Integer lovNum; //喜欢数量
    private Integer tranNum; //转发数量

    private Integer isFromInnovate;//是否来自合理化建议


    private String username; //用户姓名
    private String deptname; //部门名称
    private String avatar; //用户头像
    private String timeStr; //创建时间
    private String cateName;//分类名称

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "cate_id")
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "comm_num")
    public Integer getCommNum() {
        return commNum;
    }

    public void setCommNum(Integer commNum) {
        this.commNum = commNum;
    }

    @Column(name = "deptid")
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    @Column(name = "see_num")
    public Integer getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(Integer seeNum) {
        this.seeNum = seeNum;
    }

    @Column(name = "lov_num")
    public Integer getLovNum() {
        return lovNum;
    }

    public void setLovNum(Integer lovNum) {
        this.lovNum = lovNum;
    }

    @Column(name = "tran_num")
    public Integer getTranNum() {
        return tranNum;
    }

    public void setTranNum(Integer tranNum) {
        this.tranNum = tranNum;
    }

    @Column(name = "is_from_innovate")
    public Integer getIsFromInnovate() {
        return isFromInnovate;
    }

    public void setIsFromInnovate(Integer isFromInnovate) {
        this.isFromInnovate = isFromInnovate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}

