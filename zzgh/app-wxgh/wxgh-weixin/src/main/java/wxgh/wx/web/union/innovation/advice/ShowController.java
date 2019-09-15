package wxgh.wx.web.union.innovation.advice;


import com.libs.common.data.DateUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.utils.WeixinUtils;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.innovation.AdviceComm;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.sys.service.weixin.union.innovation.AdviceComService;
import wxgh.sys.service.weixin.union.innovation.AdviceZanService;
import wxgh.sys.service.weixin.union.innovation.InnovateAdviceService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-19 17:17
 *----------------------------------------------------------
 */
@Controller
public class ShowController {

    @Autowired
    private InnovateAdviceService innovateAdviceService;

    @Autowired
    private AdviceZanService adviceZanService;

    @Autowired
    private AdviceComService adviceComService;

    @Autowired
    private FileApi fileApi;

    @RequestMapping
    public void index(Model model, Integer id, HttpSession session) throws WeixinException {

        boolean isZan = false;

        InnovateAdvice advice = innovateAdviceService.get(id);

        if (advice != null) {

            SeUser user = UserSession.getUser();
            if (user != null) {
                isZan = adviceZanService.isZan(user.getUserid(), 1, id, null);
            }
            advice.setZanIs(isZan);

            advice.setAddTimeStr(DateUtils.formatDate(advice.getAddTime()));

            //更新浏览次数
            Boolean isRead = (Boolean) session.getAttribute("innovate_advice_is_see_" + id);
            if (isRead == null) {
                innovateAdviceService.updateSeeNumb(id);
                session.setAttribute("innovate_advice_is_see_" + id, true);
                advice.setSeeNumb(advice.getSeeNumb() + 1);
            }

        }
        model.put("advice", advice);

        List<String> apiList = ApiList.getImageApi();
        WeixinUtils.sign(model, apiList);

    }


    @RequestMapping
    public ActionResult addcomm(AdviceComm com, String imgs, String txt) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        List<String> tempImg = new ArrayList<>();
        List<String> tempImgs = new ArrayList<>();
        if (!StrUtils.empty(imgs)) {
            String[] txImgs = imgs.split(",");
            for (String img : txImgs) {
                fileApi.wxDownload(img, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        tempImg.add(file.getFilePath());
                        tempImgs.add(file.getFileId());
                    }

                });
            }
        }

        String saveImgs = TypeUtils.listToStr(tempImg);
        String imageIds = TypeUtils.listToStr(tempImgs);

        com.setCnt("{\"txt\":\"" + txt + "\",\"imgs\":\"" + saveImgs + "\"}");
        com.setImageIds(imageIds);
        com.setContent(txt);
        com.setUserid(user.getUserid());
        com.setUsername(user.getName());
        com.setAddTime(new Date());
        com.setZanNumb(0);
        com.setStatus(1);

        adviceComService.addCom(com);

        com.setAddTimeStr(DateUtils.formatDate(com.getAddTime()));
        com.setAvatar(user.getAvatar());

        return ActionResult.ok(null, com);
    }

    @RequestMapping
    public ActionResult listcom(Integer adviceId) {

        Boolean zanIs = false;

        SeUser user = UserSession.getUser();

        List<AdviceComm> coms = adviceComService.getComs(adviceId);
        // 2017-8改
//        List<AdviceComm> coms = adviceComService.getComments(adviceId);
        if (coms != null && coms.size() > 0) {
            for (int i = 0; i < coms.size(); i++) {
                AdviceComm resultCom = coms.get(i);
                coms.get(i).setAddTimeStr(DateUtils.formatDate(resultCom.getAddTime()));

                if (user == null) {
                    coms.get(i).setZanIs(false);
                } else {
                    zanIs = adviceZanService.isZan(user.getUserid(), 2, resultCom.getAdviceId(), resultCom.getId());
                    coms.get(i).setZanIs(zanIs);
                }
            }
        }
        return ActionResult.ok(null, coms);
    }

    @RequestMapping
    public ActionResult zan(Integer id, Integer commId) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        if (commId != null) { //用户评论的赞
            boolean isZan = adviceZanService.isZan(user.getUserid(), 2, id, commId);
            if (isZan) {
                return ActionResult.error("你已经赞过了哦");
            }
            adviceComService.updateZan(commId, user.getUserid(), id);
        } else {
            boolean isZan = adviceZanService.isZan(user.getUserid(), 1, id, null);
            if (isZan) {
                return ActionResult.error("你已经赞过了哦");
            }
            innovateAdviceService.updateZanNumb(id, user.getUserid());

        }

        return ActionResult.ok();
    }

}
