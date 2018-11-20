package pub.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import pub.functions.StrFuncs;
import pub.models.listview.ListViewModel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 11-6-28
 */
public class ExportUtils {

    public static int ROW_LIMIT;

    public static void exportToExcel(HttpServletRequest request,
            HttpServletResponse response,
            ListViewModel model, String name) {
        try {
            Workbook workbook = createWorkbook(model, name);

            String attachmentName = name + ".xls";

            String ua = request.getHeader("User-Agent");
            boolean isChrome = (ua != null && ua.contains("Chrome"));

            try {
                if (isChrome) {
                    attachmentName = java.net.URLEncoder.encode(attachmentName, "UTF-8");
                }
                else {
                    attachmentName = new String(attachmentName.getBytes("GB2312"), "iso8859-1");
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            response.setHeader("Content-disposition", "attachment; filename=" + attachmentName);
            response.setContentType("application/msexcel");
            ServletOutputStream os = response.getOutputStream();
            workbook.write(os);
            os.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Workbook createWorkbook(ListViewModel model, String name) throws Exception {
        String sheetName = name;
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = null;
        int a = model.getRowCount();
        if (a > 65535) {
            int length = a / 65535;
            for (int i = 0; i <= length; i++) {
                if (sheet != null) {
                    sheet = null;
                }
                sheet = workbook.createSheet(sheetName + i);
                int colCount = model.getColCount();
                Row titleRow = sheet.createRow(0);
                for (int c = 0; c < colCount; c++) {
                    Cell cell = titleRow.createCell(c);
                    cell.setCellValue(model.getColTitle(c));
                }
                if ((i + 1) * 65535 < a) {
                    for (int r = i * 65535; r < (i + 1) * 65535; r++) {
                        Row row = sheet.createRow(r + 1);
                        for (int c = 0; c < colCount; c++) {
                            Cell cell = row.createCell(c);
                            Object value = model.getCellValue(r, c);
                            String text = StrFuncs.valueOf(value);
                            cell.setCellValue(text);
                        }
                    }
                }
                else {
                    for (int r = i * 65535; r < a; r++) {
                        int j = r - i * 65535;
                        Row row = sheet.createRow(j + 1);
                        for (int c = 0; c < colCount; c++) {
                            Cell cell = row.createCell(c);
                            Object value = model.getCellValue(r, c);
                            String text = StrFuncs.valueOf(value);
                            cell.setCellValue(text);
                        }
                    }
                }
            }
        }
        else {
            sheet = workbook.createSheet(name);
            int colCount = model.getColCount();
            Row titleRow = sheet.createRow(0);
            for (int c = 0; c < colCount; c++) {
                Cell cell = titleRow.createCell(c);
                cell.setCellValue(model.getColTitle(c));
            }
            for (int r = 0; r < model.getRowCount(); r++) {
                Row row = sheet.createRow(r + 1);
                for (int c = 0; c < colCount; c++) {
                    Cell cell = row.createCell(c);
                    Object value = model.getCellValue(r, c);
                    String text = StrFuncs.valueOf(value);
                    cell.setCellValue(text);
                }
            }
        }
        return workbook;
    }
}
