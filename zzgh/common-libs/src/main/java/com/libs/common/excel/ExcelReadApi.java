package com.libs.common.excel;


import com.libs.common.exception.BaseException;
import com.libs.common.type.TypeUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-01  14:41
 * --------------------------------------------------------- *
 */
public class ExcelReadApi {

    private int startRow = 0;  // 开始行, 默认不过滤为0
    private int startColumn = 0;   // 开始列
    private Map<Integer, ExcelType> map = new HashMap<>();
    private Map<Integer, CellFilter> mapCall = new HashMap<>();
    private String path;
    private InputStream inputStream;
    private String fileName;
    private List<Integer> filterRow = new ArrayList<>();

    private ExcelReadApi() {
    }

    private ExcelReadApi(int start) {
        this.startRow = start;
    }

    public static ExcelReadApi start(){
        ExcelReadApi excelApi = new ExcelReadApi();
        return excelApi;
    }

    public static ExcelReadApi start(int start){
        ExcelReadApi excelApi = new ExcelReadApi(start);
        return excelApi;
    }

    public ExcelReadApi setStartRow(int start) {
        this.startRow = start;
        return this;
    }

    public ExcelReadApi setStartColumn(int column) {
        this.startColumn = column;
        return this;
    }

    public ExcelReadApi fliterRow(Integer row){
        filterRow.add(row);
        return this;
    }

    public ExcelReadApi fliterRow(List<Integer> row){
        filterRow.addAll(row);
        return this;
    }

    public ExcelReadApi fliterRow(Integer[] row){
        for (Integer i : row)
            filterRow.add(i);
        return this;
    }

    public ExcelReadApi set(Integer i, ExcelType type){
        map.put(i, type);
        return this;
    }

    public ExcelReadApi setPath(String path){
        this.path = path;
        return this;
    }

    public ExcelReadApi setInputStream(InputStream inputStream){
        this.inputStream = inputStream;
        return this;
    }

    public ExcelReadApi setFileName(String fileName){
        this.fileName = fileName;
        return this;
    }

    public ExcelReadApi addCall(Integer i, CellFilter cellFilter){
        mapCall.put(i, cellFilter);
        return this;
    }

    private Object transfer(Cell cell, ExcelType type){
        if(cell == null)
            return "";
        switch (type){
            case Boolean:
                return ExcelUtils.getBoolean(cell);
            case Date:
                return ExcelUtils.getDate(cell);
            case Long:
                return ExcelUtils.getLong(cell);
            case Float:
                return ExcelUtils.getFloat(cell);
            case Double:
                return ExcelUtils.getDouble(cell);
            case String:
                return ExcelUtils.getString(cell);
            case Integer:
                return ExcelUtils.getInteger(cell);
            default:
                return ExcelUtils.getString(cell);
        }
    }

    public void read(ExcelReadSheet callBack){
        Workbook workbook = null;
        if(path != null){
            File file = new File(path);
            workbook =  ExcelUtils.getWorkbook(file);
        }else if(inputStream != null){
            workbook =  ExcelUtils.getWorkbook(inputStream, fileName);
        }else
            new BaseException("请设置Excel文件信息！");
        // 获取sheet 个数
        int sheetSize = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetSize; i++) {
            // 获取第i个sheet
            Sheet sheet = workbook.getSheetAt(i);
            List<List> sheetList = new ArrayList<>();
            // 获取行数
            int rowSize = sheet.getLastRowNum() + 1;
            for (int j = 0; j < rowSize; j++) {
                // 获取第j行数据
                Row row = sheet.getRow(j);

                if(row == null) continue;
                // 过滤隐藏行
                if(row.getZeroHeight() == true)
                {
                    System.out.println("row("+j+")是隐藏的");
                    continue ;
                }

                // 获取单元格个数
                int cellSize = row.getLastCellNum();
                // 行过滤
                if(j + 1 < startRow)
                    continue;
                // 行过滤
                if(filterRow.contains(j + 1))
                    continue;

                //获取指定单元格的对象引用
                List<Object> list = new ArrayList<>();

                // 判断是否为行
                Boolean isNull = false;
                for (int k = 0; k < cellSize; k++) {
                    // 列过滤
                    if(k + 1 < startColumn)
                        continue;
                    Cell cell = row.getCell(k);
                    if(cell != null && !TypeUtils.empty(cell.toString()))
                        isNull = true;

                    // 有回调，优先处理
                    if(mapCall.containsKey(k + 1)){
                        Object result = mapCall.get(k + 1).call(cell);
                        list.add(result);
                    }else{
                        if(map.containsKey(k + 1) && cell != null){
                            list.add(transfer(cell, map.get(k + 1)));
                        }else{  // 默认String处理
                            list.add(transfer(cell, ExcelType.String));
                        }
                    }
                }
                // 不为空行则添加
                if(isNull) {
                    sheetList.add(list);
                    callBack.row(list, j + 1);
                }
            }
            callBack.sheet(sheetList, i + 1);
        }
    }

    /**
     *
     * @param line 从1开始
     * @param callBack
     */
    public void readOneLine(Integer line, ExcelReadRow callBack){
        line--;
        Workbook workbook = null;
        if(path != null){
            File file = new File(path);
            workbook =  ExcelUtils.getWorkbook(file);
        }else if(inputStream != null){
            workbook =  ExcelUtils.getWorkbook(inputStream, fileName);
        }else
            new BaseException("请设置Excel文件信息！");

        // 默认读第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(line);
        int cellSize = row.getLastCellNum();

        // 获取指定单元格的对象引用
        List<Object> list = new ArrayList<>();

        for (int k = 0; k < cellSize; k++) {
            // 列过滤
            if(k + 1 < startColumn)
                continue;
            Cell cell = row.getCell(k);

            // 有回调，优先处理
            if(mapCall.containsKey(k + 1)){
                Object result = mapCall.get(k + 1).call(cell);
                list.add(result);
            }else{
                if(map.containsKey(k + 1) && cell != null){
                    list.add(transfer(cell, map.get(k + 1)));
                }else{  // 默认String处理
                    String value = "";
                    if (cell != null)
                        value = cell.toString();
                    list.add(value);
                }
            }
        }
        callBack.row(list, line);
    }

    //写入操作start
    public ExcelReadApi createSheet(String title){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(title);
        return this;
    }
    //写入操作end

    public static void write(String json){

    }

    public enum ExcelType{

        String("string"),
        Date("date"),
        Float("float"),
        Long("long"),
        Boolean("boolean"),
        Double("double"),
        Integer("integer");

        private String type;

        ExcelType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public interface CellFilter{

        Object call(Cell cell);

    }

}
