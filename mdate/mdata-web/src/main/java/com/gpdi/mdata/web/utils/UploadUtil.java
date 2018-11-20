package com.gpdi.mdata.web.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:识别excel文档
 * @author: WangXiaoGang
 * @data: Created in 2018/7/20 14:33
 * @modifier:
 */
public class UploadUtil {
    public static List<List<String>> parse(InputStream is, String fileName) throws Exception {
        Workbook wb = null;//创建一个工作薄
        if (fileName.toLowerCase().endsWith("xls")) {
            wb = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);//获取工作薄中第一张sheet表
        int rowNum = sheet.getLastRowNum() + 1;//获取总行数
        int rowStart = Math.min(1, sheet.getFirstRowNum());//开始行
        //int rowEnd = Math.max(60, sheet.getLastRowNum());//结束行
        List<List<String>> sheetData = new ArrayList<>();
        for (int i = 1; i <rowNum; i++) {
            Row row = sheet.getRow(i);//获取一行
            int cellNum = row.getLastCellNum();//获取总列数
            List<String> values = new ArrayList<>();
            int prevColIndex = -1;//当前指针位置
            for (int j = 0; j < cellNum; j++) {
                String cellValue = null;
                Cell cell = null;
                if (row.getCell(j) ==null){
                    cellValue="";
                    for (int tColIndex = prevColIndex + 1; tColIndex < j; tColIndex++) {
                        values.add(null);
                    }
                    prevColIndex = j;
                }else {
                    cell = row.getCell(j);
                    int colIndex = cell.getColumnIndex();//获取当前列数
                    for (int tColIndex = prevColIndex + 1; tColIndex < colIndex; tColIndex++) {
                        values.add(null);//将每一列的值加到当前行中,
                    }
                    prevColIndex = colIndex;

                    // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                    switch (cell.getCellType()) {
                        case 0:
                            String s = String.valueOf(cell.getNumericCellValue());
                            if (s.endsWith(".0")) {
                                cellValue = String.valueOf((int) cell.getNumericCellValue());//整数类型
                                break;
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());//数字类型
                                break;
                            }
                        case 1:
                            cellValue = cell.getStringCellValue();//字符串类型
                            break;
                        case 2:
                            cellValue = String.valueOf(cell.getDateCellValue());//日期类型
                            break;
                        case 3:
                            cellValue = "";//空格
                            break;
                        case 4:
                            cellValue = String.valueOf(cell.getBooleanCellValue());//boolean类型
                            break;
                        case 5:
                            cellValue = String.valueOf(cell.getErrorCellValue());//错误类型
                            break;
                        default:
                            cellValue = cell.getStringCellValue();
                            break;
                    }
                }
                values.add(cellValue);
            }
            //sheetData.add(values.toArray(new String[values.size()]));
            sheetData.add(values);
        }
        is.close();
        return sheetData;
    }


}