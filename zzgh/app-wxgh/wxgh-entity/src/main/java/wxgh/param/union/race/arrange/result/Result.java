package wxgh.param.union.race.arrange.result;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public class Result {

    private List<String> address;
    private List<ResultDay> resultDays;

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<ResultDay> getResultDays() {
        return resultDays;
    }

    public void setResultDays(List<ResultDay> resultDays) {
        this.resultDays = resultDays;
    }
}
