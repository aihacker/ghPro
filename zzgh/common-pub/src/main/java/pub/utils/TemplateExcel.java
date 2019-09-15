package pub.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public class TemplateExcel {

    private HSSFWorkbook workbook;
    private HSSFCellStyle style;
    private DataValidationHelper helper;

    public static final String[] heads = {"党支部", "姓名", "性别", "民族", "籍贯",
            "出生年月", "学历", "党内职务", "参加工作时间", "入党时间", "转正时间",
            "正式/预备", "是否本年度新调进党员", "户籍所在地", "身份证号", "联系电话",
            "备注", "区内党支部"};

    //民族
    public static final String[] nations = {"汉族", "壮族", "满族", "回族", "苗族",
            "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族",
            "朝鲜族", "白族", "哈尼族", "哈萨克族", "黎族", "傣族", "畲族", "傈僳族",
            "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族",
            "土族", "仫佬族", "锡伯族", "柯尔克孜族", "达斡尔族", "景颇族", "毛南族",
            "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族",
            "京族", "基诺族", "德昂族", "保安族", "俄罗斯族", "裕固族", "乌兹别克族",
            "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族"};

    //学历
    public static final String[] xueli = {"初中", "高中", "大专", "本科", "硕士", "博士"};

    public TemplateExcel(HSSFWorkbook workbook) {
        this.workbook = workbook;
        this.style = workbook.createCellStyle();
        style = ExcelUtils.getCellStyle(style);
    }

    public void toExcel(List<String> depts) {
        HSSFSheet sheet = workbook.createSheet("党员列表");
        helper = sheet.getDataValidationHelper();
        writeHead(sheet);
        addValidation(sheet, depts);
    }

    private void addValidation(HSSFSheet sheet, List<String> depts) {
        //党支部
        addSelectValidation(sheet, TypeUtils.listToArray(depts), 1, 0);

        //性别
        addSelectValidation(sheet, new String[]{"男", "女"}, 1, 2);

        //民族
        addSelectValidation(sheet, nations, 1, 3);

        //出生年月
        addTypeValidation(sheet, 1, 5, Date.class);

        //参加工作时间
        addTypeValidation(sheet, 1, 8, Date.class);

        //入党时间
        addTypeValidation(sheet, 1, 9, Date.class);

        //转正时间
        addTypeValidation(sheet, 1, 10, Date.class);

        //学历
        addSelectValidation(sheet, xueli, 1, 6);

        //正式/预备
        addSelectValidation(sheet, new String[]{"正式党员", "预备党员"}, 1, 11);

        //是否新调度党员
        addSelectValidation(sheet, new String[]{"是", "否"}, 1, 12);

        //联系电话
        addTypeValidation(sheet, 1, 15, Integer.class);
    }

    private void addSelectValidation(HSSFSheet sheet, String[] strs, int firstRow, int col) {
        CellRangeAddressList addressList1 = new CellRangeAddressList(firstRow, 1000, col, col);
        DataValidationConstraint constraint1 = helper.createExplicitListConstraint(strs);
        DataValidation dataValidation = helper.createValidation(constraint1, addressList1);
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    private <T> void addTypeValidation(HSSFSheet sheet, int firstRow, int col, Class<T> tClass) {
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, 1000, col, col);
        DataValidationConstraint constraint = null;
        if (tClass == Date.class) {
            constraint = helper.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN, "1900-01-01", "2100-01-01", "yyyy-MM-dd");
        } else if (tClass == Integer.class) {
            constraint = helper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, "10000000000", "200000000000");
        }
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        sheet.addValidationData(dataValidation);
    }

    private void writeHead(HSSFSheet sheet) {
        //头
        HSSFRow headRow = sheet.createRow(0);
        style.setFont(ExcelUtils.createHeadFont(workbook));
        for (int i = 0; i < heads.length; i++) {
            HSSFCell headCell = headRow.createCell(i);
            headCell.setCellStyle(style);
            headCell.setCellValue(heads[i]);
        }
        style.setFont(ExcelUtils.createDefaultFont(workbook));
    }

}
