package wxgh.sys.service.weixin.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.party.news.NewsList;
import wxgh.entity.notice.Notice;
import wxgh.entity.notice.NoticeNews;
import wxgh.param.union.innovation.notice.NoticeParam;
import wxgh.param.union.suggest.ListParam;
import wxgh.sys.dao.notice.NoticeDao;

import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-04 15:40
 * ----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private PubDao pubDao;

    public List<Notice> getData(NoticeParam notice) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("n.id, n.title, n.type, n.content, n.add_time as addTime, n.img, f.file_path as cover, n.pid")
                .table("notice n")
                .join("sys_file f", "f.file_id = n.image_id", Join.Type.LEFT)
                .order("n.add_time", Order.Type.DESC);

        // 到期时间
//        notice.setEndTime(new Date());
//        sql.where("n.end_time > :endTime");

        // 主体为团队
//        notice.setSubjectType(Notice.SUBJECT_Team);
//        sql.where("n.subject_type = :subjectType");

        if (notice.getType() != null)
            sql.where("n.type = :type");
        if (notice.getId() != null)
            sql.where("n.id = :id");
        return pubDao.queryPage(sql.select(), notice, Notice.class);
    }

    @Transactional
    public Integer add(Notice notice) {
        noticeDao.save(notice);
        return notice.getId();
    }

    @Transactional
    public void del(Integer id) {
        noticeDao.delete(id);
    }

    public List<NewsList> get_news(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("news_notice n")
                .field("n.id, n.title, n.add_time")
                .sys_file("n.image");
        if (param.getStatus() != null) {
            sql.where("n.type = :status");
        }
        return pubDao.queryPage(sql, param, NewsList.class);
    }

    //详情页面
    public NoticeNews get_detail(Integer id) {
        if(id==null)
            id=1;
        SQL sql = new SQL.SqlBuilder()
                .table("news_notice n")
                .field("n.id,n.title,n.content,n.image,n.link,n.type,n.add_time")
                .where("n.id=?")
                .build();
         return pubDao.query(NoticeNews.class,sql.sql(),id);
    }
}

