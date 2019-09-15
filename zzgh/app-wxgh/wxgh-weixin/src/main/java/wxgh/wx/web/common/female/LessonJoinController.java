package wxgh.wx.web.common.female;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.common.female.FemaleLessonJoin;
import wxgh.param.common.female.QueryFemaleLessonJoin;
import wxgh.sys.service.weixin.common.female.FemaleLessonJoinService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 10:13
 *----------------------------------------------------------
 */@Controller
public class LessonJoinController {

    @Autowired
    private FemaleLessonJoinService femaleLessonJoinService;

    @RequestMapping
    public void index(Model model){

    }

    @RequestMapping
    public ActionResult getListData(Integer indexID, boolean isFirst, boolean isRefresh, Integer fid){
        QueryFemaleLessonJoin queryFemaleLessonJoin = new QueryFemaleLessonJoin();
        queryFemaleLessonJoin.setFid(fid);

        if (!isFirst && !isRefresh){
            queryFemaleLessonJoin.setIndexID(indexID);
        }

        List<FemaleLessonJoin> femaleLessonJoinList = femaleLessonJoinService.getData(queryFemaleLessonJoin);


        return ActionResult.ok(null, femaleLessonJoinList);
    }
    
}

