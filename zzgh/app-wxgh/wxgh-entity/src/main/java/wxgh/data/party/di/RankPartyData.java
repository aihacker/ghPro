package wxgh.data.party.di;


/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-15  10:59
 * --------------------------------------------------------- *
 */
public class RankPartyData {

    private Integer rank;         // 排名
    private Integer id;
    private String avatar;
    private String groupId;
    private String name;
    private Integer userCount;    // 群组人数
    private Integer joinCount;    // 报名人数
    private Integer joinExamCount;// 考试人数
    private Integer notJoinExamCount;// 未考试人数

    private Float joinRate;       // 报名率
    private Float joinExamRate;   // 考试率

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public Integer getJoinExamCount() {
        return joinExamCount;
    }

    public void setJoinExamCount(Integer joinExamCount) {
        this.joinExamCount = joinExamCount;
    }

    public Integer getNotJoinExamCount() {
        return notJoinExamCount;
    }

    public void setNotJoinExamCount(Integer notJoinExamCount) {
        this.notJoinExamCount = notJoinExamCount;
    }

    public Float getJoinRate() {
        return joinRate;
    }

    public void setJoinRate(Float joinRate) {
        this.joinRate = joinRate;
    }

    public Float getJoinExamRate() {
        return joinExamRate;
    }

    public void setJoinExamRate(Float joinExamRate) {
        this.joinExamRate = joinExamRate;
    }
}
