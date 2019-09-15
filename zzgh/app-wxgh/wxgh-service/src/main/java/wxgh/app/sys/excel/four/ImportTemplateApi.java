package wxgh.app.sys.excel.four;

import com.libs.common.data.DateUtils;
import com.libs.common.excel.ExcelApi;
import com.libs.common.excel.ExcelType;
import com.libs.common.type.TypeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import wxgh.data.four.excel.FourDetailExcelData;

import java.util.Date;
import java.util.List;

public class ImportTemplateApi extends ExcelApi<FourDetailExcelData> {

    //设备情况
    public static final String[] conditions = {"良好", "可以使用", "需要更换"};

    //资产所属
    public static final String[] ZCS = {"工会", "企业"};

    //资金来源
    public static final String[] LYS = {"福利费", "工会经费", "资本投资"};

    private List<String> marketings;
    private List<String> depts;
    private List<String> projects;

    public ImportTemplateApi(List<String> marketings, List<String> depts, List<String> projects) {
        super("四小台账导入模版", ExcelType.XLSX);
        this.marketings = marketings;
        this.depts = depts;
        this.projects = projects;
    }

    @Override
    public String[] head() {
        return new String[]{"序号", "营销中心", "四小项目", "设施项目内容", "品牌", "规格型号", "采购日期", "数量", "单位", "设备情况", "备注说明", "资产所属", "计划更新时间", "计划使用年限", "维修次数", "资金来源", "预算单价", "维修记录"};
    }

    @Override
    protected int createHead(Sheet sheet) {
        String[] heads = head();

        //标题栏
        Row row1 = sheet.createRow(0);
        row1.setHeightInPoints(rowHeight());
        Cell headCell = row1.createCell(0);
        headCell.setCellValue("“四小”台账明细表");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, heads.length - 1));
        headCell.setCellStyle(headStyle);

        //分公司名称
        Row row2 = sheet.createRow(1);
        row2.setHeightInPoints(rowHeight());
        Cell compCell = row2.createCell(0);
        compCell.setCellValue("分公司名称");

        createCell(row2, 1, "本部");
        createCell(row2, 2, "");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
        compCell.setCellStyle(headStyle);

        Row row = sheet.createRow(2);
        row.setHeightInPoints(30);
        for (int i = 0; i < heads.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(heads[i]);
            cell.setCellStyle(headStyle);
        }
        return 3;
    }

    @Override
    public FourDetailExcelData getTemplate() {
        FourDetailExcelData data = new FourDetailExcelData();
        data.setId(1);
        data.setDeptName("佛山分公司本部");
        data.setProjectName("小浴室");
        data.setProjectContent("冰箱");
        data.setBrand("海尔");
        data.setModelName("BCD176DA");
        data.setBuyTime(new Date());
        data.setNumb(1);
        data.setUnit("台");
        data.setCondit("良好");
        data.setRemark("");
        data.setCondStr("工会");
        data.setPlanUpdate(new Date());
        data.setUsefulLife(10);
        data.setRepairCount(0);
        data.setPriceSource("工会经费");
        data.setPrice(2000d);
        data.setRepairString("无");
        return data;
    }

    @Override
    public void createCellItem(FourDetailExcelData data, Row row, int i) {
        row.createCell(0).setCellValue(data.getId());
        row.createCell(1).setCellValue(data.getDeptName());
        row.createCell(2).setCellValue(data.getProjectName());
        row.createCell(3).setCellValue(data.getProjectContent());
        row.createCell(4).setCellValue(data.getBrand());
        row.createCell(5).setCellValue(data.getModelName());
        row.createCell(6).setCellValue(DateUtils.toStr(data.getBuyTime(), "yyyy-MM-dd"));
        row.createCell(7).setCellValue(data.getNumb());
        row.createCell(8).setCellValue(data.getUnit());
        row.createCell(9).setCellValue(data.getCondit());
        row.createCell(10).setCellValue(data.getRemark());
        row.createCell(11).setCellValue(data.getCondStr());
        row.createCell(12).setCellValue(DateUtils.toStr(data.getPlanUpdate(), "yyyy-MM-dd"));
        row.createCell(13).setCellValue(data.getUsefulLife());
        row.createCell(14).setCellValue(data.getRepairCount());
        row.createCell(15).setCellValue(data.getPriceSource());
        row.createCell(16).setCellValue(data.getPrice());
        row.createCell(17).setCellValue(data.getRepairString());
    }

    @Override
    public void addValidation(Sheet sheet) {
        //部门
        addSelectValidationNoHidden(sheet, TypeUtils.listToArray(depts), 1, 1, 1);

        //营销中心
        addSelectValidation(sheet, TypeUtils.listToArray(marketings), 3, 1);

        //四小项目
        addSelectValidation(sheet, TypeUtils.listToArray(projects), 3, 2);

        //设备情况
        addSelectValidation(sheet, conditions, 3, 9);

        //资产所属
        addSelectValidation(sheet, ZCS, 3, 11);

        //资金来源
        addSelectValidation(sheet, LYS, 3, 15);

        //序号
        addTypeValidation(sheet, 3, 0, Number.class);

        // 2017-10-23 时间单元格 修改 Date.class  -> Number.class

        //采购日期
        addTypeValidation(sheet, 3, 6, Number.class);
        //数量
        addTypeValidation(sheet, 3, 7, Number.class);
        //计划更新时间
        addTypeValidation(sheet, 3, 12, Number.class);
        //计划使用年限
        addTypeValidation(sheet, 3, 13, Number.class);
        //维修次数
        addTypeValidation(sheet, 3, 14, Number.class);
        //预算单价
        addTypeValidation(sheet, 3, 16, Double.class);
    }
}
