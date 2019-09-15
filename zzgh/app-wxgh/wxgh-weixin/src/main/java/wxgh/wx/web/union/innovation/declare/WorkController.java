package wxgh.wx.web.union.innovation.declare;


import com.libs.common.json.JsonUtils;
import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.ObjectTransUtils;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.data.union.innovation.AddData;
import wxgh.data.union.innovation.declare.ResultContent;
import wxgh.data.union.innovation.declare.ResultImg;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.entity.union.innovation.InnovateShop;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.entity.union.innovation.WorkUser;
import wxgh.sys.service.weixin.union.innovation.InnovateApplyService;
import wxgh.sys.service.weixin.union.innovation.InnovateShopService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-24 16:00
 *----------------------------------------------------------
 */
@Controller
public class WorkController {

    @Autowired
    private PushAsync pushAsync;


    @Autowired
    private FileApi fileApi;

    @Autowired
    private InnovateApplyService innovateApplyService;

    @Autowired
    private InnovateShopService innovateShopService;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public ActionResult index(Integer id, Model model, HttpServletRequest request) throws WeixinException {
        ActionResult result = ActionResult.ok();

        List<String> apiList = ApiList.getImageApi();
        apiList.add(ApiList.OPENENTERPRISECONTACT);
        WeixinUtils.sign(model, apiList);

        WeixinUtils.sign_contact(model);

        model.put("applyId", id);
        return result;
    }

    @RequestMapping
    public ActionResult submit(AddData info, HttpServletRequest request) {
        ActionResult result = ActionResult.ok();

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户！");
        }

        //保存记录
        Integer applyId = innovateApplyService.add(ObjectTransUtils.SeUserToUser(user), InnovateApply.TYPE_SHOP, null);
        info.setApplyId(applyId);

        // 微信图片下载
        List<ResultImg> imgs = new ArrayList<>();
        if (info.getMediaIds() != null) {
            for (String url : info.getMediaIds()) {

                final ResultImg img = new ResultImg();
                fileApi.wxDownload(url, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        img.setUrl(file.getFilePath());
                    }
                });

                imgs.add(img);

            }
        }
        InnovateShop innovateShop = new InnovateShop();
        innovateShop.setShopType(info.getShopType());
        innovateShop.setTeamName(info.getTeamName());
        innovateShop.setItemName(info.getItemName());
        innovateShop.setTeamLeader(info.getUserIds()[0]);
        innovateShop.setAddress(info.getAddress());
        innovateShop.setApplyId(info.getApplyId());
        innovateShop.setType(1);
        innovateShop.setStatus(1);
        innovateShop.setCreateTime(new Date());
        Integer id = innovateShopService.save(innovateShop);

        /*
        * save members
        * */
        Integer count = 0;
        for (int i = 0; i < info.getUserIds().length; i++) {
            String userId = info.getUserIds()[i];
            String username = info.getUsernames()[i];
            WorkUser workUser = new WorkUser();
            workUser.setName(username);
            workUser.setUserid(userId);
            workUser.setUserType(1);
            workUser.setTypeName("成员");
            workUser.setStatus(1);
            workUser.setWorkId(id);
            workUser.setWorkType(2);//工作坊

            if (i == 0) {
                workUser.setUserType(2);
                workUser.setTypeName("组长");
            }

            Integer workUserId = innovateShopService.saveRaceMember(workUser);
            if (workUserId > 0) {
                count++;
            }
        }

        /*
        * save work result
        * */
        WorkResult workResult = new WorkResult();
        String content = info.getContent();

        ResultContent resultContent = new ResultContent();
        resultContent.setTxt(content);
        resultContent.setImgs(imgs);

        workResult.setContent(JsonUtils.stringfy(resultContent));
        workResult.setType("txtimg");
        workResult.setAddTime(new Date());
        workResult.setWorkId(id);
        workResult.setWorkType(2);

        workResult.setOrderid(0);

        Integer workResultId = innovateShopService.saveWorkResult(workResult);

        /*
        * check set step success
        * */
        Integer success = innovateShopService.checkSetStepSuccess(info.getApplyId());

        if (success == 1 && workResultId > 0 && count > 0) {
            result.setData("success");
        }

        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.INNOVATION_WORK, user.getUserid(), id.toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("工作坊");
        weixinPush.apply(push);

        return result;
    }
    
}

