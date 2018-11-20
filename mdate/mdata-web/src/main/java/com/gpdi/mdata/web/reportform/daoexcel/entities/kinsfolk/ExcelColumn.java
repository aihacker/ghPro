package com.gpdi.mdata.web.reportform.daoexcel.entities.kinsfolk;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/6/27 16:35
 * @description:要读取的excel表的首行的标题字段
 */
public enum ExcelColumn {


    enterprise_name("企业名称"),
    enterprise_code("企业代码"),
    kinsfolk_name("亲属姓名"),
    sex("性别"),
    identity_card_number("身份证号码"),
    duty("职务"),
    data_update_time("数据更新时间"),
    data_update_way("数据更新方式");

    private int column;
    private String name;

    ExcelColumn(String name) {
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


















}
