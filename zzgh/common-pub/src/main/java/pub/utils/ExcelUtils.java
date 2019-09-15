package pub.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @Author xlkai
 * @Version 2016/12/26
 */
public class ExcelUtils {

    public static Workbook getWorkBook(String filename, InputStream inputStream) throws IOException {
        if (filename.endsWith("xlsx")) {
            return new XSSFWorkbook(inputStream);
        }
        return new HSSFWorkbook(inputStream);
    }

    public static HSSFWorkbook createWorkBook(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        System.out.println("我输出了");
        response.setContentType("application/vnd.ms-excel");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");

        HSSFWorkbook workbook = new HSSFWorkbook();
        return workbook;
    }

    public static XSSFWorkbook createXWorkBook(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        return workbook;
    }

    /**
     * 创建基本样式
     *
     * @param style
     * @return
     */
    public static HSSFCellStyle getCellStyle(HSSFCellStyle style) {
        //设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // style.setWrapText(true); //自动换行
        return style;
    }

    public static XSSFCellStyle getCellStyle(XSSFCellStyle style) {
        //设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // style.setWrapText(true); //自动换行
        return style;
    }

    /**
     * 创建默认字体样式
     *
     * @param workbook
     * @return
     */
    public static XSSFFont createDefaultFont(XSSFWorkbook workbook) {
        //设置字体
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        return font;
    }

    public static HSSFFont createDefaultFont(HSSFWorkbook workbook) {
        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        return font;
    }

    /**
     * 创建excel表格头字体样式
     *
     * @param workbook
     * @return
     */
    public static HSSFFont createHeadFont(HSSFWorkbook workbook) {
        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        return font;
    }

    public static XSSFFont createHeadFont(XSSFWorkbook workbook) {
        //设置字体
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        return font;
    }

    public static Date getCellDateValue(Cell cell, String format) {
        Date date = null;
        try {
            date = cell.getDateCellValue();
        } catch (Exception e) {
            String dataStr = cell.getStringCellValue().trim();
            date = DateUtils.formatStr(dataStr, format == null ? "yyyy-MM-dd" : format);
        }
        return date;
    }

    public static Date getCellDateValue(Cell cell) {
        return getCellDateValue(cell, null);
    }

    public static <T> T getCellValue(Class<T> tClass, Cell cell) throws Exception {
        if (cell == null) {
            return null;
        }
        if (tClass == String.class) { //字符串类型
            String strVal = "";
            try {
                strVal = cell.getStringCellValue();
            } catch (Exception e) {
                try {
                    strVal = String.valueOf((long) cell.getNumericCellValue());
                } catch (Exception e1) {
                    strVal = String.valueOf(cell.getBooleanCellValue());
                }
            }
            return (T) (strVal.trim());
        } else if (tClass == Long.class) { //数字类型
            Long val = null;
            try {
                val = (long) cell.getNumericCellValue();
            } catch (Exception e) {
                val = Long.valueOf(cell.getStringCellValue());
            }
            return (T) val;
        } else if (tClass == Float.class) {
            Float val = null;
            try {
                val = (float) cell.getNumericCellValue();
            } catch (Exception e) {
                val = Float.valueOf(cell.getStringCellValue());
            }
            return (T) val;
        } else if (tClass == Double.class) {
            Double val = null;
            try {
                val = cell.getNumericCellValue();
            } catch (Exception e) {
                val = Double.valueOf(cell.getStringCellValue());
            }
            return (T) val;
        } else if (tClass == Boolean.class) { //boolean类型
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
            return (T) val;
        } else {
            throw new Exception("未知数据类型");
        }
    }
}
