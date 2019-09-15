package wxgh.data.party.branch;

/**
 * Created by 蔡炳炎 on 2017/9/12.
 */
public class EchartsData {
    private Integer value;
    private String name;

    public EchartsData(){

    }
    public EchartsData(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
