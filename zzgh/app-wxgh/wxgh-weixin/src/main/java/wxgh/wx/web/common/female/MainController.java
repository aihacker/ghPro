package wxgh.wx.web.common.female;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.common.female.FemaleLessonData;
import wxgh.param.common.female.QueryFemaleLesson;
import wxgh.sys.service.weixin.common.female.FemaleLessonService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:16
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private FemaleLessonService femaleLessonService;

    @RequestMapping
    public void index(Model model) throws UnsupportedEncodingException {
        QueryFemaleLesson queryFemaleLesson = new QueryFemaleLesson();
        queryFemaleLesson.setAll(false);
        List<FemaleLessonData> femaleLessonList = femaleLessonService.getDatas(queryFemaleLesson);
        for (FemaleLessonData femaleLesson: femaleLessonList){
            femaleLesson.setName(URLDecoder.decode(femaleLesson.getName(), "UTF-8"));
            femaleLesson.setContent(URLDecoder.decode(femaleLesson.getContent(), "UTF-8"));
//            femaleLesson.setCover(PathUtils.getImagePath(femaleLesson.getCover()));
        }
        model.addAttribute("lesson", femaleLessonList);
    }
    
}

