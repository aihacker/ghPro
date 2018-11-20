package com.gpdi.mdata.web.utils;


import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pub.utils.support.ExcelRecordProcessor;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-5-4
 * Time: 20:52:20
 */
public class ExcelXUtils {

    public static List<Object[]> parse(InputStream is) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook(is);//创建一个工作薄
        XSSFSheet sheet = wb.getSheetAt(0);//获取第一个表格sheet
        XSSFRow row;//创建行
        XSSFCell cell;//创建列
        Iterator rows = sheet.rowIterator();//获取总行值
        List<Object[]> sheetData = new ArrayList<>();
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();//获取每一行
            List<Object> values = new ArrayList<>();
            Iterator cells = row.cellIterator();//获取总列
            int prevColIndex = -1;//当前指针位置
            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();//获取当前列值
                int colIndex = cell.getColumnIndex();//获取当前列数
                for(int tColIndex = prevColIndex + 1; tColIndex < colIndex; tColIndex++) {
                    values.add(null);//将每一列的值加到当前行中,
                }
                prevColIndex = colIndex;
                Object value = null;
                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    value = cell.getStringCellValue();
                }
                else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
//                    value = cell.getNumericCellValue();
                    value = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                else {
                    //U Can Handel Boolean, Formula, Errors
                        value = cell.getRawValue();
                }
                values.add(value);
            }
            sheetData.add(values.toArray(new Object[values.size()]));
        }
//        Closeable closeable = wb;
//        closeable.close();
        is.close();
        return sheetData;
    }

    public static void main(String[] args) {
        try {
            test();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test() throws Exception {
        FileInputStream fis = new FileInputStream("t:\\t6\\a.xls");
        List<Object[]> rows = null;
        try {
            rows = parse(fis);
        }
        finally {
            fis.close();
        }
        System.out.println("??");
    }

}
