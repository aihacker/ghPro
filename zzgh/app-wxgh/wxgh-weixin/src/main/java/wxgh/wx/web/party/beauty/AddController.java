package wxgh.wx.web.party.beauty;

import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.bean.FileBean;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.data.pub.push.Push;
import wxgh.entity.party.beauty.Work;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.entity.pub.SysFile;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.weixin.party.beauty.WorkFileService;
import wxgh.sys.service.weixin.party.beauty.WorkService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 09:41
 *----------------------------------------------------------
 */
@Controller
public class AddController {

    @Autowired
    private WorkService workService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WorkFileService workFileService;

    @Autowired
    private WeixinPush weixinPush;

    public static final String USERIDS = "zzj";


    @Autowired
    private PushAsync pushAsync;

    @RequestMapping
    public void index(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());

        SeUser user = UserSession.getUser();
        model.put("user", user);
    }

    @RequestMapping
    public ActionResult save(Work work) {
        SeUser user = UserSession.getUser();
        work.setUserId(user.getUserid());
        work.setAddTime(new Date());
        work.setStatus(0);
        workService.saveWork(work);

        return ActionResult.ok(null, work.getId());
    }

    @RequestMapping
    public ActionResult success(Integer id) {
        workService.updateStatus(id, 0);
        SeUser user = UserSession.getUser();

//        // TODO 推送
//        Push push = new Push();
//        push.setAll(true);
////        List<String> us = new ArrayList<>();
////        us.add("15902064445");
////        push.setTousers(us);
//        push.setAgentId(Agent.SHEYING.getAgentId());
//        pushAsync.sendBySheYing(id, push);

        //推送管理员消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.PHOTO, user.getUserid(), id.toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("魅美影像发布申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult upload(@RequestParam("file") MultipartFile[] files, Integer workid, Integer worktype, Integer mftype) {
        List<WorkFile> workFiles = new ArrayList<>();

        String fileIds = "";
        for (MultipartFile file : files) {
            File toFile = null;
            File thumbFile = null;
            WorkFile workFile = new WorkFile();

            String fileId = StringUtils.uuid();
            try {
                toFile = PathUtils.getUpload(fileId, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileBean fileBean = new FileBean(file, fileId);
            fileBean.setSaveFile(toFile);

            workFile.setWorkId(workid);
            SysFile sysFile = null;
            // 缩略图
            if (worktype == 1) { //图片
                sysFile = fileApi.addFile(fileBean, new FileParam());
            } else {
                FileParam param = new FileParam();
                param.setType("video");
                sysFile = fileApi.addFile(fileBean, param);
            }

            fileIds += sysFile.getFileId() + ",";

            workFile.setMd5(sysFile.getMd5());
            workFile.setFilename(sysFile.getFilename());
            workFile.setSize(sysFile.getSize());
            workFile.setMimetype(sysFile.getMimeType());
            workFile.setPath(sysFile.getFilePath());
            workFile.setAddTime(new Date());
            workFile.setType(mftype == null ? 1 : mftype);
            workFile.setThumb(sysFile.getThumbPath());

            workFiles.add(workFile);
        }

        Work work = workService.get(workid);
        if(!TypeUtils.empty(fileIds))
            work.setFileIds(fileIds.substring(0, fileIds.length() - 1));
        workService.saveWork(work);

        if (!TypeUtils.empty(workFiles)) {
            workFileService.saveList(workFiles);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add(Work work, HttpSession session) {
        if (StrUtils.empty(work.getFiles())) {
            return ActionResult.error("图片不能为空哦");
        }

        SeUser user = UserSession.getUser();

        String[] imgs = work.getFiles().split(",");
        List<String> list = new ArrayList<>();
        for (String mediaId : imgs) {

            fileApi.wxDownload(mediaId, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    list.add(file.getFileId());
                }
            });
        }
        // fileIds
        String fileIds = TypeUtils.listToStr(list);
        work.setFiles(fileIds);
        work.setFileIds(fileIds);

        work.setAddTime(new Date());
        work.setUserId(user.getUserid());
        work.setStatus(1);

        Integer wId = workService.saveWork(work);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add2(@RequestParam("uploadvideo") MultipartFile file, Work work) {
        SeUser user = UserSession.getUser();

        work.setAddTime(new Date());
        work.setUserId(user.getUserid());
        work.setStatus(1);

        if (!file.isEmpty()) {
            if (!StrUtils.empty(file.getOriginalFilename())) {

                SysFile sysFile = fileApi.addFile(file, null);

                work.setFiles(sysFile.getFileId());
                work.setFileIds(sysFile.getFileId());
                Integer wId = workService.saveWork(work);


                // TODO 推送
                Push push = new Push();
                        push.setAll(true);
//                List<String> us = new ArrayList<>();
//                us.add("15902064445");
//                push.setTousers(us);
                push.setAgentId(Agent.SHEYING.getAgentId());
                pushAsync.sendBySheYing(wId, push);

                return ActionResult.ok();
            } else {
                return ActionResult.error("视频不能为空");
            }
        } else {
            return ActionResult.error("视频不能为空");
        }
    }

    
}

