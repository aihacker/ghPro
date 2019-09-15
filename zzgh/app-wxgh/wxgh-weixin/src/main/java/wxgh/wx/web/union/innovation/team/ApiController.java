package wxgh.wx.web.union.innovation.team;

import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.union.innovation.team.UserJsonItem;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.union.innovation.team.ChatGroupService;

import java.io.File;
import java.util.List;

/**
 * ----------------------------------------------------------
 * @Description
 * ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-14  16:14
 * ----------------------------------------------------------
 */
@Controller
public class ApiController {

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private FileApi fileApi;

    /**
     * 添加用户
     * @param json
     * @param id
     * @return
     */
    @RequestMapping
    @ResponseBody
    public ActionResult add_user(String json, Integer id) {
        List<UserJsonItem> list = JsonUtils.parseList(json, UserJsonItem.class);
        chatGroupService.addUsers(list, id);
        return ActionResult.ok();
    }

    /**
     * 申请加入团队
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult apply(Integer id){
        chatGroupService.apply(id, UserSession.getUserid());
        return ActionResult.ok();
    }

    /**
     * 退出团队或解散团队
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult out(Integer id){
        chatGroupService.out(id, UserSession.getUserid());
        return ActionResult.ok();
    }

    /**
     * 编辑团队
     * @return
     */
    @RequestMapping
    public ActionResult edit(ChatGroup group){
        if (!TypeUtils.empty(group.getAvatar())) {
            fileApi.wxDownload(group.getAvatar(), new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    group.setAvatar(file.getFileId());
                }
            });
        }
        chatGroupService.updateChatGroup(group, UserSession.getUserid());
        return ActionResult.ok();
    }

}
