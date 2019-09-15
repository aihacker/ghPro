package wxgh.data.pcadmin.nav;

import wxgh.data.pub.NameValue;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */
public class NavValue extends NameValue {

    private List<NameValue> navs;

    public List<NameValue> getNavs() {
        return navs;
    }

    public void setNavs(List<NameValue> navs) {
        this.navs = navs;
    }
}
