package com.gpdi.mdata.web.reportform.exclude.source.proportion.action;

import com.gpdi.mdata.web.utils.ExcelSupportApi;
import com.gpdi.mdata.web.utils.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import pub.dao.query.QueryResult;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/14 17:47
 * @modifier:
 */
public class ProportionWriteApi extends ExcelSupportApi<TempData>{

    public ProportionWriteApi() {
        super( "各采购类型所占比例表", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "序号","采购方式",
                        "签订合同数量", "合同总数", "数量所占总比例","签订合同金额","合同总金额","金额所占总比例"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
        };
    }

    @Override
    public void createCellItem(TempData data, Row row, int i) {
        if(data == null)
            return;

        createCell(row, 0, i + 1);
        createCell(row, 1, data.getAa());
        createCell(row, 2, data.getBb().toString());
        createCell(row, 3, data.getCc());
        createCell(row, 4, data.getDd());
        createCell(row, 5, data.getEe());
        createCell(row, 6, data.getFf());
        createCell(row, 7, data.getGg());
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
