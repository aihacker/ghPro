package wxgh.entity.entertain.place;

/**
 * @Author xlkai
 * @Version 2017/2/18
 */
public class Rule {

    public static final String TYPE_DAY = "day";
    public static final String TYPE_WEEK = "week";
    public static final String TYPE_MONTH = "month";

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
