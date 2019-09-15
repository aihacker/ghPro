package wxgh.app.sys.excel.exam;


import com.libs.common.excel.ExcelApi;
import com.libs.common.excel.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import wxgh.data.pcadmin.party.di.ExportBranchExam;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-18  10:58
 * --------------------------------------------------------- *
 */
@Component
@Scope("prototype")
public class Exam4ExportApi extends ExcelApi<ExportBranchExam> {

    public Exam4ExportApi() {
        super("支部考试率排名", ExcelType.XLSX);
    }

    @Override
    public String[] head() {
        return new String[]{"序号", "支部名称", "完成考试人次", "总考试人次", "未考试人次", "考试率"};
    }

    @Override
    public void createCellItem(ExportBranchExam exportExam, Row row, int i) {
        if(exportExam == null)
            return;
        createCell(row, 0, i + 1);
        createCell(row, 1, exportExam.getName());
        createCell(row, 2, exportExam.getFinishExamCount());
        createCell(row, 3, exportExam.getUserCount());
        createCell(row, 4, exportExam.getNotJoinExamCount());
        createCell(row, 5, exportExam.getRate());
    }

}
