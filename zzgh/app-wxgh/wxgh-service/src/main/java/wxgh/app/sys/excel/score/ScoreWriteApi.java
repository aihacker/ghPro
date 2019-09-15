package wxgh.app.sys.excel.score;

import com.libs.common.excel.ExcelSupportApi;
import com.libs.common.excel.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import wxgh.entity.pub.score.ScoreExcel;

public class ScoreWriteApi extends ExcelSupportApi<ScoreExcel> {

    public ScoreWriteApi() {
        super( "所有成员积分情况", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "所有成员积分情况"
                },
                {
                        "导出","所有成员积分情况"
                },
                {
                        "成员姓名","所属区局","所属部门","场馆积分",
                        "已兑换场馆积分","可兑换工会积分","已兑换工会积分"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
                {0, 0, 0, 6},
                {1, 1, 1, 6},
                /*{1, 1, 2, 4}*/
        };
    }

    @Override
    public void createCellItem(ScoreExcel scoreExcel, Row row, int i) {

        if(scoreExcel == null)
            return;

        createCell(row, 0,scoreExcel.getUsername());
        createCell(row, 1,scoreExcel.getDepartment());
        createCell(row, 2,scoreExcel.getDept());
        createCell(row, 3,scoreExcel.getLoca_score()==null?"0":""+scoreExcel.getLoca_score());
        createCell(row, 4,scoreExcel.getLocaPay_score()==null?"0":""+scoreExcel.getLocaPay_score());
        createCell(row, 5,scoreExcel.getGh_score()==null?"0":""+scoreExcel.getGh_score());
        createCell(row, 6,scoreExcel.getGhPay_score()==null?"0":""+scoreExcel.getGhPay_score());

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
