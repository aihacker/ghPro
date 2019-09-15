package wxgh.wx.web.union.innovation.member;


import com.libs.common.json.JsonUtils;
import com.weixin.Agent;
import com.weixin.WeixinException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.ObjectTransUtils;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.pub.SysFile;
import wxgh.entity.pub.User;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.entity.union.innovation.InnovateMicro;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.entity.union.innovation.WorkUser;
import wxgh.param.union.innovation.QueryInnovateMicro;
import wxgh.param.union.innovation.work.WorkUserQuery;
import wxgh.sys.service.weixin.pub.UserService;
import wxgh.sys.service.weixin.union.innovation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-21 14:41
 *----------------------------------------------------------
 */
@Controller
public class MicroController {

    private static final Log log = LogFactory.getLog(FileApi.class);

    @Autowired
    private InnovateMicroService innovateMicroService;

    @Autowired
    private User2Service user2Service;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkUserService workUserService;

    @Autowired
    private InnovateApplyService innovateApplyService;

    @Autowired
    private WorkResultService workResultService;

    @Autowired
    private PushAsync pushAsync;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

    /**
     * 资金申请页面
     * @param model
     * @throws WeixinException
     */
    @RequestMapping
    public void index(Model model) throws WeixinException {
        WeixinUtils.sign(model);
    }

    /**
     * 添加
     * @param innovateMicro
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping
    public ActionResult add(InnovateMicro innovateMicro, HttpServletRequest request) throws UnsupportedEncodingException {

        SeUser user = UserSession.getUser();

        if (null == user) {
            return ActionResult.error("请先登录");
        }
//        if (null == innovateMicro.getType()) {
//            return ActionResult.error("申报类别不能为空哦");
//        }
        if (null == innovateMicro.getName()) {
            return ActionResult.error("成果名称不能为空哦");
        }
        if (null == innovateMicro.getPoint()) {
            return ActionResult.error("创新点不能为空哦");
        }
        if (null == innovateMicro.getBehave()) {
            return ActionResult.error("创新举措不能为空哦");
        }
        if (null == innovateMicro.getTxt()) {
            return ActionResult.error("成果描述不能为空哦");
        }
        if (null == innovateMicro.getTeam()) {
            return ActionResult.error("团队名称不能为空哦");
        }
        if (innovateMicro.getName().length() > 80) {
            return ActionResult.error("成果名称不能大于80个字符哦");
        }
        if (innovateMicro.getTeam().length() > 80) {
            return ActionResult.error("团队名称不能大于80个字符哦");
        }
        if (innovateMicro.getPoint().length() > 800) {
            return ActionResult.error("创新点不能大于800个字符哦");
        }
        if (innovateMicro.getBehave().length() > 800) {
            return ActionResult.error("创新举措不能大于800个字符哦");
        }
        if (URLDecoder.decode(innovateMicro.getTxt(), "UTF-8").length() > 800) {
            return ActionResult.error("创新成果描述不能大于800个字符哦");
        }

        /// //// 下载微信图片
        final List<String> fileIds = new LinkedList<>();
        final List<String> filePaths = new LinkedList<>();
        if (!StrUtils.empty(innovateMicro.getImgs())) {

            String[] imgs = innovateMicro.getImgs().split(",");
            for (String mediaId : imgs) {
                fileApi.wxDownload(mediaId, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        log.info("download image success!");
                        log.info("image-path:" + file.getFilePath());
                        fileIds.add(file.getFileId());
                        filePaths.add(file.getFilePath());
                    }
                });
            }
        }

        Integer applyId = innovateApplyService.add(ObjectTransUtils.SeUserToUser(user), InnovateApply.TYPE_MICRO, innovateMicro.getPrice());

        innovateMicro.setDeptid(user.getDeptid());
        innovateMicro.setPid(applyId);
        innovateMicro.setAddTime(new Date());
        innovateMicro.setCate1("微创新");

        Integer id = innovateMicroService.addOne(innovateMicro);
        if (null != id) {
            //更新建议的状态
            innovateApplyService.updateStatus(innovateMicro.getAdviceId(), InnovateApply.STATUS_ADVICE);

            WorkResult workResult = new WorkResult();
            workResult.setAddTime(new Date());
            // workResult.setAdminId(user.getUserid());
            workResult.setType("txtimg");
            workResult.setWorkId(innovateMicro.getPid());
            workResult.setWorkType(3);
            workResult.setOrderid(0);
            // 设置imageIds
            workResult.setImageIds(fileIds == null ? "" : TypeUtils.listToStr(fileIds));

            String[] imgs = innovateMicro.getImgs().split(",");
            String images = "";

            for (String s : filePaths){
                images = images + "{\"url\":\"" + s + "\"},";
            }
            images = images.substring(0, images.length() > 0 ? images.length() : 0);

            if(images.length() > 0)
                images = images.substring(0, images.length() - 1);

            String txt = innovateMicro.getTxt();
            workResult.setTxt(txt);
            if (txt.contains("\'")) {
                txt = txt.replace("\'", "\\'");
            }
            String content = "{\"txt\":\"" + txt + "\", \"imgs\":[" + images + "]}";
            workResult.setContent(content);
            Integer integer1 = workResultService.save(workResult);
            if (null != integer1) {

                // 推送消息
                ApplyPush push = new ApplyPush(ApplyPush.Type.INNOVATION_ADVICE_MRICO, user.getUserid(), innovateMicro.getAdviceId().toString());
                push.setAgentId(Agent.ADMIN.getAgentId());
                push.setMsg("创新项目资金申请");
                weixinPush.apply(push);

                return ActionResult.ok("success", id);
            } else {
                return ActionResult.error("fail");
            }
        } else {
            return ActionResult.error("fail");
        }
    }

    /**
     * 查询用户
     * @return
     */
    @RequestMapping
    public ActionResult query(String name) {
        SeUser user = UserSession.getUser();
        ActionResult actionResult = ActionResult.ok();
//        actionResult.setData(user2Service.getList(user.getDeptid(), name));
        actionResult.setData(user2Service.getList( name));
        return actionResult;
    }


