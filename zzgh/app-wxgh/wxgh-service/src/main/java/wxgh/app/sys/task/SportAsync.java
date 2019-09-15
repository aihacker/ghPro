package wxgh.app.sys.task;

import wxgh.data.entertain.sport.save.UserSport;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/25.
 */
public interface SportAsync {

    void saveSport(Map<String, UserSport> map, Date createTime);

}
