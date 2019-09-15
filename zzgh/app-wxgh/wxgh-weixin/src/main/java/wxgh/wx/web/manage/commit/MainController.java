package wxgh.wx.web.manage.commit;

import com.libs.common.data.DateUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.party.tribe.TribeActInfo;
import wxgh.sys.service.weixin.tribe.TribeActService;

import java.util.Date;

@Controller
public class MainController {

    @RequestMapping
    private void list(){

    }

    @RequestMapping
    private void act(Model model, Integer id){
        TribeActInfo act = tribeActService.getManageAct(id);
        Date now = new Date();
        if (now.after(act.getEndTime())) {
            act.setBtnStr("活动已结束");
            act.setJoinIs(3);
        } else if (now.before(act.getStartTime())) {
            if(1 == act.getJoinIs()){
                act.setBtnStr("已报名");
            }else{
                act.setBtnStr("我要报名");
                act.setJoinIs(0);
            }
        } else if (1 == act.getJoinIs()) {
            act.setBtnStr("已报名");
        } else if (0 == act.getJoinIs()) {
            act.setBtnStr("我要报名");
        }
        act.setTimeStr(DateUtils.formatDate(act.getStartTime(), act.getEndTime()));
        model.put("act", act);
    }

    @Autowired
    private TribeActService tribeActService;

    @RequestMapping
    public void show(Model model, Integer id) {
        TribeActInfo act = tribeActService.getManageAct(id);
        Date now = new Date();
        if (now.after(act.getEndTime())) {
            act.setBtnStr("活动已结束");
            act.setJoinIs(3);
        } else if (now.before(act.getStartTime())) {
            act.setBtnStr("活动未开始");
            act.setJoinIs(2);
        } else if (1 == act.getJoinIs()) {
            act.setBtnStr("已报名");
        } else if (0 == act.getJoinIs()) {
            act.setBtnStr("我要报名");
        }
        act.setTimeStr(DateUtils.formatDate(act.getStartTime(), act.getEndTime()));
        model.put("act", act);
    }

    /**
            * 报名列表
     *
             * @param id
     */
    @RequestMapping
    public void join_list(Integer id) {
    }

    /**
     * 立即评论
     *
     * @param model
     * @throws WeixinException
     */
    @RequestMapping
    public void comment(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    /**
     * 活动列表
     *
     * @param model
     */
    @RequestMapping
    public void act_list(Model model) {
    }

    /**
     * 活动备注或简介详情
     *
     * @param id
     * @param type
     * @param model
     */
    @RequestMapping
    public void info(Integer id,Integer type, Model model) {
        String info = tribeActService.getInfoManage(type, id);
        model.put("info", info);
    }
}
