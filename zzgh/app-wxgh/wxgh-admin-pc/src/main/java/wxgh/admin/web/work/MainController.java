package wxgh.admin.web.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.work.Carousel;
import wxgh.sys.service.weixin.pub.carousel.CarouselService;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class MainController {
    @RequestMapping
    public void team(){

    }

    @RequestMapping
    public void inovate(){

    }

    @RequestMapping
    public void honor(){}

    @RequestMapping
    public void honoradd(){}

    @RequestMapping
    public void work(){}

    @RequestMapping
    public void carousel(){}

    @RequestMapping
    public void edit(Model model, Integer id){
        Carousel carousel = carouselService.getCarousel(id);
        model.put("data",carousel);
    }

    @RequestMapping
    public void publicity(){

    }

    @Autowired
    private CarouselService carouselService;
}
