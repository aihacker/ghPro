package wxgh.wx.web.union.woman.teach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.page.Page;
import pub.spring.web.mvc.ActionResult;

import wxgh.app.sys.excel.teach.TeachWriteApi;
import wxgh.data.entertain.act.JoinList;
import wxgh.data.union.woman.teach.TeachList;
import wxgh.param.union.woman.JoinParam;
import wxgh.sys.service.weixin.union.woman.TeachService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(Page page) {
        page.setPageIs(true);

        List<TeachList> teachs = teachService.teachList(page);

        return ActionResult.okRefresh(teachs, page);
    }

    @RequestMapping
    public ActionResult join_list(JoinParam param) {
        param.setPageIs(true);

        List<JoinList> joins = teachService.joinList(param);

        return ActionResult.okRefresh(joins, param);
    }

    @RequestMapping
    public ActionResult join(Integer id) {
        teachService.join(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public void downmsg(Integer id, HttpServletRequest request, HttpServletResponse response){

        List<JoinList> joins = teachService.downmsg(id);

        if(joins.size()>0){
            TeachWriteApi teachWriteApi = new TeachWriteApi();
            teachWriteApi.toExcel(joins);
            teachWriteApi.response(response);
        }

    }

    @Autowired
    private TeachService teachService;

}