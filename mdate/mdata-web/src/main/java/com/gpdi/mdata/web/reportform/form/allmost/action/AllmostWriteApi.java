package com.gpdi.mdata.web.reportform.form.allmost.action;

import com.gpdi.mdata.web.utils.ExcelSupportApi;
import com.gpdi.mdata.web.utils.ExcelType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/14 19:50
 * @modifier:
 */
public class AllmostWriteApi extends ExcelSupportApi<AllmostTempData> {

    public AllmostWriteApi() {
        super( "采购合同表", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "序号","供应商名称",
                        "签订合同数量","合同总金额 单位:元"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
        };
    }

    @Override
    public void createCellItem(AllmostTempData data, Row row, int i) {
        if(data == null)
            return;

        createCell(row, 0, i + 1);
        createCell(row, 1, data.getSupplier_name());
        createCell(row, 2, data.getCc());
        createCell(row, 3, data.getDd());
        createCell(row, 4, data.getContract_type_name());
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
