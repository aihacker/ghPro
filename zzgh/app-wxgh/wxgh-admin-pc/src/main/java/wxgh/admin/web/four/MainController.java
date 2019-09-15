package wxgh.admin.web.four;

import com.libs.common.excel.ExcelApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.sys.excel.four.FourReadApi;
import wxgh.app.sys.excel.four.FourWriteApi;
import wxgh.app.sys.excel.four.ImportTemplateApi;
import wxgh.data.four.excel.FourDetailExcelData;
import wxgh.entity.pub.Dept;
import wxgh.param.four.excel.FourExcelParam;
import wxgh.sys.service.weixin.four.excel.FourExcelService;
import wxgh.sys.service.weixin.pub.DeptService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
@Scope("prototype")
public class MainController {

    @Autowired
    private FourExcelService fourExcelService;

    @Autowired
    private FourReadApi fourReadApi;

    @Autowired
    private DeptService deptService;

    @RequestMapping
    public void apply() {
    }

    @RequestMapping
    public void list(Model model) {
        // 测试版
        List<Dept> depts = deptService.getCompanyList();
        // 正式版
//        List<Dept> depts = deptService.getFourCompany();
        model.put("depts", depts);
    }

    @RequestMapping
    public void export_list(Model model) {
        // 测试版
        List<Dept> depts = deptService.getCompanyList();
        // 正式版
//        List<Dept> depts = deptService.getFourCompany();
        model.put("depts", depts);
    }

    @RequestMapping
    public void markets(Model model) {
        model.put("depts", deptService.getCompanyList());
    }

    /**
     * 导入
     *
     * @param request
     * @param file
     * @return
     */
    @RequestMapping
    public ActionResult _import(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        try {
            InputStream input = file.getInputStream();
            fourReadApi.detail(input, file.getOriginalFilename());
            // 错误信息
            String result = fourReadApi.buildMessage();
            input.close();
            if (result != null)
                return ActionResult.error(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fourExcelService.importDetails(fourReadApi.getListFourDetail());
        return ActionResult.ok();

    }

    /**
     * 导出excel
     *
     * @param param
     * @param response
     */
    @RequestMapping
    public void export(FourExcelParam param, HttpServletResponse response) {
        System.out.println("");
        System.out.println("是否进入进入导出方法");
        List<FourDetailExcelData> list = fourExcelService.getList(param);
        if (list.size() > 0) {
            FourWriteApi.setCompany(list.get(0).getCompanyName());
            FourWriteApi fourWriteApi = new FourWriteApi();
            fourWriteApi.toExcel(list);
            fourWriteApi.response(response);
        }
    }

    /**
     * 模版下载
     */
    @RequestMapping
    public void export_template(HttpServletResponse response) {
        //部门
        List<String> depts = fourExcelService.listCompanys();
        //营销中心
        List<String> marketing = fourExcelService.getMarketings();
        //四小项目
        List<String> projects = fourExcelService.getProjects();

        ExcelApi excelApi = new ImportTemplateApi(marketing, depts, projects);
        excelApi.toExcel(Collections.emptyList());

        excelApi.response(response);
    }

    @RequestMapping
    public void ruku(Model model){
        List<Dept> depts = deptService.getCompanyList();
        model.put("depts", depts);
    }

}
