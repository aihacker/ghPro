package wxgh.data.entertain.place;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/2/22
 */
public class SelectYuyue {

    private List<SelectTime> times;
    private List<SelectSite> sites;

    public List<SelectTime> getTimes() {
        return times;
    }

    public void setTimes(List<SelectTime> times) {
        this.times = times;
    }

    public List<SelectSite> getSites() {
        return sites;
    }

    public void setSites(List<SelectSite> sites) {
        this.sites = sites;
    }
}
