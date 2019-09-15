package wxgh.wx.admin.web.beauty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.TypeUtils;
import wxgh.entity.party.beauty.Work;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.sys.service.weixin.party.beauty.WorkService;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private WorkService workService;

    @RequestMapping
    public void show(Model model, Integer id){
        Work work = workService.getOne(id);
        List<WorkFile> files = work.getWorkFiles();
        if (!TypeUtils.empty(files)) {
            for (int i = 0; i < files.size(); i++) {
                WorkFile f = files.get(i);
                files.get(i).setThumb(PathUtils.getImagePath(f.getThumb()));
                files.get(i).setPath(PathUtils.getImagePath(f.getPath()));
            }
        }

        model.put("work", work);

    }

    @RequestMapping
    public void index(){

    }
}
