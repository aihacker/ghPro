package wxgh.admin.web.party;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.ExcelUtils;
import pub.utils.TemplateExcel;
import wxgh.entity.party.party.PartyGroup;
import wxgh.entity.party.party.PartyUser;
import wxgh.param.party.PartyParam;
import wxgh.sys.service.admin.party.PartyGroupService;
import wxgh.sys.service.admin.party.PartyUserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by cby on 2017/8/15.
 */
@Controller
public class MainController {

    @Autowired
    private PartyUserService partyUserService;

    @Autowired
    private PartyGroupService partyGroupService;

    @RequestMapping
    public void index(Model model, PartyUser partyUser, PartyGroup partyGroup, PartyParam query){
        List<PartyUser> groups = partyUserService.getGroups(partyUser);
        List<PartyUser> nations = partyUserService.getNations(partyUser);
        Integer count = partyUserService.getCount(query);
        List<PartyGroup> partyGroups = partyGroupService.getGroups(partyGroup);
        model.put("group",groups);
        model.put("nations",nations);
        model.put("count",count);
        model.put("allgroup",partyGroups);
    }

    @RequestMapping
    public void add(Model model,PartyUser partyUser, PartyGroup partyGroup){
        List<PartyUser> nations = partyUserService.getNations(partyUser);
        model.put("nations",nations);
    }

    @RequestMapping
    public void import_people(Model model){

    }

    @RequestMapping(value = "/party/admin/template/download.html")
    public void download(HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = null;
        OutputStream out = null;
//        InputStream in = null;
        try {
//            File file = PathUtils.getUpload(PathUtils.PATH_TEMPLATE, "党群模版.xls", "党群模版", false);
//
//            String fileName = file.getName();
//            response.setContentType("application/vnd.ms-excel");
//            fileName = URLEncoder.encode(fileName, "UTF-8");
//            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            workbook = ExcelUtils.createWorkBook(response, "党员导入模版");

            List<String> depts = partyGroupService.getPartys();

            new TemplateExcel(workbook).toExcel(depts);

            out = response.getOutputStream();

            workbook.write(out);
//            in = new FileInputStream(file);

//            byte[] bytes = new byte[2048];
//            int len;
//            while ((len = in.read(bytes)) > 0) {
//                out.write(bytes, 0, len);
//            }
        } finally {
            out.flush();
//            in.close();
            out.close();
            workbook.close();
        }

    }
}
