package wxgh.param.party;


import pub.dao.page.Page;

/**
 * Created by Administrator on 2016/12/28.
 */
public class PartyParam extends Page {

    private Integer id;
    private Integer start;
    private Integer num;
    private Integer type;
    private Integer oldId;

    private Integer groupId;//党支部
    private String nation;//民族
    private Integer IsRepublican;//1正式党员，2预备党员
    private String searchKey;//搜索关键字

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getIsRepublican() {
        return IsRepublican;
    }

    public void setIsRepublican(Integer isRepublican) {
        IsRepublican = isRepublican;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
