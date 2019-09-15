package wxgh.wx.admin.web.common.bbs;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.common.Article;
import wxgh.sys.service.weixin.common.bbs.ArticleService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index(Model model) {
        List<String> counts = articleService.countApply();
        model.put("counts", counts);
    }

    @RequestMapping
    public void show(Model model, Integer id) {
        Article article = articleService.get(id);
        article.setCreateFormatDate(DateUtils.formatDate(article.getCreateTime()));

        model.put("a", article);
    }

    @Autowired
    private ArticleService articleService;

}
