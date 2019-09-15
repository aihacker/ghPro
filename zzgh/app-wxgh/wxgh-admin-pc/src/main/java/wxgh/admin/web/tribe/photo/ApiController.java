package wxgh.admin.web.tribe.photo;

import com.libs.common.crypt.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.party.match.PhotoMatch;
import wxgh.param.tribe.admin.PhotoParam;
import wxgh.param.union.group.ListParam;
import wxgh.sys.service.admin.tribe.PhotographService;

import java.util.Date;
import java.util.List;

/**
 * Created by cby on 2017/8/29.
 */
@Controller
public class ApiController {
    @Autowired
    private PhotographService photographService;

    @RequestMapping
    public ActionResult save(PhotoMatch match){
        match.setAddTime(new Date());
        match.setContent(URL.encode(match.getContent()));
        match.setRule(URL.encode(match.getRule()));
        photographService.save(match);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult get_results(ListParam param){
        param.setPageIs(true);
        List<PhotoMatch> results = photographService.get_result(param);
        for (int i=0;i<results.size();i++)
            results.get(i).setTel(""+(i+1));
        return ActionResult.okAdmin(results,param);
    }
    @RequestMapping
    public ActionResult total_human(ListParam param){
        param.setPageIs(true);
        List<PhotoParam> photoParams=null;
        if(param.getStatus()==1){
            photoParams = photographService.totalHuman(param);
            if(photoParams==null)
                return ActionResult.ok();
            for (int i=0;i<photoParams.size();i++)
                photoParams.get(i).setOrderId(i+1);
        }else if(param.getStatus()==2){
            photoParams = photographService.getworks(param);
            if(photoParams==null)
                return ActionResult.ok();
            for (int i=0;i<photoParams.size();i++)
                photoParams.get(i).setOrderId(i+1);
        }
        return ActionResult.okAdmin(photoParams,param);
    }

    @RequestMapping
    public ActionResult del_detail(String id){
        photographService.del_detail(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_work(String id){
        photographService.del_work(id);
        return ActionResult.ok();
    }
}
