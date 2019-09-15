package wxgh.data.union.group.pay;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/24.
 */
public class ListData {

    private Integer id;
    private String money;
    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
