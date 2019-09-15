package wxgh.wx.web.entertain.act;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.entertain.act.ActInfo;
import wxgh.data.entertain.act.ActList;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.pub.SysFile;
import wxgh.param.entertain.act.ActParam;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.weixin.entertain.act.ActService;
import wxgh.sys.service.weixin.pub.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
public class BigController {

    @RequestMapping
    public void add(Model model) throws WeixinException {
        Integer total=userService.getTotal();
        model.put("total",total);
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public void addlink(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public void list(Model model, Integer id) {
        String info = actService.getDetails(id);
        model.put("info", info);
    }

    @RequestMapping
    public void detail() {

    }

    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {
        ActInfo actInfo = actService.getActInfo(id);

        Integer total=userService.getTotal();
        model.put("total",total);
        model.put("a", actInfo);

        WeixinUtils.sign(model, ApiList.getShareApi());
    }

    @RequestMapping
    public ActionResult list_api(ActParam param) {
        param.setPageIs(true);
        if(param.getActType() == null){
            param.setActType(Act.ACT_TYPE_BIG);
        }
        param.setStatus(Status.NORMAL.getStatus());
        param.setRegular(0);

        List<ActList> acts = actService.listAct(param);

        return ActionResult.okRefresh(acts, param);
    }

    @RequestMapping
    public ActionResult add_api(Act act, @RequestParam("file") MultipartFile[] multipartFile) {

        if (!TypeUtils.empty(act.getImgId())) {
            List<String> mediaList = TypeUtils.strToList(act.getImgId());
            List<String> images = new ArrayList<>();
            for(String media : mediaList)
                fileApi.wxDownload(media, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        images.add(file.getFileId());
                    }
                });
            if(images.size() > 0){
                act.setImgId(images.get(0));       // 单张封面图
                act.setImageIds(TypeUtils.listToStr(images));   // 轮播图
            }
        }

        if(!TypeUtils.empty(act.getLink())){

        }

        SeUser user = UserSession.getUser();
        act.setUserid(user.getUserid());
        act.setStatus(Status.APPLY.getStatus()); //
//        act.setActType(Act.ACT_TYPE_BIG); //大型活动
        act.setAllIs(0);
        act.setRegular(0);

        if (multipartFile != null && multipartFile.length > 0) {
            List<String> list = new ArrayList<>();
            for (MultipartFile m : multipartFile) {
                SysFile sysFile = fileApi.addFile(m, new FileParam());
                list.add(sysFile.getFileId());
            }
            act.setFileIds(TypeUtils.listToStr(list));
        }

        actService.addAct(act, false);

        ApplyPush applyPush = new ApplyPush(ApplyPush.Type.BIG_ACT, act.getUserid(), act.getId().toString());
        applyPush.setAgentId(WeixinAgent.AGENT_ADMIN);
        applyPush.setMsg("“" + act.getName() + "”活动");
        weixinPush.apply(applyPush);


        return ActionResult.okWithData(act.getId());
    }



    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private ActService actService;

    @Autowired
    private UserService userService;

}
