package wxgh.wx.web.union.innovation.work;


import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.union.innovation.InnovateShop;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.param.union.innovation.QueryInnovateShop;
import wxgh.param.union.innovation.work.WorkResultQuery;
import wxgh.param.union.innovation.work.WorkUserQuery;
import wxgh.sys.service.weixin.union.innovation.InnovateShopService;
import wxgh.sys.service.weixin.union.innovation.WorkResultService;
import wxgh.sys.service.weixin.union.innovation.WorkUserService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *----------------------------------------------------------
 * @Description 工作坊
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-19 09:50
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private WorkResultService workResultService;

    @Autowired
    private WorkUserService workUserService;

    @Autowired
    private InnovateShopService innovateShopService;

    @Autowired
    private PubDao pubDao;

    /**
     * 首页
     * @param model
     * @param query
     * @return
     */
    @RequestMapping
    public void index(Model model, WorkResultQuery query) {
        ActionResult actionResult = ActionResult.ok();
        query.setWorkType(2);
        query.setStatus(1);
        List<WorkResult> workResultList = workResultService.resultList(query);
        if(workResultList != null)
            for (WorkResult workResult : workResultList) {

                String content = workResult.getContent();
                Map map = JsonUtils.parseMap(content, String.class, Object.class);
                ArrayList imgs = (ArrayList) map.get("imgs");
                String img = "";
                if (imgs.size() > 0) {
                    Map map2 = (Map) imgs.get(0);
                    img = (String) map2.get("url");
                }
                workResult.setImgAvatar(img);
            }
        model.put("results",workResultList);
    }

    /**
     * 工作坊详情
     * @param model
     * @param query
     */
    @RequestMapping
    public void detail(Model model, QueryInnovateShop query) throws UnsupportedEncodingException {
        InnovateShop innovateShop = innovateShopService.getOne(query);

        if(innovateShop == null)
            return ;

        model.put("data", innovateShop);
        WorkUserQuery workUserQuery = new WorkUserQuery();
        workUserQuery.setWorkId(innovateShop.getId());
        workUserQuery.setStatus(null);
        workUserQuery.setWorkType(2);
        model.put("users", workUserService.getUsers(workUserQuery));

        String content = innovateShop.getContent();
        Map map = JsonUtils.parseMap(content, String.class, Object.class);
        ArrayList imgs = (ArrayList) map.get("imgs");
        String txt = (String) map.get("txt");
        txt = URLDecoder.decode(txt, "UTF-8");
        List<String> imgList = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            Map map2 = (Map) imgs.get(i);
            String img = (String) map2.get("url");
            imgList.add(img);
        }
        model.put("imgList", imgList);
        model.put("txt", txt);
        model.put("showType", query.getShowType());
    }

}
