package wxgh.entity.common.suggest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:44
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_suggest_comm")
public class SuggestComm implements Serializable {

    public static final Integer STATUS_NORMAL = 1; //默认正常
    public static final Integer STATUS_FORBID = 2; //违规评论
    public static final Integer STATUS_DEL = 3; //已删除

    private Integer id;
    private String content;
    private Integer sugId;
    private String userid;
    private Date addTime;
    private Integer status;
    private Integer lovNum;
    private Integer parentid;

    private String username; //用户姓名
    private String avatar; //用户头像
    private String timeStr;
    private List<SuggestComm> suggestComms;
    private Boolean lovIs = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "sug_id")
    public Integer getSugId() {
        return sugId;
    }

    public void setSugId(Integer sugId) {
        this.sugId = sugId;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "lov_num")
    public Integer getLovNum() {
        return lovNum;
    }

    @Column(name = "parentid")
    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public void setLovNum(Integer lovNum) {
        this.lovNum = lovNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<SuggestComm> getSuggestComms() {
        return suggestComms;
    }

    public void setSuggestComms(List<SuggestComm> suggestComms) {
        this.suggestComms = suggestComms;
    }

    public Boolean getLovIs() {
        return lovIs;
    }

    public void setLovIs(Boolean lovIs) {
        this.lovIs = lovIs;
    }
}


