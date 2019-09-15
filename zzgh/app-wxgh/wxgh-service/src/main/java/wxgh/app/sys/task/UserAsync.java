package wxgh.app.sys.task;

import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;

/**
 * Created by Administrator on 2017/9/25.
 */
public interface UserAsync {

    void updateUser(User user);

    void addUser(User user);

    void delUser(String id);

    void delDept(Integer id);

    void updateDept(Dept dept);
}
