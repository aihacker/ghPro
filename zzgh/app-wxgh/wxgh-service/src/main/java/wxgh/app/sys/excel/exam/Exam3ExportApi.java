package wxgh.app.sys.excel.exam;


import com.libs.common.excel.ExcelApi;
import com.libs.common.excel.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import wxgh.data.pcadmin.party.di.ExportNoExam;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-11  14:58
 * --------------------------------------------------------- *
 */
@Component
@Scope("prototype")
public class Exam3ExportApi extends ExcelApi<ExportNoExam> {

    public Exam3ExportApi() {
        super("未考试名单", ExcelType.XLSX);
    }

    @Override
    public String[] head() {
        return new String[]{"序号", "支部名称", "姓名", "手机号"};
    }

    @Override
    public void createCellItem(ExportNoExam exportExam, Row row, int i) {
        if(exportExam == null)
            return;
        createCell(row, 0, i + 1);
        createCell(row, 1, exportExam.getBranchName());
        createCell(row, 2, exportExam.getUsername());
        createCell(row, 3, exportExam.getMobile());
    }

}
