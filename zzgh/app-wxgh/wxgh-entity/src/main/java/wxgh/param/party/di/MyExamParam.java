package wxgh.param.party.di;


import pub.dao.page.Page;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-17  14:16
 * --------------------------------------------------------- *
 */
public class MyExamParam extends Page{

    private String userid;
    // 1 已完成 2 未报名 未参加
    private Integer type;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
