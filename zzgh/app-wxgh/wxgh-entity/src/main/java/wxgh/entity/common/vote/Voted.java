package wxgh.entity.common.vote;

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
 * Created by Admin on 2016/7/7.
 */
@Entity
@Table(name = "t_voted")
public class Voted implements Serializable {

    private Integer id;
    //主题
    private String theme;
    //创建时间
    private Date createTime;
    private String createTimeStr;
    //结束时间
    private Date endTime;
    //用户id
    private String userid;
    //用户名称
    private String userName;
    //类型
    private Integer type;
    //状态
    private Integer status;
    //是否删除
    private Integer isdel;
    //头像
    private String headimg;
    //总票数
    private Integer optionnum;

    //参与投票人数
    private Integer usernum;

    public Integer getUsernum() {
        return usernum;
    }

    public void setUsernum(Integer usernum) {
        this.usernum = usernum;
    }

    private Date effectiveTime;

    private Integer userVote; //标识当前用户是否投票，小于等于0表示未投票，大于0表示已投票

    private List<VoteOption> voteOptions;

    private List<VotePicOption> votePicOptions;

    private Date startTime;

    private Integer deptid;

    private String userids;

    private String imageIds;

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    @Column(name = "theme")
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Column(name = "isdel")
    public Integer getIsdel() {
        return isdel;
    }

    @Column(name = "effective_time")
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
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

    public Integer getOptionnum() {
        return optionnum;
    }

    public void setOptionnum(Integer optionnum) {
        this.optionnum = optionnum;
    }

    public Integer getUserVote() {
        return userVote;
    }

    public void setUserVote(Integer userVote) {
        this.userVote = userVote;
    }

    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public List<VotePicOption> getVotePicOptions() {
        return votePicOptions;
    }

    public void setVotePicOptions(List<VotePicOption> votePicOptions) {
        this.votePicOptions = votePicOptions;
    }

    public String getUserids() {
        return userids;
    }

    public void setUserids(String userids) {
        this.userids = userids;
    }


    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imagesIds) {
        this.imageIds = imagesIds;
    }
}
