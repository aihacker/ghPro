package com.gpdi.mdata.web.reportform.daoexcel.entities.machineAccount;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/8/8 10:17
 * @description:要读取的excel表的首行的标题字段
 */
public enum ExcelColumn {
    contract_code("合同编号"),
    contract_name("合同名称"),
    commany_name("我方名称"),
    contract_type("合同类型"),
    receipt_pay_type("收付类型"),
    contract_amount("合同金额"),
    contract_status("合同状态"),
    undertake_dept("承办部门"),
    undertake_man("承办人"),
    current_step("当前办理步骤"),
    sign_basis("签约依据"),
    is_busi_outsourcing("是否业务外包合同"),
    is_itc_project("是否ICT"),
    draft_date("拟稿日期"),
    finalize_date("定稿日期"),
    stamp_date("盖章日期"),
    sign_type("签约类型"),
    is_correlation_contract("是否关联合同"),
    correlation_contract_info("关联合同信息"),
    is_cooperate_shared("是否合作分成"),
    supplier_name("对方名称"),
    is_correlation_trade("是否关联交易"),
    is_correlation_trade_type("关联交易类型"),
    is_original_suppliey("是否原合同对方"),
    suppliey_legal_rep("对方法人代表"),
    performance_begin("履行期限起"),
    performance_end("履行期限止"),
    purchase_type("采购类型"),
    set_service_type("设备及服务类型"),
    purchase_way("采购方式"),
    purchase_kind("采购种类"),
    set_type_lv1("设备类型第一层"),
    set_type_lv2("设备类型第二层"),
    contract_prop("合同属性"),
    fee_prop("费用属性");



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
