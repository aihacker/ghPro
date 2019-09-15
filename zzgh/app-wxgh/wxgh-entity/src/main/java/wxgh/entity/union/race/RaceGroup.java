package wxgh.entity.union.race;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/20.
 */
@Entity
@Table(name = "t_race_group")
public class RaceGroup implements Serializable {

    private Integer id;
    private Integer raceId;
    private Integer numb;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "race_id")
    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    @Column(name = "numb")
    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
