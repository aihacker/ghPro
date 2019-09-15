package wxgh.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-26 11:08
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_comment")
public class Comment {

    public static final Integer TYPE_BBS = 1; //热点论坛
    public static final Integer TYPE_PARTYSUG = 2; //总经理直通车
    public static final Integer TYPE_ACT_RESULT = 3; //活动成果

    //评论id
    private Integer comId;
    //评论内容
    private String atlComment;
    //文章id
    private Integer atlId;
    //用户id
    private String userid;
    //用户昵称
    private String userName;
    //创建时间
    private Date createTime;
    private String createFormatTime;
    private String createFormatDate;

    //用户头像
    private String headimg;
    //是否删除
    private Integer isdel;

    private Integer type;

    private Integer status;
    private Integer zanNum;

    private String deptname;
    private Integer isZan;

    @Id
    @Column(name = "com_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    @Column(name = "atl_comment")
    public String getAtlComment() {
        return atlComment;
    }

    public void setAtlComment(String atlComment) {
        this.atlComment = atlComment;
    }

    @Column(name = "atl_id")
    public Integer getAtlId() {
        return atlId;
    }

    public void setAtlId(Integer atlId) {
        this.atlId = atlId;
    }

    @Column(name = "user_id")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateFormatTime() {
        if (createTime != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createFormatTime = format.format(createTime);
        } else {
            createFormatTime = "";
        }
        return createFormatTime;
    }

    public void setCreateFormatTime(String createFormatTime) {
        this.createFormatTime = createFormatTime;
    }

    @Column(name = "isdel")
    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateFormatDate() {
        return createFormatDate;
    }

    public void setCreateFormatDate(String createFormatDate) {
        this.createFormatDate = createFormatDate;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Column(name = "zan_num")
    public Integer getZanNum() {
        return zanNum;
    }

    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }

    public Integer getIsZan() {
        return isZan;
    }

    public void setIsZan(Integer isZan) {
        this.isZan = isZan;
    }
}


