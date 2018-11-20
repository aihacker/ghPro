package com.gpdi.mdata.web.reportform.daoexcel.entities.PurchaseContract;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/6/27 16:35
 * @description:要读取的excel表的首行的标题字段
 */
public enum ExcelColumn {
    contract_code("合同编号"),
    purchase_time("定稿时间"),
    contract_name("合同名称"),
    contract_amount("合同金额(元)"), //合同金额  数据库字段类型用decimal,精度要求高可用BigDecimal
    undertake_org("承办单位"), //承办单位
    undertake_dept("承办部门"),  //承办部门
    undertake_man("承办人"),   //承办人
    supplier_name("对方名称"),   //对方名称
    purchase_way("采购方式"),    //采购方式
    purchase_type("采购类型"),   //采购类型
    company_code("公司代码"),    //公司代码
    purchase_kind("采购种类"),   //采购种类
    set_service_type("设备/服务类型"), //设备/服务类型
    set_type_lv1("设备类型第一层"),     //设备类型第一层
    set_type_lv2("设备类型第二层"),     //设备类型第二层
    sign_type("签约类型"),
    is_correlation_trade("是否关联交易"),
    correlation_trade_type("关联交易类型"),
    is_itc_project("是否ICT项目"),
    is_pivotal_contract("是否属于关键合同"),
    kong(""),//空值占位
    receipt_pay_type("收付类型"),
    contract_type("合同类型"),
    purchase_result_code("采购结果编号"),
    contract_prop("合同属性"),
    archive_date("归档日期"),
    contract_status("合同状态");

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
