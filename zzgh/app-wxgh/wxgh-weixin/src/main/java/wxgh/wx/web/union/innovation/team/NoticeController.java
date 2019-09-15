package wxgh.wx.web.union.innovation.team;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.entertain.act.ActList;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.notice.Notice;
import wxgh.entity.pub.SysFile;
import wxgh.param.entertain.act.ActParam;
import wxgh.sys.service.weixin.entertain.act.ActService;
import wxgh.sys.service.weixin.notice.NoticeService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ----------------------------------------------------------
 * @Description
 * ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-15  18:15
 * ----------------------------------------------------------
 */
@Controller
public class NoticeController {

    @Autowired
    private FileApi fileApi;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ActService actService;

    @RequestMapping
    public void index(Model model, ActParam param) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
        param.setPageIs(false);
        param.setStatus(Status.NORMAL.getStatus());
        param.setActType(Act.ACT_TYPE_TEAM);
        List<ActList> acts = actService.getTeamActList(param);
        model.put("actlist", acts);
    }

    @RequestMapping
    public ActionResult add(Notice teamNotice, Integer groupId) throws UnsupportedEncodingException {

        SeUser user = UserSession.getUser();

        if (null != teamNotice.getImg()) {

            String[] imgs = teamNotice.getImg().split(",");
            String mediaId = imgs[0];
//                File file = PathUtils.getUpload(PathUtils.PATH_MATERIA, "", mediaId);
//                downWxImgTask.syncDownLoadImage(mediaId, file.getAbsolutePath());
//                teamNotice.setImg(PathUtils.getUploadPath(file));
            fileApi.wxDownload(mediaId, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    teamNotice.setImg(file.getFilePath());
                    teamNotice.setImageId(file.getFileId());
                }
            });

        }

        teamNotice.setSubjectType(Notice.SUBJECT_Team);
        teamNotice.setAddTime(new Date());
        teamNotice.setDeptid(user.getDeptid());
        Integer id = noticeService.add(teamNotice);

        // TODO 推送
//        ChatMsg chatMsg = new ChatMsg();
//        chatMsg.setUserid(user.getUserid());
//        chatMsg.setDeptid(groupId);
//        chatMsg.setDeptType("team");
//        chatMsg.setSendTime(new Date());
//        chatMsg.setMsgType("notice");

        String imgSrc;
        if (!StrUtils.empty(teamNotice.getImg())) {
            imgSrc = teamNotice.getImg();
        } else {
            imgSrc = PathUtils.getContextPath() + "/image/default/notice.png";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

//        String content = "{\"title\":\" " + URLEncoder.encode(teamNotice.getTitle(), "utf-8") + " \", \"time\":\"" + formatter.format(new Date()) + "\", \"imgSrc\":\"" + imgSrc + "\", \"txt\":\"" + URLEncoder.encode(teamNotice.getContent(), "utf-8") + "\", \"url\":\"" + PathUtils.getHostAddr() + "/projectTeam/notice_detail.wx?id=" + id + "&type=" + teamNotice.getType() + "\"}";
//        chatMsg.setContent(content);
//        chatMsgService.saveMsg(chatMsg);

        return ActionResult.ok();
    }

}
