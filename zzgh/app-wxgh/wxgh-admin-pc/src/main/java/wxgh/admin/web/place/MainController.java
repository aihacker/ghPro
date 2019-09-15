package wxgh.admin.web.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.spring.web.mvc.model.Model;
import wxgh.data.pub.NameValue;
import wxgh.entity.entertain.place.Place;
import wxgh.entity.entertain.place.PlaceCate;
import wxgh.entity.entertain.place.PlaceRule;
import wxgh.param.entertain.place.PlaceParam;
import wxgh.sys.service.weixin.entertain.place.PlaceCateService;
import wxgh.sys.service.weixin.entertain.place.PlaceRuleService;
import wxgh.sys.service.weixin.entertain.place.PlaceService;

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
            Place place = placeService.getPlace(placeQuery);

            List<PlaceCate> cates = placeCateService.getPlaceCate(place.getTypeInt());

            List<PlaceCate> allCates = placeCateService.getCates(1);

            //获取rule
            List<PlaceRule> rules = placeRuleService.getRules(null);

            model.put("rules", rules);
            model.put("place", place);
            model.put("cates", cates);
            model.put("allCates", allCates);
        }
    }

    @RequestMapping
    public void yuyue(Model model){
        String sql = "select * from t_place";
        List<Place> places = pubDao.queryList(Place.class,sql);
        model.put("places", places);
    }

    @RequestMapping
    public void close(Model model) {
        String sql = new SQL.SqlBuilder()
                .table("place p")
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
    private PlaceService placeService;

    @Autowired
    private PlaceCateService placeCateService;

    @Autowired
    private PlaceRuleService placeRuleService;
}
