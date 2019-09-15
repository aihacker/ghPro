package wxgh.app.utils;


import wxgh.param.pub.apply.ApplyResult;

import java.util.Comparator;

/**
 * Created by XDLK on 2016/8/30.
 * <p>
 * Date： 2016/8/30
 */
public class ComparatorDate implements Comparator<ApplyResult> {

    @Override
    public int compare(ApplyResult o1, ApplyResult o2) {
        return o2.getApplyTime().compareTo(o1.getApplyTime());
    }
}
