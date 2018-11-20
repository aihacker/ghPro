package com.gpdi.mdata.sys.entity.report;

/**
 * @description:法定招标信息
 * @author: WangXiaoGang
 * @data: Created in 2018/10/10 17:41
 * @modifier:
 */
public class Legal {
    private String titleThree;//标题

    private String money;//金额

    public String getTitleThree() {
        return titleThree;
    }

    public void setTitleThree(String titleThree) {
        this.titleThree = titleThree;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Legal{" +
                "titleThree='" + titleThree + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
