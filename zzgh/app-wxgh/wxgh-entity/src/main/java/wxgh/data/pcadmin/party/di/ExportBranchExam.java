package wxgh.data.pcadmin.party.di;


/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-18  11:30
 * --------------------------------------------------------- *
 */
public class ExportBranchExam {

    private Integer id;
    private String name;
    private Integer finishExamCount;   // 完成考试
    private Integer userCount;
    private Integer notJoinExamCount;
    private String rate;

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

    public Integer getFinishExamCount() {
        return finishExamCount;
    }

    public void setFinishExamCount(Integer finishExamCount) {
        this.finishExamCount = finishExamCount;
    }



    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getNotJoinExamCount() {
        return notJoinExamCount;
    }

    public void setNotJoinExamCount(Integer notJoinExamCount) {
        this.notJoinExamCount = notJoinExamCount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
