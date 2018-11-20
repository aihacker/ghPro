package com.gpdi.mdata.web.reportform.daoexcel.entities.StationBill;

/**
 * @author: zzy
 * @data: Created in 2018/6/27 16:35
 * @description:要读取的excel表的首行的标题字段
 */
public enum ExcelColumnBill {
    card_number("卡号"),
    date("时间"),
    business_type("业务类型"),
    variety("品种"),
    number("数量"),
    unit_price("单价"),
    sum("金额(分值)"),
    reward_points("奖励分值"),
    preferential_price("优惠价"),
    balance("余额"),
    site("地点"),
    operator("操作员"),
    remark("备注"),
    itle_id("标题");


    //private int title_id;
    private int column;
    private String name;

    ExcelColumnBill(String name) {
        this.name = name;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public int getTitle_id() {
        return title_id;
    }

    public void setTitle_id(int title_id) {
        this.title_id = title_id;
    }*/
}
