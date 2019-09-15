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
 * @Date 2017-11-23  16:01
 * --------------------------------------------------------- *
 */
public class RankGroupData {

    private Integer rank;         // 排名
    private Integer id;
    private String avatar;
    private String groupId;
    private String name;
    private Integer examCount;    // 考试个数
    private Integer userCount;    // 群组人数
    private Integer timesCount;   // 总考试人次( 考试数量 * 群组人数 )
    private Integer questionCount;// 参加考试人的题目总数
    private Integer joinCount;    // 报名人数
    private Integer joinExamCount;// 考试人数
    private Integer rightCount;   // 正确题目数

    private Float joinRate;       // 报名率
    private Float joinExamRate;   // 考试率
    private Float rightRate;      // 得分率

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

    public Integer getExamCount() {
        return examCount;
    }

    public void setExamCount(Integer examCount) {
        this.examCount = examCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getTimesCount() {
        return timesCount;
    }

    public void setTimesCount(Integer timesCount) {
        this.timesCount = timesCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
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

    public Integer getRightCount() {
        return rightCount;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
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

    public Float getRightRate() {
        return rightRate;
    }

    public void setRightRate(Float rightRate) {
        this.rightRate = rightRate;
    }
}
