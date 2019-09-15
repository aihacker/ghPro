package wxgh.app.web.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.api.FileApi;
import wxgh.entity.pub.SysFile;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.weixin.pub.SysFileService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
@Controller
public class MainController {

    private static final Log log = LogFactory.getLog(MainController.class);

    @RequestMapping
    public ActionResult upload(HttpServletRequest request, FileParam param) {
        ActionResult result;
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> fileMap = mulRequest.getMultiFileMap();
            if (fileMap != null && !fileMap.isEmpty()) {
                List<String> fileIds = new ArrayList<>();
                for (MultiValueMap.Entry<String, List<MultipartFile>> entry : fileMap.entrySet()) {
                    for (MultipartFile f : entry.getValue()) {
                        SysFile sysFile = fileApi.addFile(f, param);

                        fileIds.add(sysFile.getFileId());
                    }
                }
                result = ActionResult.okWithData(fileIds);
            } else {
                result = ActionResult.fail("没有上传的文件");
            }
        } else {
            result = ActionResult.fail("没有上传的文件");
        }
        return result;
    }

    @RequestMapping
    public ActionResult delete(String id) {
        sysFileService.delete(id);
        return ActionResult.ok();
    }

    @Autowired
    private FileApi fileApi;

    @Autowired
    private SysFileService sysFileService;
}
