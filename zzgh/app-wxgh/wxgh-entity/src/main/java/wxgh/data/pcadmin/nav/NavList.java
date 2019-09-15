package wxgh.data.pcadmin.nav;

import wxgh.entity.admin.Nav;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */
public class NavList extends Nav {

    private List<Nav> navs;

    public List<Nav> getNavs() {
        return navs;
    }

    public void setNavs(List<Nav> navs) {
        this.navs = navs;
    }
}
