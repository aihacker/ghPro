package com.libs.common.excel;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/1.
 */
public class ExcelUtils {

    public static void removeRow(Sheet sheet, int rowIndex) {
        int num = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < num) {
            sheet.shiftRows(rowIndex + 1, num, -1);
        } else if (rowIndex == num) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) sheet.removeRow(row);
        }
    }

    public static Workbook getWorkbook(File file) {
        Workbook workbook = null;
        try {
            FileInputStream in = new FileInputStream(file);
            if (file.getName().endsWith(ExcelType.XLSX.getType())) {
                workbook = new XSSFWorkbook(in);
            } else {
                workbook = new HSSFWorkbook(in);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static Workbook getWorkbook(InputStream inputStream, String fileName) {
        Workbook workbook = null;
        try {
            if (fileName.endsWith(ExcelType.XLSX.getType())) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static Date getDate(Cell cell, String format) {
        if (cell == null) return null;
        Date date = null;
        try {
            date = cell.getDateCellValue();
        } catch (Exception e) {
            String dataStr = cell.getStringCellValue().trim();
            date = DateUtils.formatStr(dataStr, format == null ? "yyyy-MM-dd" : format);
        }
        return date;
    }

    public static Date getDate(Cell cell) {
        return getDate(cell, null);
    }

    public static String getString(Cell cell) {
        if (cell == null) return null;
        // 设置单元格为string类型
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String strVal;
        try {
            strVal = cell.getStringCellValue();
        } catch (Exception e) {
            try {
                strVal = String.valueOf(cell.getNumericCellValue());
            } catch (Exception e1) {
                strVal = String.valueOf(cell.getBooleanCellValue());
            }
        }
        return strVal.trim();
    }

    public static Double getDouble(Cell cell) {
        if (cell == null) return null;
        Double val;
        try {
            val = cell.getNumericCellValue();
        } catch (Exception e) {
            val = Double.valueOf(cell.getStringCellValue().trim());
        }
        return val;
    }

    public static Float getFloat(Cell cell) {
        if (cell == null) return null;
        Float val;
        try {
            val = (float) cell.getNumericCellValue();
        } catch (Exception e) {
            val = Float.valueOf(cell.getStringCellValue().trim());
        }
        return val;
    }

    public static Long getLong(Cell cell) {
        if (cell == null) return null;
        Long val;
        try {
            val = (long) cell.getNumericCellValue();
        } catch (Exception e) {
            val = Long.valueOf(cell.getStringCellValue());
        }
        return val;
    }

    public static Integer getInteger(Cell cell) {
        if (cell == null) return null;
        Integer val;
        try {
            val = (int) cell.getNumericCellValue();
        } catch (Exception e) {
            if (TypeUtils.empty(cell.getStringCellValue().trim()))
                val = 0;
            else
                val = Integer.valueOf(cell.getStringCellValue().trim());
        }
        return val;
    }

    public static Boolean getBoolean(Cell cell) {
        if (cell == null) return null;
        Boolean val;
        try {
            val = cell.getBooleanCellValue();
        } catch (Exception e) {
            double nmb = cell.getNumericCellValue();
            if (((int) nmb) == 1) {
                val = true;
            } else {
                val = false;
            }
        }
        return val;
    }

    /**
     * 添加类型验证
     *
     * @param helper
     * @param sheet
     * @param firstRow
     * @param col
     * @param tClass
     * @param <T>
     */
    public static <T> void addTypeValidation(DataValidationHelper helper, Sheet sheet, int firstRow, int col, Class<T> tClass) {
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, 1500, col, col);

    }

    public static <T> void addTypeValidation(Sheet sheet, int firstRow, int col, Class<T> tClass) {
        DataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        addTypeValidation(helper, sheet, firstRow, col, tClass);
    }

}
