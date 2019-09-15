package wxgh.wx.web.common.publicity;

import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.page.Page;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.common.publicity.PublicityData;
import wxgh.data.common.publicity.PublicityList;
import wxgh.entity.common.publicity.Publicity;
import wxgh.param.common.publicity.PublicityParam;
import wxgh.sys.service.weixin.common.publicity.PublicityService;

import java.util.Date;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-02 09:56
 * ----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private PublicityService publicityService;

    @RequestMapping
    public ActionResult info(Model model, Integer id) {
        Publicity publicity = publicityService.getPublicity(id);
        return ActionResult.ok(null, publicity);
    }

    @RequestMapping
    public void index(Model model) {
        List<Publicity> publicities = publicityService.getList(new PublicityParam());
        if (publicities != null && publicities.size() > 0) {
            for (int i = 0; i < publicities.size(); i++) {
                Publicity publicity = publicities.get(i);
                if ("url".equals(publicity.getType())) {
                    Publicity tmp = JsonUtils.parseBean(publicity.getContent(), Publicity.class);
                    if (tmp != null)
                        publicities.get(i).setUrl(tmp.getUrl());
                }
            }
        }
        model.put("publicities", publicities);
    }

    @RequestMapping
    public ActionResult list(Page page) {
        page.setPageIs(true);

        List<PublicityList> publicityLists = publicityService.listPublicity(page);
        return ActionResult.okRefresh(publicityLists, page);
    }

    @RequestMapping
    public ActionResult addPublicity(PublicityData publicityData) {
        ActionResult actionResult = ActionResult.ok();

        SeUser user = UserSession.getUser();

        String[] imgArray = publicityData.getImgs().split("-cut//cut-");
        System.out.println(imgArray.length);
        String myImgs = "";
        for (int i = 0; i < imgArray.length; i++) {
            String img = imgArray[i];
            if (i == imgArray.length - 1) {
                myImgs = myImgs + "{\"url\":\"" + img + "\", \"info\":\"\"}";
            } else {

                myImgs = myImgs + "{\"url\":\"" + img + "\", \"info\":\"\"},";
            }

        }

        String content = "{ \"txt\":\"" + publicityData.getName() + "\", \"imgs\":[" + myImgs + "]}";
        publicityData.setContent(content);
        publicityData.setName(publicityData.getName());
        publicityData.setTime(new Date());
        publicityData.setType("txtimg");
        publicityData.setUserid(user.getUserid());
        Integer integer = publicityService.addPublicity(publicityData);
        if (null != integer) {
            actionResult.setMsg("success");
        } else {
            actionResult.setMsg("fail");
        }

        return actionResult;
    }

    @RequestMapping
    public void addPublicityUrl(PublicityData publicityData) {
        publicityData.setTime(new Date());

        publicityService.addPublicity(publicityData);

    }


}

