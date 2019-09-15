package com.libs.common.excel;


import com.libs.common.type.TypeUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017/6/16.
 */
public abstract class ExcelApi<T> {

    protected String name; //Excel文件名称
    protected ExcelType type; //Excel类型

    protected Workbook workbook;
    protected CellStyle style;
    protected CellStyle headStyle;

    protected T data;

    private Sheet hiddenSheet;
    private Map<String, DataValidationHelper> validationHelperMap;

    public ExcelApi() {
        this("import_result", ExcelType.XLSX);
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExcelApi(String name, ExcelType type) {
        if (type.equals(ExcelType.XLS)) {
            workbook = new HSSFWorkbook();
        } else if (type.equals(ExcelType.XLSX)) {
            workbook = new XSSFWorkbook();
        }
        style = createStyle(workbook);

        headStyle = createStyle(workbook);
        headStyle.setFont(createHeadFont(workbook));

        this.name = name;
        this.type = type;
    }

    public void toExcel(List<T> tList, String sheetName) {
        Sheet sheet = createSheet(workbook, sheetName);
        int startRow = createHead(sheet); //创建表头
        if (!TypeUtils.empty(tList)) {
            Font font = createFont(workbook);
            style.setFont(font);
            for (int i = 0, len = tList.size(); i < len; i++) {
                Row row = sheet.createRow(i + startRow);
                row.setHeightInPoints(rowHeight());
                createCellItem(tList.get(i), row, i);
            }
        } else {
            Row row = sheet.createRow(startRow);
            createCellItem(getTemplate(), row, 1);
        }
        addValidation(sheet);
    }

    public void toExcel(List<T> tList) {
        toExcel(tList, null);
    }

    public T getTemplate() {
        return null;
    }

    public void toExcel(Map<String, List<T>> tMap) {
        for (Map.Entry<String, List<T>> entry : tMap.entrySet()) {
            toExcel(entry.getValue(), processSheetName(entry.getKey()));
        }
    }

    /**
     * 处理sheet名称
     *
     * @param name
     * @return
     */
    protected String processSheetName(String name) {
        return name;
    }

    public void response(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            String prefix = type.equals(ExcelType.XLS) ? ".xls" : ".xlsx";
            String filename = URLEncoder.encode(name, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition", "attachment;filename=" + filename + prefix);

            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public abstract String[] head();

    public abstract void createCellItem(T t, Row row, int i);

    public void addValidation(Sheet sheet) {
    }

    protected int createHead(Sheet sheet) {
        String[] heads = head();
        if (heads == null || heads.length <= 0) {
            return 0;
        }
        Row row = sheet.createRow(0);
        row.setHeightInPoints(rowHeight());
        for (int i = 0; i < heads.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(heads[i]);
            cell.setCellStyle(headStyle);
        }
        return 1;
    }

    protected Sheet createSheet(Workbook wb) {
        return createSheet(wb, null);
    }

    protected Sheet createSheet(Workbook wb, String name) {
        Sheet sheet;
        if (!TypeUtils.empty(name)) {
            name = ExcelCharFilter.replace(name);
            sheet = wb.createSheet(name);
        } else {
            sheet = wb.createSheet();
        }
        sheet.setDefaultColumnWidth(columWidth());
        return sheet;
    }

    protected Cell createCell(Row row, int column, Object val) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(style);
        if (val instanceof Double) {
            cell.setCellValue((Double) val);
        } else if (val instanceof Integer) {
            cell.setCellValue(((Integer) val).doubleValue());
        } else if (val instanceof Float) {
            cell.setCellValue(((Float) val).doubleValue());
        } else if (val instanceof Date) {
            cell.setCellValue((Date) val);
        } else if (val instanceof Timestamp) {
            cell.setCellValue((Timestamp) val);
        } else if (val instanceof Boolean) {
            cell.setCellValue((Boolean) val);
        } else if (val instanceof RichTextString) {
            cell.setCellValue((RichTextString) val);
        } else if (val instanceof Calendar) {
            cell.setCellValue((Calendar) val);
        } else {
            if (val == null) cell.setCellValue("");
            else cell.setCellValue(val.toString());
        }
        return cell;
    }

    protected Font createHeadFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        return font;
    }

    protected Font createFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        return font;
    }

