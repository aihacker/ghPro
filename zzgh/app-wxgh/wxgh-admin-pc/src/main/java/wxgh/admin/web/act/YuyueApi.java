package wxgh.admin.web.act;

import com.libs.common.data.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import pub.utils.ExcelUtils;
import wxgh.entity.entertain.act.ActJoin;

import java.util.List;

/**
 * @author hhl
 * @create 2017-09-15
 **/
public class YuyueApi {

    private HSSFWorkbook workbook;
    private HSSFCellStyle style;
    private DataValidationHelper helper;

    public static final String[] heads = {"姓名","联系电话","部门","添加时间","状态"};

    public YuyueApi(HSSFWorkbook workbook) {
        this.workbook = workbook;
        this.style = workbook.createCellStyle();
        style = ExcelUtils.getCellStyle(style);
    }

    public void toExcel(List<ActJoin> actList, String name) {
        HSSFSheet sheet = workbook.createSheet(name+"报名表");
        helper = sheet.getDataValidationHelper();
        writeHead(sheet);
        writeRow(sheet,actList);
//        addValidation(sheet, depts);
    }

    private void writeHead(HSSFSheet sheet) {
        //头
        HSSFRow headRow = sheet.createRow(0);
        for (int i = 0; i < heads.length; i++) {
            HSSFCell headCell = headRow.createCell(i);
            headCell.setCellStyle(style);
            headCell.setCellValue(heads[i]);
        }
        style.setFont(ExcelUtils.createDefaultFont(workbook));
    }

    private void writeRow(HSSFSheet sheet, List<ActJoin> actList){
        for(int i = 0;i<actList.size();i++){
            HSSFRow row = sheet.createRow(i+1);
            HSSFCell cell0 = row.createCell(0);
            if(actList.get(i).getName()!=null){
                cell0.setCellValue(actList.get(i).getName());
            }else{
                cell0.setCellValue("未填写");
            }

            HSSFCell cell1 = row.createCell(1);
            if(actList.get(i).getMobile()!=null){
                cell1.setCellValue(actList.get(i).getMobile());
            }else{
                cell1.setCellValue("未填写");
            }

            HSSFCell cell2 = row.createCell(2);
            if(actList.get(i).getDeptName()!=null){
                cell2.setCellValue(actList.get(i).getDeptName());
            }else{
                cell2.setCellValue("未填写");
            }

            HSSFCell cell3 = row.createCell(3);
            if(actList.get(i).getAddTime()!=null){
                cell3.setCellValue(DateUtils.toStr(actList.get(i).getAddTime(),"yyyy-MM-dd"));
            }else{
                cell3.setCellValue("未填写");
            }

            HSSFCell cell4 = row.createCell(4);
            if(actList.get(i).getType()!=null){
                if(actList.get(i).getType() == 1){
                    cell4.setCellValue("报名");
                }else{
                    cell4.setCellValue("请假");
                }
            }else{
                cell4.setCellValue("未填写");
            }

        }
    }
}
