package wxgh.app.utils.suggest;

import pub.web.ServletUtils;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:57
 *----------------------------------------------------------
 */
public class SuggestSeeSession {

    public static final String SESSION = "seggest_see";

    public static boolean getIsSee(Integer id) {
        Object obj = ServletUtils.getSession().getAttribute(SESSION + id);
        if (obj == null) {
            return false;
        } else {
            return (boolean) obj;
        }
    }

    public static void setIsSee(boolean isSee, Integer id) {
        ServletUtils.getSession().setAttribute(SESSION + id, isSee);
    }

}