    protected CellStyle createStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        //设置边框
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        //居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //自动换行
//        style.setWrapText(true);
        return style;
    }

    protected short getFormat(String format) {
        DataFormat dataFormat = workbook.createDataFormat();
        return dataFormat.getFormat(format);
    }

    protected short getFormat() {
        return getFormat("yyyy-MM-dd");
    }

    protected int columWidth() {
        return 30;
    }

    protected float rowHeight() {
        return 30f;
    }

    public RichTextString getRichText(String text) {
        RichTextString richTxt = null;
        if (type.equals(ExcelType.XLS)) {
            richTxt = new HSSFRichTextString(text);
        } else if (type.equals(ExcelType.XLSX)) {
            richTxt = new XSSFRichTextString(text);
        }
        return richTxt;
    }

    public String getName() {
        return name;
    }

    public ExcelType getType() {
        return type;
    }

    public CellStyle getStyle() {
        return style;
    }

    protected void addSelectValidation(Sheet sheet, String[] strs, int firstRow, int lastRow, int col) {
        if (hiddenSheet == null) {
            hiddenSheet = workbook.createSheet("hidden");
            workbook.setSheetHidden(1, true);
        }
        DataValidationHelper helper = getValidationHelper(sheet);

        for (int i = 0, len = strs.length; i < len; i++) {
            Row row = hiddenSheet.getRow(i);
            if (row == null) {
                row = hiddenSheet.createRow(i);
                // 设置为隐藏行
                row.setZeroHeight(true);
            }
            Cell cell = row.createCell(col);
            cell.setCellValue(strs[i]);
        }
        Name nameCell = workbook.createName();
        nameCell.setNameName("hidden_" + col);
        String chr = TypeUtils.getNameByIndex(col);
        String formu = "hidden!$" + chr + "$1:$" + chr + "$" + strs.length;

        nameCell.setRefersToFormula(formu);

        DataValidationConstraint constraint = helper.createFormulaListConstraint("hidden_" + col);
        createValidation(constraint, helper, sheet, firstRow, lastRow, col);
    }

    protected void addSelectValidation(Sheet sheet, String[] strs, int firstRow, int col) {
        addSelectValidation(sheet, strs, firstRow, 1500, col);
    }

    protected void addSelectValidationNoHidden(Sheet sheet, String[] strs, int firstRow, int lastRow, int col) {

        if(strs != null && strs.length > 0){
            DataValidationHelper helper = getValidationHelper(sheet);
            DataValidationConstraint constraint = helper.createExplicitListConstraint(strs);
            createValidation(constraint, helper, sheet, firstRow, lastRow, col);
        }

    }

    protected <D> void addTypeValidation(Sheet sheet, int firstRow, int col, Class<D> tClass) {
        addTypeValidation(sheet, firstRow, 1500, col, tClass);
    }

    protected <D> void addTypeValidation(Sheet sheet, int firstRow, int lastRow, int col, Class<D> tClass) {
        DataValidationHelper helper = getValidationHelper(sheet);

        DataValidationConstraint constraint = null;
        if (tClass == Date.class) {
            constraint = helper.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN, "1900-01-01", "2100-01-01", "yyyy-MM-dd");
        } else if (tClass == Integer.class) {
            constraint = helper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, "10000000000", "200000000000");
        } else if (tClass == Number.class) {
            constraint = helper.createNumericConstraint(DataValidationConstraint.ValidationType.INTEGER, DataValidationConstraint.OperatorType.BETWEEN, String.valueOf(Integer.MIN_VALUE), String.valueOf(Integer.MAX_VALUE));
        } else if (tClass == Double.class || tClass == Float.class) {
            constraint = helper.createDecimalConstraint(DataValidationConstraint.OperatorType.BETWEEN, String.valueOf(Double.MIN_VALUE), String.valueOf(Double.MAX_VALUE));
        }
        createValidation(constraint, helper, sheet, firstRow, lastRow, col);
    }

    private DataValidationHelper getValidationHelper(Sheet sheet) {
        if (validationHelperMap == null) {
            validationHelperMap = new HashMap<>();
        }
        DataValidationHelper helper = validationHelperMap.get(sheet.getSheetName());
        if (helper == null) {
            helper = new XSSFDataValidationHelper((XSSFSheet) sheet);
            validationHelperMap.put(sheet.getSheetName(), helper);
        }
        return helper;
    }

    private void createValidation(DataValidationConstraint constraint, DataValidationHelper helper, Sheet sheet, int firstRow, int lastRow, int col) {
        CellRangeAddressList addressList1 = new CellRangeAddressList(firstRow, lastRow, col, col);

        DataValidation dataValidation = helper.createValidation(constraint, addressList1);
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }
}
