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
 * @Date 2017-12-15  14:58
 * --------------------------------------------------------- *
 */
public class CountByBranchData {

    private Integer joinCount;      // 已报名
    private Integer joinExamCount;  // 已完成考试
    private Integer notExamCount;   // 未考试

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

    public Integer getNotExamCount() {
        return notExamCount;
    }

    public void setNotExamCount(Integer notExamCount) {
        this.notExamCount = notExamCount;
    }
}
