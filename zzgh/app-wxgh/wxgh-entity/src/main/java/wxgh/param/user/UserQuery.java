package wxgh.param.user;


import pub.bean.Page;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/7/11.
 * <p>
 * Date： 2016/7/11
 */
public class UserQuery extends Page implements Serializable {

    private Integer deptId; //部门ID，可以是工会ID、公司ID和部门ID中的任何一个

    private String openid;//微信openid
    private String mobile; //手机号码
    private String weixinid; //微信id
    private String email; //邮箱地址
    private String userid; //用户ID

    private Integer status; //用户状态

    private Integer start;
    private Integer num;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWeixinid() {
        return weixinid;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
