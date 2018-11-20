package com.gpdi.mdata.web.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import pub.functions.StrFuncs;
import pub.types.ValidationError;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author gan
 * @date 2018/4/19
 * @using
 */
public class ExcelUtil {

    public static List<List<String>> parse(InputStream file) throws ValidationError {
        OPCPackage pkg = null;
        InputStream sheetInputStream = null;

        try {
            pkg = OPCPackage.open(file);
            XSSFReader xssfReader = new XSSFReader(pkg);

            StylesTable styles = xssfReader.getStylesTable();//获取文件类型
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            sheetInputStream = xssfReader.getSheetsData().next();//读取数据

            return processSheet(styles, strings, sheetInputStream);
        } catch (InvalidFormatException | InvalidOperationException e) {
            throw new ValidationError("请导入2007+版本以上的Excel.");
        }catch (OpenXML4JException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            throw new ValidationError("读取失败,请重试.");
        } finally {
            if(sheetInputStream != null){
                try {
                    sheetInputStream.close();
                } catch (IOException ignored) {
                }
            }
            if(pkg != null){
                try {
                    pkg.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static List<List<String>> processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws SAXException, ParserConfigurationException, IOException{
        XMLReader sheetParser = SAXHelper.newXMLReader();

        SimpleSheetContentsHandler handler = new SimpleSheetContentsHandler();

        sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, handler, false));
        sheetParser.parse(new InputSource(sheetInputStream));
        List<List<String>> list =  new LinkedList<>();
        list.addAll(handler.rows);
        return list;
    }

    public static class SimpleSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
        private List<String> row = new LinkedList<>();
        protected List<List<String>> rows = new LinkedList<>();

        @Override
        public void startRow(int rowNum) {
            row.clear();
        }

        @Override
        public void endRow(int rowNum) {
            List<String> temp = new LinkedList<>();
            temp.addAll(row);
            rows.add(temp);
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            row.add(StrFuncs.trim(formattedValue));
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }
}
