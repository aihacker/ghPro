package wxgh.wx.web.union.group;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.union.group.add.AddUser;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.group.Group;
import wxgh.sys.service.weixin.union.group.GroupService;
import wxgh.sys.service.weixin.union.group.GroupUserService;

import java.io.File;

/**
 * Created by Administrator on 2017/7/21.
 */
@Controller
public class ShowController {

    @RequestMapping
    @ResponseBody
    public ActionResult add_user(@RequestBody AddUser user) {
        groupUserService.addUsers(user.getUsers(), user.getId());
        return ActionResult.ok();
    }

    /**
     * 退出协会或解散协会
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult out(Integer id) {
        return groupService.dissolve(id, UserSession.getUserid());
    }

    /**
     * 申请加入协会
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult apply(Integer id) {
        groupUserService.apply(id, UserSession.getUserid());
        return ActionResult.ok();
    }

    /**
     * 协会信息更新
     *
     * @param group
     * @return
     */
    @RequestMapping
    public ActionResult edit(final Group group) {
        if (!TypeUtils.empty(group.getAvatarId())) {
            fileApi.wxDownload(group.getAvatarId(), new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    group.setAvatarId(file.getFileId());
                }
            });
        }
        groupService.updateGroup(group, UserSession.getUserid());
        return ActionResult.ok();
    }

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private FileApi fileApi;
}
