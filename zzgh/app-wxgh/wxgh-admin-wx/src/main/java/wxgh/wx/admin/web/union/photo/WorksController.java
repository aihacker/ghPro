package wxgh.wx.admin.web.union.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.TypeUtils;
import wxgh.app.utils.UrlUtils;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.entity.party.match.SheyingMatch;
import wxgh.entity.party.match.SheyingMatchJoin;
import wxgh.param.union.group.user.ListParam;
import wxgh.sys.service.weixin.party.beauty.WorkFileService;
import wxgh.sys.service.weixin.party.match.SheyingMatchJoinService;
import wxgh.sys.service.weixin.party.match.SheyingMatchService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

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
public class WorksController {

    @Autowired
    private SheyingMatchJoinService sheyingMatchJoinService;

    @Autowired
    private WorkFileService workFileService;

    @Autowired
    private SheyingMatchService sheyingMatchService;

    @RequestMapping
    public ActionResult update(Integer id, Integer status){
        sheyingMatchJoinService.updateStatus(id,status);
        return ActionResult.ok();
    }

    @RequestMapping
    public void joins(Model model, Integer id){
        model.put("id", id);
    }

    @RequestMapping
    public ActionResult list(ListParam param){
        return ActionResult.okRefresh(sheyingMatchJoinService.listUser(param), param);
    }

    @RequestMapping
    public void index(Model model, Integer id) throws UnsupportedEncodingException {

        Map<Integer, List<WorkFile>> fileMap = workFileService.getMatchFiles(id);

        List<SheyingMatchJoin> joins = sheyingMatchJoinService.getJoinsBy();
        if (!TypeUtils.empty(joins)) {
            for (int i = 0; i < joins.size(); i++) {
                List<WorkFile> files = fileMap.getOrDefault(joins.get(i).getId(), null);
                if (!TypeUtils.empty(files)) {
                    for (int j = 0; j < files.size(); j++) {
                        WorkFile tmpFile = files.get(j);
                        files.get(j).setPath(tmpFile.getPath());
                        files.get(j).setThumb(tmpFile.getThumb());
                    }
                }
                joins.get(i).setFiles(files);
                joins.get(i).setRemark(UrlUtils.URLDecode(joins.get(i).getRemark()));
            }
        }

        SheyingMatch match = sheyingMatchService.getMatch(id);

        model.put("joins", joins);
        model.put("match", match);

    }
    
}

