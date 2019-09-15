package wxgh.data.party.di;

/**
 * Created by Administrator on 2017/7/29.
 */
public class ExamResult {

    private Integer id;
    private String name;
    private Integer right; //正确题数
    private Integer mistake; //错误题数
    private Integer total; //总题数
    private Integer complete;  //完成题数

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

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getMistake() {
        return mistake;
    }

    public void setMistake(Integer mistake) {
        this.mistake = mistake;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }
}