    /**
     * 添加成员
     * @param workUser
     * @return
     */
    @RequestMapping
    public ActionResult addUser(WorkUser workUser) {
        ActionResult actionResult = ActionResult.ok();
        User user = userService.getUser(workUser.getUserid());
        workUser.setName(user.getName());
        workUser.setUserType(1);
        workUser.setTypeName("成员");
        workUser.setStatus(1);
        workUser.setWorkType(3);
        Integer integer = workUserService.saveUser(workUser);
        return actionResult;
    }

    /**
     * 详情
     * @param model
     * @param query
     * @throws UnsupportedEncodingException
     */
    @RequestMapping
    public void detail(Model model, QueryInnovateMicro query) throws UnsupportedEncodingException {
        InnovateMicro innovateMicro = innovateMicroService.getOne(query);
        model.put("data", innovateMicro);
        WorkUserQuery workUserQuery = new WorkUserQuery();
        workUserQuery.setWorkId(innovateMicro.getId());
        workUserQuery.setStatus(null);
        workUserQuery.setWorkType(3);
        model.put("users", workUserService.getUsers(workUserQuery));

        String content = innovateMicro.getContent();
        Map map = JsonUtils.parseMap(content, String.class, Object.class);
        ArrayList imgs = (ArrayList) map.get("imgs");
        String txt = (String) map.get("txt");
        List<String> imgList = new ArrayList<String>();
        for (int i = 0; i < imgs.size(); i++) {
            Map map2 = (Map) imgs.get(i);
            String img = (String) map2.get("url");
            imgList.add(URLDecoder.decode(img, "UTF-8"));
        }
        model.put("imgList", imgList);
        model.put("txt", URLDecoder.decode(txt, "UTF-8"));
        model.put("showType", query.getShowType());
    }
    
}

