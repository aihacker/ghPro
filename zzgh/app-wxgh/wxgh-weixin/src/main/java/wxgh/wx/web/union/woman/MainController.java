package wxgh.wx.web.union.woman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.common.FileInfo;
import wxgh.param.pub.CarouselParam;
import wxgh.sys.service.weixin.pub.carousel.CarouselService;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index(Model model) {
        CarouselParam param = CarouselParam.woman();
        List<FileInfo> infos = carouselService.listCarousel(param);
        model.put("imgs", infos);
    }

    @Autowired
    private CarouselService carouselService;
}
