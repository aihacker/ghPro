package wxgh.entity.four;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ✔ on 2016/11/25.
 */
@Entity
@Table(name = "t_four_repair")
public class FourRepair implements Serializable {
    
    private Integer id;
    private Integer repairId;
    private Date addTime;
    private String reson;
    private Float price;
    private Integer fourId;

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Column(name = "reson")
    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    @Column(name = "price")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column(name = "four_id")
    public Integer getFourId() {
        return fourId;
    }

    public void setFourId(Integer fourId) {
        this.fourId = fourId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "repair_id")
    public Integer getRepairId() {
        return repairId;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
