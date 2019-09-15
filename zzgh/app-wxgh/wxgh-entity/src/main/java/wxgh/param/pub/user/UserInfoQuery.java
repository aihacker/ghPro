package wxgh.param.pub.user;

import java.io.Serializable;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:30
 *----------------------------------------------------------
 */
public class UserInfoQuery implements Serializable {

    private Integer applyStatus; //申请状态
    private Integer stepNumb; //申请步骤
    private String userid;
    private Integer start;
    private Integer num;
    private String userOldestId;
    private Integer isFirst;

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public String getUserOldestId() {
        return userOldestId;
    }

    public void setUserOldestId(String userOldestId) {
        this.userOldestId = userOldestId;
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

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Integer getStepNumb() {
        return stepNumb;
    }

    public void setStepNumb(Integer stepNumb) {
        this.stepNumb = stepNumb;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}


