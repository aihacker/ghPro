package com.gpdi.mdata.web.reportform.daoexcel.entities.clean;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/6/27 16:35
 * @description:要读取的excel表的首行的标题字段
 */
public enum ExcelColumn {

    flow_path("流程"),
    title("标题"),
    iscm_number("ISCM编号"),
    flow_path_number("流程编号"),
    clean_advance("是否即时清结电商企业预付款"),
    supplier_code("供应商编码"),
    supplier_name("供应商名称"),
    material_code("物料编码"),
    material_name("物料名称"),
    unit_measurement("计量单位"),
    transaction_price("成交单价（元）"),
    turnover("成交数量"),
    trading_volume("成交金额（元）"),
    draft_unit("拟稿单位"),
    draft_people("拟稿人"),
    draft_department("拟稿部门"),
    purchase_amount("采购金额（万）"),
    draft_time("拟稿时间"),
    procurement_method("采购方式"),
    whether_open_purchasing("是否在经过公开评选年度供应商范围内组织的采购"),
    procurement_classification("采购分类"),
    capital_source("资金来源"),
    newest_time("最新到达采购实施部门时间"),
    procurement_submit_time("采购实施部门提交时间"),
    current_state("当前状态"),
    current_handler("当前处理人"),
    product_service_type("产品或服务类型");





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
