package wxgh.wx.web.canteen.act;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import org.omg.CORBA.NamedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.entertain.act.ActInfo;
import wxgh.data.entertain.act.ActList;
import wxgh.data.entertain.act.JoinList;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.canteen.CanteenUser;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.entertain.act.ScoreRule;
import wxgh.entity.pub.SysFile;
import wxgh.param.entertain.act.ActParam;
import wxgh.param.entertain.act.JoinParam;
import wxgh.sys.service.weixin.canteen.CanteenActJoinService;
import wxgh.sys.service.weixin.canteen.CanteenActService;
import wxgh.sys.service.weixin.entertain.act.ActService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
@Controller
public class MainController {

    /**
     * 活动列表页面
     */
    @RequestMapping
    public void index(Integer id) {

    }

    @RequestMapping
    public ActionResult list(ActParam param) {
        param.setGroupId(param.getGroupId());
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setActType(CanteenAct.ACT_TYPE_CANTEEN);
        param.setStatus(Status.NORMAL.getStatus());
        if (param.getRegular() == null) {
            param.setRegular(0);
        } else if (param.getRegular() == 2) {
            param.setUserid(UserSession.getUserid());
        }
        List<ActList> acts = actService.listAct(param);

        return ActionResult.okRefresh(acts, param);
    }

    /**
     * 取消活动
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult cancel(Integer id) {
        actService.cancelAct(id);
        return ActionResult.ok();
    }


    /**
     * 添加活动
     *
     * @param act
     * @param push
     * @return
     */
    @RequestMapping
    public ActionResult add(CanteenAct act, Integer push) {
        if (!TypeUtils.empty(act.getImgId())) {
            List<String> mediaList = TypeUtils.strToList(act.getImgId());
            List<String> images = new ArrayList<>();
            for (String media : mediaList)
                fileApi.wxDownload(media, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        images.add(file.getFileId());
                    }
                });
            if (images.size() > 0) {
                act.setImgId(images.get(0));       // 单张封面图
                act.setImageIds(TypeUtils.listToStr(images));   // 轮播图
            }
        }

        SeUser user = UserSession.getUser();
        act.setUserid(user.getUserid());
        act.setStatus(Status.NORMAL.getStatus()); //饭堂活动默认正常状态
        act.setActType(CanteenAct.ACT_TYPE_CANTEEN); //饭堂活动

        boolean pushIs = push != null && push.intValue() == 1;

        actService.addAct(act, pushIs);

        /**
         * 推送消息给群组用户
         */
        if (pushIs) {
            //weixinPush.act(act.getActId(), null, true);
            weixinPush.act(act.getActId(),null,true, WeixinAgent.AGENT_CANTEEN);
        }
        return ActionResult.okWithData(act.getId());
    }

    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {
        ActInfo actInfo = actService.getActInfo(id);

        model.put("a", actInfo);

        /*WeixinUtils.sign(model, ApiList.getShareApi());*/
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
     * 查看报名列表
     *
     * @param id
     */
    @RequestMapping
    public void join(Integer id) {
    }


    @RequestMapping
    public ActionResult act_join(Integer id, Integer type) {
        actJoinService.actJoin(id, type);
        return ActionResult.ok();
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
    public void joinlist(){

    }

    @RequestMapping
    public ActionResult getjoinlist(ActParam param){
        SeUser user= UserSession.getUser();
        String userId=user.getUserid();
        param.setGroupId(null);
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setUserid(userId);
        param.setActType(CanteenAct.ACT_TYPE_CANTEEN);
        param.setStatus(Status.NORMAL.getStatus());
        if (param.getRegular() == null) {
            param.setRegular(0);
        } else if (param.getRegular() == 2) {
            param.setUserid(UserSession.getUserid());
        }
        List<ActList> acts = actService.userListJoinAct(param);

        return ActionResult.okRefresh(acts, param);
    }

    @Autowired
    private CanteenActJoinService actJoinService;

    @Autowired
    private CanteenActService actService;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private FileApi fileApi;

}
