package wxgh.admin.web.union.woman.teach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.web.ServletUtils;
import wxgh.data.union.woman.teach.TeachDetail;
import wxgh.sys.service.weixin.union.woman.TeachService;

/**
 * Created by Administrator on 2017/9/13.
 */
@Controller
public class MainController {

    @Autowired
    private TeachService teachService;

    @RequestMapping
    public void list() {
    }

    @RequestMapping
    public void edit(Model model, Integer id){
        model.put("teach", teachService.get(id));
    }

    @RequestMapping
    public void detail(Model model, Integer id){
        TeachDetail teachDetail = teachService.getDetail(id);
        model.put("teach", teachDetail);
        String html = "";
        if(teachDetail.getPathList() != null && teachDetail.getPathList().size() > 0){
            int size = teachDetail.getPathList().size();
            for (int i = 0; i < size; i++) {
                if(teachDetail.getPathList().get(i) != null && teachDetail.getNameList().get(i) != null){
                    html += "<a target=\"_blank\" href='"+ ServletUtils.getHostAddr()+teachDetail.getPathList().get(i)+"'>"+teachDetail.getNameList().get(i)+"</a>";
                    if(i != size - 1)
                        html += "、";
                }
            }
        }
        model.put("html", html);

    }

}
