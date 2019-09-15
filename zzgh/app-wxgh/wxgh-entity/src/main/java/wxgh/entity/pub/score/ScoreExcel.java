package wxgh.entity.pub.score;

import java.io.Serializable;

public class ScoreExcel implements Serializable {
    private String id;
    private String username;
    private String dept;
    private String department;
    private String loca_score;
    private String locaPay_score;
    private String gh_score;
    private String ghPay_score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLoca_score() {
        return loca_score;
    }

    public void setLoca_score(String loca_score) {
        this.loca_score = loca_score;
    }

    public String getLocaPay_score() {
        return locaPay_score;
    }

    public void setLocaPay_score(String locaPay_score) {
        this.locaPay_score = locaPay_score;
    }

    public String getGh_score() {
        return gh_score;
    }

    public void setGh_score(String gh_score) {
        this.gh_score = gh_score;
    }

    public String getGhPay_score() {
        return ghPay_score;
    }

    public void setGhPay_score(String ghPay_score) {
        this.ghPay_score = ghPay_score;
    }

    @Override
    public String toString() {
        return "ScoreExcel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", dept='" + dept + '\'' +
                ", department='" + department + '\'' +
                ", loca_score=" + loca_score +
                ", locaPay_score=" + locaPay_score +
                ", gh_score=" + gh_score +
                ", ghPay_score=" + ghPay_score +
                '}';
    }
}
