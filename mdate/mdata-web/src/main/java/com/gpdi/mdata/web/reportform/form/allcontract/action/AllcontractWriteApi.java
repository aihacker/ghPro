package com.gpdi.mdata.web.reportform.form.allcontract.action;

import com.gpdi.mdata.web.utils.ExcelSupportApi;
import com.gpdi.mdata.web.utils.ExcelType;
import com.gpdi.mdata.web.utils.PercentUtil;
import org.apache.poi.ss.usermodel.Row;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/14 20:21
 * @modifier:
 */
public class AllcontractWriteApi extends ExcelSupportApi<AllcontractTempData> {

    public AllcontractWriteApi() {
        super( "采购合同表", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "序号","合同编号",
                        "定稿时间","合同名称","合同金额","承办单位","承办部门","承办人","对方名称","采购方","采购类型",
                        "公司代码","采购种类","设备/服务器类型","设备类型第一层","设备类型第二层","签约类型","是否关联交易",
                        "关联交易类型","是否ITC项目","是否属于关键合同","收付类型","合同类型","采购结果编号","合同属性",
                        "归档日期","合同状态"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
        };
    }

    @Override
    public void createCellItem(AllcontractTempData data, Row row, int i) {
        if(data == null)
            return;

        createCell(row, 0, data.getId());
        createCell(row, 1, data.getContract_code());
        createCell(row, 2, data.getPurchase_time());
        createCell(row, 3, data.getContract_name());
        createCell(row, 4, data.getContract_amount());
        createCell(row, 5, data.getUndertake_org());
        createCell(row, 6, data.getUndertake_dept());
        createCell(row, 7, data.getUndertake_man());
        createCell(row, 8, data.getSupplier_name());
        createCell(row, 9, data.getPurchase_way());
        createCell(row, 10, data.getPurchase_type());
        createCell(row, 11, data.getCompany_code());
        createCell(row, 12, data.getPurchase_kind());
        createCell(row, 13, data.getSet_service_type());
        createCell(row, 14, data.getSet_type_lv1());
        createCell(row, 15, data.getSet_type_lv2());
        createCell(row, 16, data.getSign_type());
        createCell(row, 17, data.getIs_correlation_trade());
        createCell(row, 18, data.getCorrelation_trade_type());
        createCell(row, 19, data.getIs_itc_project());
        createCell(row, 20, data.getIs_pivotal_contract());
        createCell(row, 21, data.getReceipt_pay_type());
        createCell(row, 22, PercentUtil.getChinese(data.getContract_type()));
        createCell(row, 23, data.getPurchase_result_code());
        createCell(row, 24, data.getContract_code());
        createCell(row, 25, data.getArchive_date());
        createCell(row, 26, data.getContract_status());
    }

    @Override
    protected float rowHeight() {
        return 1;
    }

    private String formatRepair(int v){
        if(v == 0) return "无";
        else return "" + v;
    }

}
