package pub.spring.web.mvc.view;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractView;
import pub.dao.query.QueryResult;
import pub.functions.StrFuncs;
import pub.models.listview.MapBasedListViewModel;
import pub.types.Formatter;
import pub.types.support.BooleanFormatter;
import pub.types.support.DateFormatter;
import pub.types.support.DateTimeFormatter;
import pub.utils.ExportUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-4-30
 * Time: 22:16:12
 */
public class ExcelView extends AbstractView {

    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    private static final String EXTENSION = ".xls";
    public static final String rootFolder = "/WEB-INF/view";

    // 下载时显示的文件名属性
    public static final String OUTPUT_FILENAME = "OUTPUT_FILENAME";

    // 模板文件名属性，可不指定.xls后缀，如果与当前action路径配对，不需要写路径；
    // 支持用相对路径表示子路径，全路径必须首字符以'/'开始，表示相对于/WEB-INF/view的文件。
    public static final String TEMPLATE_FILENAME = "TEMPLATE_FILENAME";

    public ExcelView() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected final void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        if (request.getParameter("expCols") != null) {
            exportWithoutTemplate(model, request, response);
            return;
        }

        prepareResponseEx(model, request, response);

        String templateFileName = (String) model.get(TEMPLATE_FILENAME);
        String url;
        String uri = request.getServletPath();
        if (StrFuncs.isEmpty(templateFileName)) {
            int dotPos = uri.lastIndexOf('.');
            uri = uri.substring(0, dotPos);
            url = rootFolder + uri + EXTENSION;
        }
        else {
            if (!templateFileName.endsWith(EXTENSION)) {
                templateFileName += EXTENSION;
            }
            if (templateFileName.startsWith("/")) {
                url = rootFolder + templateFileName;
            }
            else {
                templateFileName = "/" + templateFileName;
                int slashPos = uri.lastIndexOf('/');
                uri = uri.substring(0, slashPos);
                url = rootFolder + uri + templateFileName;
            }
        }

        HSSFWorkbook workbook = getTemplateSource(url, request);

        //buildExcelDocument(model, workbook, request, response);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformWorkbook(workbook, model);

        // Set the content type.
        response.setContentType(getContentType());

        // Should we set the content length here?
        // response.setContentLength(workbook.getBytes().length);

        // Flush byte array to servlet output stream.
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }

    protected HSSFWorkbook getTemplateSource(String url, HttpServletRequest request) throws
            Exception {
        Resource inputFile = getApplicationContext().getResource(url);

        // Create the Excel document from the source.
        if (logger.isDebugEnabled()) {
            logger.debug("Loading Excel workbook from " + inputFile);
        }
        POIFSFileSystem fs = new POIFSFileSystem(inputFile.getInputStream());
        return new HSSFWorkbook(fs);
    }

    private void prepareResponseEx(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) {

//        String attachmentName = (String) model.get(OUTPUT_FILENAME);
    	 String attachmentName = request.getParameter("OUTPUT_FILENAME");
         if (attachmentName == null) {
             attachmentName  = (String) model.get(OUTPUT_FILENAME);
         }
        if (attachmentName == null) {
            String name = request.getServletPath();
            name = name.substring(name.lastIndexOf('/') + 1);
            name = name.substring(0, name.lastIndexOf('.')) + EXTENSION;
            attachmentName = name;
        }
        else if (!attachmentName.endsWith(EXTENSION)) {
            attachmentName += EXTENSION;
        }

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

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-disposition", "attachment; filename=" + attachmentName);
    }

    @SuppressWarnings("unchecked")
    private void exportWithoutTemplate(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) {

        String sExpCols = request.getParameter("expCols");
        String[] expCols = sExpCols.split("!");

        List<String> colNames = new ArrayList<>();
        List<String> colTitles = new ArrayList<>();
        Map<String, Formatter> formatterMap = new HashMap<>();

        for (int n = 0; n < expCols.length; n++) {
            String expCol = expCols[n];
            String[] parts = expCol.split("-");
            String colName = parts[0];
            String colTitle = parts[1];

            colNames.add(colName);
            colTitles.add(colTitle);

            if(parts.length > 2) {
                String format = parts[2];
                Formatter formatter = buildFormatter(format);
                formatterMap.put(colName, formatter);
            }
        }

        QueryResult<Map<String, Object>> queryResult =
                (QueryResult<Map<String, Object>>) model.get("queryResult");

        MapBasedListViewModel lvm = new MapBasedListViewModel();
        lvm.setRows(queryResult.getRows());
        lvm.setColNames(colNames);
        lvm.setColTitles(colTitles);
        lvm.setFormatters(formatterMap);

        String title = request.getParameter("_title");
        if (title == null) {
            title = "查询结果";
        }
        ExportUtils.exportToExcel(request, response, lvm, title);
    }

    private Formatter buildFormatter(final String type) {
        if("boolean".equals(type)) {
            return BooleanFormatter.instance;
        }
        if("useState".equals(type)) {
            return new BooleanFormatter("启用", "停用");
        }
        if("auditState".equals(type)){
        	 return new BooleanFormatter("已审核", "新建");
        }
        if("booleanSex".equals(type)){
        	 return new BooleanFormatter("男", "女");
        }
        if("date".equals(type)) {
            return new DateFormatter();
        }
        if("datetime".equals(type)) {
            return new DateTimeFormatter();
        }
        throw new RuntimeException("unknown type: " + type);
    }

}
