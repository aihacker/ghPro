package wxgh.data.entertain.place;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/2/22
 */
public class SelectSite {

    private Integer id;
    private String name;
    private Float price;
    private Float score;
    private List<SelectTime> times;

    public List<SelectTime> getTimes() {
        return times;
    }

    public void setTimes(List<SelectTime> times) {
        this.times = times;
    }

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
