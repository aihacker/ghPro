package wxgh.data.entertain.sport.score;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：15:35
 * version：V1.0
 * Description：
 */
public enum SportScoreType {

    WEEK("每周", "week"),
    MONTH("每月", "month");

    private String name;
    private String key;

    SportScoreType(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
