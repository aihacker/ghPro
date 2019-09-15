package wxgh.app.utils;


import wxgh.app.session.bean.SeUser;
import wxgh.entity.pub.User;

/**
 *----------------------------------------------------------
 * @Description 对象互相转化
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-19 17:47
 *----------------------------------------------------------
 */
public class ObjectTransUtils {

    public static User SeUserToUser(SeUser user){
        User user1 = new User();
        if(user.getUserid() != null)
            user1.setUserid(user.getUserid());
        if(user.getName() != null)
            user1.setName(user.getName());
        if(user.getMobile() != null)
            user1.setMobile(user.getMobile());
        if(user.getAvatar() != null)
            user1.setAvatar(user.getAvatar());
        return user1;
    }

}
