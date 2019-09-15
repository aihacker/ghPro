package wxgh.app.sys.excel.bodycheck;

import com.libs.common.excel.ExcelSupportApi;
import com.libs.common.excel.ExcelType;
import org.apache.poi.ss.usermodel.Row;
import wxgh.entity.common.bodycheck.Bodycheck;

public class BodycheckWriteApi extends ExcelSupportApi<Bodycheck> {

    public BodycheckWriteApi() {
        super( "体检预约情况", ExcelType.XLSX);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "体检预约情况"
                },
                {
                        "姓名","联系电话","部门","年龄","性别","婚姻状况","身份证号码",
                        "免费额度","医院","选择套餐","调剂金额","额外费用","额外费用金额","加项包"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
                {0, 0, 0, 13},
//                {1, 1, 1, 6},
                /*{1, 1, 2, 4}*/
        };
    }

    @Override
    public void createCellItem(Bodycheck bodycheck, Row row, int i) {

        if(bodycheck == null)
            return;

        createCell(row, 0,bodycheck.getName());
        createCell(row, 1,bodycheck.getMobile());
        createCell(row, 2,bodycheck.getDept());
        createCell(row, 3,bodycheck.getAge());
        createCell(row, 4,bodycheck.getGender());
        createCell(row, 5,bodycheck.getMarriage());
        createCell(row, 6,bodycheck.getIdcard());
        createCell(row, 7,bodycheck.getQuote());
        createCell(row, 8,bodycheck.getHospital());
        createCell(row, 9,bodycheck.getCategory());
        createCell(row, 10,bodycheck.getFuli());
        createCell(row, 11,bodycheck.getExtra());
        createCell(row, 12,bodycheck.getEncore());
        createCell(row, 13,bodycheck.getPlus());
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
