package com.gpdi.mdata.web.reportform.daoexcel.entities.bidcominfo;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/6/27 16:35
 * @description:要读取的excel表的首行的标题字段
 */
public enum ExcelColumn {

    project_name("电信采购项目名称"),
    project_code("采购项目编码"),
    purchase_way("采购方式"),
    opening_time("开标时间"),
    tendering_company("投标公司名称"),
    credit_code("公司社会信用编码"),
    is_it_eligible("是否符合投标条件");


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
