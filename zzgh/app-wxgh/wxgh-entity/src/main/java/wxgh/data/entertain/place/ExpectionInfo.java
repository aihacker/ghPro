package wxgh.data.entertain.place;


import wxgh.entity.entertain.place.RuleExpection;

/**
 * @Author xlkai
 * @Version 2017/2/18
 */
public class ExpectionInfo extends RuleExpection {

    private String username;
    private String deptname;
    private String ruleName;
    private Integer ruleType;

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}
