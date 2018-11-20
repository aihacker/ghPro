package com.gpdi.mdata.web.reportform.exclude.dismantling.action;

import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.web.utils.ExcelSupportApi;
import com.gpdi.mdata.web.utils.ExcelType;
import com.gpdi.mdata.web.utils.JiebaUtil;
import org.apache.poi.ss.usermodel.Row;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/14 14:13
 * @modifier:
 */
public class DismantlingWriteApi extends ExcelSupportApi<DismantTemp> {

    public DismantlingWriteApi() {
        super( "拆单倾向分析采购合同表", ExcelType.XLS);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "序号", "合同名称对比","签订时间","合同金额", "采购方式",
                        "合同编号", "采购方案号", "合同类型","供应商","经办部门"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
        };
    }

    @Override
    public void createCellItem(DismantTemp data, Row row, int i) {
        if(data == null)
            return;

        createCell(row, 0, i + 1);
        createCell(row, 1, data.getColect().toString().replace("[","").replace("]","").replace(" ",""));
        createCell(row, 2, data.getPurTime().toString().replace("[","").replace("]"," ").replace(",",","));
        createCell(row, 3, data.getPurMoney().toString().replace("[","").replace("]"," ").replace(",",","));
        createCell(row, 4, data.getPurWay().toString().replace("[","").replace("]"," ").replace(",",","));
        createCell(row, 5, data.getPurCode().toString().replace("[","").replace("]"," ").replace(",",","));
        createCell(row, 6, data.getPurPlanCode().toString().replace("[","").replace("]"," ").replace(",",""));

        createCell(row, 7, data.getUndertakeDept());

        createCell(row, 8, data.getSupplierName());
        createCell(row, 9, data.getContractTypeName());
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
