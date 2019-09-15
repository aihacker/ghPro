package wxgh.wx.web.union.innovation.team;


import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import com.weixin.utils.MenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.union.group.user.ApplyCount;
import wxgh.data.union.innovation.group.ChatGroupData;
import wxgh.data.union.innovation.group.ChatUserData;
import wxgh.data.union.innovation.group.MyGroupData;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.entity.pub.SysFile;
import wxgh.param.union.innovation.group.GroupQuery;
import wxgh.param.union.innovation.team.TeamListParam;
import wxgh.sys.service.weixin.union.innovation.team.ChatGroupService;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description 项目团队
 * ----------------------------------------------------------
 * @Author Ape<阿佩>
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-19 16:31
 * ----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private FileApi fileApi;

    /**
     * 首页
     *
     * @param model
     */
    @RequestMapping
    public void index(Model model) {
    }

    /**
     * 创建团队页面
     *
     * @param model
     */
    @RequestMapping
    public void create(Model model) throws WeixinException {
        List<String> apiList = ApiList.getImageApi();
        apiList.add(ApiList.OPENENTERPRISECONTACT);
        WeixinUtils.sign(model, apiList);
        WeixinUtils.sign_contact(model);
    }

    /**
     * 所有团队
     */
    @RequestMapping
    public void all() {

    }

    /**
     * 我的团队
     */
    @RequestMapping
    public void my() {

    }

    /**
     * 团队列表
     */
    @RequestMapping
    public ActionResult lists(GroupQuery param) {
        param.setType(ChatGroup.TYPE_TEAM);
        param.setPageIs(true);
        List<ChatGroupData> groups = chatGroupService.getList(param);
        return ActionResult.okRefresh(groups, param);
    }

    /**
     * 我的团队
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult list(GroupQuery param) {
        if (param.getStatus() == null) {
            param.setStatus(Status.NORMAL.getStatus());
        }
        SeUser user = UserSession.getUser();
        param.setUserid(user.getUserid());

        param.setType(ChatGroup.TYPE_TEAM);
        param.setPageIs(true);
        param.setRowsPerPage(10);

        List<ChatGroupData> groups = chatGroupService.getMyGroups(param);

        return ActionResult.okRefresh(groups, param);
    }

    /**
     * 创建团队
     *
     * @param group
     * @param avatar
     * @return
     */
    @RequestMapping
    public ActionResult add(ChatGroup group, String avatar) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录哦");
        }
        if (TypeUtils.empty(group.getName()))
            return ActionResult.error("请输入团队名称");

        if (!StrUtils.empty(avatar)) {
            fileApi.wxDownload(avatar, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    group.setAvatar(file.getFileId());
                }
            });
        }

        // 插入数据
        ChatGroup chatGroup = chatGroupService.addGroup(user.getUserid(), group);

        // TODO 推送消息给管理员，待实现

        return ActionResult.ok(null, chatGroup.getGroupId());
    }


    /**
     * 添加会员接口
     *
     * @return
     */
    @RequestMapping
    public ActionResult addUser(String userid, String groupId) {
        ActionResult actionResult = ActionResult.ok();

        ChatUser chatUser = new ChatUser();
        chatUser.setGroupId(groupId);
        chatUser.setUserid(userid);
        chatUser.setAddTime(new Date());
        chatUser.setType(ChatUser.TYPE_MEMBER);
        chatUser.setStatus(Status.NORMAL.getStatus());
        chatGroupService.addUser(chatUser);

//        GroupUser member1 = group2Service.getGroupUser(groupUser.getUserid(), groupUser.getGroupId());
//
//        //当用户不存在，则添加
//        //若用户存在，且未加入到团队
//        if (member1 == null || (member1 != null && member1.getStatus() != 1)) {
//            groupUser.setJoinTime(new Date());
//            groupUser.setType(1);
//            groupUser.setStatus(1);
//            groupUserService.addOrUpdateUser(groupUser);
//        }
        return actionResult;
    }

    /**
     * 展示团队详情
     *
     * @param model
     * @param id
     * @throws WeixinException
     */
    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {

        // 获取团队信息
        MyGroupData group = chatGroupService.getGroup(id);
        GroupQuery query = new GroupQuery();

        if (group == null)
            return;

        query.setGroupId(id);
        query.setStatus(1);

        SeUser user = UserSession.getUser();
        boolean hasPermission = false;
        if (user != null) {
            query.setUserid(user.getUserid());
            query.setStatus(null);
            // 查询协会用户
//            ChatUserData u = chatGroupService.getUser(query);
            ChatUserData u = chatGroupService.getUser(user.getUserid(), group.getGroupId());

            model.put("u", u);
            // 判断是否为创建人
            if (u != null && u.getType() == ChatUser.TYPE_CREATEOR) {
                hasPermission = true;
            }
        }

        query.setUserid(null);
        query.setRowsPerPage(hasPermission ? 3 : 4);
        query.setStatus(1);
        query.setPageIs(true);

        TeamListParam listParam = new TeamListParam();
        listParam.setGroupId(id);
        listParam.setStatus(1);
        listParam.setPageIs(true);
        listParam.setRowsPerPage(hasPermission ? 3 : 4);

        List<ChatUserData> members = chatGroupService.listUser(listParam);

        model.put("g", group);
        model.put("members", members);

        model.put("hasPermission", hasPermission); //是否为团队创建者
        model.put("count", chatGroupService.getUserCount(id));

        List<String> apis = ApiList.getShareApi();
        if (hasPermission) {
            apis.add(ApiList.OPENENTERPRISECONTACT);
            apis.add(ApiList.CHOOSEIMAGE);
            apis.add(ApiList.PREVIEWIMAGE);
            apis.add(ApiList.UPLOADIMAGE);
            WeixinUtils.sign_contact(model);
        }

        //判断是否有最新的申请请求
        query.setStatus(0);
        ApplyCount newCount = chatGroupService.getCount(id);
        model.put("newCount", newCount.getApply());

        WeixinUtils.sign(model, apis, MenuList.getShareMenuList());
    }

}
