package wxgh.admin.web.nanhai.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.spring.web.mvc.model.Model;
import wxgh.data.pub.NameValue;
import wxgh.entity.entertain.nanhai.place.*;
import wxgh.param.entertain.place.PlaceParam;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceCateService;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceRuleService;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceService;


import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class MainController {
    @RequestMapping
    public void cate(){

    }

    @RequestMapping
    public void cateadd(){

    }

    @RequestMapping
    public void list(){

    }

    @RequestMapping
    public void placeedit(Integer id,Model model){
        if (id != null) {
            PlaceParam placeQuery = new PlaceParam();
            placeQuery.setId(id);
            NanHaiPlace place = placeService.getPlace(placeQuery);

            List<NanHaiPlaceCate> cates = placeCateService.getPlaceCate(place.getTypeInt());

            List<NanHaiPlaceCate> allCates = placeCateService.getCates(1);

            List<NanHaiPlaceRule> rules = placeRuleService.getRules(null);
            //获取rule
//            List<PlaceRule> rules = placeRuleService.getRules(null);

            model.put("rules", rules);
            model.put("place", place);
            model.put("cates", cates);
            model.put("allCates", allCates);
        }
    }

    /**
     *
     * @param model
     *  1本周预约  2下周
     */
    @RequestMapping
    public void yuyue(Model model){
        //, Integer week
        String sql = "select * from t_nanhai_place";
        List<NanHaiPlace> places = pubDao.queryList(NanHaiPlace.class, sql);
        model.put("places", places);
        //model.put("week", week);
    }

    @RequestMapping
    public void close(Model model) {
        String sql = new SQL.SqlBuilder()
                .table("nanhai_place p")
                .field("p.title as name,p.id as value")
                .select()
                .build()
                .sql();
        List<NameValue> places = pubDao.queryList(NameValue.class, sql);
        model.put("place", places);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private NanHaiPlaceService placeService;

    @Autowired
    private NanHaiPlaceCateService placeCateService;

    @Autowired
    private NanHaiPlaceRuleService placeRuleService;
}
