package wxgh.wx.web.union.group.act;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import pub.utils.StrUtils;
import pub.web.ServletUtils;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.NameValue;
import wxgh.data.union.group.act.result.ResultInfo;
import wxgh.data.union.group.act.result.ResultList;
import wxgh.entity.common.Comment;
import wxgh.entity.common.Zan;
import wxgh.entity.union.group.ActResult;
import wxgh.param.common.bbs.CommParam;
import wxgh.param.entertain.act.SignParam;
import wxgh.sys.service.weixin.common.bbs.CommentService;
import wxgh.sys.service.weixin.common.bbs.ZanService;
import wxgh.sys.service.weixin.union.group.act.result.ActResultService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
@Controller
public class ResultController {

    @RequestMapping
    public void add(Model model, Integer id) throws WeixinException {
        List<NameValue> act = actResultService.listAct(id);
        model.put("acts", act);
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public void show(Integer id, Model model) {
        HttpSession session = ServletUtils.getSession();
        Boolean isSee = (Boolean) session.getAttribute("actresult_see_session_" + id);
        if (isSee == null || !isSee) {
            actResultService.updateSeeNum(id);
            session.setAttribute("actresult_see_session_" + id, true);
        }
        ResultInfo result = actResultService.getResult(id);
        if (result != null) {
            result.setTimeStr(DateUtils.formatDate(result.getAddTime()));
        }

        model.put("a", result);
    }

    @RequestMapping
    public void list(Integer id){}

    @RequestMapping
    public ActionResult api_list(SignParam param){
        param.setPageIs(true);

        List<ResultList> results = actResultService.listResult(param);

        return ActionResult.okRefresh(results, param);
    }

    @RequestMapping
    public ActionResult api_add(ActResult result, Integer pushType) {
        Integer resultId = actResultService.addResult(result);
        weixinPush.act_result(pushType, resultId);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult api_comm(CommParam param) {
        param.setPageIs(true);
        param.setType(Comment.TYPE_ACT_RESULT);

        param.setUserid(UserSession.getUserid());

        List<Comment> comments = actResultService.listComm(param);
        if (!TypeUtils.empty(comments)) {
            for (int i = 0; i < comments.size(); i++) {
                comments.get(i).setCreateFormatDate(
                        DateUtils.formatDate(comments.get(i).getCreateTime()));
            }
        }
        return ActionResult.okRefresh(comments, param);
    }

    /**
     * 点赞
     *
     * @param id
     * @param type 1文章，2评论
     * @return
     */
    @RequestMapping
    public ActionResult api_zan(Integer id, Integer type) {

        String userid = UserSession.getUserid();
        Integer num = 0;
        Integer zanType = 0;
        if (type == 1) { //文章点赞
            num = actResultService.getNumb(id, "zan_numb");
            zanType = actResultService.updateZanNum(id, userid);
        } else { //评论点赞
            num = commentService.getCommNum(id);
            zanType = commentService.updateCommNum(id, userid);
        }

        if (zanType == 1) {
            num++;
            Zan zan = new Zan();
            zan.setUserid(userid);
            zan.setZanId(id);
            zan.setZanType(type == 1 ? Zan.TYPE_ACT_RESULT : Zan.TYPE_ACT_RESULT_COMM);
            zanService.save(zan);
        } else {
            num--;
        }
        RefData refData = new RefData();
        refData.put("type", zanType);
        refData.put("num", num);
        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult add_comm(Comment comment) {
        if (StrUtils.empty(comment.getAtlComment())) {
            return ActionResult.error("评论内容不能为空哦");
        }
        String userid = UserSession.getUserid();

        comment.setUserid(userid);
        comment.setType(Comment.TYPE_ACT_RESULT);

        commentService.addComm(comment);

        return ActionResult.ok();
    }

    @Autowired
    private ActResultService actResultService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ZanService zanService;

    @Autowired
    private WeixinPush weixinPush;

}
