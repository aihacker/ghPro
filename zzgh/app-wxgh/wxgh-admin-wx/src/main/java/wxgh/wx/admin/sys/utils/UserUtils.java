package wxgh.wx.admin.sys.utils;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/12/19
 */
public class UserUtils {

    public static boolean isInList(List<Integer> deptIds, Integer target) {
        boolean flag;
        flag = deptIds.contains(target);
        return flag;
    }

}
