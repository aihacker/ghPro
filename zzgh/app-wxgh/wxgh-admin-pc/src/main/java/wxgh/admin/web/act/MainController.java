package wxgh.admin.web.act;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.ExcelUtils;
import wxgh.data.entertain.act.ActList;
import wxgh.entity.entertain.act.ActJoin;
import wxgh.param.entertain.act.ActParam;
import wxgh.sys.service.weixin.entertain.act.ActService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class MainController {

    @RequestMapping
    private void apply(){}

    @RequestMapping
    private void list(Model model, ActParam actParam){
        actParam.setStatus(1);
        List<ActList> acts = actService.listAct(actParam);

        model.put("acts",acts);
    }

    @RequestMapping(value = "/act/admin/template/export.html")
    private void export(ActParam actParam, HttpServletResponse httpServletResponse) throws IOException{
        HSSFWorkbook workbook = null;
        OutputStream out = null;

        actParam.setRegular(0);
        List<ActJoin> acts = actService.listActJoins(actParam);
        String name = actService.getName(actParam.getActId());
        try{
            workbook = ExcelUtils.createWorkBook(httpServletResponse,name+"报名表");


            new YuyueApi(workbook).toExcel(acts,name);

            out = httpServletResponse.getOutputStream();

            workbook.write(out);

        }finally {
            out.flush();
            out.close();
            workbook.close();
        }
    }

    @Autowired
    private ActService actService;

}
