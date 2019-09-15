package wxgh.wx.web.manage.commit;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.utils.StrUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.party.tribe.CommInfo;
import wxgh.entity.pub.SysFile;
import wxgh.entity.tribe.TribeAct;
import wxgh.entity.tribe.TribeActComment;
import wxgh.entity.tribe.TribeActJoin;
import wxgh.param.party.tribe.JoinParam;
import wxgh.query.tribe.TribeActQuery2;
import wxgh.sys.service.weixin.tribe.TribeActCommentService;
import wxgh.sys.service.weixin.tribe.TribeActJoinService;
import wxgh.sys.service.weixin.tribe.TribeActService;

import java.io.File;
import java.util.*;

@Controller
public class ApiController {

    @Autowired
    private TribeActJoinService tribeActJoinService;

    @Autowired
    private TribeActService tribeActService;


    /**
     * 报名参加
     *
     * @param actId
     * @return
     */
    @RequestMapping
    public ActionResult join(Integer actId) throws WeixinException {
        TribeActJoin actJoin = new TribeActJoin();
        String thisUserid = UserSession.getUserid();
        actJoin.setUserid(thisUserid);
        actJoin.setActId(actId);
        actJoin.setStatus(1);
        actJoin.setIntegral(1);
        actJoin.setJoinTime(new Date());
        tribeActJoinService.addManage(actJoin);

        /*
        TribeActJoin tribeActJoin1 = new TribeActJoin();
        tribeActJoin1.setActId(actId);
        List<TribeActJoin> tribeActJoins1 = tribeActJoinService.getAll(tribeActJoin1);

        // 满足条件创建群组
        if (3 == tribeActJoins1.size()) {
            TribeAct tribeAct = tribeActService.getOne(actId);
            createGroup(tribeAct, tribeActJoins1, tribeActJoins1.get(0));
        } else if (tribeActJoins1.size() > 3) {
            WxConversation wxConversation = new WxConversation();
            wxConversation.setActId(actId);
            WxConversation wxConversation1 = conversationService.get(wxConversation);
            // 获取活动信息
            TribeAct tribeAct = tribeActService.getOne(actId);

            // 如果群组不存在  则创建
            if (wxConversation1 == null) {
                Chat chatGroup = createGroup(tribeAct, tribeActJoins1, tribeActJoins1.get(0));
                wxConversation1 = new WxConversation();
                wxConversation1.setChatid(chatGroup.getChatid());
                wxConversation1.setName(chatGroup.getName());
            }

            UpdateChat updateChat = new UpdateChat();
            List<String> userids = new ArrayList<String>();
            userids.add(thisUserid);

            updateChat.setChatid(wxConversation1.getChatid());
            updateChat.setAddUsers(userids);
            updateChat.setOpUser(tribeAct.getLeader());
            updateChat.setName(wxConversation1.getName());
            updateChat.setOwner(tribeAct.getLeader());
            ChatApi.update(updateChat);

            for (String userid : updateChat.getAddUsers()) {
                User user = userService.getUser(userid);
                WxConverUser wxConverUser = new WxConverUser();
                wxConverUser.setUserid(userid);
                wxConverUser.setNick(user.getName());
                wxConverUser.setConverId(updateChat.getChatid());
                wxConverUser.setAddTime(new Date());
                wxConverUser.setType(1); // 普通成员
                converUserService.save(wxConverUser);
            }

            // 会话创建成功发送一条消息
            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setSender(updateChat.getOwner());
            chatMsg.setMsg(new Text(UserSession.getUser().getName() + " 加入聊天"));
            chatMsg.setReceiverType(Chat.Type.GROUP);
            chatMsg.setReceiverId(wxConversation1.getChatid());
            ChatApi.send(chatMsg);

        }
        */

        return ActionResult.ok();
    }

    /**
     * 评论列表
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult comm_list(Integer id) {
        List<CommInfo> commInfos = tribeActService.listCommManage(id);
        return ActionResult.okWithData(commInfos);
    }

    @RequestMapping
    public ActionResult join_list(JoinParam param) {
        param.setPageIs(true);
        param.setStatus(1);
        List<TribeActJoin> joins = tribeActJoinService.listJoinManage(param);
        return ActionResult.okRefresh(joins, param);
    }

    @RequestMapping
    public ActionResult add_comment(TribeActComment tribeActComment) {
        //判断档案是否符合条件
        if (StrUtils.empty(tribeActComment.getContent())) {
            return ActionResult.error("评论不能为空哦");
        }

        if (tribeActComment.getImg() != null) {
            //分割字符串
            String[] imgs = tribeActComment.getImg().split(",");
            //倒序
            Collections.reverse(Arrays.asList(imgs));
            //清空imgList
            List<String> imgsStrs = new ArrayList<>();
            for (String mediaId : imgs) {
                fileApi.wxDownload(mediaId, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        imgsStrs.add(file.getFileId());
                    }
                });
            }

            tribeActComment.setImg(TypeUtils.listToStr(imgsStrs));
        } else {
            tribeActComment.setImg(null);
        }

        SeUser user = UserSession.getUser();
        tribeActComment.setUserid(user.getUserid());
        tribeActComment.setAddTime(new Date());

        tribeActCommentService.addManage(tribeActComment);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult list_act(TribeActQuery2 tribeActQuery2) {
        //设置条数
        if (tribeActQuery2.getStatus() == null) {
            tribeActQuery2.setStatus(1);
        }

        tribeActQuery2.setPageIs(true);

        List<TribeAct> yugao = tribeActService.getDatas2(tribeActQuery2);

        return ActionResult.okRefresh(yugao, tribeActQuery2);
    }

    @Autowired
    private TribeActCommentService tribeActCommentService;

    @Autowired
    private FileApi fileApi;
}
