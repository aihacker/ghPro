package com.gpdi.mdata.web.reportform.cleanInventory.show.action;

import com.gpdi.mdata.web.utils.ExcelSupportApi;
import com.gpdi.mdata.web.utils.ExcelType;
import com.gpdi.mdata.web.utils.PercentUtil;
import org.apache.poi.ss.usermodel.Row;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/14 21:55
 * @modifier:
 */
public class ShowWriteApi extends ExcelSupportApi<ShowTempData> {

    public ShowWriteApi() {
        super( "即时清洁查询表", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "序号",
                        "流程",
                        "标题",
                        "ISCM编号",
                        "流程编号",
                        "是否即时清洁电商企业预付款",
                        "供应商编号",
                        "供应商名称",
                        "物料编码",
                        "物料名称",
                        "计量单位",
                        "成交单价(元)",
                        "成交数量",
                        "成交金额(元)",
                        "拟稿单位",
                        "拟稿人",
                        "拟稿部门",
                        "采购金额(万)",
                        "拟稿时间",
                        "采购方式",
                        "是否在经过公开评选年度供应商范围内组织的采购",
                        "采购分类",
                        "资金来源",
                        "最新到达采购实施部门时间",
                        "采购实施部门提交时间",
                        "当前状态",
                        "当前处理人",
                        "产品或服务类型"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
        };
    }

    @Override
    public void createCellItem(ShowTempData data, Row row, int i) {
        if(data == null)
            return;

        createCell(row, 0, data.getId());
        createCell(row, 1, data.getFlow_path());
        createCell(row, 2, data.getTitle());
        createCell(row, 3, data.getIscm_number());
        createCell(row, 4, data.getFlow_path_number());
        createCell(row, 5, data.getClean_advance());
        createCell(row, 6, data.getSupplier_code());
        createCell(row, 7, data.getSupplier_name());
        createCell(row, 8, data.getMaterial_code());
        createCell(row, 9, data.getMaterial_name());
        createCell(row, 10, data.getUnit_measurement());
        createCell(row, 11, data.getTransaction_price());
        createCell(row, 12, data.getTurnover());
        createCell(row, 13, data.getTrading_volume());
        createCell(row, 14, data.getDraft_unit());
        createCell(row, 15, data.getUndertake_man());
        createCell(row, 16, data.getDraft_department());
        createCell(row, 17, data.getPurchase_amount());
        createCell(row, 18, data.getDraft_time());
        createCell(row, 19, data.getProcurement_method());
        createCell(row, 20, data.getWhether_open_purchasing());
        createCell(row, 21, data.getProcurement_classification());
        createCell(row, 22, data.getCapital_source());
        createCell(row, 23, data.getNewest_time());
        createCell(row, 24, data.getProcurement_submit_time());
        createCell(row, 25, data.getCurrent_state());
        createCell(row, 26, data.getCurrent_handler());
        createCell(row, 27, data.getProduct_service_type());
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
