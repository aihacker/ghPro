package wxgh.app.sys.excel.exam;


import com.libs.common.excel.ExcelApi;
import com.libs.common.excel.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import wxgh.data.party.di.exam.ExamExportResult;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-22  11:19
 * --------------------------------------------------------- *
 */
public class GuoqingExamExPortApi extends ExcelApi<ExamExportResult> {

    public GuoqingExamExPortApi() {
        super("国庆活动", ExcelType.XLSX);
    }

    @Override
    public String[] head() {
        return new String[]{
                "姓名", "部门", "区公司", "联系电话", "答对题数", "答错题数", "获奖等级"
        };
    }

    @Override
    protected float rowHeight() {
        return 1;
    }

    @Override
    public void createCellItem(ExamExportResult data, Row row, int i) {
        if(data == null)
            return;

        createCell(row, 0, data.getUsername());
        createCell(row, 1, data.getDeptName());
        createCell(row, 2, data.getCompanyName());
        createCell(row, 3, data.getMobile());
        createCell(row, 4, data.getRightTotal());
        createCell(row, 5, data.getErrorTotal());
        createCell(row, 6, prize(data.getRightTotal()));
    }

    private String prize(Integer numb){
        if(numb >= 10)
            return "一等奖";
        else if(numb >= 8)
            return "二等奖";
        return "参与奖";
    }

}
