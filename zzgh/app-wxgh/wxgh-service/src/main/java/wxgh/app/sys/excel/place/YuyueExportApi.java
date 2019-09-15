package wxgh.app.sys.excel.place;


import com.libs.common.data.DateUtils;
import com.libs.common.excel.ExcelApi;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import wxgh.entity.entertain.place.PlaceYuyue;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-20  09:38
 * --------------------------------------------------------- *
 */
@Component
public class YuyueExportApi extends ExcelApi<PlaceYuyue> {

    @Override
    public String[] head() {
        return new String[]{"序号", "用户信息", "预约时间", "场馆信息", "支付方式", "操作时间"};
    }

    @Override
    public void createCellItem(PlaceYuyue placeYuyue, Row row, int i) {
        if(placeYuyue == null)
            return;

        createCell(row, 0, i + 1);
        createCell(row, 1, placeYuyue.getUserName());
        createCell(row,2, placeYuyue.getDateId() + "/周" + placeYuyue.getWeek() + "(" + placeYuyue.getStartTime() + "-" + placeYuyue.getEndTime() + ")");
        createCell(row,3, placeYuyue.getPlaceTitle() + placeYuyue.getCateName() + placeYuyue.getSiteName());
        createCell(row,4, placeYuyue.getPayType() == 1 ? "积分" : "现金");
        createCell(row,5, DateUtils.formatDate(placeYuyue.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
    }
}
