package com.gpdi.mdata.web.utils;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16.
 */
public abstract class ExcelSupportApi<T> {

    protected String name; //Excel文件名称
    protected ExcelType type; //Excel类型

    protected Workbook workbook;
    protected CellStyle style;
    protected CellStyle headStyle;
    protected Sheet sheet;

    private Integer headSize = 1;

    protected T data;

    public ExcelSupportApi(String name, ExcelType type) {
        if (type.equals(ExcelType.XLS)) {
            workbook = new HSSFWorkbook();
        } else if (type.equals(ExcelType.XLSX)) {
            workbook = new XSSFWorkbook();
        }
        style = createStyle(workbook);

        headStyle = createStyle(workbook);
        headStyle.setFont(createHeadFont(workbook));

        sheet = createSheet(workbook);
        this.name = name;
        this.type = type;

        createHead(); //创建表头
    }

    public void toExcel(List<T> tList) {
        if (!TypeUtils.empty(tList)) {
            Font font = createFont(workbook);
            style.setFont(font);
            for (int i = 0, len = tList.size(); i < len; i++) {
                Row row = sheet.createRow(i + headSize);
                row.setHeightInPoints(rowHeight() * 20);
                createCellItem(tList.get(i), row, i);
            }
        } else {
            Row row = sheet.createRow(headSize);
            createCellItem(null, row, 1);
        }
    }

    public void toExcel(Map<String, T> tMap) {
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

    public abstract String[][] head();

    public abstract Integer[][] cellRange();

    public abstract void createCellItem(T t, Row row, int i);

    protected void createHead() {

        Integer[][] ranges = cellRange();

        String[][] heads = head();
        if (heads == null || heads.length <= 0) {
            return;
        }
        headSize = heads.length;
        int columMax = 0;
        for (int i = 0; i < headSize; i++) {
            int len = heads[i].length;
            if(len > columMax)
                columMax = len;
        }
        for (int i = 0; i < headSize; i++) {
            Row row = sheet.createRow(i);
            row.setHeightInPoints(30);
            int len = heads[i].length;
            for (int j = 0; j < columMax; j++) {
                Cell cell = row.createCell(j);
                if(j < len)
                    cell.setCellValue(heads[i][j]);
                cell.setCellStyle(headStyle);
            }
        }
        for (int i = 0; i < ranges.length; i++) {
            //在sheet里增加合并单元格
            CellRangeAddress cra = new CellRangeAddress(ranges[i][0], ranges[i][1], ranges[i][2], ranges[i][3]);
            sheet.addMergedRegion(cra);
        }
    }

    protected Sheet createSheet(Workbook wb) {
        Sheet sheet = wb.createSheet();
        sheet.setColumnWidth(0, columWidth() * 256);
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
        } else if (val instanceof Boolean) {
            cell.setCellValue((Boolean) val);
        } else if (val instanceof RichTextString) {
            cell.setCellValue((RichTextString) val);
        } else if (val instanceof Calendar) {
            cell.setCellValue((Calendar) val);
        } else {
            if(val == null) cell.setCellValue("");
            else cell.setCellValue(val.toString());
        }
        return cell;
    }

    protected Font createHeadFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
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
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);

        //居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

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
        return 40;
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
}
