package wxgh.app.sys.excel.four;


import com.libs.common.excel.ExcelSupportApi;
import com.libs.common.excel.ExcelType;
import com.libs.common.type.TypeUtils;
import org.apache.poi.ss.usermodel.Row;
import pub.utils.DateUtils;
import wxgh.data.four.excel.FourDetailExcelData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-13  10:21
 * --------------------------------------------------------- *
 */
public class FourWriteApi extends ExcelSupportApi<FourDetailExcelData> {

    private static String company;

    public static void setCompany(String _company) {
        company = _company;
    }

    public FourWriteApi() {
        super(company + "“四小”台账明细表", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        String year =  DateUtils.formatDate(new Date(), "yyyy");
        return new String[][]{
                {
                    year + "年“四小”台账明细表"
                },
                {
                    "分公司", company
                },
                {
                        "序号", "市公司/区公司","营销中心", "四小项目", "设施项目内容",
                        "品牌", "规格型号", "采购日期", "数量", "单位",
                        "设备情况", "备注说明", "资产所属", "计划更新时间",
                        "计划使用年限", "维修次数", "资金来源", "预算单价",
                        "维修记录","是否导入图片","设备安放/安装地址"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
                {0, 0, 0, 20},
                {1, 1, 1, 3},
                {1, 1, 4, 20}
        };
    }

    @Override
    public void createCellItem(FourDetailExcelData data, Row row, int i) {

        if(data == null)
            return;

        createCell(row, 0, i + 1);
        createCell(row, 1, data.getCompanyName().indexOf("区")!=-1?data.getCompanyName()+"分公司":"佛山市公司本部");
        createCell(row, 2, data.getDeptName());
        createCell(row, 3, data.getProjectName());
        createCell(row, 4, data.getProjectContent());
        createCell(row, 5, data.getBrand());
        createCell(row, 6, data.getModelName());

//        style.setDataFormat(getFormat("yyyy-MM-dd"));
        createCell(row, 7, data.getBuyTime() == null ? "未知时间" : new SimpleDateFormat("yyyy年MM月dd日").format(data.getBuyTime()));

        createCell(row, 8, data.getNumb());
        createCell(row, 9, data.getUnit());
        createCell(row, 10, data.getCondit());
        createCell(row, 11, data.getRemark());
        createCell(row, 12, data.getCondStr());

        createCell(row, 13, data.getPlanUpdate() == null ? "未知时间" : new SimpleDateFormat("yyyy年MM月dd日").format(data.getPlanUpdate()));
        createCell(row, 14, data.getUsefulLife());
        createCell(row, 15, formatRepair(data.getRepairCount()));
        createCell(row, 16, data.getPriceSource());
        createCell(row, 17, Double.valueOf(data.getPrice()));
        createCell(row, 18, TypeUtils.empty(data.getRepairString()) ? "无" : data.getRepairString());
        createCell(row, 19, TypeUtils.empty(data.getThumb()) ? "未导入" : "已导入");
        createCell(row, 20, data.getMachineLocation());

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
