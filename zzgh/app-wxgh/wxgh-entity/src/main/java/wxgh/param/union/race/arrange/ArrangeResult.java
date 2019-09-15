package wxgh.param.union.race.arrange;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/5.
 */
public class ArrangeResult {

    private Map<Integer, List<Arrange>> arrangeMap;
    private Integer total; //总场数
    private Integer lunshu; //轮数

    public Integer getLunshu() {
        return lunshu;
    }

    public void setLunshu(Integer lunshu) {
        this.lunshu = lunshu;
    }

    public Map<Integer, List<Arrange>> getArrangeMap() {
        return arrangeMap;
    }

    public void setArrangeMap(Map<Integer, List<Arrange>> arrangeMap) {
        this.arrangeMap = arrangeMap;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
