package wxgh.entity.union.innovation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "t_innovate_team")
public class InnovateTeam implements Serializable {

    private Integer id;
    private String creater;
    private String team;
    private String teamImg;
    private String remark;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "creater")
    public String getCreater() {
        return creater;
    }

    @Column(name = "team")
    public String getTeam() {
        return team;
    }

    @Column(name = "team_img")
    public String getTeamImg() {
        return teamImg;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setTeamImg(String teamImg) {
        this.teamImg = teamImg;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
