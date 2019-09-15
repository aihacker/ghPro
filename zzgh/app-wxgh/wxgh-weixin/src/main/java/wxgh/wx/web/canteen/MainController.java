package wxgh.wx.web.canteen;

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
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.data.union.group.GroupList;
import wxgh.data.union.group.GroupShow;
import wxgh.entity.canteen.Canteen;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.canteen.CanteenShow;
import wxgh.entity.canteen.CanteenUser;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.group.GroupUser;
import wxgh.param.union.group.ListParam;
import wxgh.sys.service.weixin.canteen.CanteenService;
import wxgh.sys.service.weixin.canteen.CanteenUserService;

import java.io.File;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private FileApi fileApi;

    @Autowired
    private CanteenService canteenService;

    @Autowired
    private CanteenUserService canteenUserService;

    /**
     * 我的饭堂列表
     */
    @RequestMapping
    public void index() {
    }

    /**
     * 现有饭堂
     */
    @RequestMapping
    public void search() {

    }

    /**
     * 添加饭堂
     */
    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model);
    }

    /**
     * 获取当前用户所加入的饭堂
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

        List<GroupList> groups = canteenService.listMeGroup(param);

        return ActionResult.okRefresh(groups, param);
    }


    /**
     * 添加饭堂
     *
     * @param group
     * @return
     */
    @RequestMapping
    public ActionResult add_api(Canteen canteen) {
        if (!TypeUtils.empty(canteen.getAvatarId())) {
            fileApi.wxDownload(canteen.getAvatarId(), new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    canteen.setAvatarId(file.getFileId());
                }
            });
        }
        Integer groupId = canteenService.addGroup(canteen);


        /**
         * 以下推送消息给管理员，待实现
         */
//        ApplyPush push = new ApplyPush(ApplyPush.Type.GROUP, UserSession.getUserid(), groupId.toString());
//        push.setMsg("“" + group.getName() + "”协会申请");
//        weixinPush.apply(push);

        return ActionResult.ok();
    }

    /**
     * 饭堂详情页面
     *
     * @param model
     * @param id
     * @throws WeixinException
     */
    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {
        String userid = UserSession.getUserid();
        CanteenShow group = canteenService.getInfo(id, userid);

        boolean hasPermission = false;
        //判断用户是否拥有权限
        if (group.getUserType() != null) {
            group.setJoin(true);
            if (group.getUserType() <= GroupUser.TYPE_GL) {
                hasPermission = true;
            }
        }

        //获取协会前4个用户头像
        List<String> avatars = canteenUserService.getGroupAvatar(group.getGroupId(), hasPermission ? 3 : 4);
        group.setAvatars(avatars);

        //判断当前用户是否已经提交申请等
        CanteenUser curU = canteenUserService.getUser(userid, group.getGroupId());

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


}
