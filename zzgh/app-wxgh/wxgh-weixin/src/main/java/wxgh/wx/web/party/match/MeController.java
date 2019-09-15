package wxgh.wx.web.party.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.TypeUtils;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.entity.party.match.SheyingMatchJoin;
import wxgh.sys.service.weixin.party.beauty.WorkFileService;
import wxgh.sys.service.weixin.party.match.SheyingMatchJoinService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 16:04
 *----------------------------------------------------------
 */
@Controller
public class MeController {

    @RequestMapping
    public void index(Model model, Integer id) {
        SheyingMatchJoin join = sheyingMatchJoinService.getJoin(id);

        List<WorkFile> files = workFileService.getFiles(id, WorkFile.TYPE_MATCH);
        if (!TypeUtils.empty(files)) {
            for (int i = 0; i < files.size(); i++) {
                WorkFile tmp = files.get(i);
                files.get(i).setPath(tmp.getPath());
                files.get(i).setThumb(tmp.getThumb());
            }
        }
        model.put("files", files);
        model.put("join", join);
    }

    @RequestMapping
    public ActionResult delete(Integer id) {
//        workFileService.delete(id);
        workFileService.updateStatus(id, 2); //已删除
        return ActionResult.ok();
    }

    @Autowired
    private WorkFileService workFileService;

    @Autowired
    private SheyingMatchJoinService sheyingMatchJoinService;
    
}

