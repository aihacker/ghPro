package wxgh.app.sys.excel.exam;

import com.libs.common.data.DateUtils;
import com.libs.common.excel.ExcelApi;
import com.libs.common.excel.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import wxgh.data.pcadmin.party.di.ExportExam;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/9/28.
 */
@Component
@Scope("prototype")
public class Exam2ExportApi extends ExcelApi<ExportExam> {

    public Exam2ExportApi() {
        super("考试记录", ExcelType.XLSX);
    }

    @Override
    public String[] head() {
        return new String[]{"序号", "姓名", "手机号码", "部门", "答题状态", "正确数", "错题数", "总题数", "得分", "报名时间", "所属支部", "考试名称"};
    }

    @Override
    public void createCellItem(ExportExam exportExam, Row row, int i) {

        if(exportExam == null)
            return;

        createCell(row, 0, i + 1);
        createCell(row, 1, exportExam.getUsername());
        createCell(row, 2, exportExam.getMobile());
        createCell(row, 3, exportExam.getDeptName());
        createCell(row, 4, exportExam.getStatus() == null ? "未报名" : (exportExam.getStatus() == 1 ? "已考试" : "未完成考试"));
        createCell(row, 5, exportExam.getRightTotal());
        createCell(row, 6, exportExam.getErrorTotal());
        createCell(row, 7, exportExam.getTotal());
        // 去掉被除数为零的情况
        Float score = null;
        if(!exportExam.getTotal().equals(0))
            score = 100f / (exportExam.getTotal()) * exportExam.getRightTotal();
        else
            score = 0f;
        BigDecimal b = new BigDecimal(score);
        createCell(row, 8, b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
        createCell(row, 9, DateUtils.formatDate(exportExam.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
        createCell(row, 10, exportExam.getGroupName());
        createCell(row, 11, exportExam.getName());
    }
}
