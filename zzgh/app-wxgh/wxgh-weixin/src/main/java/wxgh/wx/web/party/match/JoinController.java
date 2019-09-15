package wxgh.wx.web.party.match;

import com.libs.common.type.StringUtils;
import com.weixin.bean.FileBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.TypeUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.entity.party.match.SheyingMatchJoin;
import wxgh.entity.pub.SysFile;
import wxgh.param.party.match.JoinQuery;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.weixin.party.beauty.WorkFileService;
import wxgh.sys.service.weixin.party.match.SheyingMatchJoinService;
import wxgh.sys.service.weixin.party.match.SheyingMatchService;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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
 * @Date 2017-08-07 16:00
 *----------------------------------------------------------
 */
@Controller
public class JoinController {

    @Autowired
    private SheyingMatchJoinService sheyingMatchJoinService;

    @Autowired
    private SheyingMatchService sheyingMatchService;

    @Autowired
    private WorkFileService workFileService;

    @Autowired
    private FileApi fileApi;

    @RequestMapping
    public String index(Model model, Integer id) {
        SheyingMatchJoin syj = sheyingMatchJoinService.get(UserSession.getUserid(), id);
        if(syj != null && syj.getStatus() == 1)
           return "redirect:" + PathUtils.getHostAddr() + "/wx/party/match/me/index.html?id=" + id;
        return  "index";
//        SheyingMatch sheyingMatchQuery = new SheyingMatch();
//        sheyingMatchQuery.setId(id);
//        List<SheyingMatch> matches = sheyingMatchService.getData(sheyingMatchQuery);
//        if (!TypeUtils.empty(matches)) {
//            model.addAttribute("data", matches.get(0));
//        }  // 原来的
    }

    @RequestMapping
    public ActionResult join(SheyingMatchJoin join) {
        SeUser user = UserSession.getUser();
        join.setUserid(user.getUserid());
        sheyingMatchJoinService.updateJoin(join);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult success(JoinQuery query) {
        SeUser user = UserSession.getUser();
        query.setStatus(1);
        query.setUserid(user.getUserid());
//        sheyingMatchJoinService.updateStatus(query);
        System.out.println("mid:" + query.getMid());
        sheyingMatchJoinService.updateStatus(query.getMid());
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult addDataImage(SheyingMatchJoin sheyingMatchJoin) throws IOException {

        String[] imgs = sheyingMatchJoin.getWorks().split(",");
        for (String mediaId : imgs) {
//                File file = PathUtils.getUpload(PathUtils.PATH_MATERIA, "", mediaId, false);
//                downWxImgTask.asyncDownloadImage(mediaId, file.getAbsolutePath());
            fileApi.wxDownload(mediaId, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {

                }
            });
        }

        SeUser user = UserSession.getUser();
        sheyingMatchJoin.setUserid(user.getUserid());
        sheyingMatchJoin.setStatus(1);
        sheyingMatchJoin.setRemark(URLEncoder.encode(sheyingMatchJoin.getRemark(), "UTF-8"));
        sheyingMatchJoin.setAddTime(new Date());
        sheyingMatchJoinService.addData(sheyingMatchJoin);
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

        if (!TypeUtils.empty(workFiles)) {
            workFileService.saveList(workFiles);
        }
        return ActionResult.ok();
    }

/*
    @RequestMapping
    public ActionResult addDataVideo(SheyingMatchJoin sheyingMatchJoin, @RequestParam(value = "videoFile", required = true) MultipartFile multipartFile) throws IOException {

        File toFile = PathUtils.getUpload(PathUtils.PATH_SHEYING_MATCH_WORKS, multipartFile.getOriginalFilename(), StrUtils.getUUID());
        multipartFile.transferTo(toFile);
        sheyingMatchJoin.setWorks(PathUtils.getUploadPath(toFile));
        SeUser user = UserSession.getUser();
        sheyingMatchJoin.setUserid(user.getUserid());
        sheyingMatchJoin.setStatus(1);
        sheyingMatchJoin.setRemark(URLEncoder.encode(sheyingMatchJoin.getRemark(), "UTF-8"));
        sheyingMatchJoin.setAddTime(new Date());

        String videoPath = toFile.getPath();

        String frontImagePath = "uploads" + PathUtils.PATH_SHEYING_MATCH_WORKS;

        Integer date = DateFuncs.getIntToday();
        frontImagePath += (File.separator + date);

        String imageName = StrUtils.getUUID() + new Date().getTime() + ".jpg";

        String imagePath = File.separator + frontImagePath + File.separator + imageName;
        String imagePathNew = frontImagePath + File.separator + imageName;


        imagePath = imagePath.replaceAll("\\\\", "/");
        sheyingMatchJoin.setPreviewImage(imagePath);

        imagePath = PathUtils.getRealPath() + imagePathNew;

        VideoUtils.getVideoImage(videoPath, imagePath);

        sheyingMatchJoinService.addData(sheyingMatchJoin);
        return ActionResult.ok();
    }

    private String saveFile(MultipartFile multipartFile, String imgs) throws IOException {
        if (multipartFile != null) {
            File toFile = PathUtils.getUpload(PathUtils.PATH_SHEYING_MATCH_WORKS, multipartFile.getOriginalFilename(), StrUtils.getUUID());
            multipartFile.transferTo(toFile);
            return imgs += PathUtils.getUploadPath(toFile) + ",";
        } else {
            return imgs;
        }
    }
    */
}

