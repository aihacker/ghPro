package pub.utils;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import pub.utils.support.ExcelRecordProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-5-4
 * Time: 20:52:20
 */
public class ExcelUtils {

	public static List<Object[]> parse(InputStream is) throws Exception {
		POIFSFileSystem poifs = new POIFSFileSystem(is);
		InputStream dis = poifs.createDocumentInputStream("Workbook");
		HSSFRequest req = new HSSFRequest();
		ExcelRecordProcessor processor = new ExcelRecordProcessor();
		req.addListenerForAllRecords(processor);
		HSSFEventFactory factory = new HSSFEventFactory();
		factory.processEvents(req, dis);
		dis.close();
		return processor.getResult();
	}
	
	/**
	 * 将excel文件转换成map键值对
	 * @param excelFile
	 * @param sheetnum
	 * @return
	 * @throws BusinessException
	 */
	public static Map<Integer,Map<String,Object>> transExcelFileTomap(Workbook wb ,int sheetnum, 
			Map<Integer,String> excelHead ){
		Map<Integer,Map<String,Object>> dataMap = null;
		try {
			 Sheet sheet = wb.getSheetAt(sheetnum);
			 dataMap = new HashMap<Integer,Map<String,Object>>();
			 int rounum = 0;
			 String headName = null;
			 Map<String,Object> rowMap;
			 for (Row row : sheet) {
				 if(rounum == 0) { //第一行为列头  忽略
					 rounum++;
					 continue;
				 }
				 int cellnum = 0;
				 rowMap = new HashMap<String,Object>();
				 for (Cell cell : row) {
					 cellnum =  cell.getColumnIndex();
					 headName = excelHead.get(Integer.valueOf(cellnum));
					 rowMap.put(headName, getCellValue(cell));
			      }
				 dataMap.put(Integer.valueOf(rounum), rowMap);
				 rounum++;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}
	
	/**
	 * 获取单元格的值
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Cell cell){
		Object cellValue = null;
		if(Cell.CELL_TYPE_NUMERIC == cell.getCellType() && DateUtil.isCellDateFormatted(cell)){ //日期
			cellValue = cell.getDateCellValue();
		}else if(Cell.CELL_TYPE_BOOLEAN == cell.getCellType()){ 
			 cellValue = cell.getBooleanCellValue();
		}else if(Cell.CELL_TYPE_FORMULA == cell.getCellType()){
			cellValue = cell.getCellFormula();
		}else {
			cell.setCellType (Cell.CELL_TYPE_STRING);
			cellValue = cell.toString();
		}
		return cellValue;
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
