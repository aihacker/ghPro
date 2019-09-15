package wxgh.wx.admin.web.common.publicity;


import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.HtmlUtils;
import wxgh.app.utils.WeixinUtils;
import wxgh.entity.common.publicity.Publicity;
import wxgh.entity.pub.SysFile;
import wxgh.param.common.publicity.PublicityQuery;
import wxgh.sys.service.weixin.common.publicity.PublicityService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/12/29
 */
@Controller
public class MainController {

    @Autowired
    private PublicityService publicityService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public ActionResult get(PublicityQuery query) {

        Integer count = publicityService.getNoticeCount(query);

        query.setPageIs(true);
        query.setRowsPerPage(8);
        query.setTotalCount(count);

        List<Publicity> publicities = publicityService.getNotices(query);

        RefData refData = new RefData();
        refData.put("notices", publicities);
        refData.put("page", query.getPages()); //总页数
        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult del(Integer id) {
        publicityService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult stick(Integer id, Integer top) {
        publicityService.updateNotice(id, top);
        return ActionResult.ok();
    }


    @RequestMapping
    public ActionResult add1(Publicity publicity, Integer push) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        if(publicity.getType().equals("url")){
            try {
                publicity.setPicture(HtmlUtils.getWeixinImages(publicity.getUrl()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        if(publicity.getType().equals("txtimg"))
            if (!StrUtils.empty(publicity.getPicture())) {
                String[] imgs = publicity.getPicture().split(",");
                File toFile = null;
                List<String> list = new ArrayList<>();
                for (int i = 0; i < imgs.length; i++) {
                    fileApi.wxDownload(imgs[i], new SuccessCallBack() {
                        @Override
                        public void onSuccess(SysFile file, File toFile) {
                            list.add(file.getFileId());
                        }
                    });
                }
                publicity.setPicture(TypeUtils.listToStr(list));
            }



        publicity.setUserid(user.getUserid());
        publicity.setTime(new Date());
        publicityService.saveNotice(publicity);

        if (push == 1) {

            weixinPush.publicity(publicity.getId() + "");

        }
        return ActionResult.ok();
    }

}
