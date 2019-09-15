package wxgh.sys.service.weixin.pub.carousel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.common.FileInfo;
import wxgh.entity.work.Carousel;
import wxgh.param.pub.CarouselParam;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
@Service
@Transactional(readOnly = true)
public class CarouselService {

    public List<FileInfo> listCarousel(CarouselParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("work_carousel c")
                .field("c.brief_info as filename")
                .sys_file("c.img")
                .order("c.sort_id");
        if(param.getType() != null){
            sql.where("c.type = :type");
        }
        if(param.getDisplay() != null){
            sql.where("c.display = :display");
        }
        return pubDao.queryList(sql.build().sql(), param, FileInfo.class);
    }

    public Carousel getCarousel(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("work_carousel c")
                .field("c.*")
                .sys_file("c.img")
                .where("c.id = ?")
                .build();
        return pubDao.query(Carousel.class,sql.sql(),id);
    }

    @Autowired
    private PubDao pubDao;
}
