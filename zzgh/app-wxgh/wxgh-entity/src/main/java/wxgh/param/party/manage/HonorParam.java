package wxgh.param.party.manage;

import pub.dao.page.Page;

import java.util.Date;

public class HonorParam extends Page {
    private Integer id;
    private Integer grade;
    private String name;
    private String honor;

    private String belong;
    private Integer branch_id;
    private Integer party_id;
    private Integer get_time;
    private Integer type;
    private String gradeName;
    private String typeName;
    private String partyName;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Integer getParty_id() {
        return party_id;
    }

    public void setParty_id(Integer party_id) {
        this.party_id = party_id;
    }

    public Integer getGet_time() {
        return get_time;
    }

    public void setGet_time(Integer get_time) {
        this.get_time = get_time;
    }
}
