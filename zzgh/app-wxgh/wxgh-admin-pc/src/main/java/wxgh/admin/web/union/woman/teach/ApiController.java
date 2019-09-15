package wxgh.admin.web.union.woman.teach;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pub.dao.page.Page;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.union.woman.teach.TeachList;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.woman.WomanTeach;
import wxgh.sys.service.weixin.union.woman.TeachService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(Page page) {
        page.setPageIs(true);

        List<TeachList> teachs = teachService.teachList(page);

        return ActionResult.okAdmin(teachs, page);
    }

    @RequestMapping
    public ActionResult delete(String id) {
        teachService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add(WomanTeach teach, Integer push, HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("upfiles");
            List<String> fileIds = new ArrayList<>();
            for (MultipartFile f : files) {
                SysFile sysFile = fileApi.addFile(f, null);
                fileIds.add(sysFile.getFileId());
            }
            teach.setFiles(TypeUtils.listToStr(fileIds));
        }
        Integer teachId = teachService.addTeach(teach);
        if (push != null && push == 1) {
            weixinPush.womanTeach(teachId);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult edit(WomanTeach teach, HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest m = (MultipartHttpServletRequest) request;
            if(m != null){
                List<MultipartFile> files = m.getFiles("upfiles");
                List<String> fileIds = new ArrayList<>();
                for (MultipartFile f : files) {
                    SysFile sysFile = fileApi.addFile(f, null);
                    fileIds.add(sysFile.getFileId());
                }
                teach.setFiles(TypeUtils.listToStr(fileIds));
            }
        }
        teachService.editTeach(teach);
        return ActionResult.ok();
    }

    @Autowired
    private TeachService teachService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;
}
