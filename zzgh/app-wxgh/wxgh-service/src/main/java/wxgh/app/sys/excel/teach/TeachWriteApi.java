package wxgh.app.sys.excel.teach;

import com.libs.common.excel.ExcelSupportApi;
import com.libs.common.excel.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import wxgh.data.entertain.act.JoinList;

public class TeachWriteApi extends ExcelSupportApi<JoinList> {

    public TeachWriteApi() {
        super( "女子课堂报名人员信息", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "女子课堂报名信息"
                },
                {
                       "导出","女子课堂报名信息"
                },
                {
                        "报名成员姓名","手机号","所属部门","所属区局"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
                {0, 0, 0, 3},
                {1, 1, 1, 3},
                /*{1, 1, 2, 4}*/
        };
    }

    @Override
    public void createCellItem(JoinList joinList, Row row, int i) {

        if(joinList == null)
            return;

        createCell(row, 0,joinList.getUsername());
        createCell(row, 1,joinList.getMobile());
        createCell(row, 2,joinList.getDeptname());
        createCell(row, 3,joinList.getDept());

    }

    @Override
    protected float rowHeight() {
        return 1;
    }

    private String formatRepair(int v){
        if(v == 0) return "无";
        else return "" + v;
    }

}
