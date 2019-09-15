package wxgh.wx.web.pub.apply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.param.common.disease.ApplyQuery;
import wxgh.sys.service.weixin.common.disease.DiseaseService;

/**
 * Created by Administrator on 2016/10/9.
 */
@Controller
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @RequestMapping
    public void index(Model model, Integer id) {
        ApplyQuery query = new ApplyQuery();
        query.setId(id);
        DiseaseApply diseaseApply = diseaseService.getApply(query);

        model.put("apply", diseaseApply);
    }

}
