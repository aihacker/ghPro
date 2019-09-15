package wxgh.wx.admin.web.union.advice;


import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.UrlUtils;
import wxgh.data.pub.push.ReplyPush;
import wxgh.data.wxadmin.union.AdviceInfo;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.entity.union.innovation.InnovateMicro;
import wxgh.param.union.innovation.QueryInnovateMicro;
import wxgh.param.union.innovation.work.WorkUserQuery;
import wxgh.sys.service.weixin.union.innovation.InnovateApplyService;
import wxgh.sys.service.weixin.union.innovation.InnovateMicroService;
import wxgh.sys.service.weixin.union.innovation.WorkUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static wxgh.entity.union.innovation.InnovateApply.TYPE_ADVICE;
import static wxgh.entity.union.innovation.InnovateApply.TYPE_SHOP;

/**
 * Created by Administrator on 2017/6/8.
 */
@Controller
public class MicroController {

    @RequestMapping
    public void index(Model model, QueryInnovateMicro query) {

        InnovateMicro innovateMicro = innovateMicroService.getOne(query);
        model.put("data", innovateMicro);
        if (innovateMicro != null) {
            WorkUserQuery workUserQuery = new WorkUserQuery();
            workUserQuery.setWorkId(innovateMicro.getId());
            workUserQuery.setStatus(null);
            workUserQuery.setWorkType(3);
            model.put("users", workUserService.getUsers(workUserQuery));

            String content = innovateMicro.getContent();
            Map map = null;
            if(content != null){
                map = JsonUtils.parseMap(content, String.class, Object.class);
                if(map.containsKey("imgs")){
                    List<String> imgList = new ArrayList<String>();
                    ArrayList imgs = (ArrayList) map.get("imgs");
                    for (int i = 0; i < imgs.size(); i++) {
                        Map map2 = (Map) imgs.get(i);
                        String img = (String) map2.get("url");
                        imgList.add(UrlUtils.URLDecode(img));
                    }
                    model.put("imgList", imgList);
                }

                String txt = (String) map.get("txt");
                model.put("txt", UrlUtils.URLDecode(txt));
            }
        }
    }

    @RequestMapping
    public ActionResult apply(InnovateApply apply, Integer adviceId) {
        apply.setAuditTime(new Date());
        innovateApplyService.updateApply(apply);

        Integer status;
        if (apply.getStatus() == InnovateApply.STATUS_PASS) {
            status = InnovateApply.STATUS_FNISH;
        } else {
            status = InnovateApply.STATUS_FAILED;
        }
        innovateApplyService.updateStatusByAdviceId(adviceId, status);

        //回复消息，通知用户审核已通过
        AdviceInfo adviceInfo = innovateApplyService.getInfo(adviceId);
        ReplyPush push = new ReplyPush(adviceInfo.getUserid(), apply.getStatus() == InnovateApply.STATUS_PASS ? Status.NORMAL.getStatus() : Status.FAILED.getStatus());
        if(adviceInfo.getApplyType().equals(TYPE_SHOP)){
            push.setMsg("工作坊审核结果");
            weixinPush.reply(push);
        } else if(adviceInfo.getApplyType().equals(TYPE_ADVICE)){
            push.setMsg("创新建议审核结果");
            weixinPush.reply(push);
        }

        return ActionResult.ok();
    }

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private WorkUserService workUserService;

    @Autowired
    private InnovateMicroService innovateMicroService;

    @Autowired
    private InnovateApplyService innovateApplyService;

}
