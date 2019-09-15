package wxgh.wx.admin.web.union.suggest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.wxadmin.suggest.SuggestInfo;
import wxgh.sys.service.wxadmin.union.SuggestService;

/**
 * Created by Administrator on 2017/8/8.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index() {
    }

    @RequestMapping
    public void show(Integer id, Model model) {
        SuggestInfo suggestInfo = suggestService.getSuggest(id);
        model.put("s", suggestInfo);
    }

    @Autowired
    private SuggestService suggestService;
}
