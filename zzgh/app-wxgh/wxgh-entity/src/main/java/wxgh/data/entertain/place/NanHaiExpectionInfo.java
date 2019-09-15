package wxgh.data.entertain.place;

import wxgh.entity.entertain.nanhai.place.NanHaiRuleExpection;
/**
 * @Author xlkai
 * @Version 2017/2/18
 */
public class NanHaiExpectionInfo extends NanHaiRuleExpection {

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
