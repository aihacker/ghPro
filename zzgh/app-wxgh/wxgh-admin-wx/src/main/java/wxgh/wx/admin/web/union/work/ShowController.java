package wxgh.wx.admin.web.union.work;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.entity.union.innovation.InnovateShop;
import wxgh.param.union.innovation.QueryInnovateShop;
import wxgh.param.union.innovation.work.WorkUserQuery;
import wxgh.sys.service.weixin.union.innovation.InnovateShopService;
import wxgh.sys.service.weixin.union.innovation.WorkUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
public class ShowController {

    public static final String[] types = {"创新工作坊", "工作室"};

    @RequestMapping
    public void index(Model model, Integer id) {
        QueryInnovateShop query = new QueryInnovateShop();
        query.setId(id);
        InnovateShop shop = innovateShopService.getOne(query);
        shop.setShopTypeName(getType(shop.getShopType()));

        WorkUserQuery userQuery = new WorkUserQuery(id);
        userQuery.setPageIs(true);
        userQuery.setRowsPerPage(4);
        userQuery.setStatus(1);
        userQuery.setWorkType(InnovateApply.TYPE_SHOP);
        List<String> imgs = workUserService.getUserImg(userQuery);
        List<String> newImgs = new ArrayList<>();
        for (String img : imgs) {
            newImgs.add(PathUtils.getImagePath(img));
        }

        model.put("s", shop);
        model.put("imgs", newImgs);
    }

    private String getType(Integer type) {
        String str;
        if (null == type) {
            str = "未知类型";
        } else {
            str = types[type - 1];
        }
        return str;
    }

    @Autowired
    private InnovateShopService innovateShopService;

    @Autowired
    private WorkUserService workUserService;

}
