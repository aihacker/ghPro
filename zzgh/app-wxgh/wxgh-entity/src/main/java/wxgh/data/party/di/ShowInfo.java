package wxgh.data.party.di;

/**
 * Created by Administrator on 2017/8/3.
 */
public class ShowInfo {

    private Integer id;
    private String name;
    private String content;
    private Boolean endIs; //考试是否已经结束
    private Boolean joinIs; //是否报名
    private Integer exam; //是否考试

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getEndIs() {
        return endIs;
    }

    public void setEndIs(Boolean endIs) {
        this.endIs = endIs;
    }

    public Boolean getJoinIs() {
        return joinIs;
    }

    public void setJoinIs(Boolean joinIs) {
        this.joinIs = joinIs;
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }
}
