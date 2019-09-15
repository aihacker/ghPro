package wxgh.wx.web.union.group;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import com.weixin.utils.MenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.data.union.group.GroupList;
import wxgh.data.union.group.GroupShow;
import wxgh.data.union.group.act.account.AccountData;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.group.GroupUser;
import wxgh.param.pub.TipMsg;
import wxgh.param.union.group.ListParam;
import wxgh.sys.service.weixin.union.group.GroupService;
import wxgh.sys.service.weixin.union.group.GroupUserService;
import wxgh.sys.service.weixin.union.group.act.AccountService;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Controller
public class MainController {

    /**
     * 我的协会列表
     */
    @RequestMapping
    public void index() {
    }

    /**
     * 现有协会
     */
    @RequestMapping
    public void search() {

    }

    /**
     * 添加协会
     */
    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model);
    }

    /**
     * 添加协会
     *
     * @param group
     * @return
     */
    @RequestMapping
    public ActionResult add_api(Group group) {
        if (!TypeUtils.empty(group.getAvatarId())) {
            fileApi.wxDownload(group.getAvatarId(), new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    group.setAvatarId(file.getFileId());
                }
            });
        }
        Integer groupId = groupService.addGroup(group);

        /**
         * 以下推送消息给管理员，待实现
         */
        ApplyPush push = new ApplyPush(ApplyPush.Type.GROUP, UserSession.getUserid(), groupId.toString());
        push.setMsg("“" + group.getName() + "”协会申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

    /**
     * 协会详情页面
     *
     * @param model
     * @param id
     * @throws WeixinException
     */
    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {
        String userid = UserSession.getUserid();
        GroupShow group = groupService.getInfo(id, userid);

        boolean hasPermission = false;
        //判断用户是否拥有权限
        if (group.getUserType() != null) {
            group.setJoin(true);
            if (group.getUserType() <= GroupUser.TYPE_GL) {
                hasPermission = true;
            }
        }

        //获取协会前4个用户头像
        List<String> avatars = groupUserService.getGroupAvatar(group.getGroupId(), hasPermission ? 3 : 4);
        group.setAvatars(avatars);

        //判断当前用户是否已经提交申请等
        GroupUser curU = groupUserService.getUser(userid, group.getGroupId());

        List<String> apis = ApiList.getShareApi();
        if (hasPermission) {
            apis.add(ApiList.OPENENTERPRISECONTACT);
            apis.add(ApiList.CHOOSEIMAGE);
            apis.add(ApiList.PREVIEWIMAGE);
            apis.add(ApiList.UPLOADIMAGE);
            WeixinUtils.sign_contact(model);
        }
        WeixinUtils.sign(model, apis, MenuList.getShareMenuList());
        model.put("g", group);
        model.put("hasPermission", hasPermission);
        model.put("u", curU);
    }

    /**
     * 协会活动积分结算
     *
     * @param id 活动Id
     */
    @RequestMapping
    public String account(Integer id, Model model) {
        AccountData accountData = accountService.getAccountData(id);

        //判断当前活动是否有积分
        if (accountData == null) {
            TipMsg tipMsg = TipMsg.info("系统提示", "对不起，该活动没有积分哦！");
            return tipMsg.getTipUrl();
        }

        //活动是否已经结算
        if (1 == accountData.getAccount()) {
            TipMsg tipMsg = TipMsg.info("系统提示", "对不起，活动已经结算哦！");
            return tipMsg.getTipUrl();
        }

        model.put("a", accountData);
        return null;
    }

    /**
     * 获取当前用户所加入的协会
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult list(ListParam param) {
        if (param.getStatus() == null) {
            param.setStatus(Status.NORMAL.getStatus());
        }
        SeUser user = UserSession.getUser();
        param.setUserid(user.getUserid());

        param.setPageIs(true);
        param.setRowsPerPage(10);

        List<GroupList> groups = groupService.listMeGroup(param);

        return ActionResult.okRefresh(groups, param);
    }

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

}
