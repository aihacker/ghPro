package wxgh.entity.entertain.sport;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：17:21
 * version：V1.0
 * Description：
 */
public class SportActRule implements Serializable {

    private Integer id;
    private String name;
    private String type;
    private Integer score;
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
