package wxgh.wx.web.entertain.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.sys.excel.entertain.BigWriteApi;
import wxgh.data.entertain.act.JoinList;
import wxgh.entity.entertain.act.ScoreRule;
import wxgh.param.entertain.act.JoinParam;
import wxgh.sys.service.weixin.entertain.act.ActJoinService;
import wxgh.sys.service.weixin.entertain.act.ActService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
@Controller
public class ShowController {

    /**
     * 查看报名列表
     *
     * @param id
     */
    @RequestMapping
    public void join(Integer id) {
    }

    /**
     * 查看积分规则
     *
     * @param model
     * @param id
     */
    @RequestMapping
    public void score(Model model, Integer id) {
        ScoreRule rule = actService.getScoreRule(id);
        model.put("rule", rule);
    }

    /**
     * 活动介绍详情
     *
     * @param model
     * @param id
     */
    @RequestMapping
    public void detail(Model model, Integer id) {
        String info = actService.getDetails(id);
        model.put("info", info);
    }

    /**
     * 获取报名列表
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult join_list(JoinParam param) {
        param.setPageIs(true);

        List<JoinList> joinLists = actJoinService.listJoins(param);

        return ActionResult.okRefresh(joinLists, param);
    }

    @RequestMapping
    public ActionResult act_join(Integer id, Integer type) {
        actJoinService.actJoin(id, type);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult act_join_big(Integer id, Integer type) {
        actJoinService.actJoinBig(id, type);
        return ActionResult.ok();
    }

    /**
     * 团队 报名 接口
     * @param id
     * @param type
     * @return
     */
    @RequestMapping
    public ActionResult act_join_by_team(Integer id, Integer type) {
        actJoinService.actJoin(id, type);
        return ActionResult.ok();
    }

    @RequestMapping
    public void downmsg(JoinParam param, HttpServletRequest request, HttpServletResponse response){

        List<JoinList> joinLists = actJoinService.listJoins(param);

        if(joinLists.size()>0){
            BigWriteApi bigWriteApi = new BigWriteApi();
            bigWriteApi.toExcel(joinLists);
            bigWriteApi.response(response);
        }
    }


    @Autowired
    private ActJoinService actJoinService;

    @Autowired
    private ActService actService;

}
