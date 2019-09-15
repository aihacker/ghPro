package wxgh.entity.party;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/27.
 */

@Entity
@Table(name = "t_party_article")
public class PartyArticle implements Serializable {

    private Integer id;
    private String title;
    private String briefInfo;
    private String img;
    private String content;
    private Integer type;
    private Date addTime;
    private String userId;
    private Integer seeNumb;
    private Integer zanNumb;

    private String deptName;
    private String userName;

    private String partyGroup;

    public String getPartyGroup() {
        return partyGroup;
    }

    public void setPartyGroup(String partyGroup) {
        this.partyGroup = partyGroup;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "userid")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "brief_info")
    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "see_numb")
    public Integer getSeeNumb() {
        return seeNumb;
    }

    public void setSeeNumb(Integer seeNumb) {
        this.seeNumb = seeNumb;
    }

    @Column(name = "zan_numb")
    public Integer getZanNumb() {
        return zanNumb;
    }

    public void setZanNumb(Integer zanNumb) {
        this.zanNumb = zanNumb;
    }
}
